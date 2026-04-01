package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteEnemy;

public class EliteEnemyFactory implements EnemyFactory {
    @Override
    public AbstractAircraft createEnemy(int x, int y) {
        return new EliteEnemy(x, y, 0, 10, 30);
    }
}