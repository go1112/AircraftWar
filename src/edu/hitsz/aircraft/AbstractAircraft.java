package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.shoot.ShootStrategy;
import edu.hitsz.basic.AbstractFlyingObject;

import java.util.LinkedList;
import java.util.List;

/**
 * 所有种类飞机的抽象父类
 * 
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {

    // 最大生命值
    protected int maxHp;
    protected int hp;

    // 射击策略模式
    protected ShootStrategy shootStrategy;

    // 射击方向（1向下，-1向上）
    protected int direction = 1;

    // 子弹威力
    protected int power = 10;

    // 构造器
    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    // 扣除血量
    public void decreaseHp(int decrease) {
        hp -= decrease;
        if (hp <= 0) {
            hp = 0;
            vanish(); // 标记消失
        }
    }

    // 获取血量
    public int getHp() {
        return hp;
    }

    // 获取最大血量
    public int getMaxHp() {
        return maxHp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setShootStrategy(ShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
    }

    // 使用策略模式射击
    public List<BaseBullet> shoot() {
        if (shootStrategy != null) {
            return shootStrategy.shoot(
                    this.getLocationX(),
                    this.getLocationY(),
                    this.getSpeedY(), // 传入子弹速度
                    this.direction, // 传入方向
                    this.power // 传入威力
            );
        }
        return new LinkedList<>();
    }

}
