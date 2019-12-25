package day23;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import intcode.IntcodeMachine;

public class NetworkController {

    final static int NUMBER_OF_MACHINES = 50;
    Set<Integer> initialized = new HashSet<>();
    Map<Integer ,Queue<Long>> inputs = new HashMap<>();
    Map<Integer ,Thread> threads = new HashMap<>();
    String program;

	public NetworkController(String program) {
        this.program = program;
	}

	public void runMachines() {

		ExecutorService executor = Executors.newFixedThreadPool(50);
        
        IntStream.range(0, NUMBER_OF_MACHINES)
            .forEach(i -> inputs.put(i, new LinkedList<>()));

        IntStream.range(0, NUMBER_OF_MACHINES)
            .forEach(i -> {
            	executor.execute(() -> 
                				new IntcodeMachine(program)
									.setInputIterator(new NetworkInputIterator(this, i))
				                    .setOutputHandler(new NetworkOutputHandler(this))
				                    .runProgram());
            });
        
        executor.shutdown();
	}

}
