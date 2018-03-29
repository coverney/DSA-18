import java.util.ArrayList;
import java.util.List;

public class CoinsOnAClock {

    /**
     * Creates a deep copy of the input array and returns it
     */
    private static char[] copyOf(char[] A) {
        char[] B = new char[A.length];
        System.arraycopy(A, 0, B, 0, A.length);
        return B;
    }

    //Time: O(3^n), n is height, an each node has three branches
    //Space: O(n^2): array of arrays
    public static List<char[]> coinsOnAClock(int pennies, int nickels, int dimes, int hoursInDay) {
        List<char[]> result = new ArrayList<>();
        char[]current = new char[hoursInDay];
        for (int j = 0; j < hoursInDay; j++) {
            current[j] = '.';
        }
        solve(result, current, pennies, nickels, dimes, hoursInDay, 0);
        return result;
    }

    private static void solve(List<char[]> solutions, char[] current, int p, int n, int d, int hrs, int index){
        if (n == 0 && d == 0 && p == 0){
            solutions.add(copyOf(current));
        }

        else{
            if (current[index] == '.'){
                for (int i = 0; i < 3; i++) {
                    if (i == 0 && p > 0){
                        current[index] = 'p';
                        solve(solutions, current, p-1, n, d, hrs, (index+1)%hrs);
                        current[index] = '.';
                    }
                    else if (i == 1 && n > 0){
                        current[index] = 'n';
                        solve(solutions, current, p, n-1, d, hrs, (index+5)%hrs);
                        current[index] = '.';
                    }
                    else if (i == 2 && d > 0){
                        current[index] = 'd';
                        solve(solutions, current, p, n, d-1, hrs, (index+10)%hrs);
                        current[index] = '.';
                    }
                }
            }
        }
    }
}
