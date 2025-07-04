package pl.app.JWT_Backend.user.enums;

public enum Theme {
    LIGHT("LIGHT"),
    DARK("DARK");

    private final String value;

    Theme(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
