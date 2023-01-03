package me.andyst.monopoly.game;

import me.andyst.monopoly.graphics.DrawingPanel;
import me.andyst.monopoly.graphics.GraphicsManager;
import me.andyst.monopoly.graphics.GraphicsSettings;
import me.andyst.monopoly.lot.Lot;
import me.andyst.monopoly.board.Board;
import me.andyst.monopoly.controls.KeyListener;
import me.andyst.monopoly.player.impl.Player;
import me.andyst.monopoly.util.Dice;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class Game {
    private final Queue<Player> players;
    private final Board board;
    private final Dice dice;
    private final DrawingPanel display;
    private final Graphics2D graphics;
    private final GraphicsManager graphicsManager;
    private final KeyListener keyListener;

    public Game(Board board, Dice dice, int playerCount) {
        this(board, dice, new LinkedList<>());
        for (int i = 0; i < playerCount; i++) {
            Player p = new Player(board, "p" + (i + 1));
            initPlayerPosition(p);
        }
    }

    public Game(Board board, Dice dice, Player... players) {
        this(board, dice, new LinkedList<>());
        for (Player p : players) {
            initPlayerPosition(p);
        }
    }

    public Game(Board board, Dice dice, Queue<Player> players) {
        this.dice = dice;
        this.board = board;
        this.players = players;

        this.display = new DrawingPanel(GraphicsSettings.WINDOW_WIDTH, GraphicsSettings.WINDOW_HEIGHT);
        this.graphics = this.display.getGraphics();
        this.graphics.setBackground(Color.WHITE);
        this.graphicsManager = new GraphicsManager(this.graphics);

        this.graphicsManager.initializeBoard();
        this.graphicsManager.drawLots(board);

        this.keyListener = new KeyListener(this.display, this);
        this.display.addKeyListener(this.keyListener);
    }

    private void initPlayerPosition(Player p) {
        this.players.add(p);
        Lot first = board.getLotAt(0);
        first.incrementPlayerCount();
        this.graphicsManager.drawPlayerPosition(board, p, first.getPlayerCount());
    }

    public void tickAll() {
        for (int i = 0; i < this.players.size(); i++) {
            tick();
        }
    }

    public void tick() {
        Player p = players.remove();
        int previous = p.tick(dice);
        if (previous == -1) {
            players.add(p);
            return;
        }

        graphicsManager.erasePlayersAtLot(board, previous);
        Lot lot = board.getLotAt(p.getPosition());
        graphicsManager.drawPlayerPosition(board, p, lot.getPlayerCount());

        Lot previousLot = board.getLotAt(previous);
        while (previousLot.getPlayerCount() > 0) {
            previousLot.decrementPlayerCount();
        }

        for (int i = 0; i < players.size(); i++) {
            Player other = players.remove();
            if (other.getPosition() == previous) {
                graphicsManager.drawPlayerPosition(board, other, previousLot.getPlayerCount() + 1);
                previousLot.incrementPlayerCount();
            }
            players.add(other);
        }

        players.add(p);
    }

    public void printPlayerStatus() {
        for (Player p : this.players) {
            System.out.println(p);
        }
    }
}
