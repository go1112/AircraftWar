package edu.hitsz.prop.supply;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.game.AbstractGame;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.PropEffectTimer;
import edu.hitsz.shoot.ScatterShoot;

public class FireSupply extends AbstractProp{

    public FireSupply(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft heroAircraft, AbstractGame game) {
        // 道具生效
        heroAircraft.setShootStrategy(new ScatterShoot(5));
        // 道具定时恢复
        PropEffectTimer timer = new PropEffectTimer(2, heroAircraft);
        game.setFireTimer(timer);
        new Thread(timer).start();
        // System.out.println("FireSupply active!");
    }

}
