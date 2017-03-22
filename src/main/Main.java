package main;

import main.quant.Quant;
import main.spline.SplineWaveletCompress;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    private static int[] a = {1, 2, 3, 4, 5, 0, 0, 0, 100, 200, 300, -40, -60, -80, -100, 0, 0, 0, 0};

    public static void main(String[] args) {
        System.out.println("Length = " + a.length + "\nArray = " + Arrays.toString(a));
        SplineWaveletCompress swc = new SplineWaveletCompress(a);
        swc.compress();
        Quant quantMainStream = new Quant();
        Quant quantWaveStream = new Quant();
        int net[] = swc.getNet();
        ArrayList<Integer> baseStream = quantMainStream.getDirectQuantedStream(swc.getMainStream());
        System.out.println("After quant A: " + baseStream);
        ArrayList<Double> streambase = quantMainStream.getConverseQuantStream(baseStream);

        ArrayList<Integer> waveStream = quantWaveStream.getDirectQuantedStream(swc.getWaveletStream());
        System.out.println("After quant B: " + waveStream);
        ArrayList<Double> streamWave = quantWaveStream.getConverseQuantStream(waveStream);

        swc.decompress(net, streambase, streamWave);

    }
}
