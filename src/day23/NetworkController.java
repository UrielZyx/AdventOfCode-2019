package day23;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.IntStream;

import intcode.IntcodeMachine;

public class NetworkController {

    final static int NUMBER_OF_MACHINES = 50;
    Map<Integer ,IntcodeMachine> machines = new HashMap<>();
    Set<Integer> initialized = new HashSet<>();
    Map<Integer ,Queue<Long>> inputs = new HashMap<>();
    String program;

	public NetworkController(String program) {
        this.program = program;
	}

	public void runMachines() {
        IntStream.range(0, NUMBER_OF_MACHINES)
            .parallel()
            .forEach(i -> {
                inputs.put(i, new LinkedList<>());
                machines.put(i, new IntcodeMachine(program));
                machines.get(i)
                    .setInputIterator(new NetworkInputIterator(this, i))
                    .setOutputHandler(new NetworkOutputHandler(this))
                    .runProgram();
            });
	}

}
