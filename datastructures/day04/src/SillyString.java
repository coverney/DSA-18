import java.util.HashMap;
import java.util.Map;

public class SillyString {
    private final String innerString;

    public SillyString(String innerString) {
        this.innerString = innerString;
    }

    public String toString() {
        return innerString;
    }

    @Override
    public boolean equals(Object other) {
        return this.toString().equals(other.toString());
    }

    @Override
    public int hashCode() {
        // This hashCode function is not really good because it isn't random enough. In other words, it depends on the length of the world.
        //If you have two strings with similar lengths, then their hashCode would be very similar (Hello is close to World).
        // Also, the order of the characters does not matter so two strings that are anagrams would have the same hashCode.
        int total = 0;
        for (int i=0; i<innerString.length(); i++) {
            total += innerString.charAt(i);
        }
        return total;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        SillyString first = new SillyString("Hello");
        SillyString second = new SillyString("World");
        SillyString third = new SillyString("Olelh");

        System.out.println(first.hashCode());
        System.out.println(second.hashCode());
        System.out.println(third.hashCode());
    }
}
