package me.andyst.monopoly.client;

import me.andyst.monopoly.board.Board;
import me.andyst.monopoly.game.Game;
import me.andyst.monopoly.player.impl.Player;
import me.andyst.monopoly.util.Dice;
import me.andyst.utils.SelfTrimmingStack;

public class Client {
    public static void main(String[] args) {
        Board board = Board.getInstance();
        Dice dice = Dice.getInstance();

        Player p1 = new Player(board, "Andy");
        Player p2 = new Player(board, "Emma");
        Player p3 = new Player(board, "Daddy");
        Player p4 = new Player(board, "Brula");

        Game game = new Game(board, dice, p1, p2, p3, p4);
        SelfTrimmingStack<Player> stack = new SelfTrimmingStack<>(10);
        stack.push(p2);

//        for (int i = 0; i < GameSettings.TURNS; i++) {
//            game.tick();
//        }
//
//        if (GameSettings.PRINT_FINAL_SUMMARY) {
//            game.printPlayerStatus();
//        }
    }
}
