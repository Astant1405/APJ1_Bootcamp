package Presentation.View;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.gui2.*;
import Domain.GameSession.Game;
import com.googlecode.lanterna.screen.Screen;

import java.util.Collections;

public class EndScreen {

    private final Screen screen;
    private final Game game;

    // Изменяем конструктор для приема имени игрока
    public EndScreen(Screen screen, Game game) {
        this.screen = screen;
        this.game = game;
    }

    public void showGameOver(){
        TextColor textColor = TextColor.ANSI.WHITE_BRIGHT;
        TextColor backgroundColor = TextColor.ANSI.BLACK;

        MultiWindowTextGUI gui = new MultiWindowTextGUI(
                screen,
                new DefaultWindowManager(),
                new EmptySpace(backgroundColor)
        );

        BasicWindow window = new BasicWindow();
        window.setHints(Collections.singleton(Window.Hint.FULL_SCREEN));
        window.setTheme(new SimpleTheme(textColor, backgroundColor));

        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        mainPanel.setTheme(new SimpleTheme(textColor, backgroundColor));

        addCenteredContent(mainPanel, "                REST", textColor);
        addCenteredContent(mainPanel, "                 IN", textColor);
        addCenteredContent(mainPanel, "               PEACE", textColor);
        addCenteredContent(mainPanel, "               " + game.getPlayer().getPlayerName(), textColor);
        addCenteredContent(mainPanel, "            killed by a", textColor);
        addCenteredContent(mainPanel, "              mod_name", textColor);
        addCenteredContent(mainPanel, "              " + game.getPlayer().getGold() + " Au", textColor);
        addCenteredContent(mainPanel, "                1983", textColor);
        addCenteredContent(mainPanel, "            *    *   *", TextColor.ANSI.RED);
        addCenteredContent(mainPanel, "__\\/(\\/)/(\\/ \\(//)\\)\\/(//)\\)//(\\__", TextColor.ANSI.GREEN);

        window.setComponent(mainPanel);
        gui.addWindowAndWait(window);

    }


    public void showEndScreen(){
        TextColor textColor = TextColor.ANSI.WHITE_BRIGHT;
        TextColor backgroundColor = TextColor.ANSI.BLACK;

        MultiWindowTextGUI gui = new MultiWindowTextGUI(
                screen,
                new DefaultWindowManager(),
                new EmptySpace(backgroundColor)
        );

        BasicWindow window = new BasicWindow();
        window.setHints(Collections.singleton(Window.Hint.FULL_SCREEN));
        window.setTheme(new SimpleTheme(textColor, backgroundColor));

        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        mainPanel.setTheme(new SimpleTheme(textColor, backgroundColor));

        addCenteredContent(mainPanel, "              YOU WIN", textColor);
        addCenteredContent(mainPanel, "            *    *   *", TextColor.ANSI.RED);
        addCenteredContent(mainPanel, "__\\/(\\/)/(\\/ \\(//)\\)\\/(//)\\)//(\\__", TextColor.ANSI.GREEN);

        window.setComponent(mainPanel);

        gui.addWindowAndWait(window);

    }

    private static void addCenteredContent(Panel panel, String text, TextColor color) {
        Panel wrapper = new Panel(new LinearLayout(Direction.HORIZONTAL));
        wrapper.addComponent(new EmptySpace(new TerminalSize(1, 1)));

        Label label = new Label(text);
        label.setForegroundColor(color);
        wrapper.addComponent(label.setLayoutData(
                LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));

        wrapper.addComponent(new EmptySpace(new TerminalSize(1, 1)));
        panel.addComponent(wrapper);
        panel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
    }

}