package day4;

import java.util.List;
import java.util.function.Predicate;

public class PasswordFinder {

	public static int countValidPasswords(int start, int end, List<Predicate<Integer>> validators) {
        int count = 0;
        boolean isValid;
        for (int i = start; i <= end; i++) {
            isValid = true;
            for (Predicate<Integer> validator : validators) {
                if(validator.negate().test(i)) {
                    isValid = false;
                }
            }
            if (isValid) {
                count++;
            }
        }
        return count;
	}

}
