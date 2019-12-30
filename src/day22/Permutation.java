package day22;

@FunctionalInterface
public interface Permutation {
    long apply (long deckSize, long position, long parameter);
}
