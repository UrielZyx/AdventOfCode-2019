package day4;

public class PasswordValidators {
    
    public static boolean hasRepeatingDigit(Integer n) {
        String s = n.toString();
        char c = s.charAt(0);
        for (int i = 1; i < s.length(); i++) {
            if (c == s.charAt(i)) {
                return true;
            }
            c = s.charAt(i);
        }
        return false;
    }
    
    public static boolean isNonDecreasing(Integer n) {
        String s = n.toString();
        char c = s.charAt(0);
        for (int i = 1; i < s.length(); i++) {
            if (c > s.charAt(i)) {
                return false;
            }
            c = s.charAt(i);
        }
        return true;
    }
    
    public static boolean hasIsolatedRepeatingDigit(Integer n) {
        String s = n.toString();
        char c = s.charAt(0);

        for (int i = 1, j; i < s.length(); i++) {
            if (c == s.charAt(i)) {
                if (i == s.length() - 1 || c != s.charAt(i+1)) {
                    return true;
                }
                for (j = i+1; j < s.length() && c == s.charAt(j); j++);
                if(j < s.length()) {
                    i = j;
                } else {
                    return false;
                }
            }
            c = s.charAt(i);
        }
        return false;
    }
}
