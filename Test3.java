package OfflineProblem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Test3 {
    
    static HashMap<Node, HashMap<Node, Double>> store = new HashMap<Node, HashMap<Node,Double>>();
    static HashMap<Node, Double> sums = new HashMap<Node, Double>();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int size = in.nextInt();
        Node[] arr = new Node[size];
        for (int i = 0; i < size; i++) {
            arr[i] = new Node(in.nextDouble());
        }
        
        for (int i = 0; i < size - 1; i++) {
            int first = in.nextInt();
            int second = in.nextInt();
            arr[first - 1].relations.add(arr[second - 1]);
            arr[second - 1].relations.add(arr[first - 1]);
        }
        
        System.out.println(getOptimalQuestion(arr));
    }
    
    private static int getOptimalQuestion(Node[] arr) {
        double min = Double.MAX_VALUE;
        int index = 0;
        
        for (int i = 0; i < arr.length; i++) {
            double value = getQuestionCost(arr[i], null);
            if(value < min) {
                index = i+1;
                min = value;
            }
        }
        return index;
    }

    private static double getQuestionCost(Node current, Node parent) {
        double childrenCost = 0;
        if(store.containsKey(current) && current.relations.size() == store.get(current).size()) {
            double parentPathCost = (parent == null)? 0 : store.get(current).get(parent);
            childrenCost = sums.get(current) - parentPathCost;
        }else {
            for (int i = 0; i < current.relations.size(); i++) {
                Node child = current.relations.get(i);
                if(child != parent) {
                    double childCost = getQuestionCost(child, current);
                    if(!store.containsKey(current) || store.get(current).containsKey(child)) {
                        addToCache(current, child, childCost);
                        sums.put(current, (sums.containsKey(current)? sums.get(current):0) + childCost);
                    }
                    childrenCost += childCost; 
                }
            }
        }
        if (current.relations.size() > 0) {
            if(parent != null && current.relations.size() > 1) {
                childrenCost /= current.relations.size() - 1;
            }else {
                childrenCost /= current.relations.size();
            }
        }
        return childrenCost + current.readingTime;
    }

    private static void addToCache(Node current, Node child, double childCost) {
        HashMap<Node, Double> temp = store.get(current);
        if(temp == null) {
            temp = new HashMap<Node, Double>();
        }
        temp.put(child, childCost);
        store.put(current, temp);
    }

    static class Node{
        double readingTime;
        ArrayList<Node> relations;
        
        Node(double readingTime) {
            this.readingTime= readingTime;
            relations = new ArrayList<Node>();
        }
    }
}
