package day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.omg.Messaging.SyncScopeHelper;

import myMath.MyMath;

public class MoonTracker {

	List<Moon> moons = new ArrayList<>();
	String input;
	
	public MoonTracker(String input) {
		this.input = input;
		Arrays.stream(input.split(";")).forEach(moon->moons.add(new Moon(moon)));
	}

	public MoonTracker step(int n) {
		for (int i = 0; i < n; i++) {
			step();
		}
		return this;
	}

	private MoonTracker step() {
		moons.forEach(
				m1 -> moons.forEach(
						m2 -> m1.updateVelocities(m2)
				)
		);
		moons.forEach(Moon::updateCoordinates);
		return this;
	}

	public int getTotalEnergy() {
		return moons.stream()
				.mapToInt(Moon::getTotalEnergy)
				.sum();
	}

	@Override
	public String toString() {
		return moons.stream()
				.map(Moon::toString)
				.collect(Collectors.joining("\n")) 
				+ "\n" + getTotalEnergy(); 
	}

	public long findFirstRepetitionPoint() {
		long[] repetitionPoints = {0, 0, 0};
		Set<List<Integer>>[] states = new HashSet[] {new HashSet<>(), new HashSet<>(), new HashSet<>()};
		long i = 0;
		
		while (repetitionPoints[0] == 0 || repetitionPoints[1] == 0 || repetitionPoints[2] == 0) {
			for (int j = 0; j < repetitionPoints.length; j++) {
				if (repetitionPoints[j] > 0) {
					continue;
				}
				if (!states[j].add(getState(j))) {
					repetitionPoints[j] = i;
				}
			}
			step();
			i++;
		}
		
		return MyMath.lcm(repetitionPoints[0], MyMath.lcm(repetitionPoints[1], repetitionPoints[2]));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MoonTracker) {
			MoonTracker other = (MoonTracker) obj;
			for (int i = 0; i < moons.size(); i++) {
				if (!moons.get(i).equals(other.getMoons().get(i))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public List<Moon> getMoons() {
		return moons;
	}

	private List<Integer> getState(int i) {
		return moons.stream()
				.map(m->m.getState(i))
				.flatMap(List::stream)
				.collect(Collectors.toList());
	}
}
