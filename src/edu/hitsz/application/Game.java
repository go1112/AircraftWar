package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.aircraft.factory.AceEnemyFactory;
import edu.hitsz.aircraft.factory.BossEnemyFactory;
import edu.hitsz.aircraft.factory.EliteEnemyFactory;
import edu.hitsz.aircraft.factory.EnemyFactory;
import edu.hitsz.aircraft.factory.MobEnemyFactory;
import edu.hitsz.aircraft.factory.VeteranEnemyFactory;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.GetProp;
import edu.hitsz.prop.PropFactory;
import edu.hitsz.prop.PropType;
import edu.hitsz.prop.supply.FirePlusSupply;
import edu.hitsz.prop.supply.FireSupply;
import edu.hitsz.prop.supply.HpSupply;
import edu.hitsz.basic.AbstractFlyingObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.*;
import java.util.function.Predicate;

/**
 * 游戏主面板，游戏启动
 * 
 * @author hitsz
 */
public class Game extends JPanel {

    private int backGroundTop = 0;

    // 调度器, 用于定时任务调度
    private final Timer timer;
    // 时间间隔(ms)，控制刷新频率
    private final int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts; // 多态数组
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<AbstractProp> props;

    private final Map<EnemyType, EnemyFactory> enemyFactories = new EnumMap<>(EnemyType.class);

    // 屏幕中出现的敌机最大数量
    private final int enemyMaxNumber = 5;

    // 敌机生成周期
    protected double enemySpawnCycle = 20;
    private int enemySpawnCounter = 0;

    // 英雄机和敌机射击周期
    protected double shootCycle = 20;
    private int shootCounter = 0;

    // 当前玩家分数
    private int score = 0;

    // 游戏结束标志
    private boolean gameOverFlag = false;

    // Boss机产生标志
    private boolean bossSpawned = false;
    // Boss机产生的分数阈值器
    private int scoreThreshold = 100;
    // Boss机
    private AbstractAircraft bossEnemy = null;

    public Game() {
        // 由于采用单例模式 所以用getInstance方法来获取对象
        heroAircraft = HeroAircraft.getInstance(
                Main.WINDOW_WIDTH / 2,
                Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
                0, 0, 3000);

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        enemyFactories.put(EnemyType.MOB, new MobEnemyFactory());
        enemyFactories.put(EnemyType.ELITE, new EliteEnemyFactory());
        enemyFactories.put(EnemyType.VETERAN, new VeteranEnemyFactory());
        enemyFactories.put(EnemyType.ACE, new AceEnemyFactory());
        enemyFactories.put(EnemyType.BOSS, new BossEnemyFactory());

        // 启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

        this.timer = new Timer("game-action-timer", true);

    }

    // 随机选择敌机类型（可控制概率）
    private EnemyType getRandomEnemyType() {
        double rand = Math.random();
        if (rand < 0.5)
            return EnemyType.MOB; // 50% 普通
        if (rand < 0.7)
            return EnemyType.ELITE; // 20% 精英
        if (rand < 0.85)
            return EnemyType.VETERAN; // 15% 精锐
        return EnemyType.ACE; // 15% 王牌
    }

    private int getRandomWidth(EnemyType enemyType) {
        int imageWidth = 0;
        switch (enemyType) {
            case MOB:
                imageWidth = ImageManager.MOB_ENEMY_IMAGE.getWidth();
                break;
            case ELITE:
                imageWidth = ImageManager.ELITE_ENEMY_IMAGE.getWidth();
                break;
            case VETERAN:
                imageWidth = ImageManager.VETERAN_ENEMY_IMAGE.getWidth();
            case ACE:
                imageWidth = ImageManager.ACE_ENEMY_IMAGE.getWidth();
            case BOSS:
                imageWidth = ImageManager.BOSS_ENEMY_IMAGE.getWidth();
            default:
                break;
        }
        return (int) (Math.random() * (Main.WINDOW_WIDTH - imageWidth));
    }

    private int getRandomHeight() {
        return (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05);
    }

    private boolean shouldSpawnBoss() {
        return !bossSpawned && score >= scoreThreshold && (bossEnemy == null || bossEnemy.notValid());
    }

    private void spawnBossEnemy() {
        bossEnemy = new BossEnemyFactory().createEnemy(getRandomWidth(EnemyType.BOSS), 0);
        enemyAircrafts.add(bossEnemy);
        bossSpawned = true;
    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        // 定时任务：绘制、对象产生、碰撞判定、及结束判定
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                enemySpawnCounter++;
                if (enemySpawnCounter >= enemySpawnCycle) {
                    enemySpawnCounter = 0;
                    enemyAircrafts.add(
                            enemyFactories.get(getRandomEnemyType()).createEnemy(getRandomWidth(getRandomEnemyType()),
                                    getRandomHeight()));
                }

                if (shouldSpawnBoss()) {
                    spawnBossEnemy();
                }

                // 飞机发射子弹
                shootAction();
                // 子弹移动
                bulletsMoveAction();
                // 飞机移动
                aircraftsMoveAction();
                // 道具移动
                propMoveAction();
                // 道具持续时间
                heroAircraft.updateEffect();
                // 撞击检测
                crashCheckAction();
                // 后处理
                postProcessAction();
                // 重绘界面
                repaint();
                // 游戏结束检查
                checkResultAction();
            }
        };
        // 以固定延迟时间进行执行：本次任务执行完成后，延迟 timeInterval 再执行下一次
        timer.schedule(task, 0, timeInterval);

    }

    // ***********************
    // Action 各部分
    // ***********************

    private void shootAction() {
        shootCounter++;
        if (shootCounter >= shootCycle) {
            shootCounter = 0;
            // 英雄机射击
            heroBullets.addAll(heroAircraft.shoot());
            // 敌机射击
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                enemyBullets.addAll(enemyAircraft.shoot());
            }
        }
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propMoveAction() {
        for (AbstractProp prop : props) {
            prop.forward();
        }
    }

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        triggerReward(enemyAircraft);
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // 我方获得道具，道具生效
        for (AbstractProp prop : props) {
            if (prop.notValid()) {
                continue;
            }
            if (prop.crash(heroAircraft)) {
                prop.activate(heroAircraft, this);
                prop.vanish();
            }
        }

    }

    private void triggerReward(AbstractAircraft enemyAircraft) {
        if (enemyAircraft instanceof MobEnemy) {
            score += 10;
        } else if (enemyAircraft instanceof EliteEnemy) {
            score += 20;
        } else if (enemyAircraft instanceof VeteranEnemy) {
            score += 30;
        } else if (enemyAircraft instanceof AceEnemy) {
            score += 50;
        } else {
            score += 100;
        }

        if (!(enemyAircraft instanceof BossEnemy)) {
            AbstractProp newProp = enemyAircraft.obtainProp(enemyAircraft, Math.random());
            if (newProp != null) {
                props.add(newProp);
            }
        } else {
            for (int i = 0; i < 3; i++) {
                AbstractProp newProp = enemyAircraft.obtainProp(enemyAircraft, Math.random());
                if (newProp != null) {
                    props.add(newProp);
                }
            }

            scoreThreshold = (int)(score * 1.1);
            bossSpawned = false;
            System.out.println("BOSS敌机被击毁 下次出现的分数阈值为：" + scoreThreshold);
        }
    }


    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 删除无效的道具
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
        // Todo: 删除无效道具
    }

    /**
     * 检查游戏是否结束，若结束：关闭线程池
     */
    private void checkResultAction() {
        // 游戏结束检查英雄机是否存活
        if (heroAircraft.getHp() <= 0) {
            timer.cancel(); // 取消定时器并终止所有调度任务
            gameOverFlag = true;
            System.out.println("Game Over!");
        }
    };

    // ***********************
    // Paint 各部分
    // ***********************
    /**
     * 重写 paint方法
     * 通过重复调用paint方法，实现游戏动画
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, enemyAircrafts);
        paintImageWithPositionRevised(g, props);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        // 绘制得分和生命值
        paintScoreAndLife(g);

    }

    /**
     * 
     * @param g       画笔
     * @param objects 列表（元素是AbstractFlyingObject以及它的子类） 所以包括：飞机类，子弹类，道具类
     */
    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.isEmpty()) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(Color.RED);
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE: " + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE: " + this.heroAircraft.getHp(), x, y);
    }

}
