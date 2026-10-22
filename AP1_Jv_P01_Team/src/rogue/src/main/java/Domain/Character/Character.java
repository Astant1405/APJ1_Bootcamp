package Domain.Character;

import Domain.Navigator.GetPosition;
import Domain.Navigator.PositionInterface;

public abstract class Character implements GetPosition {
  private int health;
  private int agility;
  private int strength;
  private boolean isAlive;
  PositionInterface position;

  public Character(int health, int agility, int strength, boolean isAlive,
      PositionInterface position) {
    this.health = health;
    this.agility = agility;
    this.strength = strength;
    this.isAlive = isAlive;
    this.position = position;
  }

  public PositionInterface getPosition() {
    return position;
  }

  public void setPosition(PositionInterface position) {
    this.position = position;
  }

  public int getHealth() {
    return health;
  }

  public int getAgility() {
    return agility;
  }

  public int getStrength() {
    return strength;
  }

  public boolean isAlive() {
    return isAlive;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public void setAgility(int agility) {
    this.agility = agility;
  }

  public void setStrength(int strength) {
    this.strength = strength;
  }

  public void setAlive(boolean alive) {
    isAlive = alive;
  }

  public void applyDamage(int damage) {
    setHealth(health - damage);
    if(health <= 0){
      isAlive = false;
    }
  }
}
