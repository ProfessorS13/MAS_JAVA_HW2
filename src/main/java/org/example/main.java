package org.example;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        TripletDeque<Integer> TD = new TripletDeque<>();
        System.out.println(Arrays.toString(TD.toArray()));
    }
}