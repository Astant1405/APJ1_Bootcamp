package Presentation.Model;

import Domain.GameSession.Game;
import Presentation.Controller.Controller;
import Presentation.View.Dungeon;
import Presentation.View.Information;
import Presentation.View.StartScreen;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            // Отключаем проблемные функции
            System.setProperty("lanterna.disable-stty", "true");

            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
            terminalFactory.setPreferTerminalEmulator(true);

            Terminal terminal = terminalFactory.createTerminal();
            Screen screen = new TerminalScreen(terminal);

            // ЗАПУСКАЕМ ЭКРАН ПРАВИЛЬНО
            screen.startScreen(); // ← ВАЖНО!
            terminal.setCursorVisible(false);
            Game game = new Game();

            startGame(screen, game);

        } catch (Exception e) {
            logger.severe("Ошибка инициализации: " + e.getMessage());
        }
    }

    public static void startGame(Screen screen, Game game){
        StartScreen startScreen = new StartScreen(screen, game);
        startScreen.showStartScreen();

        if (startScreen.isStarted()) {
            String playerName = game.getPlayer().getPlayerName();
            game.getPlayer().setName(playerName);

            Dungeon dungeon = new Dungeon(screen, game);
            Information info = new Information(screen, game);
            Controller controller = new Controller(game, screen, dungeon, info);
            controller.startGame();
        }
    }
}