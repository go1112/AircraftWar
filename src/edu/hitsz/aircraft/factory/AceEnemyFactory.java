package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.AceEnemy;
import edu.hitsz.shoot.ScatterShoot;

public class AceEnemyFactory implements EnemyFactory {
    @Override
    public AbstractAircraft createEnemy(int x, int y) {
        AceEnemy enemy = new AceEnemy(x, y, 8, 10, 30);
        enemy.setShootStrategy(new ScatterShoot(5)); // 散射
        return enemy;
    }
}