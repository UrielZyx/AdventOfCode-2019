package day16;

import java.util.Arrays;
import java.util.stream.Collectors;

public class RepeatedInputFFTApplier {

    int[] signal;

	public RepeatedInputFFTApplier(String inputSignal, int numberOfRepeats, int offsetLength) {
        int totalLength = inputSignal.length() * numberOfRepeats;
        int offset = Integer.parseInt(inputSignal.substring(0, offsetLength));
        signal = new int[totalLength - offset];
        
        for (int i = 0; i < signal.length; i++) {
            signal[i] = inputSignal.charAt((offset + i) % inputSignal.length()) - '0';
        }
	}

	public void apply(int n) {
        for (int i = 0; i < n; i++) {
            apply();
        }
	}

	private void apply() {
        for (int i = signal.length - 1; i > 0; i--) {
            signal[i-1] += signal[i];
            signal[i-1] %= 10;
        }
    }

    public String getResultString(int i) {
        return Arrays
                .stream(signal)
                .limit(i)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());
	}

}