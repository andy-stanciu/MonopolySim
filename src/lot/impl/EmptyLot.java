package lot.impl;

import lot.ILot;
import lot.Lot;

public class EmptyLot extends Lot implements ILot {
    public EmptyLot(String name, String formattedName) {
        super(name, formattedName);
    }
}
