package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.shoot.StraightShoot;
/**
 * 精英敌机工厂：单排直射 + 下方移动
 */
public class EliteEnemyFactory implements EnemyFactory {
    @Override
    public AbstractAircraft createEnemy(int x, int y) {
        EliteEnemy enemy = new EliteEnemy(x, y, 0, 5, 40);
        enemy.setShootStrategy(new StraightShoot(1)); // 单排直射
        return enemy;
    }
}