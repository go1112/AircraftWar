package edu.hitsz.aircraft;

import java.util.LinkedList;
import java.util.List;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.GetProp;
import edu.hitsz.prop.PropFactory;
import edu.hitsz.prop.PropType;

public class BossEnemy extends AbstractAircraft {
    // 每次射击发射子弹数量
    private int shootNum = 1;

    // // 子弹威力
    // private int power = 5;

    // // 子弹射击方向 (向上发射：-1，向下发射：1)
    // private int direction = 1;

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.power = 5;
        this.direction = 1;
    }

    // @Override
    // public List<BaseBullet> shoot() {
    // List<BaseBullet> res = new LinkedList<>();
    // int x = this.getLocationX();
    // int y = this.getLocationY() + direction * 2;
    // int speedX = 0;
    // int speedY = this.getSpeedY() + direction * 5;
    // BaseBullet bullet;
    // for (int i = 0; i < shootNum; i++) {
    // // 子弹发射位置相对飞机位置向前偏移
    // // 多个子弹横向分散
    // bullet = new EnemyBullet(x, y, speedX, speedY, power);
    // res.add(bullet);
    // }
    // return res;
    // }

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
}
