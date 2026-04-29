package edu.hitsz.prop.supply;

import java.util.ArrayList;
import java.util.List;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Game;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.observer.EnemyObserver;
import edu.hitsz.prop.observer.PropSubject;

public class BombSupply extends AbstractProp implements PropSubject{
    private List<EnemyObserver> observers = new ArrayList<>();

    public BombSupply(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft heroAircraft, Game game) {
        System.out.println("爆炸道具..");
    }

    @Override
    public void addObserver(EnemyObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(EnemyObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (EnemyObserver enemyObserver : observers) {
            enemyObserver.onBombActivated();
        }
    }

}
