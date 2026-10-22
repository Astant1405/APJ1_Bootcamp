package Domain.Item.Items;

import Domain.Character.Player.Player;
import Domain.Dungeon.Room;
import Domain.Item.Item;
import Domain.Item.ItemInterface;
import Domain.Item.ItemType;
import Domain.Item.ItemUser;
import Domain.Navigator.Position;
import Domain.Navigator.PositionInterface;

public class Weapon extends Item {

  private boolean isActive = false;
  public Weapon(int damage){
    super("w", ItemType.WEAPON, damage);
  }

  @Override
  public void useItem(ItemUser player) {
    if (!isActive) { // Если оружие еще не активно
      player.setStrength(player.getStrength() + getValue()); // Увеличиваем силу игрока
      player.useWeapon(true); // Устанавливаем флаг использования оружия
      isActive = true; // Помечаем оружие как активное
    }
  }

  public void deactivate(ItemUser player) {
    if (isActive) { // Если оружие активно
      player.setStrength(player.getStrength() - getValue()); // Уменьшаем силу игрока
      player.useWeapon(false); // Сбрасываем флаг использования оружия
      isActive = false; // Помечаем оружие как неактивное
    }
  }

  @Override
  public String getWeaponName() {
    return getDisplayName();
  }

  @Override
  public void dropToRoom(Room room, Player player) {
    PositionInterface newPos = new Position(
        player.getPosition().getX() + 1,
        player.getPosition().getY() + 1
    );
    setPosition(newPos);
    room.addItem(this);
  }



}
