package ru.lanit.model.criteria;

public enum Color {
    BLACK("Black"),
    BLUE("Blue"),
    RED("Red"),
    YELLOW("Yellow"),
    GREEN("Green"),
    ORANGE("Orange"),
    PURPLE("Purple"),
    WHITE("White");

    private final String displayValue;

    private Color(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
