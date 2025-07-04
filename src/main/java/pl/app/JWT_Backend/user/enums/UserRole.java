package pl.app.JWT_Backend.user.enums;

public enum UserRole {
    EMPLOYEE("EMPLOYEE"),
    MANAGER("MANAGER"),
    ADMIN("ADMIN"),
    IT("IT"),
    NONE("NONE");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static UserRole fromValue(String value) {
        for (UserRole role : UserRole.values()) {
            if (role.getValue().equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + value);
    }
}
