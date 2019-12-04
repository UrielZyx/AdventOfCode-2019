package day4;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Day4_1 {

    public static void main(final String[] args) {
        final List<Predicate<Integer>> validators = new ArrayList<>();
        validators.add(PasswordValidators::hasRepeatingDigit);
        validators.add(PasswordValidators::isNonDecreasing);
        System.out.println(PasswordFinder.countValidPasswords(109165,576723, validators));
    }

}