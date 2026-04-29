package edu.hitsz.prop.supply;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.game.Game;
import edu.hitsz.prop.AbstractProp;

public class HpSupply extends AbstractProp {
    private int recoverHp = 20; // 恢复的生命值

    public HpSupply(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft heroAircraft, Game game) {
        int curHp = heroAircraft.getHp();
        int maxHp = heroAircraft.getMaxHp();
        heroAircraft.setHp(Math.min(maxHp, curHp + recoverHp));
    }



}
