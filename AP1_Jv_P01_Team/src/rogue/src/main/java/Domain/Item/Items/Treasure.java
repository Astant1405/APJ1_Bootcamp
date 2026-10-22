package Domain.Item.Items;

import Domain.Character.Player.Player;
import Domain.Item.Item;
import Domain.Item.ItemType;
import Domain.Item.ItemUser;
import Domain.Navigator.PositionInterface;

public class Treasure extends Item {

  public Treasure(int cost) {
    super("t", ItemType.TREASURE, cost);
  }

  @Override
  public boolean onPickup(Player player, PositionInterface endPos) {
    if (!getPosition().equals(endPos)) return false;
    useItem(player);
    return true;
  }

  @Override
  public void useItem(ItemUser player) {
    player.addGold(getValue());
  }

}
