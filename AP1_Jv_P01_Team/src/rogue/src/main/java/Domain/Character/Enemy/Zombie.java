package Domain.Character.Enemy;

import Domain.Dungeon.Room;
import Domain.Navigator.GetPosition;
import Domain.Navigator.PositionInterface;

public class Zombie extends Enemy {

  public Zombie(int health, int agility, int strength, boolean isAlive, int hostility,
      PositionInterface positionInterface){
    super(health, agility, strength, isAlive, hostility, EnemyType.ZOMBIE, "Z",
        positionInterface);
  }

  @Override
  public void enemyMove(GetPosition player, Room room) {
    standardMove(player, room);
  }

  @Override
  public boolean shouldPursue(double distance) {
    return distance <= getHostility();
  }
}
