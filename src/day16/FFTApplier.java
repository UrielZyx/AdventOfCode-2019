package day16;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FFTApplier {

    final static int[] basePattern = {0, 1, 0, -1};
    int[] signal;

	public FFTApplier(String input, int repeat) {
        StringBuilder inputBuilder = new StringBuilder();
        for (int i = 0; i < repeat; i++) {
            inputBuilder.append(input);
        }
        setSignal(inputBuilder.toString());
	}

	public FFTApplier(String inputSignal) {
        setSignal(inputSignal);
    }
    
    private void setSignal(String inputSignal) {
        signal = new int[inputSignal.length()];
        for (int i = 0; i < signal.length; i++) {
            signal[i] = inputSignal.charAt(i) - '0';
        }
    }

	public void apply(int n) {
        int[] temp;
        long  start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            // System.out.println(i + " iterations");
            // System.out.println(System.currentTimeMillis() - start);
            temp = new int[signal.length];
            for (int j = 0; j < temp.length; j++) {
                temp[j] = caculate(j+1);
                System.out.print(temp[j]);
            }
            signal = temp;
        }
	}

    public String getResultString() {
        return getResultString(signal.length);
	}

    public String getResultString(int i) {
        return getResultString(i, 0);
	}

	public String getResultString(int i, int offset) {
        return Arrays.stream(signal)
                    .skip(offset)
                    .limit(i)
                    .mapToObj(Integer::toString)
                    .collect(Collectors.joining());
	}

	private int caculate(int j) {
        int sum = 0;

        for (int i = 0; i < signal.length; i++) {
            sum += signal[i] * basePattern[(i + 1) / j % basePattern.length];
        }

        return Math.abs(sum % 10);
    }

}
