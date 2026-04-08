package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.shoot.RingShoot;

public class BossEnemyFactory implements EnemyFactory {
    @Override
    public AbstractAircraft createEnemy(int x, int y) {
        BossEnemy enemy = new BossEnemy(x, y, 5, 0, 300);
        enemy.setShootStrategy(new RingShoot(20));
        return enemy;
    }
}