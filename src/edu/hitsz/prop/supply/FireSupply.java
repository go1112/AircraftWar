package edu.hitsz.prop.supply;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Game;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.PropEffectTimer;
import edu.hitsz.shoot.ScatterShoot;

public class FireSupply extends AbstractProp{

    public FireSupply(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft heroAircraft, Game game) {
        // heroAircraft.activateFireMode(5);
        heroAircraft.setShootStrategy(new ScatterShoot(5));
        PropEffectTimer timer = new PropEffectTimer(2, heroAircraft);
        game.setFireTimer(timer);
        new Thread(timer).start();
        System.out.println("FireSupply active!");
    }

}
