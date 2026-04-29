package edu.hitsz.prop.supply;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Game;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.PropType;
import edu.hitsz.prop.observer.ObserverManager;

public class BombSupply extends AbstractProp {

    public BombSupply(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft heroAircraft, Game game) {
        System.out.println("爆炸道具..");
        ObserverManager.getInstance().notifyObservers(PropType.BOMB);
    }

}
