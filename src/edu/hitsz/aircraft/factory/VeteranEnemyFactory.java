package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.VeteranEnemy;

public class VeteranEnemyFactory implements EnemyFactory {
    @Override
    public AbstractAircraft createEnemy(int x, int y) {
        return new VeteranEnemy(x, y, 0, 10, 30);
    }
}