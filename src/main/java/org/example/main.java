package org.example;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class main {
    public static void main(String[] args) {
        Deque<String> tQueue = new TripletDeque<>();
        Containerable cQueue = (Containerable) tQueue;

//        tQueue.addFirst("one");
//        tQueue.addFirst("two");
//        tQueue.addFirst("Something");
//        for (String s: tQueue){
//            System.out.println(s);
//        }
        tQueue.addLast("one");
//        tQueue.addFirst("one");
//        tQueue.addFirst("one");
//        tQueue.addFirst("one");
//        tQueue.addFirst("one");
//        tQueue.addFirst("one");
        System.out.println(tQueue.size());
        System.out.println(tQueue.contains("one"));
        for (String s: tQueue){
            System.out.println(s);
        }
        System.out.println("------------------");
        boolean empty = tQueue.isEmpty();
        System.out.println(empty);
        tQueue.removeLast();
//        System.out.println("Удаленный элемент: "+tQueue.removeFirst());
        System.out.println(tQueue.size());
        for (String s: tQueue){
            System.out.println("- " + s);
        }
        System.out.println(tQueue.isEmpty());

    }
}