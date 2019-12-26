package day23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.javatuples.Pair;

import intcode.IntcodeMachine;

public class NetworkController {

    final static int NUMBER_OF_MACHINES = 50;
    Queue<Long> input = new LinkedList<>();
    List<IntcodeMachine> machines = new ArrayList<>();
    
    String program;

	public NetworkController(String program) {
        this.program = program;
	}

	public long runMachines() {
		
        initMachines();
        runUntilNATPackage();
        
        input.poll();
        return input.poll();
	}
	

	public long runMachinesWithNAT() {
		
		Pair<Long, Long> currentNATPacket = Pair.with(null, null), previousNATPacket;
		Long lastYSentByNAP = null;
		
        initMachines();
        
        while (true) {
            runUntilNATPackage();
            previousNATPacket = currentNATPacket;
            currentNATPacket = Pair.with(input.poll(), input.poll());
            if (currentNATPacket.toList().stream().allMatch(Objects::isNull)) {
            	currentNATPacket = previousNATPacket;
            	if (lastYSentByNAP == currentNATPacket.getValue1()) {
					return lastYSentByNAP;
				}
            	lastYSentByNAP = currentNATPacket.getValue1();
				input.add(0L);
				input.add(currentNATPacket.getValue0());
				input.add(currentNATPacket.getValue1());
			}
		}
        
	}

	private void initMachines() {
		IntStream.range(0, NUMBER_OF_MACHINES)
        	.forEach(i -> {
        		machines.add(new IntcodeMachine(program)
									.addInput(Arrays.asList(i))
									.setOutputHandler(input::add));
        	});
	}

	private void runUntilNATPackage() {
		int address;
		runAllMachinesWithNoInput();
		
		while(!input.isEmpty()) {
			if (runMachineWithNextInput()) {
				break;
			}
			runAllMachinesWithNoInput();			
		}
	}

	private boolean runMachineWithNextInput() {
		int address;
		address = input.poll().intValue();
		System.out.println("Machine: " + address);
		if (address < NUMBER_OF_MACHINES) {
			machines.get(address)
					.addInput(Arrays.asList(input.poll(), input.poll()))
					.runProgram();
			return false;
		}
		return true;
	}

	private void runAllMachinesWithNoInput() {
		for (int i = 0; i < NUMBER_OF_MACHINES && input.isEmpty(); i++) {
			machines.get(i)
					.addInput(Arrays.asList(-1))
					.runProgram();
		}
	}

}
