package rpg.entity;

public enum Race {
    HUMAN(100, 600),
    ORC(200, 400),
    DWARF(80, 1000);

    private final Integer defaultNumber;
    private final Integer maxNumber;

    Race(Integer defaultNumber, Integer maxNumber) {
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