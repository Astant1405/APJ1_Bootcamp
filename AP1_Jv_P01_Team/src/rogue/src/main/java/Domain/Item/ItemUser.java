package Domain.Item;

public interface ItemUser {
  int getHealth();
  void setHealth(int health);
  int getMaxHealth();
  void setMaxHealth(int maxHealth);
  int getAgility();
  void setAgility(int agility);
  int getStrength();
  void setStrength(int strength);
  void addGold(int gold);
  int getGold();
  void setElixirProperty(Property property, int turns);
  void updateElixirEffect();
  void resetElixirEffect();
  void useWeapon(boolean use);
  void setElixirUse(int elixirUse);
  int getElixirUse();
  int getFoodEaten();
  void setFoodEaten(int foodEaten);
}
