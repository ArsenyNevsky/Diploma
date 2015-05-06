package spline;

import java.util.ArrayList;

/**
 * Created by erafiil on 06.04.15.
 */
public class SourceNet {

    public static ArrayList<Integer> getSourceNet(int N) {
        ArrayList<Integer> x = new ArrayList<Integer>(N);
        for (int i = 0; i < N; i++) {
            x.add(i + 1);
        }
        return x;
    }
}
