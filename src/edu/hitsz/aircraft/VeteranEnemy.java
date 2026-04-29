package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.PropFactory;
import edu.hitsz.prop.PropType;
/**
 * 精锐敌机
 */
public class VeteranEnemy extends AbstractAircraft {

    public VeteranEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.power = 5;
        this.direction = 1;
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    public AbstractProp obtainProp(AbstractAircraft enemyAircraft, double rand) {
        int propX = enemyAircraft.getLocationX();
        int propY = enemyAircraft.getLocationY();
        AbstractProp newProp = null;
        double veteranEnemyRand = 1;
        double typeRandom = Math.random();
        if (rand > veteranEnemyRand) {
            return null;
        }

        if (typeRandom < 0.3) {
            newProp = PropFactory.createProp(PropType.HP, propX, propY);
        } else if (typeRandom < 0.6) {
            newProp = PropFactory.createProp(PropType.FIRE, propX, propY);
        } else if (typeRandom < 0.8) {
            newProp = PropFactory.createProp(PropType.FIRE_PLUS, propX, propY);
        } else {
            newProp = PropFactory.createProp(PropType.BOMB, propX, propY);
        }

        return newProp;
    }

    @Override
    public void onBombActivated() {
        System.out.println("炸弹道具生效 精锐敌机坠毁...");
    }

    @Override
    public void onFrozenActivated() {
        System.out.println("炸弹道具生效 精锐敌机静止3s后恢复...");
    }

}
