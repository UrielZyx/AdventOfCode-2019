package day6;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.javatuples.Pair;

public class OrbitsCalculator {

    private final static String CENTER_OF_MASS = "COM";

    Orbit centerOfMass = new Orbit();
    Map<String, Orbit> orbits = new HashMap<>();

	public OrbitsCalculator(final String orbitMap) {
        orbits.put(CENTER_OF_MASS, centerOfMass);
        Arrays.stream(orbitMap.split(",")).map(orbit->Pair.fromArray(orbit.split("\\)"))).forEach(this::addOrbit);
    }

	public int calculateChecksum() {
		return calculateChecksum(orbits.get(CENTER_OF_MASS), 0);
    }
    
    private int calculateChecksum(Orbit orbit, int depth) {
        int sum = depth;
        for (Orbit satellite : orbit) {
            sum += calculateChecksum(satellite, depth+1);
        }
        return sum;
    }

	public int calculateOrbitalDistance(String source, String destination) {
        Map<String,Integer> pathToCom = new HashMap<>();
        
        Orbit orbit = orbits.get(source).getPrimary();
        for (int i = 0; orbit != null; i++) {
            pathToCom.put(orbit.getName(), i);
            orbit = orbit.getPrimary();
        }

        orbit = orbits.get(destination).getPrimary();
        for (int i = 0; orbit != null; i++) {
            if (pathToCom.containsKey(orbit.getName())) {
                return i + pathToCom.get(orbit.getName());
            }
            orbit = orbit.getPrimary();
        }

        return -1;
	}

    private void addOrbit(Pair<String, String> orbit) {
        String p = orbit.getValue0();
        String s = orbit.getValue1();
        orbits.putIfAbsent(p, new Orbit().setName(p));
        orbits.putIfAbsent(s, new Orbit().setName(s));
        orbits.get(s)
            .setPrimary(
                orbits.get(p
            )
        );
    }

}
