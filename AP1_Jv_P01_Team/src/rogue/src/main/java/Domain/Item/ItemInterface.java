package Domain.Item;

import Domain.Character.Player.Player;
import Domain.Dungeon.Room;
import Domain.Navigator.Position;
import Domain.Navigator.PositionInterface;

public interface ItemInterface {
  ItemType getItemType();
  int getValue();
  void useItem(ItemUser player);
  PositionInterface getPosition();
  void setPosition(PositionInterface pos);
  Property getProperty();
  String getDisplayName();
  ItemType getType();

  public boolean onPickup(Player player, PositionInterface endPos);

  default void deactivate(Player player) {
    // по умолчанию ничего
  }

  default void dropToRoom(Room room, Player player) {
    // по умолчанию ничего
  }

  default String getWeaponName() {
    return null; // по умолчанию предмет не оружие
  }

  default boolean isSameWeapon(ItemInterface other) {
    if(other != null) {
      String myName = getWeaponName();
      String otherName = other.getWeaponName();
      return myName != null && myName.equals(otherName);
    }
    return false;
  }

  default boolean isFood() {
    return false;
  }

  default boolean isElixir() {
    return false;
  }

  default boolean isScroll() {
    return false;
  }


}
