package org.example;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class main {
    public static void main(String[] args) {
        Deque<String> tQueue = new TripletDeque<>();
        Containerable cQueue = (Containerable) tQueue;

        for (int i=0; i < 15 ;i++){
            tQueue.addFirst("n_"+i);
        }
        for (String s : tQueue) {
            System.out.println(s);
        }
    }
}