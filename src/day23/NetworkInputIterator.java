package day23;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class NetworkInputIterator implements Iterator<Long> {

    NetworkController controller;
    int index;

    public NetworkInputIterator(NetworkController networkController, int i) {
        controller = networkController;
        index = i;
    }

    @Override
    public boolean hasNext() {
        if (!controller.inputs.getOrDefault(index, new LinkedList<>()).isEmpty()
                && controller.inputs.get(index).peek() == null) {
            // System.out.println("Machine " + index + " is exiting!");
            return false;
        }
        return true;
    }

    @Override
    public Long next() {
        if (!controller.initialized.containsKey(index)) {
            controller.initialized.put(index, true);
            return Long.valueOf(index);
        }
        if (controller.inputs.getOrDefault(index, new LinkedList<>()).isEmpty()) {
            try {
                Random r = new Random();
                Thread.sleep(Math.abs(r.nextInt() % 1000));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return -1L;
        }
        // System.out.println("machine: " + index + ", input: " + controller.inputs.get(index).peek());
        return controller.inputs.get(index).poll();
    }

    @Override
    public void remove() {
        // Do nothing
    }
}