package edu.hitsz.prop.supply;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Game;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.PropEffectTimer;
import edu.hitsz.shoot.RingShoot;

public class FirePlusSupply extends AbstractProp {

    public FirePlusSupply(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft heroAircraft, Game game) {
        // 道具生效
        heroAircraft.setShootStrategy(new RingShoot(20));
        // 道具定时恢复
        PropEffectTimer timer = new PropEffectTimer(5, heroAircraft);
        game.setFireTimer(timer);
        new Thread(timer).start();
        // System.out.println("FirePlusSupply active!");
    }

}
