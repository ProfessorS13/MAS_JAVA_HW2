package org.example;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class main {
    public static void main(String[] args) {
        Deque<String> tQueue = new TripletDeque<>();
        Containerable cQueue = (Containerable) tQueue;

        tQueue.addFirst("one");
        tQueue.addFirst("two");
        System.out.println(tQueue.getFirst());
        System.out.println(tQueue.getLast());
        tQueue.addFirst("three");
        System.out.println(tQueue.getFirst());
        System.out.println(tQueue.getLast());
        tQueue.addFirst("four");
        tQueue.addLast("five");
        tQueue.addFirst("six");
        System.out.println(tQueue.getFirst());
        System.out.println(tQueue.getLast());
        System.out.println(tQueue.contains("one"));
    }
}