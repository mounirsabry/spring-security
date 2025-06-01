package jets.spring_security.utils;

public class DataValidator {
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_-]{4,20}$";
    private static final String PASSWORD_PATTERN = "^.{8,32}$";

    public static void validateId(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id must start from 1");
        }
    }

    private static void validateString(String string, String prefix) {
        if (string == null) {
            throw new IllegalArgumentException(prefix + " must not be null");
        }

        String trimmed = string.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(prefix + " must not be blank");
        }

        if (!trimmed.equals(string)) {
            throw new IllegalArgumentException(prefix + " must not contain leading or trailing spaces");
        }
    }
    
    public static void validateUsernameString(String username) {
        final String prefix = "username";
        validateString(username, prefix);
        
        if (!username.matches(USERNAME_PATTERN)) {
            throw new IllegalArgumentException(prefix + " must only contains (alphanumeric, -, _) and " +
                    "has length between 4 and 20 characters");
        }
    }

    public static void validatePasswordString(String password) {
        final String prefix = "password";
        validateString(password, prefix);

        if (!password.matches(PASSWORD_PATTERN)) {
            throw new IllegalArgumentException(prefix + " must be between 8 and 32 characters");
        }
    }
}
