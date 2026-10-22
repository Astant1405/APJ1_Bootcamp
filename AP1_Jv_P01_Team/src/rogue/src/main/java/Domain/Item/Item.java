package Domain.Item;

import Domain.Character.Player.Player;
import Domain.Navigator.PositionInterface;

public abstract class Item implements ItemInterface {

  private String displayName;
  private ItemType type;
  private int value;
  private PositionInterface position;

  public Item(String displayName, ItemType type, int value) {
    this.displayName = displayName;
    this.type = type;
    this.value = value;
  }

  @Override
  public boolean onPickup(Player player, PositionInterface endPos) {
    if (!position.equals(endPos)) return false;
    player.getBag().putItem(this);
    return true;
  }

  @Override
  public ItemType getType() {
    return type;
  }

  @Override
  public int getValue() {
    return value;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

  @Override
  public ItemType getItemType() {
    return type;
  }

  @Override
  public abstract void useItem(ItemUser player);

  @Override
  public PositionInterface getPosition() {
    return position;
  }

  @Override
  public void setPosition(PositionInterface pos) {
    this.position = pos;
  }

  @Override
  public Property getProperty() {
    return null;
  }

}
