package main.spline;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static java.lang.Math.pow;

/**
 * Created by erafiil on 05.04.15.
 */
public class SplineWaveletCompress {

    public SplineWaveletCompress() {

    }

    public SplineWaveletCompress(int[] c) {
        net                = new IrregularNet(c);
        this.x             = SourceNet.getSourceNet(c.length); // инициализируем исходную сетку

        a = new ArrayList<>(c.length);
        IntStream.range(0, c.length).forEach(elem -> a.add((double) elem));
    }

    /*public SplineWaveletCompress(int irregularIndexes[], double a[], ArrayList<Double> b) {
        net = new IrregularNet(SIZE_SOURCE_NET);
        this.x = net.getSourceNet();
        decompress(irregularIndexes, a, b);
    }*/

    public void compress() {
        int xi;
        int xiInd;
        ArrayList<Integer> indexes = net.getBinaryIndexes(); // получаем сетку, от которой зависит какие узлы удалим, а какие оставим
        b = new ArrayList<>(); // вейвлетный поток
        int increaseStep = 0;
        double value = 0;
        double c3 = 0;
        double c2 = 0;
        double c1 = 0;
        double c0 = 0;
        for (int index : indexes) { // проходимся по всем узлам
            xiInd = index - increaseStep;
            xi = x.get(xiInd);

            c3 = a.get(xiInd - 3);
            c2 = a.get(xiInd - 2);
            c1 = a.get(xiInd - 1);
            c0 = a.get(xiInd - 0);

            value = -(x.get(xiInd) - xi) * pow(xi - x.get(xiInd - 2), -1) * c3 +
                    c2 * (x.get(xiInd) - x.get(xiInd - 2)) * pow(xi - x.get(xiInd - 2), -1);
            a.set(xiInd - 2, value);
            a.remove(xiInd - 1);

            double n1 = ( (x.get(xiInd + 1) - xi) * (x.get(xiInd) - xi) * c3 );
            double n2 = -( (x.get(xiInd + 1) - xi) * (x.get(xiInd) - x.get(xiInd - 2)) * c2 );
            double n3 = ( (x.get(xiInd + 1) - x.get(xiInd - 1)) * (xi - x.get(xiInd - 2) ) * c1 );
            double n4 = -( (xi - x.get(xiInd - 1)) * (xi - x.get(xiInd - 2)) * c0 );
            double n = n1 + n2 + n3 + n4;

            double m = pow(x.get(xiInd + 1) - x.get(xiInd - 1), -1) * pow(xi - x.get(xiInd - 2), -1);  // item 1.17
            b.add(n * m);
            x.remove(xiInd);
            increaseStep++;
        }
        //System.out.println("Stream A after compressing: " + a);
        //System.out.println("Stream B after compressing: " + b);
    }

    /**
     *
     * @param irregularIndexes . сетка из 1 и 0
     * @param a . основной поток
     * @param b . вейвлетный поток
     */
    public void decompress(int irregularIndexes[], ArrayList<Double> a, ArrayList<Double> b) {
        x = new ArrayList<>();
        for (int i = 0; i < irregularIndexes.length; i++) { // воспроизводим узлы, которые не удаляли
            if (irregularIndexes[i] == 1) {
                x.add(i + 1);
            }
        }

        int size = irregularIndexes.length;
        int decreaseIndex = 0;
        int ind;
        double a3 = 0;
        double a2 = 0;
        double a1 = 0;
        double value = 0;
        for (int i = size - 1; i > 0; i--) {
            if (irregularIndexes[i] == 0) {
                ind = x.size() - decreaseIndex;
                int ksi = i + 1;
                x.add(ind, ksi);

                a3 = a.get(ind - 3);
                a2 = a.get(ind - 2);
                a1 = a.get(ind - 1);

                value = a3 * (x.get(ind) - ksi) * pow(x.get(ind) - x.get(ind - 2), -1) +
                        a2 * (ksi - x.get(ind - 2)) * pow(x.get(ind) - x.get(ind - 2), -1);
                a.add(ind - 2, value);
                value = a2 * (x.get(ind + 1) - ksi) * pow(x.get(ind + 1) - x.get(ind - 1), -1) +
                        a1 * (ksi - x.get(ind - 1)) * pow(x.get(ind + 1) - x.get(ind - 1), -1) + b.get(b.size() - 1);
                a.set(ind - 1, value);
                b.remove(b.size() - 1);
            }
            decreaseIndex++;
        }
        System.out.println("Length = " + a.size() + "\nRecovered = " + a);
    }

    public int[] getNet() {
        return net.getIrregularNet();
    }

    public String getBinaryNetString() {
        return net.getBinaryNetString();
    }

    public ArrayList<Double> getResultDecompressingStream() {
        return a;
    }

    public ArrayList<Double> getMainStream() {
        return a;
    }

    public ArrayList<Double> getWaveletStream() {
        return b;
    }

    public double[] getWaveletArray() {
        double waveletStream[] = new double[b.size()];
        for (int i = 0; i < b.size(); i++) {
            waveletStream[i] = b.get(i);
        }
        return waveletStream;
    }

    private ArrayList<Double> b;
    private ArrayList<Double> a;
    private ArrayList<Integer> x;
    private IrregularNet net;
}