package day14;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.javatuples.Pair;

public class Reaction implements Iterable<Reaction> {

    private String name = "";
    private int outputAmount;
    private Map<Reaction, Integer> inputChemicals = new HashMap<>();

    public Reaction(String outputChemical) {
        name = outputChemical;
	}

	public String getName() {
        return name;
    }

    public Reaction setName(String name) {
        this.name = name;
        return this;
    }

	public Reaction setOutputAmount(int outputAmount) {
        this.outputAmount = outputAmount;
        return this;
    }

    public Reaction setInputChemicals(List<Pair<Reaction, Integer>> inputChemicals) {
        inputChemicals.stream().forEach(p -> this.inputChemicals.put(p.getValue0(), p.getValue1()));
        return this;
    }

    public int getOutputAmount() {
        return outputAmount;
    }

    @Override
    public Iterator<Reaction> iterator() {
        return inputChemicals.keySet().iterator();
    }

	public int getRequiredAmount(Reaction inputChemical) {
		return inputChemicals.getOrDefault(inputChemical, 0);
	}
}
