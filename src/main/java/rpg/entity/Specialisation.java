package rpg.entity;

public enum Specialisation {
    KNIGHT(50, 250),
    ARCHER(40, 150),
    MAGICIAN(20, 500);

    private final Integer defaultNumber;
    private final Integer maxNumber;

    Specialisation(Integer defaultNumber, Integer maxNumber) {
        this.defaultNumber = defaultNumber;
        this.maxNumber = maxNumber;
    }

    public Integer getDefaultNumber() {
        return defaultNumber;
    }

    public Integer getMaxNumber() {
        return maxNumber;
    }
}