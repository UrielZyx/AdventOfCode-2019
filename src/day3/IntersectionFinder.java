package day3;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

import org.javatuples.Pair;
import org.javatuples.Tuple;
import org.omg.CORBA.portable.ApplicationException;

public abstract class IntersectionFinder<T> {

	private PriorityQueue<T> wire1;
	private PriorityQueue<T> wire2;
	private final Comparator<T> comparator;
	
	public IntersectionFinder(WirePath path1, WirePath path2) {
		wire1 = createWireQueue();
		wire2 = createWireQueue();

		trackPath(path1, wire1);
		trackPath(path2, wire2);
		
		comparator = getComparator();
	}

	public int findClosestIntersection() {
		T closestIntersection = searchQueues(wire1, wire2);
		return distance(closestIntersection);
	}

	protected T searchQueues(PriorityQueue<T> wire1, PriorityQueue<T> wire2) {
		T p1 = wire1.poll(), p2=wire2.poll();
		try {
			while (!wire1.isEmpty() || !wire2.isEmpty()) {
				if (comparator.compare(p1,p2) < 0) {
					p1 = wire1.poll();
				} else if (comparator.compare(p1,p2) > 0) {
					p2 = wire2.poll();
				} else  if (p1.equals(p2)){
					return p1;
				}
				else {
					throw new ApplicationException("Comparator isn't full!", null);
				}
			}
		}
		catch (Exception e) {
			return null;
		}
		return null;
	}
	
	protected abstract Comparator<T> getComparator();
	
	protected abstract PriorityQueue<T> createWireQueue();
	
	protected abstract void trackPath(WirePath path, PriorityQueue wire);

	protected abstract int distance(T p);
}
