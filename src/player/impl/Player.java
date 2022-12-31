package player.impl;

import board.Board;
import lot.Lot;
import lot.impl.*;
import lot.Property;
import player.IPlayer;
import util.Dice;
import util.GameSettings;
import util.Util;

import java.util.*;

public class Player implements IPlayer {
    private final Board board;
    private final String name;
    private final TreeSet<Property> properties;
    private final Set<Property> mortgagedProperties;
    private final Map<NeighborhoodType, Neighborhood> ownedNeighborhoods;

    private int balance;
    private int position;
    private boolean isInJail;
    private int jailTurns;
    private int totalJailVisits;

    private int ownedUtilityCount;
    private int ownedRailroadCount;

    public Player(Board board, String name) {
        this.board = board;
        this.name = name;
        this.properties = new TreeSet<>();
        this.mortgagedProperties = new TreeSet<>();
        this.balance = GameSettings.STARTING_BALANCE;
        this.ownedNeighborhoods = new TreeMap<>();
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public int getPosition() {
        return position;
    }

    public Collection<Neighborhood> getOwnedNeighborhoods() {
        return ownedNeighborhoods.values();
    }

    public TreeSet<Property> getProperties() {
        return properties;
    }

    public Set<Property> getMortgagedProperties() {
        return mortgagedProperties;
    }

    public boolean isInJail() {
        return isInJail;
    }

    /**
     * Decreases this player's balance by the given amount, liquidating as many of the player's
     * assets as necessary if decreasing by the given amount would cause the player to go
     * in debt.
     * @param amount The amount of money to decrease this player's balance by.
     * @return Whether this player's balance was successfully decreased (without going bankrupt).
     */
    public boolean decreaseBalance(int amount) {
        balance -= amount;
        if (balance < 0) {
            return liquidate(balance);
        }
        return true;
    }

    /**
     * Attempts to liquidate as many houses as necessary to overcome the debt (mortgaging
     * properties is to be implemented). Liquidation occurs in descending order of the
     * player's properties, meaning their "least valuable" ones (defined by Property.compareTo)
     * are the first to be liquidated.
     * @param debt The player's debt. If it is greater than zero, nothing happens as no liquidation
     *             is necessary.
     * @return Whether liquidation was successful. Liquidation fails if the player does
     *         not have enough assets (houses) to overcome their debt.
     */
    public boolean liquidate(int debt) {
        while (debt < 0) {
            boolean liquidated = false;
            Iterator<Property> itr = properties.descendingIterator();
            int totalHousesLiquidated = 0;
            String updatedEstates = "";
            boolean overcameDebt = false;
            while (itr.hasNext() && !overcameDebt) {
                Property property = itr.next();
                if (property instanceof Estate) {
                    Estate estate = (Estate)property;
                    if (estate.removeHouse()) {
                        debt += estate.getHouseCost();
                        liquidated = true;
                        overcameDebt = debt >= 0;
                        totalHousesLiquidated++;
                        updatedEstates += updatedEstates.isEmpty() ? estate.getName() : ", " + estate.getName();
                    }
                }
            }
            if (!liquidated) {
                return false;
            }
            if (GameSettings.DEBUG) {
                System.out.println(name + " liquidated " + totalHousesLiquidated + " houses on " + updatedEstates);
            }
        }
        return true;
    }

    /**
     * Increases this player's balance by the given amount.
     * @param amount The amount to add to this player's balance.
     */
    public void increaseBalance(int amount) {
        balance += amount;
    }

    /**
     * Tries to decrease this player's balance, throwing an IllegalStateException if
     * they go bankrupt. Will attempt to liquidate assets if the given amount cannot
     * be afforded only through cash.
     * @param amount The amount of money to decrease this player's balance by.
     * @throws IllegalStateException If this player goes bankrupt.
     */
    public void tryDecreaseBalance(int amount) {
        if (!decreaseBalance(amount)) {
            throw new IllegalStateException(name + " has gone bankrupt!");
        }
    }

    /**
     * Returns whether this player can afford the given cost without liquidating any
     * of their assets.
     * @param cost The cost to check against.
     * @return Whether the player can afford the given cost.
     */
    public boolean canAfford(int cost) {
        return balance >= cost;
    }

    /**
     * Executes a turn for this player given that they are not currently in jail.
     * @param roll The roll of the dice.
     */
    public void turn(int roll) {
        tickPreBehavior();
        move(roll);
        tickPostBehavior(roll);
    }

    /**
     * Moves this player to the appropriate lot depending on the roll of the dice,
     * collecting the "Go" salary if the "Go" lot is passed. If the player lands
     * on the "Go to Jail" lot, the player is sent to jail without collecting
     * the "Go" salary.
     * @param roll The roll of the dice.
     */
    public void move(int roll) {
        int newPosition = position + roll;
        if (newPosition >= GameSettings.LOTS) {
            newPosition %= GameSettings.LOTS;
            balance += GameSettings.GO_SALARY;
        }

        if (newPosition == 30) {
            arrest();
        } else {
            position = newPosition;
        }
    }

    /**
     * Arrests this player, putting them in jail
     */
    public void arrest() {
        isInJail = true;
        this.totalJailVisits++;
        position = 10;
    }

    /**
     * Frees this player from jail.
     */
    public void free() {
        isInJail = false;
        jailTurns = 0;
    }

    /**
     * Called by the game on a player when it is their turn. If this player is in
     * jail, their turn is skipped and are unable to move.
     * @param dice Used to determine a random roll for this player's move.
     */
    public void tick(Dice dice) {
        if (!isInJail || jailTurns == GameSettings.JAIL_TURNS) {
            free();
            turn(dice.roll());
        } else {
            jailTurns++;
        }
    }

    /**
     * Ticks this player's pre-behavior (behavior occurring before rolling the dice on
     * this player's turn). This includes trying to buy houses.
     */
    private void tickPreBehavior() {
        tryBuyHouses();
    }

    /**
     * Tries to buy as many houses as affordable on all estates that are eligible for houses.
     * An estate is eligible for houses if this player owns all estates under its
     * neighborhood. Will buy houses until either no money is remaining or all estates have
     * reached maximum house capacity (5). Starts by buying houses on the cheapest estates.
     */
    private void tryBuyHouses() {
        for (NeighborhoodType key : ownedNeighborhoods.keySet()) {
            Neighborhood neighborhood = ownedNeighborhoods.get(key);
            if (neighborhood.getSize() == neighborhood.getOwned()) {
                Set<Estate> estates = neighborhood.getEstates();
                int houseCost = neighborhood.getHouseCost();

                int fullEstates = 0;
                int totalHousesBought = 0;
                while (canAfford(houseCost) && fullEstates < neighborhood.getSize()) {
                    fullEstates = 0;
                    Iterator<Estate> itr = estates.iterator();
                    while (itr.hasNext() && canAfford(houseCost)) {
                        Estate estate = itr.next();
                        if (estate.canAddHouse()) {
                            decreaseBalance(houseCost);
                            estate.addHouse();
                            totalHousesBought++;
                        } else {
                            fullEstates++;
                        }
                    }
                }

                if (GameSettings.DEBUG && totalHousesBought > 0) {
                    System.out.println(name + " bought " + totalHousesBought + " houses on " +
                            neighborhood + " for $" + totalHousesBought * houseCost);
                }
            }
        }
    }

    /**
     * Ticks this player's post-behavior (behavior occurring after rolling the dice
     * on this player's turn). This includes paying property rent, trying to
     * purchase a property, or paying a certain expense.
     * @param roll Roll of the dice.
     */
    private void tickPostBehavior(int roll) {
        Lot lot = board.getLotAt(position);
        if (lot instanceof Property) {
            Property property = (Property)lot;
            if (property.isOwned() && !property.getOwner().equals(this)) {
                payRent(property, roll);
            } else if (!property.isOwned()) {
                tryPurchaseProperty(property);
            }
        } else if (lot instanceof Expense) {
            Expense expense = (Expense)lot;
            tryDecreaseBalance(expense.getCharge());
        }
    }

    /**
     * Pays the appropriate rent to the player that owns the given property. If the player
     * does not have enough liquid money to afford the rent, the player will attempt to
     * liquidate their assets using <code>liquidate(int debt)</code>, which may throw an
     * IllegalStateException if the player goes bankrupt.
     * @param property The property that this player landed on.
     * @param roll The roll of the dice (necessary to determine utility rent).
     * @throws ClassCastException If an illegal property was provided.
     * @throws IllegalStateException If this player goes bankrupt.
     */
    private void payRent(Property property, int roll) {
        Player owner = property.getOwner();
        int rent;

        if (property instanceof Estate) {
            Estate estate = (Estate)property;
            Neighborhood neighborhood = estate.getNeighborhood();
            rent = estate.getRent();
            if (estate.getHouses() == 0 && neighborhood.getOwned() == neighborhood.getSize()) {
                rent *= GameSettings.UNIMPROVED_NEIGHBORHOOD_RENT_MULTIPLIER;
            }
        } else if (property instanceof Railroad) {
            Railroad railroad = (Railroad)property;
            rent = railroad.getRent(owner.ownedRailroadCount);
        } else if (property instanceof Utility) {
            Utility utility = (Utility)property;
            rent = utility.getRent(owner.ownedUtilityCount, roll);
        } else {
            throw new ClassCastException("Property " + property + " could not be cast!");
        }

        if (GameSettings.DEBUG) {
            System.out.println(name + " (Balance: $" + balance + ") was charged $" + rent + " by " +
                    owner.name + " for landing on " + property.getName());
        }

        tryDecreaseBalance(rent);
        owner.increaseBalance(rent);

        if (GameSettings.DEBUG) {
            System.out.println(name + " sent $" + rent + " to " + owner.name + " for landing on " + property.getName());
        }
    }

    /**
     * Tries to purchase a property (estate, railroad, or utility). Will only buy if player is
     * able to afford without liquidation. Always buys railroads and utilities if affordable.
     * Only buys estates if they belong to an owned neighborhood or if less than
     * GameSettings.MAX_NEIGHBORHOODS_PER_PLAYER neighborhoods are owned.
     * @param property Property to purchase.
     */
    private void tryPurchaseProperty(Property property) {
        if (canAfford(property.getValue())) {
            boolean purchasedProperty = true;
            if (property instanceof Estate) {
                Estate estate = (Estate) property;
                Neighborhood neighborhood = estate.getNeighborhood();
                purchasedProperty = shouldBuyEstate(neighborhood);

                if (purchasedProperty) {
                    NeighborhoodType type = neighborhood.getType();
                    Neighborhood ownedNeighborhood = ownedNeighborhoods.get(type);
                    if (ownedNeighborhood == null) {
                        neighborhood.setOwned(1);
                        ownedNeighborhoods.put(type, neighborhood);
                    } else {
                        int owned = ownedNeighborhood.getOwned();
                        for (Estate other : neighborhood.getEstates()) {
                            if (this.equals(other.getOwner())) {
                                other.getNeighborhood().setOwned(owned + 1);
                            }
                        }
                        neighborhood.setOwned(owned + 1);
                    }
                }
            } else if (property instanceof Railroad) {
                ownedRailroadCount++;
            } else if (property instanceof Utility) {
                ownedUtilityCount++;
            }

            if (purchasedProperty) {
                decreaseBalance(property.getValue());
                property.setOwner(this);
                properties.add(property);

                if (GameSettings.DEBUG) {
                    System.out.println(name + " purchased " + property);
                }
            }
        }
    }

    /**
     * Determines whether an estate is worth buying (given that the player has enough money).
     * An estate is currently defined as worth buying if the player already owns another
     * estate in the same neighborhood, or the player owns less than a specified number of
     * neighborhood groups in total (GameSettings.MAX_NEIGHBORHOODS_PER_PLAYER).
     * @param neighborhood The neighborhood that the estate in question belongs to.
     * @return Whether the estate is worth buying.
     */
    private boolean shouldBuyEstate(Neighborhood neighborhood) {
        Set<NeighborhoodType> neighborhoods = ownedNeighborhoods.keySet();
        for (NeighborhoodType key : neighborhoods) {
            if (neighborhood.equals(ownedNeighborhoods.get(key))) {
                return true;
            }
        }
        return neighborhoods.size() < GameSettings.MAX_NEIGHBORHOODS_PER_PLAYER;
    }

    /**
     * Returns a formatted String representation of this player.
     * @return A formatted String representation of this player.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("\n");
        sb.append("Balance: $").append(balance).append("\n");
        sb.append("Position: ").append(board.getLotAt(position)).append("\n");
        sb.append("Properties: ").append(Util.formatCollection(properties)).append("\n");
        sb.append("Neighborhoods: ").append(Util.formatCollection(getOwnedNeighborhoods())).append("\n");
        sb.append("Mortgaged Properties: ").append(Util.formatCollection(mortgagedProperties)).append("\n");
        sb.append("Total Jail Visits: ").append(totalJailVisits).append("\n");
        if (isInJail) sb.append("In Jail").append("\n");
        return sb.toString();
    }

    public boolean equals(Object other) {
        if (other instanceof IPlayer) {
            IPlayer player = (IPlayer)other;
            return name.equals(player.getName());
        }
        return false;
    }
}