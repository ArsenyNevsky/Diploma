package spline;

import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * Created by erafiil on 06.04.15.
 */
public class SourceNet {

    public static ArrayList<Integer> getSourceNet(int N) {
        ArrayList<Integer> x = new ArrayList<Integer>(N);
        IntStream.range(0, N).forEach(i -> x.add(++i));
        return x;
    }
}
