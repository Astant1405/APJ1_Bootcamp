package Domain.Navigator;

import Domain.Character.Player.Player;
import Domain.GameSession.Game;
import Domain.Item.ItemInterface;

public interface ValidateLocation {

  boolean isValid(PositionInterface pos);

  default void onPlayerEnter(Player player, PositionInterface endPos, Game game) {
    // По умолчанию ничего
  }

  default void moveEnemies(Player player) {
    // По умолчанию ничего не делаем
  }

  default void addItemToLocation(ItemInterface item, Player player) {
    // по умолчанию ничего
  }

}
