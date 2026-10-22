package Domain.Character.Enemy;

import Domain.Dungeon.Room;
import Domain.Navigator.Direction;
import Domain.Navigator.GetPosition;
import Domain.Navigator.PositionInterface;

public class Snake extends Enemy {

  public Snake(int health, int agility, int strength, boolean isAlive, int hostility,
      PositionInterface positionInterface){
    super(health, agility, strength, isAlive, hostility, EnemyType.SNAKE, "S",
        positionInterface);
  }


    @Override
    public void enemyMove(GetPosition player, Room room) {
        PositionInterface currentPos = getPosition();
        double distance = calculateDistance(player);

        if(shouldPursue(distance) && hasPathTo(player, room)){
            Direction dir = getDirectionTo(player.getPosition());
            PositionInterface nextPos = currentPos.move(dir, 1);
            if(room.isValid(nextPos)){
                setPosition(nextPos);
            }
        } else {
            // Только 4 основных направления (вверх, вниз, влево, вправо)
            Direction[] basicDirections = {
                    Direction.UP,
                    Direction.DOWN,
                    Direction.LEFT,
                    Direction.RIGHT
            };

            Direction randDir = basicDirections[random.nextInt(basicDirections.length)];
            PositionInterface nextPos = currentPos.move(randDir, 1);
            if(room.isValid(nextPos)){
                setPosition(nextPos);
            }
        }
    }

  @Override
  public boolean shouldPursue(double distance) {
    return distance <= getHostility();
  }
}
