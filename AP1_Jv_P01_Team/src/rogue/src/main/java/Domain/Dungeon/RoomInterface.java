package Domain.Dungeon;

import Domain.Character.Enemy.Enemy;
import Domain.Item.Item;
import Domain.Item.ItemInterface;
import Domain.Navigator.Position;
import Domain.Navigator.PositionInterface;
import java.util.List;

public interface RoomInterface {

  int getWidth();
  int getHeight();

  boolean isValid(PositionInterface pos);

  List<PositionInterface> listWalls();
  List<PositionInterface> getDoors();

  List<ItemInterface> getItems();
  void addItem(ItemInterface item);

  List<Enemy> getEnemies();
  void addEnemy(Enemy enemy);

  List<PositionInterface> getWalls();
  PositionInterface getRandomPoint();

  void setDoors(List<PositionInterface> doors);

  PositionInterface getPosition();

  boolean isCross(List<Room> rooms);

  PositionInterface getCenter();

  boolean isParallel(List<Room> rooms);

}
