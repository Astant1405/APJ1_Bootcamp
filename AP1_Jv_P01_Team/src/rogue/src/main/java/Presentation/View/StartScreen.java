package Presentation.View;

import Domain.GameSession.Game;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;

public class StartScreen {
    private final Screen screen;
    private boolean isStarted = false;
    private final Game game;


    public StartScreen(Screen screen, Game game) {
        this.screen = screen;
        this.game = game;
    }

    public void showStartScreen() {
        try {
            screen.clear();
            TextGraphics graphics = screen.newTextGraphics();

            TerminalSize terminalSize = screen.getTerminalSize();
            int screenWidth = terminalSize.getColumns();
            int screenHeight = terminalSize.getRows();
            int centerX = screenWidth / 2;

            graphics.setBackgroundColor(TextColor.ANSI.BLACK);
            graphics.fill(' ');

            drawDoubleBorder(graphics, 0, 0, screenWidth, screenHeight, TextColor.ANSI.YELLOW);

            int currentY = 3;

            graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            centerText(graphics, "ROGUE: The Adventure Game", currentY++, centerX);
            currentY++;

            graphics.setForegroundColor(TextColor.ANSI.MAGENTA);
            centerText(graphics, "The game of Rogue was originated by:", currentY++, centerX);

            graphics.setForegroundColor(TextColor.ANSI.WHITE);
            centerText(graphics, "Michael C. Toy", currentY++, centerX);
            centerText(graphics, "and", currentY++, centerX);
            centerText(graphics, "Kenneth C.R.C. Arnold", currentY++, centerX);
            currentY++;

            graphics.setForegroundColor(TextColor.ANSI.MAGENTA);
            centerText(graphics, "Adapted for the IBM PC by:", currentY++, centerX);

            graphics.setForegroundColor(TextColor.ANSI.WHITE);
            centerText(graphics, "Jon Lane", currentY++, centerX);
            currentY++;

            graphics.setForegroundColor(TextColor.ANSI.MAGENTA);
            centerText(graphics, "Significant design contributions by:", currentY++, centerX);

            graphics.setForegroundColor(TextColor.ANSI.WHITE);
            centerText(graphics, "Glenn Wichman and scores of others", currentY++, centerX);
            currentY++;

            graphics.setForegroundColor(TextColor.ANSI.YELLOW);
            centerText(graphics, "(C) Copyright 1983", currentY++, centerX);

            graphics.setForegroundColor(TextColor.ANSI.WHITE);
            centerText(graphics, "Artificial Intelligence Design", currentY++, centerX);

            graphics.setForegroundColor(TextColor.ANSI.YELLOW);
            centerText(graphics, "All Rights Reserved", currentY++, centerX);

            int inputBoxWidth = 38;
            int inputBoxHeight = 3;
            int inputBoxX = centerX - inputBoxWidth / 2;
            int inputBoxY = Math.min(currentY + 4, screenHeight - inputBoxHeight - 2);

            drawDoubleBorder(graphics, 0, inputBoxY + 2, screenWidth, screenHeight, TextColor.ANSI.YELLOW);

            graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            graphics.putString( 2, inputBoxY + inputBoxHeight, "Rogue's Name?");

            screen.setCursorPosition(null);

            int inputStartY = inputBoxY + 1;

            updateInputDisplay(graphics, 2, inputBoxY + inputBoxHeight + 2, "", inputBoxX, inputStartY, inputBoxWidth);

            screen.refresh();

            readPlayerName(graphics, 17, inputBoxY + 3, inputBoxX, inputStartY, inputBoxWidth);

            screen.setCursorPosition(null);
            screen.refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawDoubleBorder(TextGraphics graphics, int startX, int startY, int width, int height, TextColor color) {
        graphics.setForegroundColor(color);

        // Углы
        graphics.setCharacter(startX, startY, '╔');
        graphics.setCharacter(startX + width - 1, startY, '╗');
        graphics.setCharacter(startX, startY + height - 1, '╚');
        graphics.setCharacter(startX + width - 1, startY + height - 1, '╝');

        // Горизонтальные линии
        for (int x = startX + 1; x < startX + width - 1; x++) {
            graphics.setCharacter(x, startY, '═');
            graphics.setCharacter(x, startY + height - 1, '═');
        }

        // Вертикальные линии
        for (int y = startY + 1; y < startY + height - 1; y++) {
            graphics.setCharacter(startX, y, '║');
            graphics.setCharacter(startX + width - 1, y, '║');
        }
    }

    private void centerText(TextGraphics graphics, String text, int y, int centerX) {
        int x = centerX - text.length() / 2;
        graphics.putString(x, y, text);
    }

    private void readPlayerName(TextGraphics graphics, int startX, int startY, int boxX, int boxY, int boxWidth) throws IOException {
        StringBuilder nameBuilder = new StringBuilder();
        boolean reading = true;
        while (reading) {
            KeyStroke key = screen.readInput();
            if (key != null) {
                if (key.getKeyType() == KeyType.Character) {
                    if (nameBuilder.length() < 12) {
                        nameBuilder.append(key.getCharacter());
                        updateInputDisplay(graphics, startX, startY, nameBuilder.toString(), boxX, startY, boxWidth);
                    }
                } else if (key.getKeyType() == KeyType.Enter) {
                    game.getPlayer().setPlayerName(nameBuilder.toString().trim());
                    if (!game.getPlayer().getPlayerName().isEmpty()) {
                        isStarted = true;
                        reading = false;
                    }
                } else if (key.getKeyType() == KeyType.Backspace) {
                    if (nameBuilder.length() > 0) {
                        nameBuilder.setLength(nameBuilder.length() - 1);
                        updateInputDisplay(graphics, startX, startY, nameBuilder.toString(), boxX, startY, boxWidth);
                    }
                } else if (key.getKeyType() == KeyType.Escape) {
                    reading = false;
                }
            }
        }
        updateInputDisplay(graphics, startX, startY, nameBuilder.toString(), boxX, startY, boxWidth); // последнее отображение без курсора
    }

    private void updateInputDisplay(TextGraphics graphics, int startX, int startY, String text,
                                    int boxX, int boxY, int boxWidth) throws IOException {
        graphics.setBackgroundColor(TextColor.ANSI.BLACK);
        for (int i = 0; i < 14; i++) {
            graphics.setCharacter(startX + i, startY, ' ');
        }
        graphics.setForegroundColor(TextColor.ANSI.WHITE);
        graphics.putString(startX, startY, text + "_");
        screen.setCursorPosition(null);
        screen.refresh();
    }

    public boolean isStarted() {
        return isStarted;
    }
}
