package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
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

    /**
     * 飞机射击方法
     * 
     * @return
     *         可射击对象需实现，返回子弹列表
     *         非可射击对象空实现，返回空列表
     */
    public abstract List<BaseBullet> shoot();

}
