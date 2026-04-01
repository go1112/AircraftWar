package edu.hitsz.aircraft;

import java.util.List;

import edu.hitsz.bullet.BaseBullet;

public class AceEnemy extends AbstractAircraft{

    public AceEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        //TODO Auto-generated constructor stub
    }

    @Override
    public List<BaseBullet> shoot() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'shoot'");
    }

}
