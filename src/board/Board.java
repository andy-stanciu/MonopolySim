package board;

import lot.Lot;
import lot.factory.*;
import lot.impl.Estate;
import util.GameSettings;

public class Board {
    private static Board board;
    private static Lot[] lots;

    public static Board getInstance() {
        if (board == null) {
            board = new Board();
            board.initializeNeighborhoods();
        }
        return board;
    }

    public Board() {
        lots = new Lot[GameSettings.LOTS];
        createBoard();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Lot lot : lots) {
            sb.append(lot);
            sb.append("\n");
        }
        return sb.toString();
    }

    public Lot getLotAt(int position) {
        return lots[position];
    }

    public Estate getEstateAt(int position) {
        try {
            return (Estate)lots[position];
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Lot at position" + position + " is not an estate!");
        }
    }

    private void initializeNeighborhoods() {
        for (Lot lot : lots) {
            if (lot instanceof Estate) {
                ((Estate)lot).getNeighborhood().setEstates(board);
            }
        }
    }

    private void createBoard() {
        lots[0] = EmptyLotFactory.getGo();
        lots[1] = EstateFactory.getMediterranean();
        lots[2] = EmptyLotFactory.getCommunityChest();
        lots[3] = EstateFactory.getBaltic();
        lots[4] = ExpenseFactory.getIncomeTax();
        lots[5] = RailroadFactory.getReading();
        lots[6] = EstateFactory.getOriental();
        lots[7] = EmptyLotFactory.getChance();
        lots[8] = EstateFactory.getVermont();
        lots[9] = EstateFactory.getConnecticut();
        lots[10] = EmptyLotFactory.getVisitingJail();
        lots[11] = EstateFactory.getStCharles();
        lots[12] = UtilityFactory.getElectricCompany();
        lots[13] = EstateFactory.getStates();
        lots[14] = EstateFactory.getVirginia();
        lots[15] = RailroadFactory.getPennsylvania();
        lots[16] = EstateFactory.getStJames();
        lots[17] = EmptyLotFactory.getCommunityChest();
        lots[18] = EstateFactory.getTennessee();
        lots[19] = EstateFactory.getNewYork();
        lots[20] = EmptyLotFactory.getFreeParking();
        lots[21] = EstateFactory.getKentucky();
        lots[22] = EmptyLotFactory.getChance();
        lots[23] = EstateFactory.getIndiana();
        lots[24] = EstateFactory.getIllinois();
        lots[25] = RailroadFactory.getBAndO();
        lots[26] = EstateFactory.getAtlantic();
        lots[27] = EstateFactory.getVentnor();
        lots[28] = UtilityFactory.getWaterWorks();
        lots[29] = EstateFactory.getMarvinGardens();
        lots[30] = EmptyLotFactory.getGoToJail();
        lots[31] = EstateFactory.getPacific();
        lots[32] = EstateFactory.getNoCarolina();
        lots[33] = EmptyLotFactory.getCommunityChest();
        lots[34] = EstateFactory.getPennsylvania();
        lots[35] = RailroadFactory.getShortLine();
        lots[36] = EmptyLotFactory.getChance();
        lots[37] = EstateFactory.getParkPlace();
        lots[38] = ExpenseFactory.getLuxuryTax();
        lots[39] = EstateFactory.getBoardwalk();
    }
}
