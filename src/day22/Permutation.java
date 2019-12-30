package day22;

import java.math.BigInteger;

import org.javatuples.Pair;

@FunctionalInterface
public interface Permutation {
    Pair<BigInteger, BigInteger> apply (BigInteger deckSize, BigInteger parameter);
}
