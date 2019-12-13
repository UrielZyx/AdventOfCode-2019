package day13;

import java.util.Iterator;

public class joystickIterator implements Iterator<Long>{

    BreakoutGame game;

    public joystickIterator(BreakoutGame breakoutGame) {
        this.game = breakoutGame;
	}

	@Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Long next() {
        return game.getJoystickPosition();
    }

}
