package me.andyst.monopoly.controls;

import me.andyst.monopoly.game.Game;
import me.andyst.monopoly.graphics.DrawingPanel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {
    private final DrawingPanel display;
    private final Game game;

    public KeyListener(DrawingPanel display, Game game) {
        this.display = display;
        this.game = game;
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_RIGHT) {
            game.tick();
        }
    }
}
