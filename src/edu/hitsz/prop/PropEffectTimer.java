package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.shoot.ShootStrategy;
import edu.hitsz.shoot.StraightShoot;

public class PropEffectTimer implements Runnable {
    private int durationSeconds;
    private HeroAircraft heroAircraft;
    private ShootStrategy originalStrategy = new StraightShoot(3);
    private volatile boolean isCancelled = false;

    public PropEffectTimer(int durationSeconds, HeroAircraft heroAircraft) {
        this.durationSeconds = durationSeconds;
        this.heroAircraft = heroAircraft;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < durationSeconds; i++) {
                if (isCancelled) {
                    break;
                }
                Thread.sleep(1000);
            }
            if (!isCancelled) {
                heroAircraft.setShootStrategy(originalStrategy);
                System.out.println("恢复原始射击.......");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void cancel() {
        this.isCancelled = true;
    }
}
