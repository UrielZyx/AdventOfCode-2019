package day11;

import java.util.Iterator;

public class GridIterator implements Iterator<Long> {

	PaintingRobot robot;
	
	public GridIterator(PaintingRobot paintingRobot) {
		robot = paintingRobot;
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public Long next() {
		return robot.getPannelColor(robot.getPosition());
	}

}
