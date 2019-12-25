package day23;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import intcode.IntcodeMachine;

public class NetworkController {

    final static int NUMBER_OF_MACHINES = 50;
    Map<Integer, Boolean> initialized = new ConcurrentHashMap<>();
    Map<Integer ,Queue<Long>> inputs = new ConcurrentHashMap<>();
    Map<Integer ,Thread> threads = new ConcurrentHashMap<>();
    String program;

	public NetworkController(String program) {
        this.program = program;
	}

	public void runMachines() {

		ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_MACHINES);
        
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
