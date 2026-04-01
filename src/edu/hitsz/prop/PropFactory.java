package edu.hitsz.prop;

import edu.hitsz.prop.supply.BombSupply;
import edu.hitsz.prop.supply.FirePlusSupply;
import edu.hitsz.prop.supply.FireSupply;
import edu.hitsz.prop.supply.FrozenSupply;
import edu.hitsz.prop.supply.HpSupply;

// 道具简单工厂
public class PropFactory {
    public static AbstractProp createProp(PropType type, int x, int y) {
        switch (type) {
            case HP:
                return new HpSupply(x, y, 0, 3);
            case FIRE:
                return new FireSupply(x, y, 0, 3);
            case FIRE_PLUS:
                return new FirePlusSupply(x, y, 0, 3);
            case BOMB:
                return new BombSupply(x, y, 0, 3);
            case FROZEN:
                return new FrozenSupply(x, y, 0, 3);
            default:
                throw new IllegalArgumentException("Undefined : " + type);
        }
    }
}
