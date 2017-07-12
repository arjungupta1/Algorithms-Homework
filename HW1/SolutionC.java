import java.io.*;
import java.util.*;

public class SolutionC {

    public void findSums(ArrayList<Integer> inputs, int index, int sumSoFar) {
        if (index == inputs.size()) {
            System.out.println(sumSoFar);
            return;
        }   
        findSums(inputs, index + 1, sumSoFar);
        findSums(inputs, index + 1, sumSoFar + inputs.get(index));


    }

    public static void main(String[] args) {

        ArrayList<Integer> inputs = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            int num = Integer.parseInt(line);
            inputs.add(num);
        }
        SolutionC s = new SolutionC();
        s.findSums(inputs, 0, 0);

    }
}