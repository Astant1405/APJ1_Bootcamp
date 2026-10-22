package Domain.Fight;

import Domain.Character.Character;
import Domain.Character.Enemy.Enemy;
import Domain.Character.Player.Player;
import java.util.Random;

public class Combat implements Domain.Fight.Fight {

  private boolean lastHit = false;
  private final Domain.Character.Character attacker;

  public Combat(Domain.Character.Character attacker){
    this.attacker = attacker;
  }


  /**
   * Формула рассчета вероятности попадания
   * P = min(95,max(10,50+0.5⋅(∆)))<br>
   * P: Вероятность попадания<br>
   * ∆: Разница ловкости аттакующего и аттакуемого<br>
   * 0,5:Коэффициент влияния дельты<br>
   * 10: Минимальный шанс<br>
   * 50: Базовый шанс<br>
   * 95: Максимальный шанс
   * @param target
   * @return Вероятность попадания
   */
  private int calculateHitChance(Domain.Character.Character target) {
    int baseChance = 50;
    int minChance = 10;
    int maxChance = 95;
    int agilityDiff = attacker.getAgility() - target.getAgility();
    double k = 0.5;

    // Линейная аппроксимация логистической функции
    int rawChance = (int) (baseChance + k * agilityDiff);

    // Ограничение диапазона
    return Math.min(maxChance, Math.max(minChance, rawChance));
  }

  private int calculateHitDamage(Domain.Character.Character target) {
    return target.getStrength();
  }


  //@Override
  public void attack(Domain.Character.Character target) {
    if(attacker instanceof Enemy enemy){
      switch(enemy.getType()){
        case ZOMBIE, MIMIK -> standardAttack(target);
        case VAMPIRE -> {
          if(enemy.isFirstHit() && target instanceof Player){
            enemy.setFirstHit(false);
            return;
          }
          standardAttack(target);
          if(lastHit && target instanceof Player player){
            player.setMaxHealth(player.getMaxHealth() - 5);
          }
        }
        case GHOST -> {
          if(!enemy.isVisible()){
            return;
          }
          standardAttack(target);
        }
        case OGRE -> {
          if (enemy.getRestCounter() > 0) {
            enemy.setRestCounter(enemy.getRestCounter() - 1);
            return; // Отдых после атаки
          }
          standardAttack(target);
          if (new Random().nextInt(100) < calculateHitChance(target)) {
            enemy.setRestCounter(1); // Отдыхает 1 ход
          }
        }
        case SNAKE -> {
          standardAttack(target);
          if (new Random().nextInt(100) < 20 && target instanceof Player player) {
            player.setSleepTurnsLeft(1);
          }
        }
      }
    }
    if(target instanceof Player player){
      if(player.getSleepTurnsLeft() > 0){
        return;
      }
      standardAttack(target);
    }
  }

  private void standardAttack(Character target){
    int hitChance = calculateHitChance(target);
    lastHit = new Random().nextInt(100) < hitChance;
    if(lastHit){
      int playerDamageForEnemy = calculateHitDamage(target);
      int enemyDamageForPlayer = calculateHitDamage(attacker);
      attacker.applyDamage(playerDamageForEnemy);
      target.applyDamage(enemyDamageForPlayer);
      if(attacker instanceof Player player){
        if(target instanceof Enemy enemy){
          if(!enemy.isAlive()){
            player.setMonsterKills(player.getMonsterKills() + 1);
          }
        }
      }
    }
  }

}
