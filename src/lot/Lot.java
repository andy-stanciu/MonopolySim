package lot;

public abstract class Lot implements ILot {
    private final String name;
    private final String formattedName;

    public Lot(String name, String formattedName) {
        this.name = name;
        this.formattedName = formattedName;
    }

    public String getName() {
        return name;
    }

    public String getFormattedName() {
        return formattedName;
    }

    public String toString() {
        return name;
    }
}
