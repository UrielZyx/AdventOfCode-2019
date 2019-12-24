package day21;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import intcode.IntcodeMachine;

public class SpringDroid {

    private static final String SPRING_DROID_PROGRAM_WALK = ""
    + "NOT B J\n"
    + "NOT C T\n"
    + "OR T J\n"
    + "AND D J\n"
    + "NOT A T\n"
    + "OR T J\n"
    + "WALK\n";

    private static final String SPRING_DROID_PROGRAM_RUN = ""
    + "NOT A J\n"
    + "AND D J\n"

    + "NOT B T\n"
    + "AND D T\n"
    + "AND H T\n"
    + "OR T J\n"
    
    + "NOT C T\n"
    + "AND D T\n"
    + "AND E T\n"
    + "OR T J\n"
    
    + "NOT C T\n"
    + "AND D T\n"
    + "AND H T\n"
    + "OR T J\n"
    
    + "RUN\n";

    IntcodeMachine machine;

	public SpringDroid(String program) {
        machine = new IntcodeMachine(program);
	}

	public void run() {
        run(false);
    }

    public void run(boolean shouldRun) {
        String program = shouldRun? SPRING_DROID_PROGRAM_RUN: SPRING_DROID_PROGRAM_WALK;
        machine.replaceInput(program
                                .chars()
                                .mapToObj(Integer::valueOf)
                                .collect(Collectors.toList()))
                .setOutputHandler(o -> System.out.print(o < 255? Character.valueOf((char)o.intValue()): " " + o))
                .runProgram();
	}

}
