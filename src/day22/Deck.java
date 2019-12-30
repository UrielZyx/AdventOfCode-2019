package day22;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.javatuples.Pair;

public class Deck {

    final static BigInteger ZERO = BigInteger.valueOf(0);
    final static BigInteger MINUS_ONE = BigInteger.valueOf(-1);
    final static BigInteger ONE = BigInteger.valueOf(1);
    final static BigInteger TWO = BigInteger.valueOf(2);

    enum Technique {
        NEW_STACK ((size, parameter) -> Pair.with(MINUS_ONE, size.subtract(ONE))),
        //size - 1 - position
        CUT (
            (size, parameter) -> Pair.with(ONE, size.subtract(parameter)),
            //position - parameter + size 
            (size, parameter) -> Pair.with(ONE, size.add(parameter))
            //position + parameter + size
        ),
        INCREMENT (
            (size, parameter) -> Pair.with(parameter, ZERO),
            //position * parameter
            (size, parameter) -> Pair.with(reciprocal(parameter, size), ZERO)
            //position * reciprocal(parameter, size)
        );

        Permutation permute;
        Permutation reverse;

        Technique (Permutation permute) {
            this(permute, permute);
        }

        Technique (Permutation permute, Permutation reverse) {
            this.permute = permute;
            this.reverse = reverse;
        }
        
        static Technique valueOf(String[] line) {
            if(line[0].equals("cut")) {
                return CUT;
            }
            if (line[1].equals("into")) {
                return NEW_STACK;
            }
            return INCREMENT;
        }
    }

    BigInteger deckSize;
    List<Pair<Technique, BigInteger>> techniques = new ArrayList<>();
    Pair<BigInteger, BigInteger> shufflePermutation = Pair.with(ONE, ZERO);
    Pair<BigInteger, BigInteger> reverseShufflePermutation = Pair.with(ONE, ZERO);

	public Deck(long size) {
        deckSize = BigInteger.valueOf(size);
	}

    public void setShufflingMethod(List<String> input) {
        String[] splitLine;
        Technique technique;
        BigInteger parameter;
        for (String line : input) {
            splitLine = line.split(" ");
            technique = Technique.valueOf(splitLine);
            parameter = ZERO;
            try{
                parameter = BigInteger.valueOf(Long.parseLong(splitLine[splitLine.length - 1]));
            } catch (Exception e){
                //Nothing
            }
            techniques.add(Pair.with(technique, parameter));
        }
        for (int i = 0; i < techniques.size(); i++) {
            shufflePermutation = composePermutation(
                                    shufflePermutation, 
                                    getPermutation(techniques.get(i)));
            reverseShufflePermutation = composePermutation(
                                    reverseShufflePermutation, 
                                    getReverse(techniques.get(techniques.size() - i - 1)));
        }
	}

    public void shuffle(long i) {
        BigInteger index = BigInteger.valueOf(i);
        System.out.println(index
                            .multiply(shufflePermutation.getValue0())
                            .add(shufflePermutation.getValue1())
                            .mod(deckSize)
        );
	}

	public void findOrigin(long i, long numberOfIterations) {
        BigInteger t = BigInteger.valueOf(i);
        BigInteger orig = BigInteger.valueOf(i);
        for (long j = 0; j < numberOfIterations; j++) {
            t = findOrigin(t);
            if (t.compareTo(orig) == 0) {
                System.out.println(t);
            }
        }
        System.out.println(t);
	}

	public BigInteger findOrigin(BigInteger i) {
        Pair<Technique, BigInteger> p;
        for (int j = techniques.size() - 1; j >= 0; j--) {
            p = techniques.get(j);
            // i = p.getValue0().reverse.apply(deckSize, i, p.getValue1());
        }
        return i;
	}

	public static BigInteger reciprocal(BigInteger parameter, BigInteger size) {
        Stack<BigInteger> powers = new Stack<>();
        Stack<BigInteger> powerCounters = new Stack<>();
        BigInteger result = ONE;
        BigInteger resultCounter = ZERO;
        powers.push(parameter);
        powerCounters.push(ONE);
        while (powerCounters.peek().compareTo(size.subtract(TWO)) < 0) {
            powers.push((powers.peek().multiply(powers.peek())).remainder(size));
            powerCounters.push(powerCounters.peek().multiply(TWO));
        }
        while (resultCounter.compareTo(size.subtract(TWO)) < 0) {
            if (resultCounter.add(powerCounters.peek()).compareTo(size.subtract(TWO)) <= 0) {
                resultCounter = resultCounter.add(powerCounters.peek());
                result = (result.multiply(powers.peek())).remainder(size);
            }
            powerCounters.pop();
            powers.pop();
        }
        return result;
    }

    private Pair<BigInteger, BigInteger> getPermutation(Pair<Technique, BigInteger> pair) {
        return pair.getValue0().permute.apply(deckSize, pair.getValue1());
    }

    private Pair<BigInteger, BigInteger> getReverse(Pair<Technique, BigInteger> pair) {
        return pair.getValue0().reverse.apply(deckSize, pair.getValue1());
    }

	private Pair<BigInteger, BigInteger> composePermutation(Pair<BigInteger, BigInteger> original,
    Pair<BigInteger, BigInteger> additional) {
        BigInteger a, b, c, d, alpha, beta;
        a = original.getValue0();
        b = original.getValue1();
        c = additional.getValue0();
        d = additional.getValue1();
        alpha = a.multiply(c).mod(deckSize);
        beta = b.multiply(c).add(d).mod(deckSize);
        return Pair.with(alpha, beta);
    }
}
