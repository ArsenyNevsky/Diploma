package quant;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.round;

/**
 * Created by erafiil on 06.05.15.
 */
public class Quant {

    public ArrayList<Integer> getDirectQuantedStream(ArrayList<Double> stream) {
        min = Collections.min(stream);
        max = Collections.max(stream);
        final int SIZE = stream.size();
        ArrayList<Integer> outputStream = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            outputStream.add((int)round((stream.get(i) - min) / (max - min) * 255));
        }
        return outputStream;
    }

    public ArrayList<Double> getConverseQuantStream(ArrayList<Integer> stream) {
        final int SIZE = stream.size();
        ArrayList<Double> outputStream = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            outputStream.add(stream.get(i) * (max - min) / 255.0 + min);
        }
        return outputStream;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    private double min;
    private double max;
}
