import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class SolutionB {

    public static void main(String[] args) {
        int outputA, outputB, outputC;
        ArrayList<Integer> nums = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            int num = Integer.parseInt(line);
            nums.add(num);
        }
        outputA = outputB = outputC = 0;
        for (int a = 0; a < nums.size(); a++) {
            for (int b = 1; b < nums.size(); b++) {
                for (int c = 2; c < nums.size(); c++) {
                    if(nums.get(a) * nums.get(b) == nums.get(c)) {
                        if (nums.get(c) > outputC) {
                            outputA = nums.get(a);
                            outputB = nums.get(b);
                            outputC = nums.get(c);
                        }
                    }
                }

            }
        }

        System.out.println(outputA + "\n" + outputB + "\n" + outputC);

    }
}
