package Domain.Character.Enemy;

import Domain.Dungeon.Room;
import Domain.Navigator.GetPosition;
import Domain.Navigator.PositionInterface;

public interface EnemyMove {

  void enemyMove(GetPosition player, Room room);
  boolean shouldPursue(double distance);

}
