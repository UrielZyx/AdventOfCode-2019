package day14;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.javatuples.Pair;

public class NanoFactory {

	private final static String FUEL = "FUEL";
	private final static String ORE = "ORE";

	Map<String, Reaction> reactions = new HashMap<>();

	public NanoFactory(List<String> reactionStrings) {
		reactionStrings.forEach(s -> addReaction(s.split("=>")));
	}

	public long numberOfOresNeededForFuel( long fuelAmount) {
		Map<Reaction, Long> required = new HashMap<>();
		Map<Reaction, Long> leftover = new HashMap<>();
		Reaction[] temp = new Reaction[0];
		Reaction currentReaction;
		long requiredChemicalLeftoverAmount, requiredAmount, baseRequirement, actualGenerated;

		required.put(reactions.get(FUEL), fuelAmount);
		
		while (required.size() > 1 || !required.containsKey(reactions.get(ORE))) {
			temp = required.keySet().toArray(temp);
			currentReaction = temp[0];
			if (currentReaction.equals(reactions.get(ORE))) {
				currentReaction = temp[1];
			}
			
			baseRequirement = required.get(currentReaction) - leftover.getOrDefault(currentReaction, 0L);
			if (baseRequirement < 0) {
				leftover.put(currentReaction, leftover.get(currentReaction) + baseRequirement);
				continue;
			}
			leftover.remove(currentReaction);
			actualGenerated = baseRequirement / currentReaction.getOutputAmount() + Long.signum(baseRequirement % currentReaction.getOutputAmount());
			leftover.put(currentReaction, actualGenerated * currentReaction.getOutputAmount() - baseRequirement);

			for (Reaction requiredChemical : currentReaction) {
				requiredAmount = actualGenerated * currentReaction.getRequiredAmount(requiredChemical);
				requiredChemicalLeftoverAmount = leftover.getOrDefault(requiredChemical, 0L);
				if (requiredAmount < requiredChemicalLeftoverAmount) {
					leftover.put(currentReaction, requiredChemicalLeftoverAmount - requiredAmount);
					continue;
				}
				leftover.remove(requiredChemical);
				requiredAmount -= requiredChemicalLeftoverAmount;

				required.put(
					requiredChemical, 
					required.getOrDefault(requiredChemical, 0L) + requiredAmount);
			}
			required.remove(currentReaction);
		}

		return required.get(reactions.get(ORE));
	}

	private void addReaction(String[] reactionParams) {
		String outputChemical = reactionParams[1].trim().split(" ")[1];
		int outputAmount = Integer.parseInt(reactionParams[1].trim().split(" ")[0]);
		List<Pair<Reaction, Integer>> inputChemicals = 
			Arrays.stream(reactionParams[0].trim().split(","))
				.map(String::trim)
				.map(s -> s.split(" "))
				.map(arr -> {
					reactions.putIfAbsent(arr[1], new Reaction(arr[1]));
					return Pair.with(
						reactions.get(arr[1]), 
						Integer.parseInt(arr[0]));})
				.collect(Collectors.toList());
		reactions.putIfAbsent(outputChemical, new Reaction(outputChemical));
		reactions.get(outputChemical)
			.setOutputAmount(outputAmount)
			.setInputChemicals(inputChemicals);
	}

}
