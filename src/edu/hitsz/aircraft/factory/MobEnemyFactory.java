package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.MobEnemy;

/**
 * 普通敌机工厂：向下方移动 + 不可左右移动 + 不发射子弹
 */
public class MobEnemyFactory implements EnemyFactory {

    @Override
    public AbstractAircraft createEnemy(int x, int y) {
        return new MobEnemy(x, y, 0, 5, 20);
    }

}