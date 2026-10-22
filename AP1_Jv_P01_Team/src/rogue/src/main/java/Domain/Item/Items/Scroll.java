package Domain.Item.Items;

import Domain.Item.Item;
import Domain.Item.ItemType;
import Domain.Item.ItemUser;
import Domain.Item.Property;

public class Scroll extends Item {

  private final Property property;

  public Scroll(Property property, int boost) {
    super("s", ItemType.SCROLL, boost);
    this.property = property;
  }

  @Override
  public boolean isScroll() {
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
  }

  @Override
  public Property getProperty() {
    return property;
  }

}
