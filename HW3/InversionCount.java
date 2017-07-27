import java.util.*;

class LinkedWithWeight {
    LinkedList<String> ll; //represents the movie strings
    int inversionWeight; //represents the total weight made by inversions
    int totalWeight; //for the LinkedList ll, represents the total weight of all movies in the LinkedList

    LinkedWithWeight(LinkedList<String> ll, int inversionWeight, int totalWeight) {
        this.ll = ll;
        this.inversionWeight = inversionWeight;
        this.totalWeight = totalWeight;
    }
}

public class InversionCount {

    HashMap<String, Integer> rankings = new HashMap<>(); //An example Hashmap would be {(It, 1), (No, 2), (Up, 3)} representing the first critic's order
    HashMap<String, Integer> weights = new HashMap<>(); //An example HashMap would be {(It, 7), (No, 3), (Up, 2)} representing the weights of each movie
    LinkedList<String> secondCritic = new LinkedList<String>(); //a list is required to mergesort the second critic's choices

    public LinkedWithWeight mergeAndCount(LinkedWithWeight left, LinkedWithWeight right) {
        LinkedList<String> tempMerge = new LinkedList<String>();
        int inversionW = 0;
        //represents the total weight of the left and right sublists
        int mergedTotalWeight = left.totalWeight + right.totalWeight;


        while((left.ll.size() > 0) && (right.ll.size() > 0)) {
            //if the head of the right list is greater
            if (this.rankings.get(right.ll.get(0)) < this.rankings.get(left.ll.get(0))) {
                //there is an inversion!
                inversionW += (left.totalWeight * weights.get(right.ll.get(0)));
                /*
                Since both lists are sorted, if the first element of right is greater than the first element of left, we can assume that the first element of right is greater than all elements of left, so we can multiply the weight of the first element of right by sum of all the weights of left
                */
                tempMerge.add(right.ll.pop());
            }
            else {
                //left is greater, so no inversion needed
                String removed = left.ll.pop();
                tempMerge.add(removed);
                //subtract the total weight of left by the weight of the item you just removed
                left.totalWeight -= weights.get(removed);
            }
        }

        //left list has remaining elements so add them all
        if (left.ll.size() > 0) {
            tempMerge.addAll(left.ll);
        }
        //right list has remaining elements so add them all
        else {
            tempMerge.addAll(right.ll);
        }

        return new LinkedWithWeight(tempMerge, inversionW, mergedTotalWeight);
    }


    public LinkedWithWeight sortAndCount(LinkedWithWeight list) {
        if (list.ll.size() == 0 || list.ll.size() == 1) {
            //no inversions
            return new LinkedWithWeight(list.ll, list.inversionWeight, list.totalWeight);
        }

        LinkedList<String> left = new LinkedList<>();
        LinkedList<String> right = new LinkedList<>();
        int leftTotalWeight = 0;
        int rightTotalWeight = 0;

        for (int k = 0; k < list.ll.size(); k++) {
            if (k < list.ll.size() / 2) {
                left.add(list.ll.get(k));
                leftTotalWeight += weights.get(list.ll.get(k));
            }
            else {
                right.add(list.ll.get(k));
                rightTotalWeight += weights.get(list.ll.get(k));
            }
        }

        LinkedWithWeight leftHalf = new LinkedWithWeight(left, 0, leftTotalWeight);
        LinkedWithWeight rightHalf = new LinkedWithWeight(right, 0, rightTotalWeight);
        leftHalf = sortAndCount(leftHalf);
        rightHalf = sortAndCount(rightHalf);
        LinkedWithWeight merged = mergeAndCount(leftHalf, rightHalf);
        //need to add the total inversion weight of left, right, and merged. merged also already contains the total weight of everything so far
        return new LinkedWithWeight(merged.ll, leftHalf.inversionWeight + rightHalf.inversionWeight + merged.inversionWeight, merged.totalWeight);
    }


    public static void main(String[] args) {
        InversionCount i = new InversionCount();
        Scanner scan = new Scanner(System.in);
        ArrayList<String> inputs = new ArrayList<>();
        ArrayList<String> movieRanks = new ArrayList<>();
        while (scan.hasNextLine()) {
            inputs.add(scan.nextLine());
        }
        int count = 0;
        inputs.remove(0);
        while(!inputs.get(0).equals("Critic 2")) {
            count++;
            String remove = inputs.remove(0);
            i.rankings.put(remove, count);
        }
        inputs.remove(0);
        while (!inputs.get(0).equals("Movies")) {
            i.secondCritic.add(inputs.remove(0));
        }
        inputs.remove(0);
        while(!inputs.isEmpty()) {
            movieRanks.add(inputs.remove(0));
        }
        for (int k = 0; k < movieRanks.size(); k++) {
            String input = movieRanks.get(k);
            String weightString = input.substring(0, input.indexOf("/"));
            int weight = Integer.parseInt(weightString);
            String movieName = input.substring(input.indexOf("/") + 1);
            i.weights.put(movieName, weight);
        }

        LinkedWithWeight l = new LinkedWithWeight(i.secondCritic, 0, 0);
        LinkedWithWeight finished = i.sortAndCount(l);
        System.out.println(finished.inversionWeight);


    }
}
