package main.spline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import static java.lang.Math.*;


public class IrregularNet {

    /**
     * @param c - исходный поток
     */
    public IrregularNet(int c[]) {
        this.N = c.length;
        this.c = c;
        x = SourceNet.getSourceNet(N);
        C = getC();
        xx = new int[N];
        generateIndexesIrregularNet();
    }

    public IrregularNet(int N) {
        this.N = N;
        x = SourceNet.getSourceNet(N);
    }

    public ArrayList<Integer> getBinaryIndexes() {
        return binaryIndexes;
    }

    /**
     * @return сетку, заполненную 1 и 0;
     */
    public int[] getIrregularNet() {
        return xx;
    }

    /**
     * @return исходную сетку
     */
    public ArrayList<Integer> getSourceNet() {
        return x;
    }

    /**
     * @return представление сетки в виде набора 0 и 1, которые обозначают какие
     * узлы надо удалить, а какие оставить
     */
    public String getBinaryNetString() {
        return binary.toString();
    }

    private void generateIndexesIrregularNet() {
        binaryIndexes = new ArrayList<>();
        Arrays.fill(xx, 1);
        binary = new StringBuilder(N);
        IntStream.range(0, N).forEach(i -> binary.append(1));

        int S = 3;
        int currentS;
        while (S < N) { // или S < N - 3
            currentS = getNewIndexS(S);
            for (int i = S; i < (S + currentS - 1) && (i < N - 3); i++) {
                xx[i] = 0;
                binary.setCharAt(i, '0');
                binaryIndexes.add(i);
            }
            S += currentS;
        }
    }

    private int getNewIndexS(int i) {
        //q = getQ(i);
        q = 14;
        int g = 10;
        double P = 0.6;
        double summ = 0;
        /*for (int j = i; j < i + q - 1 && (i + q - 1) < N; j++) {
            summ += abs((c.get(j) - c.get(j - 1)) / (float) (x.get(j) - x.get(j - 1)));
        }*/
        for (int j = i; j < min(i + q - 1, N); j++) {
            summ += abs((c[j] - c[j - 1]) / (float) (x.get(j) - x.get(j - 1)));
        }
        int S = (int) round(C / (P * (summ / q + C / (double) g)));
        return max(1, S);
    }

    private double getC() {
        double C = 0;
        for (int i = 0; i < N - 1; i++) {
            //C += abs((c[i] - c[i - 1]) / (float) (x[i] - x[i - 1])); // if index i start from 1 to N
            C += abs((c[i + 1] - c[i]) / (double) (x.get(i + 1) - x.get(i)));
        }

        return C / N;
    }

    private int getQ(int i) {
        return min(10, N - i);
    }

    private ArrayList<Integer> binaryIndexes;
    private StringBuilder binary;
    private int xx[];
    private double C; // эта константа необходима для алгоритма выбора сетки
    private int q = 10;
    private int[] c;
    private ArrayList<Integer> x;
    private final int N;
}
