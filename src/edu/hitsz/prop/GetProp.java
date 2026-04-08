package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;

public interface GetProp {
    AbstractProp obtainProp(AbstractAircraft enemyAircraft, double rand);
}
