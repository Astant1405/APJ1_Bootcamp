package Domain.Character.Enemy;

import Domain.Character.Player.Player;
import Domain.Dungeon.Room;
import Domain.Navigator.GetPosition;
import Domain.Navigator.PositionInterface;
import Domain.Navigator.ValidateLocation;

public class Mimik extends Enemy {

  public Mimik(int health, int agility, int strenght, boolean isAlive, int hostility,
      PositionInterface startPosition){
    super(health, agility, strenght, isAlive, hostility, EnemyType.MIMIK, "M",
        startPosition);
  }

  @Override
  public void onPlayerEncounter(Player player) {
    if (!isVisible()) {
      setVisible(true);
    }
    super.onPlayerEncounter(player);
  }

  @Override
  public void enemyMove(GetPosition player, Room room) {
    if(isVisible()){
      standardMove(player, room);
    }
  }

  @Override
  public boolean shouldPursue(double distance) {
    return distance <= getHostility();
  }
}