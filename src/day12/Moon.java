package day12;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Moon {

	private int[] coordinates;
	private int[] velocities = new int[3];
	
	public Moon(String moon) {
		coordinates = Arrays.stream(
				moon
				.replaceAll("[\\<\\>]", "")
				.split(","))
				.mapToInt(
						s->Integer.parseInt(s.substring(s.indexOf('=') + 1))
				)
				.toArray();
	}

	public void updateVelocities(Moon other) {
		for (int i = 0; i < coordinates.length; i++) {
			velocities[i] += Integer.signum(other.getCoordinates()[i] - coordinates[i]);
		}
	}

	public void updateCoordinates() {
		for (int i = 0; i < coordinates.length; i++) {
			coordinates[i] += velocities[i];
		}
	}

	public int getTotalEnergy() {
		return Arrays.stream(coordinates).map(Math::abs).sum() 
				* Arrays.stream(velocities).map(Math::abs).sum();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Moon) {
			Moon other = (Moon) obj;
			for (int i = 0; i < coordinates.length; i++) {
				if (coordinates[i] != (other.getCoordinates()[i])) {
					return false;
				}
				if (velocities[i] != (other.getVelocities()[i])) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public int[] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(int[] coordinates) {
		this.coordinates = coordinates;
	}

	public int[] getVelocities() {
		return velocities;
	}

	public void setVelocities(int[] velocities) {
		this.velocities = velocities;
	}
	
	public List<Integer> getState(int i) {
		return Arrays.asList(coordinates[i], velocities[i]);
	}
	
	@Override
	public String toString() {
		return "position = <" 
				+ Arrays.stream(coordinates)
					.mapToObj(Integer::toString)
					.collect(Collectors.joining(",")) 
				+ ">, velocity = <" 
				+ Arrays.stream(velocities)
					.mapToObj(Integer::toString)
					.collect(Collectors.joining(","))
				+ ">, Energy = " 
				+ getTotalEnergy(); 
	}

}
