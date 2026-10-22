package Domain.Character.Enemy;

import Domain.Dungeon.Room;
import Domain.Navigator.Direction;
import Domain.Navigator.GetPosition;
import Domain.Navigator.PositionInterface;

public class Ogre extends Enemy {

  public Ogre(int health, int agility, int strength, boolean isAlive, int hostility,
      PositionInterface positionInterface){
    super(health, agility, strength, isAlive, hostility, EnemyType.OGRE, "O",
        positionInterface);
  }

  @Override
  public void enemyMove(GetPosition player, Room room) {
    if(getRestCounter() > 0){
      setRestCounter(getRestCounter() - 1);
      return;
    }

    PositionInterface currentPos = getPosition();
    double distance = calculateDistance(player);
    if(shouldPursue(distance) && hasPathTo(player, room)){
      Direction dir = getDirectionTo(player.getPosition());
      PositionInterface nextPos = currentPos.move(dir, 1);
      if(room.isValid(nextPos)){
        setPosition(nextPos);
      }
    } else {
      Direction[] dir = Direction.values();
      Direction randDir = dir[random.nextInt(4)];
      PositionInterface nextPos = currentPos.move(randDir, 2);
      if(room.isValid(nextPos)){
        setPosition(nextPos);
      }else {
        nextPos = currentPos.move(randDir, 1);
        if (room.isValid(nextPos)) {
          setPosition(nextPos);
        }
      }
    }

  }

  @Override
  public boolean shouldPursue(double distance) {
    return distance <= getHostility();
  }
}
