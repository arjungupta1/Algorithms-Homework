import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class SolutionD {

    public static void main(String[] args) {
        int outputA, outputB, outputC;
        ArrayList<Integer> nums = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        boolean[] lookup = new boolean[10000];
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            int num = Integer.parseInt(line);
            nums.add(num);
            lookup[num] = true;
        }

        outputA = outputB = outputC = 0;
        for (int a = 0; a < nums.size(); a++) {
            for (int b = a + 1; b < nums.size(); b++) {
                if ((a * b) < 10000 && lookup[a * b]) {
                    outputA = a;
                    outputB = b;
                    outputC = a * b;
                }
            }
        }
        System.out.println(outputA + "\n" + outputB + "\n" + outputC);

    }
}
