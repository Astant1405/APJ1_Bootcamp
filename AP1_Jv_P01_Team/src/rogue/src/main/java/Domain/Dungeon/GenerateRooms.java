package Domain.Dungeon;

import Domain.Character.Enemy.Enemy;
import Domain.Character.Enemy.Ghost;
import Domain.Character.Enemy.Mimik;
import Domain.Character.Enemy.Ogre;
import Domain.Character.Enemy.Snake;
import Domain.Character.Enemy.Vampire;
import Domain.Character.Enemy.Zombie;
import Domain.GameSession.GameSettings;
import Domain.Item.Items.Elixir;
import Domain.Item.Items.Food;
import Domain.Item.Items.Scroll;
import Domain.Item.Items.Treasure;
import Domain.Item.Items.Weapon;
import Domain.Item.Property;
import Domain.Navigator.Position;
import Domain.Navigator.PositionInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateRooms {

  private static final Random random = new Random();

  public static List<Room> createRooms(int level) {
    List<Room> rooms = new ArrayList<>();

    for (int i = 0; i < GameSettings.COUNT_ROOMS; i++) {
      int roomWidth = random.nextInt(5) + GameSettings.MIN_ROOM_WIDTH;
      int roomHeight = random.nextInt(4) + GameSettings.MIN_ROOM_HEIGHT;
      int x = random.nextInt(GameSettings.GAME_WIDTH - roomWidth);
      int y = random.nextInt(GameSettings.GAME_HEIGHT - roomHeight);
      PositionInterface roomPosition = new Position(x, y);

      Room room = new Room(roomPosition, roomWidth, roomHeight);

      // Проверяем, пересекается ли новая комната с другими
      if (i != 0 && (room.isCross(rooms))) {
        i--;
        continue;
      }
      if (room.isParallel(rooms)) {
        i--;
        continue;
      }
      rooms.add(room);
    }
    // Рандомно определяем стартовую и конечную комнаты
    setStartAndEndRooms(rooms);
    setItems(rooms, level);
    setEnemies(rooms, level);
    return rooms;
  }


  private static void setStartAndEndRooms(List<Room> rooms) {
    // Случайным образом выбираем стартовую комнату
    int startIndex = random.nextInt(rooms.size());
    Room startRoom = rooms.get(startIndex);
    // Устанавливаем флаг для стартовой комнаты
    startRoom.setStart(true);

    // Случайным образом выбираем конечную комнату, отличную от стартовой
    int endIndex;
    do {
      endIndex = random.nextInt(rooms.size());
    } while (endIndex == startIndex);
    Room endRoom = rooms.get(endIndex);
    // Устанавливаем флаг для конечной комнаты
    endRoom.setEnd(true);
  }

  private static void setItems(List<Room> rooms, int level) {
    // Относительно уровня будет разный рандом для предмета

    for (Room room : rooms) {
      // Определяем количество предметов по логарифмической формуле
      int maxItems = Math.max(1,
          (int) (GameSettings.MAX_COUNT_ITEM_IN_ROOM / (1 + Math.log(level + 1))));
      int itemCount = random.nextInt(maxItems) + 1;

      for (int i = 0; i < itemCount; i++) {
        int itemType = random.nextInt(5); // 5 типов предметов

        switch (itemType) {
          case 0: // Food
            room.addItem(new Food(random.nextInt(10) + level));
            break;
          case 1: // Elixir
            int power = random.nextInt(5) + level;
            Property typeElixir = Property.values()[random.nextInt(Property.values().length)];
            room.addItem(new Elixir(typeElixir, power, power / 2));
            break;
          case 2: // Scroll
            int scrollPower = random.nextInt(5) + level;
            Property typeScroll = Property.values()[random.nextInt(Property.values().length)];
            room.addItem(new Scroll(typeScroll, scrollPower));
            break;
          case 3: // Treasure
            int value = random.nextInt(10) + level;
            room.addItem(new Treasure(value));
            break;
          case 4: // Weapon
            int damage = random.nextInt(5) + level;
            room.addItem(new Weapon(damage));
            break;
        }

        // Выбираем  случайную координату в команате
        Position randomItemPosition = room.getRandomPoint();
        // Добавляем предмет в комнату
        room.getItems().get(i).setPosition(randomItemPosition);
      }
    }
  }

  private static void setEnemies(List<Room> rooms, int level) {
    // здесь нужен будет геттер который вернет какой сейчас уровень из Data.
    // Определяем количество предметов по логарифмической формуле

    for (Room room : rooms) {
      // Добавляем врагов (кроме стартовой комнаты)
      if (!room.isStart()) {

        // Количество врагов увеличивается с уровнем
        int maxEnemy = Math.min(GameSettings.MAX_COUNT_ENEMIES_IN_ROOM,
            1 + (int) Math.log(level + 1));
        int enemyCount = random.nextInt(maxEnemy) + 1;

        for (int i = 0; i < enemyCount; i++) {
          int health = random.nextInt(10) + level * 5;
          int agility = random.nextInt(5) + level * 2;
          int strength = random.nextInt(7) + level * 3;
          boolean isHostile = true;
          int damage = random.nextInt(5) + level * 3;

          PositionInterface randomEnemyPosition = room.getRandomPoint();

          // Выбираем случайного врага
          int enemyType = random.nextInt(6);
          switch (enemyType) {
            case 0 -> room.addEnemy(new Ghost(health, agility, strength, isHostile, damage,
                randomEnemyPosition));
            case 1 -> {
              Enemy enemy = new Mimik(health, agility, strength, isHostile, damage,
                  randomEnemyPosition);
              enemy.setVisible(false);
              room.addEnemy(enemy);
            }
            case 2 -> room.addEnemy(new Ogre(health, agility, strength, isHostile, damage,
                randomEnemyPosition));
            case 3 -> room.addEnemy(new Snake(health, agility, strength, isHostile, damage,
                randomEnemyPosition));
            case 4 -> room.addEnemy(new Vampire(health, agility, strength, isHostile, damage,
                randomEnemyPosition));
            case 5 -> room.addEnemy(new Zombie(health, agility, strength, isHostile, damage,
                randomEnemyPosition));
          }
          //PositionInterface randomEnemyPosition = room.getRandomPoint();
          room.getEnemies().get(i).setPosition(randomEnemyPosition);
        }
      }
    }
  }

}
