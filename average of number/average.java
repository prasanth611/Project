import java.util.*;

public class average {
    public static void main(String args[]) {

        List<Integer> list = Arrays.asList(10, 20, 30, 40, 50, 60, 70, 80, 90, 100);

        int sum = 0;

        for (int i : list) {

            sum += i;
        }

        if (list.isEmpty()) {
            System.out.println("List is empty!");
        } else {

            System.out.println("The average of the List is: " + sum / (float) list.size());
        }
    }
}