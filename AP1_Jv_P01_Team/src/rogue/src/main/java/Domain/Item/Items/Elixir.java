package Domain.Item.Items;

import Domain.Item.Item;
import Domain.Item.ItemType;
import Domain.Item.ItemUser;
import Domain.Item.Property;

public class Elixir extends Item {

  private final Property property;
  private final int elixirTurnsLeft;

  public Elixir(Property property, int boost, int elixirTurnsLeft) {
    super("e", ItemType.ELIXIR, boost);
    this.property = property;
    this.elixirTurnsLeft = elixirTurnsLeft;
  }

  @Override
  public boolean isElixir() {
    return true;
  }


  @Override
  public void useItem(ItemUser player) {
    switch(property) {
      case MAX_HEALTH -> {
        player.setMaxHealth(player.getMaxHealth() + getValue());
        player.setHealth(player.getHealth() + getValue());
      }
      case AGILITY -> player.setAgility(player.getAgility() + getValue());
      case STRENGTH -> player.setStrength(player.getStrength() + getValue());
    }
    player.setElixirProperty(property, elixirTurnsLeft);
    player.setElixirUse(player.getElixirUse() + 1);
  }


  @Override
  public Property getProperty() {
    return property;
  }
}
