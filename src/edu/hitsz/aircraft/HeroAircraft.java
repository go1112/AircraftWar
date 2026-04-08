package edu.hitsz.aircraft;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.shoot.StraightShoot;

/**
 * 英雄飞机，游戏玩家操控
 * 
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {
    // 私有静态实例
    private static volatile HeroAircraft instance = null;

    // 每次射击发射子弹数量
    private int shootNum = 3;


    public enum shootMode {
        NORMAL, // 普通模式
        FIRE, // 火力模式
        FIRE_PLUS // 超级火力模式
    }

    private shootMode currentMode = shootMode.NORMAL;
    private int effectFramesRemaining = 0;

    // 构造器私有化 把构造权掌握在自己手中
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.power = 30;
        this.direction = -1;

        this.setShootStrategy(new StraightShoot(shootNum));
    }

    // 提供全局访问点 双重锁定检查
    public static HeroAircraft getInstance(int locationX, int locationY, int speedX, int speedY, int hp) {
        if (instance == null) {
            synchronized (HeroAircraft.class) {
                if (instance == null) {
                    instance = new HeroAircraft(locationX, locationY, speedX, speedY, hp);
                }
            }
        }

        return instance;
    }

    // 重载方法
    public static HeroAircraft getInstance() {
        if (instance == null) {
            throw new IllegalStateException("HeroAircraft尚未初始化，请先调用getInstance(int, int, int, int, int)");
        }
        return instance;
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    public void updateEffect() {
        effectFramesRemaining--;
        if (effectFramesRemaining == 0) {
            // 时间到，恢复普通模式
            revertToNormalMode();
            System.out.println("弹道效果结束，恢复普通模式");
        }
    }

    public void revertToNormalMode() {
        currentMode = shootMode.NORMAL;
        this.shootNum = 3;
        this.power = 30;
    }

    public void activateFireMode(int durationSeconds) {
        // 转换为帧数（假设40ms/帧）
        effectFramesRemaining = durationSeconds * 25; // 1秒≈25帧
        currentMode = shootMode.FIRE;
        this.shootNum = 5;
        this.power = 30;
        System.out.println("火力模式激活，持续" + durationSeconds + "秒");
    }

    public void activateFirePlusMode(int durationSeconds) {
        // 转换为帧数（假设40ms/帧）
        effectFramesRemaining = durationSeconds * 25; // 1秒≈25帧
        currentMode = shootMode.FIRE;
        this.shootNum = 8;
        this.power = 50;
        System.out.println("火力加强模式激活，持续" + durationSeconds + "秒");
    }

    @Override
    public AbstractProp obtainProp(AbstractAircraft enemyAircraft, double rand) {
        return null;
    }
}
