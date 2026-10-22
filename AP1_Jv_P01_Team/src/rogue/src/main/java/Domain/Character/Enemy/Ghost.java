package Domain.Character.Enemy;

import Domain.Dungeon.Room;
import Domain.Navigator.Direction;
import Domain.Navigator.GetPosition;
import Domain.Navigator.PositionInterface;

public class Ghost extends Enemy {

  public Ghost(int health, int agility, int strength, boolean isAlive, int hostility,
      PositionInterface startPosition){
    super(health, agility, strength, isAlive, hostility, EnemyType.GHOST, "G",
    startPosition);
  }

  @Override
  public void enemyMove(GetPosition player, Room room) {
    PositionInterface currentPos = getPosition();
    double distance = calculateDistance(player);
    if(shouldPursue(distance) && hasPathTo(player, room)){
      setVisible(true);
      Direction dir = getDirectionTo(player.getPosition());
      PositionInterface nextPos = currentPos.move(dir, 1);
      if(room.isValid(nextPos)){
        setPosition(nextPos);
      }
    } else {
      PositionInterface nextPos = room.getRandomPoint();
      setPosition(nextPos);
      setVisible(random.nextBoolean());
    }

  }

  @Override
  public boolean shouldPursue(double distance) {
    return distance < getHostility();
  }

}
