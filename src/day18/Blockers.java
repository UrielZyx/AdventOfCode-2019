package day18;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Blockers implements Iterable<Character> {

    Set<Character> doors = new HashSet<>();

    public Blockers add(Blockers other) {
        other.forEach(doors::add);
        return this;
    }

    public void add(char c) {
        doors.add(c);
    }

    @Override
    public String toString() {
        return doors
            .stream()
            .sorted()
            .map(Object::toString)
            .collect(Collectors.joining());
    }

    @Override
    public Iterator<Character> iterator() {
        return doors.iterator();
    }

	public boolean doesNotBlock(Set<Character> keys) {
		return doors.stream()
			.map(Character::toLowerCase)
			.map(keys::contains)
			.allMatch(b -> b);
	}
}