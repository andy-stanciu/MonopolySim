package me.andyst.monopoly.graphics;

import me.andyst.monopoly.board.Board;
import me.andyst.monopoly.lot.IEstate;
import me.andyst.monopoly.lot.Lot;
import me.andyst.monopoly.lot.impl.Estate;
import me.andyst.monopoly.lot.impl.NeighborhoodType;
import me.andyst.monopoly.player.impl.Player;
import me.andyst.monopoly.util.GameSettings;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class GraphicsManager {
    private final Graphics2D graphics;

    private final int height;
    private final int width;
    private final int boardSize;
    private final int lotSize;
    private final int colorBandSize;
    private final int marginSize;
    private final int playerPositionOffset;

    private final int fontSize;
    private final String fontName;
    private final Font defaultFont;
    private final FontMetrics fontMetrics;

    private final int topX;
    private final int topY;
    private final int lotSideCount;
    private final int tileSize;

    private Font font;

    public GraphicsManager(Graphics2D graphics) {
        this.graphics = graphics;

        this.height = GraphicsSettings.WINDOW_HEIGHT;
        this.width = GraphicsSettings.WINDOW_WIDTH;
        this.boardSize = GraphicsSettings.BOARD_SIZE;
        this.lotSize = GraphicsSettings.LOT_SIZE;
        this.colorBandSize = GraphicsSettings.COLOR_BAND_SIZE;
        this.marginSize = GraphicsSettings.MARGIN_SIZE;
        this.playerPositionOffset = GraphicsSettings.PLAYER_POSITION_OFFSET;

        this.fontSize = GraphicsSettings.DEFAULT_FONT_SIZE;
        this.fontName = GraphicsSettings.DEFAULT_FONT;
        this.defaultFont = new Font(fontName, Font.BOLD, fontSize);

        this.graphics.setFont(defaultFont);
        this.fontMetrics = graphics.getFontMetrics();
        this.font = defaultFont;

        this.topX = (height - boardSize) / 2;
        this.topY = (height - boardSize) / 2;
        this.lotSideCount = (GameSettings.LOTS - 4) / 4;
        this.tileSize = (boardSize - lotSize * 2) / lotSideCount;
    }

    public void erasePlayersAtLot(Board board, int position) {
        drawAtPosition(board, null, position, playerPositionOffset, 0, true);
    }

    public void drawPlayerPosition(Board board, Player p, int location) {
        drawAtPosition(board, p.getName(), p.getPosition(), playerPositionOffset, location, false);
    }

    private void drawAtPosition(Board board, String str, int pos, int offset, int location, boolean erase) {
        int x = 0;
        int y = 0;
        int stringHeight = fontMetrics.getHeight();
        int stringWidth = str != null ? fontMetrics.stringWidth(str) : tileSize;
        int yOffset = location * stringHeight;

        switch (pos) {
            case 0:
                x = boardSize - lotSize;
                y = boardSize + offset + yOffset;
                break;
            case 10:
                y = boardSize + offset + yOffset;
                break;
            case 20:
                y = -offset + stringHeight - yOffset;
                break;
            case 30:
                x = boardSize - lotSize;
                y = -offset + stringHeight - yOffset;
                break;
            default:
                int tile = pos % 10;
                if (pos < 10) {
                    x = lotSize + tileSize * (10 - tile - 1);
                    y = boardSize + offset + yOffset;
                } else if (pos < 20) {
                    x = -offset - stringWidth;
                    y = lotSize + tileSize * (10 - tile - 1) + yOffset;
                } else if (pos < 30) {
                    x = lotSize + tileSize * (tile - 1);
                    y = -offset + stringHeight - yOffset;
                } else {
                    x = boardSize + offset;
                    y = lotSize + tileSize * (tile - 1) + yOffset;
                }
        }
        graphics.translate(topX, topY);
        if (erase) {
            if (pos <= 10) {
                graphics.clearRect(x, y, tileSize, lotSize);
            } else if (pos < 20) {
                graphics.clearRect(x - (lotSize - tileSize), y, lotSize, tileSize);
            } else if (pos <= 30) {
                graphics.clearRect(x, y - lotSize - stringHeight + fontMetrics.getMaxDescent(), tileSize, lotSize);
            } else {
                graphics.clearRect(x, y, lotSize, tileSize);
            }
        } else {
            graphics.drawString(str, x, y);
        }
        graphics.translate(-topX, -topY);
    }

    public void initializeBoard() {
        graphics.drawRect(topX, topY, boardSize, boardSize);

        graphics.drawLine(topX + lotSize, topY, topX + lotSize, topY + boardSize);
        graphics.drawLine(topX, topY + lotSize, topX + boardSize, topY + lotSize);
        graphics.drawLine(topX + boardSize - lotSize, topY, topX + boardSize - lotSize, topY + boardSize);
        graphics.drawLine(topX, topY + boardSize - lotSize, topX + boardSize, topY + boardSize - lotSize);

        drawSideLots(topX, topY, tileSize, lotSideCount, lotSize, true);
        drawSideLots(topX, topY, tileSize, lotSideCount, lotSize, false);
        drawSideLots(topX, topY + boardSize - lotSize, tileSize, lotSideCount, lotSize, true);
        drawSideLots(topX + boardSize - lotSize, topY, tileSize, lotSideCount, lotSize, false);

        graphics.setColor(GraphicsSettings.BOARD_COLOR);
        graphics.fillRect(topX, topY, boardSize, boardSize);
        graphics.setColor(Color.BLACK);
    }

    private void drawColorBand(Lot lot, int x, int y, int pos, boolean horizontal) {
        Estate estate = (Estate)lot;
        Color color = getNeighborhoodColor(estate.getNeighborhood().getType());
        if (horizontal) {
            graphics.setColor(color);
            graphics.fillRect(x + (pos - 1) * tileSize, y, tileSize, colorBandSize);
            graphics.setColor(Color.BLACK);
            graphics.drawRect(x + (pos - 1) * tileSize, y, tileSize, colorBandSize);
        } else {
            graphics.setColor(color);
            graphics.fillRect(x, y + (pos - 1) * tileSize, colorBandSize, tileSize);
            graphics.setColor(Color.BLACK);
            graphics.drawRect(x, y + (pos - 1) * tileSize, colorBandSize, tileSize);
        }
    }

    public void drawLots(Board board) {
        for (int i = 0; i < GameSettings.LOTS; i++) {
            int pos = i % 10;
            if (i == 0 || i == 10 || i == 20 || i == 30) { // corner lot
                Lot lot = board.getLotAt(i);
                int x = topX;
                if (i == 0 || i == 30) x += boardSize - lotSize;
                int y = topY + lotSize / 2;
                if (i == 0 || i == 10) y += boardSize - lotSize;
                drawCenteredStringWithBounds(lot.getFormattedName(), x, x + lotSize, y, false, true);
            } else if (i < 10) { // bottom row
                int heightOffset = 0;
                Lot lot =  board.getLotAt(10 - pos);
                if (lot instanceof IEstate) {
                    drawColorBand(lot, topX + lotSize, topY + boardSize - lotSize, pos, true);
                    heightOffset = colorBandSize;
                }
                drawCenteredStringWithBounds(lot.getFormattedName(), topX + lotSize + tileSize * (pos - 1),
                        topX + lotSize + tileSize * pos, topY + boardSize - lotSize + heightOffset, true, false);
            } else if (i < 20) { // left side row
                int heightOffset = 0;
                Lot lot =  board.getLotAt(20 - pos);
                if (lot instanceof IEstate) {
                    drawColorBand(lot, topX + lotSize - colorBandSize, topY + lotSize, pos, false);
                    heightOffset = colorBandSize;
                }
                AffineTransform transform = graphics.getTransform();
                // TODO: magic number, fix rotation
                graphics.translate(355, 0);
                graphics.rotate(Math.PI / 2);
                drawCenteredStringWithBounds(lot.getFormattedName(), topX + lotSize + tileSize * (pos - 1),
                        topX + lotSize + tileSize * pos, topY + lotSize - marginSize + heightOffset, true, false);
                graphics.setTransform(transform);
            } else if (i < 30) { // top row
                int heightOffset = 0;
                Lot lot =  board.getLotAt(i);
                if (lot instanceof IEstate) {
                    drawColorBand(lot, topX + lotSize, topY + lotSize - colorBandSize, pos, true);
                    heightOffset = colorBandSize;
                }
                drawCenteredStringWithBounds(lot.getFormattedName(), topX + lotSize + tileSize * (pos - 1),
                        topX + lotSize + tileSize * pos, topY + lotSize - marginSize - heightOffset, false, false);
            } else { // right side row
                int heightOffset = 0;
                Lot lot =  board.getLotAt(40 - pos);
                if (lot instanceof IEstate) {
                    drawColorBand(lot, topX + boardSize - lotSize, topY + lotSize, 10 - pos, false);
                    heightOffset = colorBandSize;
                }
                AffineTransform transform = graphics.getTransform();
                // TODO: magic number, fix rotation
                graphics.translate(0, 900);
                graphics.rotate(-Math.PI / 2);
                drawCenteredStringWithBounds(lot.getFormattedName(), topX + lotSize + tileSize * (pos - 1),
                        topX + lotSize + tileSize * pos, topY + boardSize - lotSize + heightOffset, true, false);
                graphics.setTransform(transform);
            }
        }
    }

    private void drawCenteredStringWithBounds(String str, int min, int max, int height, boolean isTop, boolean isCenter) {
        if (!graphics.getFont().equals(defaultFont)) {
            graphics.setFont(defaultFont);
        }
        FontMetrics fontMetrics = graphics.getFontMetrics();

        if (min > max) {
            throw new IllegalArgumentException("Not enough space between min and max to draw a centered string!");
        }

        int tileWidth = max - min;
        String[] lines = str.split("\n");
        boolean decreasedFontSize;
        boolean fits = false;

        while (!fits) {
            decreasedFontSize = false;
            for (String line : lines) {
                if (fontMetrics.stringWidth(line) > tileWidth) {
                    font = new Font(fontName, Font.BOLD, font.getSize() - 1);
                    graphics.setFont(font);
                    fontMetrics = graphics.getFontMetrics();
                    decreasedFontSize = true;
                    break;
                }
            }
            if (!decreasedFontSize) {
                fits = true;
            }
        }

        int lineCount = lines.length;
        int stringHeight = fontMetrics.getHeight();

        for (int i = 0; i < lineCount; i++) {
            String line = lines[i];

            int stringWidth = fontMetrics.stringWidth(line);
            int startingPos = min + (tileWidth - stringWidth) / 2;

            int lineHeight;
            if (isTop) {
                lineHeight = height + (i + 1) * stringHeight;
            } else if (!isCenter) {
                lineHeight = height - (lineCount - i - 1) * stringHeight;
            } else {
                int middle;
                if (lineCount % 2 == 1) {
                    middle = lineCount / 2;
                } else {
                    middle = lineCount / 2 - 1;
                }
                lineHeight = height + (i - middle) * stringHeight;
            }

            graphics.drawString(line, startingPos, lineHeight);
        }
    }

    private void drawSideLots(int x, int y, int tileSize, int lotSideCount, int lotSize, boolean horizontal) {
        if (horizontal) {
            for (int i = 1; i < lotSideCount; i++) {
                graphics.drawLine(x + lotSize + tileSize * i, y, x + lotSize + tileSize * i, y + lotSize);
            }
        } else {
            for (int i = 1; i < lotSideCount; i++) {
                graphics.drawLine(x, y + lotSize + tileSize * i, x + lotSize, y + lotSize + tileSize * i);
            }
        }
    }

    private Color getNeighborhoodColor(NeighborhoodType type) {
        switch (type) {
            case PURPLE:
                return GraphicsSettings.PURPLE;
            case LIGHT_BLUE:
                return GraphicsSettings.LIGHT_BLUE;
            case PINK:
                return GraphicsSettings.PINK;
            case ORANGE:
                return GraphicsSettings.ORANGE;
            case RED:
                return GraphicsSettings.RED;
            case YELLOW:
                return GraphicsSettings.YELLOW;
            case GREEN:
                return GraphicsSettings.GREEN;
            case BLUE:
                return GraphicsSettings.BLUE;
            default:
                return Color.BLACK;
        }
    }
}
