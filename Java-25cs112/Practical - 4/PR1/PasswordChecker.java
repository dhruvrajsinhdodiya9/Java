public class PasswordChecker {
    public static boolean hasLength(String pw) {
        return pw != null && pw.length() >= 8;
    }

    public static boolean hasUpper(String pw) {
        return pw != null && pw.matches(".*[A-Z].*");
    }

    public static boolean hasDigit(String pw) {
        return pw != null && pw.matches(".*[0-9].*");
    }

    public static boolean hasSpecial(String pw) {
        return pw != null && pw.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");
    }

    public static String strength(String pw) {
        int passed = 0;
        if (hasLength(pw)) passed++;
        if (hasUpper(pw)) passed++;
        if (hasDigit(pw)) passed++;
        if (hasSpecial(pw)) passed++;

        if (passed <= 1) {
            return "Weak";
        } else if (passed <= 3) {
            return "Medium";
        } else {
            return "Strong";
        }
    }
}