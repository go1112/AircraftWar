package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.MobEnemy;

public class MobEnemyFactory implements EnemyFactory {

    @Override
    public AbstractAircraft createEnemy(int x, int y) {
        return new MobEnemy(x, y, 0, 10, 30);
    }

}