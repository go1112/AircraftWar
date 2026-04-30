package edu.hitsz.application.game;

import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.EnemyType;
import edu.hitsz.application.ImageManager;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.rank.Difficulty;

public class IntermendtateGame extends AbstractGame {

    public IntermendtateGame() {
        super();
        this.difficulty = Difficulty.INTERMEDIATE;
    }

    @Override
    protected void initGameSettings() {
        try {
            ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg3.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.initHeroHp = 1000;
        this.bossEnemyHp = 1000;

        this.enemyMaxNumber = 10;
        this.enemySpawnCycle = 15;
        this.heroShootCycle = 15;
        this.enemyShootCycle = 15;
        this.enemyHpFactor = 1.2;
        this.enemySpeedFactor = 1.2;

        this.scoreThreshold = 1000;

        this.propRand = 0.6;
        this.difficultyLevelUpInterval = 1500;
    }

    @Override
    protected EnemyType getRandomEnemyType() {
        double rand = Math.random();
        if (rand < 0.4)
            return EnemyType.MOB; // 40% 普通
        if (rand < 0.65)
            return EnemyType.ELITE; // 25% 精英
        if (rand < 0.85)
            return EnemyType.VETERAN; // 20% 精锐
        return EnemyType.ACE; // 15% 王牌
    }

    @Override
    protected boolean shouldSpawnBoss() {
        return !bossSpawned && score >= scoreThreshold && (bossEnemy == null || bossEnemy.notValid());
    }

    @Override
    protected int setBossEnemyHp() {
        return this.bossEnemyHp;
    }

    @Override
    protected void triggerProp(AbstractAircraft enemyAircraft) {

        if (!(enemyAircraft instanceof BossEnemy)) {
            AbstractProp newProp = enemyAircraft.obtainProp(enemyAircraft, propRand);
            if (newProp != null) {
                props.add(newProp);
            }
        } else {
            for (int i = 0; i < 3; i++) {
                AbstractProp newProp = enemyAircraft.obtainProp(enemyAircraft, 1.0);
                if (newProp != null) {
                    props.add(newProp);
                }
            }

            scoreThreshold = (int) (score * 1.2);
            bossSpawned = false;
            // System.out.println("BOSS敌机被击毁 下次出现的分数阈值为：" + scoreThreshold);
        }

    }

    @Override
    protected void difficultyLevelUp() {
        this.enemyMaxNumber = Math.min(15, this.enemyMaxNumber + 1);
        this.enemySpawnCycle = Math.max(10, this.enemySpawnCycle * 0.9);
        this.enemyHpFactor = Math.min(2, this.enemyHpFactor * 1.1);
        this.enemySpeedFactor = Math.min(2, this.enemySpeedFactor * 1.1);
        System.out.println("==========提高难度===========");
        System.out.println(String.format("敌机最大数量 = %d 个", enemyMaxNumber));
        System.out.println(String.format("敌机产生周期 = %.2f 秒", (double) enemySpawnCycle * 50 / 1000));
        System.out.println(String.format("敌机血量增值 = %.2f 倍", enemyHpFactor));
        System.out.println(String.format("敌机速度增值 = %.2f 倍", enemySpeedFactor));
        System.out.println("============================");
    }
}
