package me.andyst.monopoly.lot.impl;

import me.andyst.monopoly.lot.ILot;
import me.andyst.monopoly.lot.Lot;

public class EmptyLot extends Lot implements ILot {
    public EmptyLot(String name, String formattedName) {
        super(name, formattedName);
    }
}
