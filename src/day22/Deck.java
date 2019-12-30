package day22;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.javatuples.Pair;

public class Deck {

    final static BigInteger ZERO = BigInteger.valueOf(0);
    final static BigInteger ONE = BigInteger.valueOf(1);
    final static BigInteger TWO = BigInteger.valueOf(2);

    enum Technique {
        NEW_STACK ((size, position, parameter) -> size.subtract(position.add(ONE))),
        CUT (
            (size, position, parameter) -> (position.subtract(parameter).add(size)).remainder(size), 
            (size, position, parameter) -> (position.add(parameter).add(size)).remainder(size)
        ),
        INCREMENT (
            (size, position, parameter) -> (position.multiply(parameter)).remainder(size),
            (size, position, parameter) -> (position.multiply(reciprocal(parameter, size))).remainder(size)
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
	}

	public void shuffle(long i) {
        BigInteger index = BigInteger.valueOf(i);
        for (Pair<Technique, BigInteger> p : techniques) {
            index = p.getValue0().permute.apply(deckSize, index, p.getValue1());
        }
        System.out.println(index);
	}

	public void findOrigin(long i, long numberOfIterations) {
        BigInteger t = BigInteger.valueOf(i);
        for (long j = 0; j < numberOfIterations; j++) {
            t = findOrigin(t);
        }
        System.out.println(t);
	}

	public BigInteger findOrigin(BigInteger i) {
        Pair<Technique, BigInteger> p;
        for (int j = techniques.size() - 1; j >= 0; j--) {
            p = techniques.get(j);
            i = p.getValue0().reverse.apply(deckSize, i, p.getValue1());
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
}
