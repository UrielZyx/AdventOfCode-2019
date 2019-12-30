package day22;

import java.math.BigInteger;

@FunctionalInterface
public interface Permutation {
    BigInteger apply (BigInteger deckSize, BigInteger position, BigInteger parameter);
}
