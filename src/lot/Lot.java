package lot;

public abstract class Lot implements ILot {
    private final String name;

    public Lot(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }
}
