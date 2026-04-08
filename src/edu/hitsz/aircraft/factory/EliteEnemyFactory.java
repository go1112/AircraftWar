package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.shoot.StraightShoot;

public class EliteEnemyFactory implements EnemyFactory {
    @Override
    public AbstractAircraft createEnemy(int x, int y) {
        EliteEnemy enemy = new EliteEnemy(x, y, 0, 10, 30);
        enemy.setShootStrategy(new StraightShoot(1)); // 单排直射
        return enemy;
    }
}