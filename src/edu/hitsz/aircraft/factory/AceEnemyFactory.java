package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.AceEnemy;
import edu.hitsz.shoot.ScatterShoot;

/**
 * 王牌敌机工厂：机向屏幕下方移动 + 可左右移动 + 扇形散射弹道（单次同时发射 3 颗子弹）
 */
public class AceEnemyFactory implements EnemyFactory {
    @Override
    public AbstractAircraft createEnemy(int x, int y) {
        AceEnemy enemy = new AceEnemy(x, y, 5, 8, 50);
        enemy.setShootStrategy(new ScatterShoot(3)); // 散射
        return enemy;
    }
}