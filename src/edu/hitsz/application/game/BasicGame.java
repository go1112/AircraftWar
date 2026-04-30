package edu.hitsz.application.game;

import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.AceEnemy;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.EnemyType;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.aircraft.VeteranEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.rank.Difficulty;

public class BasicGame extends AbstractGame {

    public BasicGame(Difficulty difficulty) {
        super(difficulty);
    }

    @Override
    protected void initGameSettings() {
        try {
            ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg2.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected EnemyType getRandomEnemyType() {
        double rand = Math.random();
        if (rand < 0.3)
            return EnemyType.MOB; // 30% 普通
        if (rand < 0.6)
            return EnemyType.ELITE; // 30% 精英
        if (rand < 0.8)
            return EnemyType.VETERAN; // 20% 精锐
        return EnemyType.ACE; // 20% 王牌
    }

    @Override
    protected boolean shouldSpawnBoss() {
        return false;
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

}
