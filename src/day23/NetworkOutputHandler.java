package day23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class NetworkOutputHandler implements Consumer<Long>{

    NetworkController controller;
    List<Long> outputs = new ArrayList<>();

    public NetworkOutputHandler(NetworkController networkController) {
        controller = networkController;
	}

	@Override
    public void accept(Long o) {
        outputs.add(o);
        if (outputs.size() == 3) {
            if (outputs.get(0) == 255) {
                controller.inputs.keySet().stream()
                    .map(controller.inputs::get)
                    .forEach(q -> q.add(-1L));
                System.out.println(outputs.get(2));
            } else {
                while (!controller.inputs.containsKey(outputs.get(0).intValue()));
                controller.inputs
                    .get(outputs.get(0).intValue())
                    .addAll(
                        Arrays.asList(outputs.get(1), 
                        outputs.get(2)));
            }
            outputs = new ArrayList<>();
        }
    }
}
