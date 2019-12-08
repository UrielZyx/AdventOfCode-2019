package day6;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Orbit implements Iterable<Orbit> {

    String name;
    Orbit primary = null;
    List<Orbit> Satellites = new ArrayList<>();

    private void addSatellite(final Orbit satellite) {
        Satellites.add(satellite);
    }

    @Override
    public Iterator<Orbit> iterator() {
        return Satellites.iterator();
    }

    public String getName() {
        return name;
    }

    public Orbit setName(String name) {
        this.name = name;
        return this;
    }

    public Orbit getPrimary() {
        return primary;
    }

    public Orbit setPrimary(final Orbit primary) {
        this.primary = primary;
        primary.addSatellite(this);
        return this;
    }
}
