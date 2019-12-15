package day13;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.javatuples.Pair;

import intcode.IntcodeMachine;

public class BreakoutGame {

    enum Mode {
        X ((g,o) -> g.x=o),
        Y ((g,o) -> g.y=o),
        ELEMENT ((g,o) -> g.setElement(o));

        final BiConsumer<BreakoutGame, Long> update;

        private Mode (BiConsumer<BreakoutGame, Long> update) {
            this.update = update;
        }
    }

    IntcodeMachine machine;
    Map<Pair<Long, Long>, Integer> elementsMap = new HashMap<>();
    private long x, y, ballX, paddleX;
    private Mode mode = Mode.X;
    Long joystick = 0L;

	public BreakoutGame(String memory) {
        machine = new IntcodeMachine(memory).setOutputHandler(this::outputHandler);
	}

    public void run() {
        machine.runProgram();
	}

	public void play() {
        machine.setInputIterator(new joystickIterator(this)).runProgram();
	}

    public long getCount(int type) {
        return elementsMap.keySet().stream()
            .map(elementsMap::get)
            .filter(x->x.equals(type))
            .count();
    }

	public int getScore() {
		return elementsMap.getOrDefault(Pair.with(-1L, 0L), 0);
	}

	public void setElement(Long o) {
        elementsMap.put(Pair.with(x, y), o.intValue());
        if (o == 3) {
            paddleX = x;
        } else if (o == 4) {
            ballX = x;
        }
    }
    
    private void outputHandler(Long output) {
        mode.update.accept(this, output);
        updateMode();
    }

    private void updateMode() {
        mode = Mode.values()[(mode.ordinal() + 1) % Mode.values().length];
    }

    public void setJoystick(Long joystick) {
        this.joystick = joystick;
    }

	public Long getJoystickPosition() {
		return (long) Long.signum(ballX - paddleX);
	}
}
