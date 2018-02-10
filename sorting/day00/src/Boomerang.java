import java.util.HashMap;
import java.util.Map;

public class Boomerang {

    public static int numberOfBoomerangs(int[][] points) {
        Map<Double, Integer> map = new HashMap<Double, Integer>();
        int result = 0;
// go through each point and find all the distances between each point and the others, then add the distances to the Hashmap.
// The values with greater than one means that two points were equidistant to the first point, so you want to add n(n-1) to the final count
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {

                if (i == j){
                    continue;
                }

                double distance = getDistance(points[i], points[j]);

                if (!map.containsKey(distance)){
                    map.put(distance, 1);
                }

                else{
                    map.put(distance, map.get(distance)+1);
                }
            }

            for (int value: map.values()) {
                result += value*(value-1);
            }
            map.clear();
        }

        return result;
    }

    private static double getDistance(int[] a, int[] b){
        double dx = b[0] - a[0];
        double dy = b[1] - a[1];
        double distance = (Math.pow(dx, 2) + Math.pow(dy, 2));
        return Math.pow(distance, 0.5);
    }
}

