package edu.hitsz.bullet;

import edu.hitsz.prop.observer.EnemyObserver;

/**
 * 敌机子弹
 * @Author hitsz
 */
public class EnemyBullet extends BaseBullet implements EnemyObserver{

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }

    @Override
    public void onBombActivated() {
        System.out.println("炸弹道具生效 敌机子弹消失...");
    }

    @Override
    public void onFrozenActivated() {
        System.out.println("炸弹道具生效 敌机子弹静止5s后恢复...");
    }

}
