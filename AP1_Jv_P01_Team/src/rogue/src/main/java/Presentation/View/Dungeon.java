package Presentation.View;

import Domain.Character.Enemy.Enemy;
import Domain.Character.Enemy.Mimik;
import Domain.Dungeon.Corridor;
import Domain.Dungeon.Room;
import Domain.Item.Item;
import Domain.GameSession.Game;
import Domain.GameSession.GameSettings;
import Domain.Item.ItemInterface;
import Domain.Navigator.Position;
import Domain.Navigator.PositionInterface;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.TextColor.ANSI;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dungeon {
    private final Screen screen;
    private final Game game;
    private int level;
    private boolean isFinished = false;
    List<Room> visitedRooms = new ArrayList<>();
    List<Corridor> visitedCorridors = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(Dungeon.class.getName());

    public Dungeon(Screen screen, Game game) {
        this.screen = screen;
        this.game = game;
        this.level = game.getLevel().getLevel();
    }

    public void showDungeon() {
        try {
            screen.clear();
            // УБИРАЕМ static и создаем graphics локально для каждого вызова
            TextGraphics graphics = screen.newTextGraphics();

            // Получаем размеры экрана
            TerminalSize terminalSize = screen.getTerminalSize();
            int screenWidth = terminalSize.getColumns();
            int screenHeight = terminalSize.getRows();

            // Заливаем весь экран черным
            graphics.setBackgroundColor(TextColor.ANSI.BLACK);
            graphics.fill(' ');

            // Рисуем рамку вокруг всего экрана
            drawBorder(graphics, 0, 0, screenWidth, screenHeight, TextColor.ANSI.YELLOW);

            // Центрируем подземелье на экране
            int dungeonWidth = GameSettings.GAME_WIDTH;
            int dungeonHeight = GameSettings.GAME_HEIGHT;

            int offsetX = (screenWidth - dungeonWidth) / 2;
            int offsetY = (screenHeight - dungeonHeight) / 2;

            // Очистка списка посещённых комнат и коридоров при смене уровня
            if (level != game.getLevel().getLevel()) {
                visitedRooms.clear();
                visitedCorridors.clear();
                level = game.getLevel().getLevel();
            }

            // Передаем graphics во все методы
            showRooms(graphics, offsetX, offsetY);
            showPassage(graphics, offsetX, offsetY);
            showDoors(graphics, offsetX, offsetY);
            showPlayer(graphics, offsetX, offsetY);

            // Отображаем текущее время в правом нижнем углу
            showCurrentTime(graphics, screenWidth, screenHeight);

            if(game.getLevel().isLastLevel()){
                isFinished = true;
            }

            screen.refresh();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка в отрисовке подземелья: " + e.getMessage(), e);
        }
    }

    private void drawBorder(TextGraphics graphics, int startX, int startY, int width, int height, TextColor color) {
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

    private void showCurrentTime(TextGraphics graphics, int screenWidth, int screenHeight) {
        LocalTime currentTime = LocalTime.now();
        String timeString = currentTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        graphics.setForegroundColor(ANSI.WHITE);
        graphics.putString(screenWidth - timeString.length() - 3, screenHeight - 2, timeString);
    }

    private void showRooms(TextGraphics graphics, int offsetX, int offsetY) {
        // Проходим по всем комнатам и проверяем, был ли игрок в комнате
        for (Room room : game.getLevel().getRooms()) {
            if (room.containsPlayer(game.getPlayer()) || visitedRooms.contains(room)) {
                if (!visitedRooms.contains(room)) {
                    visitedRooms.add(room);
                }
            }
        }

        // Отображаем все посещённые комнаты
        for (Room room : visitedRooms) {
            List<PositionInterface> walls = room.getWalls();
            PositionInterface position = room.getPosition();
            List<Position> floor = room.getFloor();
            List<ItemInterface> items = room.getItems();
            List<Enemy> enemies = room.getEnemies();

            // Передаем graphics в каждый метод
            showWalls(graphics, walls, position, room, offsetX, offsetY);
            showFloor(graphics, floor, offsetX, offsetY);

            if (room.containsPlayer(game.getPlayer())) {
                showItems(graphics, items, offsetX, offsetY);
                showEnemies(graphics, enemies, offsetX, offsetY);

                if (room.isEnd()) {
                    showExit(graphics, room, offsetX, offsetY);
                }
            }
        }
    }

    private void showExit(TextGraphics graphics, Room room, int offsetX, int offsetY) {
        graphics.setForegroundColor(ANSI.GREEN);
        PositionInterface exitPosition = game.getLevel().getExitPosition();
        graphics.putString(exitPosition.getX() + offsetX, exitPosition.getY() + offsetY, "*");
    }

    private void showEnemies(TextGraphics graphics, List<Enemy> enemies, int offsetX, int offsetY) {
        graphics.setForegroundColor(ANSI.MAGENTA);
        for (Enemy enemy : enemies) {
            PositionInterface enemyPosition = enemy.getPosition();
            if (enemy.isVisible()) {
                graphics.putString(enemyPosition.getX() + offsetX, enemyPosition.getY() + offsetY, enemy.getDisplayName());
            }
            if(enemy instanceof Mimik){
                if(enemy.isVisible()){
                    graphics.putString(enemyPosition.getX() + offsetX, enemyPosition.getY() + offsetY, enemy.getDisplayName());
                } else {
                    graphics.putString(enemyPosition.getX() + offsetX, enemyPosition.getY() + offsetY, "f");
                }
            }
        }
    }

    private void showItems(TextGraphics graphics, List<ItemInterface> items, int offsetX, int offsetY) {
        graphics.setForegroundColor(ANSI.MAGENTA_BRIGHT);
        for (ItemInterface item : items) {
            Position itemPosition = (Position) item.getPosition();
            graphics.putString(itemPosition.getX() + offsetX, itemPosition.getY() + offsetY, item.getDisplayName());
        }
    }

    private void showFloor(TextGraphics graphics, List<Position> floor, int offsetX, int offsetY) {
        graphics.setForegroundColor(TextColor.ANSI.GREEN);
        for (Position pos : floor) {
            graphics.putString(pos.getX() + offsetX, pos.getY() + offsetY, ".");
        }
    }

    private void showWalls(TextGraphics graphics, List<PositionInterface> walls, PositionInterface position, Room room, int offsetX, int offsetY) {
        graphics.setForegroundColor(TextColor.ANSI.YELLOW);
        for (PositionInterface wall : walls) {
            char symbol = '║';

            if (wall.getY() == position.getY() && wall.getX() == position.getX()) {
                symbol = '╔';
            } else if (wall.getY() == position.getY() && wall.getX() == position.getX() + room.getWidth() - 1) {
                symbol = '╗';
            } else if (wall.getY() == position.getY() + room.getHeight() - 1 && wall.getX() == position.getX()) {
                symbol = '╚';
            } else if (wall.getY() == position.getY() + room.getHeight() - 1 && wall.getX() == position.getX() + room.getWidth() - 1) {
                symbol = '╝';
            } else if (wall.getY() == position.getY() || wall.getY() == position.getY() + room.getHeight() - 1) {
                symbol = '═';
            } else if (wall.getX() == position.getX() || wall.getX() == position.getX() + room.getWidth() - 1) {
                symbol = '║';
            }

            graphics.putString(wall.getX() + offsetX, wall.getY() + offsetY, String.valueOf(symbol));
        }
    }

    private void showPassage(TextGraphics graphics, int offsetX, int offsetY) {
        graphics.setForegroundColor(TextColor.ANSI.WHITE);

        for (Corridor corridor : game.getLevel().getCorridors()) {
            if (corridor.containsPlayer(game.getPlayer().getPosition())) {
                visitedCorridors.add(corridor);
            }
        }

        for (Corridor corridor : visitedCorridors) {
            for (PositionInterface position : corridor.getPath()) {
                graphics.putString(position.getX() + offsetX, position.getY() + offsetY, "░");
            }
        }
    }

    private void showDoors(TextGraphics graphics, int offsetX, int offsetY) {
        for (Room room : visitedRooms) {
            List<PositionInterface> doors = room.getDoors();
            graphics.setForegroundColor(ANSI.YELLOW);
            for (PositionInterface door : doors) {
                if (room.getWalls().contains(door)) {
                    boolean isHorizontal = false;
                    boolean isVertical = false;

                    PositionInterface left = new Position(door.getX() - 1, door.getY());
                    PositionInterface right = new Position(door.getX() + 1, door.getY());
                    PositionInterface up = new Position(door.getX(), door.getY() - 1);
                    PositionInterface down = new Position(door.getX(), door.getY() + 1);

                    if (room.getWalls().contains(left) && room.getWalls().contains(right)) {
                        isHorizontal = true;
                    }
                    else if (room.getWalls().contains(up) && room.getWalls().contains(down)) {
                        isVertical = true;
                    }
                    else {
                        // Горизонтальные стены комнаты (верхняя и нижняя)
                        if (door.getY() == room.getPosition().getY() ||
                                door.getY() == room.getPosition().getY() + room.getHeight() - 1) {
                            isHorizontal = true;
                        }
                        // Вертикальные стены комнаты (левая и правая)
                        else if (door.getX() == room.getPosition().getX() ||
                                door.getX() == room.getPosition().getX() + room.getWidth() - 1) {
                            isVertical = true;
                        }
                    }

                    if (isHorizontal) {
                        graphics.putString(door.getX() + offsetX, door.getY() + offsetY, "║");
                    } else if (isVertical) {
                        graphics.putString(door.getX() + offsetX, door.getY() + offsetY, "═");
                    }
                }
            }
        }
    }

    private void showPlayer(TextGraphics graphics, int offsetX, int offsetY) {
        graphics.setForegroundColor(ANSI.YELLOW);
        PositionInterface playerPosition = game.getPlayer().getPosition();
        graphics.putString(playerPosition.getX() + offsetX, playerPosition.getY() + offsetY, "@");
    }

    public boolean isFinished() {
        return isFinished;
    }
}