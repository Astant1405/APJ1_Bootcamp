package Presentation.View;

import Domain.Character.Player.BagInterface;
import Domain.Item.ItemType;
import Domain.GameSession.Game;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor.ANSI;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Information {
    private final Screen screen;
    private final Game game;
    private static final Logger logger = Logger.getLogger(Information.class.getName());

    public Information(Screen screen, Game game) {
        this.screen = screen;
        this.game = game;
    }

    public void showInfo(){
        try {
            TerminalSize terminalSize = screen.getTerminalSize();
            int screenHeight = terminalSize.getRows();

            TextGraphics graphics = screen.newTextGraphics();

            int infoY = screenHeight - 2;

            graphics.setBackgroundColor(ANSI.BLACK);
            for (int x = 0; x < terminalSize.getColumns(); x++) {
                graphics.setCharacter(x, infoY, ' ');
            }

            // Статистика
            String stats = String.format("L%d HP:%d/%d Str:%d Gold:%d",
                    game.getLevel().getLevel(),
                    game.getPlayer().getHealth(),
                    game.getPlayer().getMaxHealth(),
                    game.getPlayer().getStrength(),
                    game.getPlayer().getGold()
            );

            graphics.setForegroundColor(ANSI.YELLOW);
            graphics.putString(2, infoY, stats);

            // Предметы в рюкзаке
            BagInterface bag = game.getPlayer().getBag();
            long weapons = bag.getItems().stream().filter(i -> ItemType.WEAPON.equals(i.getItemType())).count();
            long food = bag.getItems().stream().filter(i -> ItemType.FOOD.equals(i.getItemType())).count();
            long elixirs = bag.getItems().stream().filter(i -> ItemType.ELIXIR.equals(i.getItemType())).count();
            long scrolls = bag.getItems().stream().filter(i -> ItemType.SCROLL.equals(i.getItemType())).count();

            String items = String.format("W:%d F:%d E:%d S:%d", weapons, food, elixirs, scrolls);
            graphics.putString(40, infoY, items);

            // Управление
            //String controls = "WASD-move  H-weapon  J-food  K-elixir  E-scroll  ESC-exit";
            //graphics.putString(60, infoY, controls);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка в отрисовке информации: " + e.getMessage(), e);
        }
    }
}