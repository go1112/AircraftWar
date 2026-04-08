package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.VeteranEnemy;
import edu.hitsz.shoot.StraightShoot;

/**
 * 精锐敌机工厂：向屏幕下方移动 + 且可左右移动 + 按设定周期向下直射双排子弹
 */
public class VeteranEnemyFactory implements EnemyFactory {
    @Override
    public AbstractAircraft createEnemy(int x, int y) {
        VeteranEnemy enemy = new VeteranEnemy(x, y, 3, 5, 30);
        enemy.setShootStrategy(new StraightShoot(2)); // 双排直射
        return enemy;
    }
}