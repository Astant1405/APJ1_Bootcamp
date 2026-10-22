package Domain.Dungeon;

import Domain.Character.Enemy.Enemy;
import Domain.Character.Player.Player;
import Domain.GameSession.Game;
import Domain.GameSession.GameSettings;
import Domain.Item.ItemInterface;
import Domain.Navigator.Position;
import Domain.Navigator.PositionInterface;
import Domain.Navigator.ValidateLocation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Room implements RoomInterface, ValidateLocation {
  private final PositionInterface position;
  private final int width, height;
  private List<ItemInterface> items;
  private List<Enemy> enemies;
  private List<PositionInterface> walls;
  private List<PositionInterface> doors;
  private boolean isStart;
  private boolean isEnd;
  private static final Random random = new Random();

  public Room(PositionInterface position, int width, int height) {
    this.position = position;
    this.width = width;
    this.height = height;
    this.items = new ArrayList<>();
    this.enemies = new ArrayList<>();
    this.walls = listWalls();
    this.isStart = false;
    this.isEnd = false;
    this.doors = new ArrayList<>();
  }

  @Override
  public void addItemToLocation(ItemInterface item, Player player) {
    Position newPos = new Position(
        player.getPosition().getX() + 1,
        player.getPosition().getY() + 1
    );
    item.setPosition(newPos);
    addItem(item);
  }

  @Override
  public void moveEnemies(Player player) {
    for (Enemy enemy : enemies) {
      if (enemy.isAlive()) {
        enemy.enemyMove(player, this);
      }
    }
  }

  @Override
  public void onPlayerEnter(Player player, PositionInterface endPos, Game game) {
    // Враги
    Iterator<Enemy> iterator = enemies.iterator();
    while (iterator.hasNext()) {
      Enemy enemy = iterator.next();
      if (!enemy.isAlive()) {
        iterator.remove();
        player.setMonsterKills(player.getMonsterKills() + 1);
      } else if (enemy.getPosition().equals(endPos)) {
        enemy.onPlayerEncounter(player);
      }
    }

    // Предметы
    items.removeIf(item -> item.onPickup(player, endPos));

    player.updateElixirEffect();

    // Новый уровень
    if (endPos.equals(game.getLevel().getExitPosition())) {
      game.newLevel();
    }
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }


  @Override
  public List<PositionInterface> getDoors() {
    return doors;
  }

  @Override
  public void setDoors(List<PositionInterface> doors) {
    this.doors.addAll(doors);
  }


  @Override
  public PositionInterface getPosition() {
    return position;
  }

  @Override
  public List<ItemInterface> getItems() {
    return items;
  }

  @Override
  public void addItem(ItemInterface item) {
    items.add(item);
  }

  @Override
  public List<Enemy> getEnemies() {
    return enemies;
  }

  @Override
  public void addEnemy(Enemy enemy) {
    enemies.add(enemy);
  }


  @Override
  public List<PositionInterface> getWalls() {
    return walls;
  }


  @Override
  public boolean isValid(PositionInterface pos) {
    int x = pos.getX();
    int y = pos.getY();

    // Проверка, что позиция находится внутри границ комнаты
    boolean withinBounds = x > position.getX() && x < position.getX() + width -1 &&
        y > position.getY() && y < position.getY() + height - 1;

    // Проверка, что позиция не является частью стены
    //boolean notWall = !walls.contains(pos);

    // Проверка, является ли позиция дверью
    boolean isDoor = doors.contains(pos);

    // Возвращаем true, если позиция внутри комнаты и не является стеной
    // или если это дверь (дверь всегда валидная позиция)
    return withinBounds || isDoor;
  }



  @Override
  public boolean isCross(List<Room> rooms) {
    for (Room other : rooms) {
      if (position.getX() < other.position.getX() + other.width + GameSettings.MIN_ROOM_DISTANCE &&
          position.getX() + width + GameSettings.MIN_ROOM_DISTANCE > other.position.getX() &&
          position.getY() < other.position.getY() + other.height + GameSettings.MIN_ROOM_DISTANCE &&
          position.getY() + height + GameSettings.MIN_ROOM_DISTANCE > other.position.getY()) {
        return true;
      }
    }
    return false;
  }


  public boolean isParallel(List<Room> rooms) {
    // Левый верхний угол текущей комнаты
    int thisX = this.getPosition().getX();
    int thisY = this.getPosition().getY();

    // Правый верхний угол текущей комнаты
    int thisTopRightX = thisX + this.getWidth() - 1;
    int thisTopRightY = thisY;

    // Левый нижний угол текущей комнаты
    int thisBottomLeftX = thisX;
    int thisBottomLeftY = thisY + this.getHeight() - 1;

    // Правый нижний угол текущей комнаты
    int thisBottomRightX = thisX + this.getWidth() - 1;
    int thisBottomRightY = thisY + this.getHeight() - 1;

    // Центр текущей комнаты
    int thisCenterX = this.getCenter().getX();
    int thisCenterY = this.getCenter().getY();

    for (Room other : rooms) {
      // Левый верхний угол другой комнаты
      int otherX = other.getPosition().getX();
      int otherY = other.getPosition().getY();

      // Правый верхний угол другой комнаты
      int otherTopRightX = otherX + other.getWidth() - 1;
      int otherTopRightY = otherY;

      // Левый нижний угол другой комнаты
      int otherBottomLeftX = otherX;
      int otherBottomLeftY = otherY + other.getHeight() - 1 ;

      // Правый нижний угол другой комнаты
      int otherBottomRightX = otherX + other.getWidth() - 1;
      int otherBottomRightY = otherY + other.getHeight() - 1;

      // Центр другой комнаты
      int otherCenterX = other.getCenter().getX();
      int otherCenterY = other.getCenter().getY();

      // Проверка на совпадение координат по X или Y
      if (thisX == otherCenterX || thisY == otherCenterY ||
          thisTopRightX == otherCenterX || thisTopRightY == otherCenterY ||
          thisBottomLeftX == otherCenterX || thisBottomLeftY == otherCenterY ||
          thisBottomRightX == otherCenterX || thisBottomRightY == otherCenterY ||
          otherX == thisCenterX || otherY == thisCenterY ||
          otherTopRightX == thisCenterX || otherTopRightY == thisCenterY ||
          otherBottomLeftX == thisCenterX || otherBottomLeftY == thisCenterY ||
          otherBottomRightX == thisCenterX || otherBottomRightY == thisCenterY) {
        return true; // Если хотя бы одна координата совпала, комнаты считаются параллельными
      }
    }

    return false; // Если не найдено совпадений, возвращаем false
  }


  @Override
  public Position getCenter() {
    int centerX = position.getX() + (width - 1) / 2;
    int centerY = position.getY() + (height - 1) / 2;
    return new Position(centerX, centerY);
  }


  public boolean isStart() {
    return isStart;
  }

  public void setStart(boolean start) {
    isStart = start;
  }

  public boolean isEnd() {
    return isEnd;
  }

  public void setEnd(boolean end) {
    isEnd = end;
  }


  public List<PositionInterface> listWalls() {
    List<PositionInterface> walls = new ArrayList<>();

    // Верхняя и нижняя границы
    for (int i = 0; i < width; i++) {
      walls.add(new Position(position.getX() + i, position.getY()));             // Верхняя
      walls.add(new Position(position.getX() + i, position.getY() + height - 1)); // Нижняя
    }

    // Левая и правая границы
    for (int j = 1; j < height - 1; j++) {
      walls.add(new Position(position.getX(), position.getY() + j));             // Левая
      walls.add(new Position(position.getX() + width - 1, position.getY() + j)); // Правая
    }

    return walls;
  }


  public Position getRandomPoint() {
    int randomX = random.nextInt(width - 2) + 1;
    int randomY = random.nextInt(height - 2) + 1;
    return new Position(position.getX() + randomX, position.getY() + randomY);
  }

  public PositionInterface getRandomWallPosition() {
    List<PositionInterface> doorways = new ArrayList<>();

    for(PositionInterface wall : walls) {
      if(wall.getY() == position.getY() && wall.getX() == position.getX()){
        continue;
      } else if(wall.getY() == position.getY() && wall.getX() == position.getX() + width - 1){
        continue;
      } else if(wall.getY() == position.getY() + height - 1 && wall.getX() == position.getX()){
        continue;
      } else if(wall.getY() == position.getY() + height - 1 && wall.getX() == position.getX() + width - 1){
        continue;
      }
      doorways.add(wall);
    }

    return doorways.get(random.nextInt(doorways.size()));
  }

  public List<Position> getFloor() {
    List<Position> floor = new ArrayList<>();

    int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
    int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;

    // Определяем границы комнаты
    for (PositionInterface wall : walls) {
      minX = Math.min(minX, wall.getX());
      minY = Math.min(minY, wall.getY());
      maxX = Math.max(maxX, wall.getX());
      maxY = Math.max(maxY, wall.getY());
    }

    // Заполняем область внутри стен
    for (int y = minY + 1; y < maxY; y++) {
      for (int x = minX + 1; x < maxX; x++) {
        Position pos = new Position(x, y);
        if (!walls.contains(pos)) { // Проверяем, что это не стена
          floor.add(pos);
        }
      }
    }
    return floor;
  }

  public void setItems(List<ItemInterface> items) {
    this.items = items;
  }

  public void setEnemies(List<Enemy> enemies) {
    this.enemies = enemies;
  }

  public void setWalls(List<PositionInterface> walls) {
    this.walls = walls;
  }

  public boolean containsPlayer(Player player) {
    return this.getFloor().contains(player.getPosition());
  }
}
