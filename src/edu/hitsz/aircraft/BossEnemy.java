package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.PropFactory;
import edu.hitsz.prop.PropType;

public class BossEnemy extends AbstractAircraft {

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
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
        double typeRandom = Math.random();
        if (typeRandom < 0.5) {
            newProp = PropFactory.createProp(PropType.HP, propX, propY);
        } else if (typeRandom < 0.7) {
            newProp = PropFactory.createProp(PropType.FIRE, propX, propY);
        } else if (typeRandom < 0.8) {
            newProp = PropFactory.createProp(PropType.FIRE_PLUS, propX, propY);
        } else if (typeRandom < 0.9) {
            newProp = PropFactory.createProp(PropType.BOMB, propX, propY);
        } else {
            newProp = PropFactory.createProp(PropType.FROZEN, propX, propY);
        }

        return newProp;

    }

    @Override
    public void onBombActivated() {
        // System.out.println("炸弹道具生效 BOSS敌机不受影响...");
    }

    @Override
    public void onFrozenActivated() {
        // System.out.println("冰冻道具生效 BOSS敌机不受影响...");
    }
}
