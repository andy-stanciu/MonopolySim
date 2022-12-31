package graphics;

import board.Board;
import lot.IEstate;
import lot.Lot;
import lot.impl.Estate;
import lot.impl.NeighborhoodType;
import util.GameSettings;

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

    private final int fontSize;
    private final String fontName;
    private final Font defaultFont;

    private final int topX;
    private final int topY;
    private final int lotSideCount;
    private final int tileSize;

    private Font font;

    public GraphicsManager(Graphics2D graphics) {
        this.graphics = graphics;

        this.height = GraphicsSettings.WINDOW_SIZE;
        this.width = GraphicsSettings.WINDOW_SIZE;
        this.boardSize = GraphicsSettings.BOARD_SIZE;
        this.lotSize = GraphicsSettings.LOT_SIZE;
        this.colorBandSize = GraphicsSettings.COLOR_BAND_SIZE;
        this.marginSize = GraphicsSettings.MARGIN_SIZE;

        this.fontSize = GraphicsSettings.DEFAULT_FONT_SIZE;
        this.fontName = GraphicsSettings.DEFAULT_FONT;
        this.defaultFont = new Font(fontName, Font.BOLD, fontSize);
        this.font = defaultFont;

        this.topX = (width - boardSize) / 2;
        this.topY = (height - boardSize) / 2;
        this.lotSideCount = (GameSettings.LOTS - 4) / 4;
        this.tileSize = (boardSize - lotSize * 2) / lotSideCount;
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

            } else if (i < 10) { // bottom row
                int heightOffset = 0;
                Lot lot =  board.getLotAt(10 - pos);
                if (lot instanceof IEstate) {
                    drawColorBand(lot, topX + lotSize, topY + boardSize - lotSize, pos, true);
                    heightOffset = colorBandSize;
                }
                drawCenteredString(lot.getFormattedName(), topX + lotSize + tileSize * (pos - 1),
                        topX + lotSize + tileSize * pos, topY + boardSize - lotSize + heightOffset, true);
            } else if (i < 20) { // left side row
                int heightOffset = 0;
                Lot lot =  board.getLotAt(20 - pos);
                if (lot instanceof IEstate) {
                    drawColorBand(lot, topX + lotSize - colorBandSize, topY + lotSize, pos, false);
                    heightOffset = colorBandSize;
                }
                AffineTransform transform = graphics.getTransform();
                graphics.translate(255, 0);
                graphics.rotate(Math.PI / 2);
                drawCenteredString(lot.getFormattedName(), topX + lotSize + tileSize * (pos - 1),
                        topX + lotSize + tileSize * pos, topY + lotSize - marginSize + heightOffset, true);
                graphics.setTransform(transform);
            } else if (i < 30) { // top row
                int heightOffset = 0;
                Lot lot =  board.getLotAt(i);
                if (lot instanceof IEstate) {
                    drawColorBand(lot, topX + lotSize, topY + lotSize - colorBandSize, pos, true);
                    heightOffset = colorBandSize;
                }
                drawCenteredString(lot.getFormattedName(), topX + lotSize + tileSize * (pos - 1),
                        topX + lotSize + tileSize * pos, topY + lotSize - marginSize - heightOffset, false);
            } else { // right side row
                int heightOffset = 0;
                Lot lot =  board.getLotAt(40 - pos);
                if (lot instanceof IEstate) {
                    drawColorBand(lot, topX + boardSize - lotSize, topY + lotSize, 10 - pos, false);
                    heightOffset = colorBandSize;
                }
                AffineTransform transform = graphics.getTransform();
                graphics.translate(0, 800);
                graphics.rotate(-Math.PI / 2);
                drawCenteredString(lot.getFormattedName(), topX + lotSize + tileSize * (pos - 1),
                        topX + lotSize + tileSize * pos, topY + boardSize - lotSize + heightOffset, true);
                graphics.setTransform(transform);
            }
        }
    }

    private void drawCenteredString(String str, int min, int max, int height, boolean isTop) {
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
            } else {
                lineHeight = height - (lineCount - i - 1) * stringHeight;
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
