package day23;

import java.util.Iterator;
import java.util.LinkedList;

public class NetworkInputIterator implements Iterator<Long>{

    NetworkController controller;
    int index;

    public NetworkInputIterator(NetworkController networkController, int i) {
        controller = networkController;
        index = i;
	}

	@Override
    public boolean hasNext() {
        if (!controller.inputs.getOrDefault(index, new LinkedList<>()).isEmpty() && controller.inputs.get(index).peek() == -1) {
            return false;
        }
        return true;
    }

    @Override
    public Long next() {
        if (!controller.initialized.contains(index)) {
            controller.initialized.add(index);
            return Long.valueOf(index);
        }
        if (controller.inputs.getOrDefault(index, new LinkedList<>()).isEmpty()) {
            return -1L;
        }
        return controller.inputs.get(index).poll();
    }

    @Override
    public void remove() {
        // Do nothing
    }
}