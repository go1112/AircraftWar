package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EnemyType;
import edu.hitsz.aircraft.VeteranEnemy;
import edu.hitsz.shoot.StraightShoot;

/**
 * 精锐敌机工厂：向屏幕下方移动 + 且可左右移动 + 按设定周期向下直射双排子弹
 */
public class VeteranEnemyFactory implements EnemyFactory {
    private int hp;
    private int speedX;
    private int speedY;

    public VeteranEnemyFactory(double enemyHpFactor, double enemySpeedFactor) {
        setParams(enemyHpFactor, enemySpeedFactor);
    }

    @Override
    public AbstractAircraft createEnemy(int x, int y) {
        VeteranEnemy enemy = Math.random() > 0.5 ? new VeteranEnemy(x, y, speedX, speedY, hp)
                : new VeteranEnemy(x, y, -speedX, speedY, hp);
        enemy.setShootStrategy(new StraightShoot(2)); // 双排直射
        return enemy;
    }

    @Override
    public void setParams(double enemyHpFactor, double enemySpeedFactor) {
        this.hp = (int) (EnemyType.VETERAN.getHp() * enemyHpFactor);
        this.speedX = (int) (EnemyType.VETERAN.getSpeedX() * enemySpeedFactor);
        this.speedY = (int) (EnemyType.VETERAN.getSpeedY() * enemySpeedFactor);
    }
}