package edu.hitsz.prop.supply;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.game.AbstractGame;
import edu.hitsz.application.game.Game;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.PropType;
import edu.hitsz.prop.observer.ObserverManager;


public class FrozenSupply extends AbstractProp {
    public FrozenSupply(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft heroAircraft, AbstractGame game) {
        System.out.println("冰冻道具");
        ObserverManager.getInstance().notifyObservers(PropType.FROZEN);
    }


}
