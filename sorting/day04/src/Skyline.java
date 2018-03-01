import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Skyline {

    static class Point {
        int x, y;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Building {
        private int l, r, h;

        Building(int l, int r, int h) {
            this.l = l;
            this.r = r;
            this.h = h;
        }
    }

    // Given an array of buildings, return a list of points representing the skyline
    //O(nlogn)
    public static ArrayList<Point> skyline(Building[] B) {
        // similar to merge sort but have a unique merge function
        ArrayList<Point> lst = new ArrayList<Point>();
        if (B.length == 0) {
            return lst;
        }

        if (B.length == 1) {
            // skyline for one building is just its top left and bottom right corners
            lst.add(new Point(B[0].l, B[0].h));
            lst.add(new Point(B[0].r, 0));
            return lst;
        }

        int mid = B.length / 2;


        Building[] left = Arrays.copyOfRange(B, 0, mid);
        Building[] right = Arrays.copyOfRange(B, mid, B.length);


        ArrayList<Point> left_skyline = skyline(left);
        ArrayList<Point> right_skyline = skyline(right);

        return merge(right_skyline, left_skyline);
    }

    public static ArrayList<Point> merge(ArrayList<Point> right, ArrayList<Point> left) {
        ArrayList<Point> lst = new ArrayList<Point>();

        // do a merge based on x position and which height is greater
        int h1 = 0;
        int h2 = 0;

        int i = 0;
        int j = 0;
        while (i < left.size() && j < right.size()) {
            // go through both lists and add point with smaller x (more left) and larger y (taller height)

            if (left.get(i).x < right.get(j).x) {
                int x = left.get(i).x;
                h1 = left.get(i).y;
                int maxh = Math.max(h1, h2);
                lst.add(new Point(x, maxh));
                i++;
            } else {
                int x = right.get(j).x;
                h2 = right.get(j).y;
                int maxh = Math.max(h1, h2);
                lst.add(new Point(x, maxh));
                j++;
            }
        }
        // add the leftovers
        while (i < left.size()) {
            lst.add(left.get(i));
            i++;
        }

        while (j < right.size()) {
            lst.add(right.get(j));
            j++;
        }

        // remove redundancy: consecutive values with same height and consecutive values with same x value
        int k = 1;
        while (k < lst.size()) {
            if (lst.get(k - 1).y == lst.get(k).y) {
                //remove k
                lst.remove(k);
            } else if (lst.get(k - 1).x == lst.get(k).x) {
                // if same x remove the shorter one
                if (lst.get(k - 1).y > lst.get(k).y) {
                    lst.remove(k);
                } else {
                    lst.remove(k - 1);
                }
            } else {
                k++;
            }
        }

        return lst;
    }
}
