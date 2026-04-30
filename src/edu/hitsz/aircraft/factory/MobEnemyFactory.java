package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EnemyType;
import edu.hitsz.aircraft.MobEnemy;

/**
 * 普通敌机工厂：向下方移动 + 不可左右移动 + 不发射子弹
 */
public class MobEnemyFactory implements EnemyFactory {
    private int hp;
    private int speedX;
    private int speedY;
    
    public MobEnemyFactory(double enemyHpFactor,double enemySpeedFactor) {
        this.hp = (int)(EnemyType.MOB.getHp() * enemyHpFactor);
        this.speedX = (int)(EnemyType.MOB.getSpeedX() * enemySpeedFactor);
        this.speedY = (int)(EnemyType.MOB.getSpeedY() * enemySpeedFactor);
    }
    @Override
    public AbstractAircraft createEnemy(int x, int y) {
        return new MobEnemy(x, y, speedX, speedY, hp);
    }

}