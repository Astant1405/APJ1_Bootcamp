package Domain.Item.Items;

import Domain.Item.Item;
import Domain.Item.ItemType;
import Domain.Item.ItemUser;

public class Food extends Item {

  private int healthBoost;

  public Food(int healthBoost){
    super("f", ItemType.FOOD, healthBoost);
    this.healthBoost = healthBoost;
  }

  @Override
  public void useItem(ItemUser player) {
    if(!(player.getHealth() == player.getMaxHealth())){
      player.setHealth(player.getHealth()+healthBoost);
      player.setFoodEaten(player.getFoodEaten() + 1);
    }
  }

  @Override
  public boolean isFood() {
    return true;
  }

}
