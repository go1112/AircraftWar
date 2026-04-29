package edu.hitsz.application.game;

import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.AceEnemy;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.EnemyType;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.aircraft.VeteranEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.prop.AbstractProp;

public class BeginningGame extends AbstractGame {

    @Override
    protected EnemyType getRandomEnemyType() {
        double rand = Math.random();
        if (rand < 0.5)
            return EnemyType.MOB; // 50% 普通
        if (rand < 0.7)
            return EnemyType.ELITE; // 20% 精英
        if (rand < 0.85)
            return EnemyType.VETERAN; // 15% 精锐
        return EnemyType.ACE; // 15% 王牌
    }

    @Override
    protected int getEnemySpawnCycle() {
        return 20;
    }

    @Override
    protected int getEnemyMaxNumber() {
        return 5;
    }

    @Override
    protected int getHeroShootCycle() {
        return 20;
    }

    @Override
    protected int getEnemyShootCycle() {
        return 20;
    }

    @Override
    protected void triggerReward(AbstractAircraft enemyAircraft) {
        if (enemyAircraft instanceof MobEnemy) {
            addScore(EnemyType.MOB.getScore());
        } else if (enemyAircraft instanceof EliteEnemy) {
            addScore(EnemyType.ELITE.getScore());
        } else if (enemyAircraft instanceof VeteranEnemy) {
            addScore(EnemyType.VETERAN.getScore());
        } else if (enemyAircraft instanceof AceEnemy) {
            addScore(EnemyType.ACE.getScore());
        } else {
            addScore(EnemyType.BOSS.getScore());
        }

        AbstractProp newProp = enemyAircraft.obtainProp(enemyAircraft, Math.random());
        if (newProp != null) {
            props.add(newProp);
        }

    }

    @Override
    protected boolean shouldSpawnBoss() {
        return false;
    }

    @Override
    protected void initGameSettings() {
        try {
            ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
