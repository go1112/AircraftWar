package edu.hitsz.prop.supply;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Game;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.shoot.RingShoot;

public class FirePlusSupply extends AbstractProp {

    public FirePlusSupply(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft heroAircraft, Game game) {
        // heroAircraft.activateFirePlusMode(5);
        heroAircraft.setShootStrategy(new RingShoot(20));
        System.out.println("FirePlusSupply active!");
    }

}
