package day22;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.javatuples.Pair;

public class Deck {

    enum Technique {
        NEW_STACK ((size, position, parameter) -> size - position - 1),
        CUT (
            (size, position, parameter) -> (position - parameter + size) % size, 
            (size, position, parameter) -> (position + parameter + size) % size
        ),
        INCREMENT (
            (size, position, parameter) -> (position * parameter) % size,
            (size, position, parameter) -> (position * reciprocal(parameter, size)) % size
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

    long deckSize;
    List<Pair<Technique, Long>> techniques = new ArrayList<>();

	public Deck(long size) {
        deckSize = size;
	}

    public void setShufflingMethod(List<String> input) {
        String[] splitLine;
        Technique technique;
        long parameter;
        for (String line : input) {
            splitLine = line.split(" ");
            technique = Technique.valueOf(splitLine);
            parameter = 0;
            try{
                parameter = Long.parseLong(splitLine[splitLine.length - 1]);
            } catch (Exception e){
                //Nothing
            }
            techniques.add(Pair.with(technique, parameter));
        }
	}

	public void shuffle(long i) {
        for (Pair<Technique, Long> p : techniques) {
            i = p.getValue0().permute.apply(deckSize, i, p.getValue1());
        }
        System.out.println(i);
	}

	public void findOrigin(long i, long numberOfIterations) {
        long t = i;
        for (long j = 0; j < numberOfIterations; j++) {
            t = findOrigin(t);
        }
        System.out.println(t);
	}

	public long findOrigin(long i) {
        Pair<Technique, Long> p;
        for (int j = techniques.size() - 1; j >= 0; j--) {
            p = techniques.get(j);
            i = p.getValue0().reverse.apply(deckSize, i, p.getValue1());
        }
        return i;
	}

	public static long reciprocal(long parameter, long size) {
        Stack<Long> powers = new Stack<>();
        Stack<Long> powerCounters = new Stack<>();
        long result = 1;
        long resultCounter = 0;
        powers.push(parameter);
        powerCounters.push(1L);
        while (powerCounters.peek() < size - 2) {
            powers.push((powers.peek() * powers.peek()) % size);
            powerCounters.push(powerCounters.peek() * 2);
        }
        while (resultCounter < size - 2) {
            if (resultCounter + powerCounters.peek() <= size - 2) {
                resultCounter += powerCounters.peek();
                result = (result * powers.peek()) % size;
            }
            powerCounters.pop();
            powers.pop();
        }
        return result;
    }
}
