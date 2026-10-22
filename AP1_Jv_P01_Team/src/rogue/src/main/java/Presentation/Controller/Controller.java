package Presentation.Controller;

import Domain.GameSession.Game;
import Presentation.View.Dungeon;
import Presentation.View.Information;
import Presentation.View.EndScreen;
import Presentation.View.StartScreen;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.googlecode.lanterna.input.KeyStroke;

public class Controller {
    private final Game game;
    private final Screen screen;
    private final Dungeon dungeon;
    private final Information information;
    private static final Logger log = Logger.getLogger(Controller.class.getName());

    public Controller(Game game, Screen screen, Dungeon dungeon, Information information) {
        this.game = game;
        this.screen = screen;
        this.dungeon = dungeon;
        this.information = information;
    }

    public void startGame(){
        try{
            // ИНИЦИАЛИЗАЦИЯ ЭКРАНА
            screen.startScreen();

            while (true) {
                // ОЧИЩАЕМ ЭКРАН
                screen.clear();

                // ОТРИСОВЫВАЕМ ВСЁ
                dungeon.showDungeon();
                information.showInfo();

                // Проверяем условия завершения игры
                if(dungeon.isFinished()) {
                    // Все уровни пройдены - показываем экран победы
                    break;
                }
                if(game.isGameOver()) {
                    // Игрок умер - показываем экран game over
                    break;
                }

                // ОБНОВЛЯЕМ ЭКРАН
                screen.refresh();

                // Обрабатываем ввод
                KeyStroke key = screen.readInput();
                if (key != null) {
                    if (key.getKeyType() == KeyType.Character) {
                        switch (Character.toLowerCase(key.getCharacter())) {
                            case 'w' -> game.processTurn("w");
                            case 's' -> game.processTurn("s");
                            case 'd' -> game.processTurn("d");
                            case 'a' -> game.processTurn("a");
                            case 'h' -> game.processTurn("h");
                            case 'j' -> game.processTurn("j");
                            case 'k' -> game.processTurn("k");
                            case 'e' -> game.processTurn("e");
                        }
                    } else if (key.getKeyType() == KeyType.Escape) {
                        // ESC - просто выходим без показа экрана завершения
                        screen.stopScreen();
                        return; // Немедленный выход
                    }
                }

                // Небольшая пауза
                try { Thread.sleep(50); } catch (InterruptedException e) {}
            }

            // Показываем экран завершения только если игра закончилась не по ESC
            if (game.isGameOver()) {
                EndScreen endScreen = new EndScreen(screen, game);
                endScreen.showGameOver();
            } else if (dungeon.isFinished()) {
                EndScreen endScreen = new EndScreen(screen, game);
                endScreen.showEndScreen();
            }

            screen.stopScreen();

        } catch (Exception e){
            log.log(Level.SEVERE, "Fail in controller: " + e.getMessage(), e);
        }
    }
}