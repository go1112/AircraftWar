package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.shoot.RingShoot;
/**
 * BOSS敌机工厂：在屏幕上方 + 左右移动 + 悬浮 + 环射（20个弹道）
 */
public class BossEnemyFactory implements EnemyFactory {
    @Override
    public AbstractAircraft createEnemy(int x, int y) {
        BossEnemy enemy = new BossEnemy(x, y, 5, 0, 1000);
        enemy.setShootStrategy(new RingShoot(20));
        return enemy;
    }
}