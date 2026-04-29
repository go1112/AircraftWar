package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.PropFactory;
import edu.hitsz.prop.PropType;

public class EliteEnemy extends AbstractAircraft {

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
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
        double eliteEnemyRand = 1;
        double typeRandom = Math.random();
        if (rand > eliteEnemyRand) {
            return null;
        }

        if (typeRandom < 0.33) {
            newProp = PropFactory.createProp(PropType.HP, propX, propY);
        } else if (typeRandom < 0.66) {
            newProp = PropFactory.createProp(PropType.FIRE, propX, propY);
        } else {
            newProp = PropFactory.createProp(PropType.FIRE_PLUS, propX, propY);
        }

        return newProp;
    }

    @Override
    public void onBombActivated() {
        System.out.println("炸弹道具生效 精英敌机坠毁...");
    }

    @Override
    public void onFrozenActivated() {
        System.out.println("冰冻道具生效 精英敌机静止4s后恢复...");
    }

}
