package client;

import board.Board;
import game.Game;
import player.impl.Player;
import util.Dice;
import util.GameSettings;

public class Client {
    public static void main(String[] args) {
        Board board = Board.getInstance();
        Dice dice = Dice.getInstance();

        Player p1 = new Player(board, "Andy");
        Player p2 = new Player(board, "Emma");
        Player p3 = new Player(board, "Daddy");
        Player p4 = new Player(board, "Brula");

        Game game = new Game(board, dice, p1, p2, p3, p4);

        for (int i = 0; i < GameSettings.TURNS; i++) {
            game.tick();
        }

        if (GameSettings.PRINT_FINAL_SUMMARY) {
            game.printPlayerStatus();
        }
    }
}
