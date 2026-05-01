package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;

// 抽象敌机工厂
public interface EnemyFactory {
    AbstractAircraft createEnemy(int x, int y);
    void setParams(double enemyHpFactor, double enemySpeedFactor);
}
