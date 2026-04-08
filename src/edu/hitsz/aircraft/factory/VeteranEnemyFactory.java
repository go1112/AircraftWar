package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.VeteranEnemy;
import edu.hitsz.shoot.StraightShoot;

public class VeteranEnemyFactory implements EnemyFactory {
    @Override
    public AbstractAircraft createEnemy(int x, int y) {
        VeteranEnemy enemy = new VeteranEnemy(x, y, 5, 10, 30);
        enemy.setShootStrategy(new StraightShoot(2)); // 双排直射
        return enemy;
    }
}