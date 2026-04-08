package edu.hitsz.shoot;

import java.util.LinkedList;
import java.util.List;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

/**
 * 散射模式
 */
public class ScatterShoot implements ShootStrategy {

    @Override
    public List<BaseBullet> shoot(int shooterX, int shooterY, int speedY, int direction, int power) {
                List<BaseBullet> res = new LinkedList<>();
        // 散射参数
        int bulletNum = 3; // 子弹数量
        int spreadAngle = 15; // 散射角度
        int baseSpeedY = speedY + direction * 5; // 基础垂直速度

        for (int i = 0; i < bulletNum; i++) {
            int offsetX = (i - bulletNum / 2) * spreadAngle;

            BaseBullet bullet = direction == -1 ? new HeroBullet(shooterX, shooterY, offsetX, baseSpeedY, power)
                    : new EnemyBullet(shooterX, shooterY, offsetX, baseSpeedY, power);

            res.add(bullet);
        }

        return res;
    }

}
