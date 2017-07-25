import java.util.*;

public class InversionCounting {

    public int mergeAndCount(LinkedList<String> list, int firstList, int secondList, int high) {
        LinkedList<String> tempMerge = new LinkedList<>();
        int i = firstList;
        int j = secondList;
        int inversionCount = 0;
        while (i <= secondList && j <= high) {
            if (list.get(i) < list.get(j)) {

            }
        }
    }

    public int sortAndCount(LinkedList<String> list, int low, int high) {
        if (low >= high) {
            return 0;
        }

        int mid = low + (high - low) / 2;
        int leftHalf = sortAndCount(list, low, mid);
        int rightHalf = sortAndCOunt(list, mid + 1, high);
        int merged = mergeAndCount(list, low, mid + 1, high);


    }


    public static void main(String[] args)  {
        Scanner scan = new Scanner(System.in);
        ArrayList<String> inputs = new ArrayList<>();
        while (scan.hasNextLine()) {
            inputs.add(scan.nextLine());
        }
        int count = 0;
        LinkedList<String> firstCritic = new LinkedList<>();
        LinkedList<String> secondCritic = new LinkedList<>();
        ArrayList<String> movieRanks = new ArrayList<>();
        inputs.remove(0);
        while(!inputs.get(0).equals("Critic 2")) {
            firstCritic.add(inputs.remove(0));
        }
        inputs.remove(0);
        while(!inputs.get(0).equals("Movies")) {
            secondCritic.add(inputs.remove(0));
        }
        inputs.remove(0);
        while(!inputs.isEmpty()) {
            movieRanks.add(inputs.remove(0));
        }
        LinkedList<Integer> firstRanks = new LinkedList<>();
        for (int k = 0; k < firstCritic.size(); k++) {
            firstRanks.add(k + 1);
        }
        LinkedList<Integer> secondRanks = new LinkedList<>();
        for (int k = 0; k < firstCritic.size(); k++) {
            int index = secondCritic.indexOf(firstCritic.get(k));
            secondRanks.add(index + 1);
        }
        HashMap<String, Integer> weights = new HashMap<>();
        for (int k = 0; k < movieRanks.size(); k++) {
            String input = movieRanks.get(k);
            String weightString = input.substring(0, input.indexOf("/"));
            int weight = Integer.parseInt(weightString);
            String movieName = input.substring(input.indexOf("/") + 1);
            weights.put(movieName, weight);
        }


    }

}
