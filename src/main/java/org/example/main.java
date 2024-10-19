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
        tQueue.addFirst("three");
        tQueue.addFirst("four");
        tQueue.addFirst("five");
        tQueue.addFirst("six");


        for(String s: tQueue){
            System.out.print(s + ", ");
        }

        System.out.println("");
        System.out.println(tQueue.removeFirstOccurrence("six"));
        System.out.println("");

        for(String s: tQueue){
            System.out.print(s + ", ");
        }
    }
}
