package org.example;

import java.awt.*;
import java.lang.ref.PhantomReference;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class TripletDeque<Type> implements Deque<Type>{
    private int CONTAINER_SIZE = 5;
    private int DEQUE_SIZE_MAX = 1000;
    private int dequeSize = 0;
    Container <Type> first = null;
    Container <Type> last = null;

    class Container<T> {
        Collection<T> previous = null;
        Collection<T> next = null;

        private Object[] objects = new Object[CONTAINER_SIZE];

        public boolean equals(Object input){
            if (input == null || input.getClass() != this.getClass()){
                return false;
            }
            else{
                return true;
            }
            //TODO Посмотреть!
        }

    }

    public TripletDeque(){
    }
    public TripletDeque(int dequeMaxSize){
        this.DEQUE_SIZE_MAX = dequeMaxSize;
    }
    public TripletDeque(int containerSize, int dequeMaxSize){
        this.CONTAINER_SIZE = containerSize;
        this.DEQUE_SIZE_MAX = dequeMaxSize;
    }

    @Override
    public void addFirst(Type t) {
        if (t == null) {
            throw new NullPointerException("Cannot add null element");
        }
        if (first == null) {
            this.first = new Container();
            //так как контейнер единственный в данный момент, он является первым и последним
            this.last = this.first;
        }
        //Если размер очереди превышен
        if (this.dequeSize >= this.DEQUE_SIZE_MAX) {
            throw new IllegalStateException("Step out of line!");
        }
//        if (this.first == this.last && this.first.eleme[0] != null ) {}

    }

    @Override
    public void addLast(Type t) {

    }

    @Override
    public boolean offerFirst(Type t) {
        return false;
    }

    @Override
    public boolean offerLast(Type t) {
        return false;
    }

    @Override
    public Type removeFirst() {
        return null;
    }

    @Override
    public Type removeLast() {
        return null;
    }

    @Override
    public Type pollFirst() {
        return null;
    }

    @Override
    public Type pollLast() {
        return null;
    }

    @Override
    public Type getFirst() {
        return null;
    }

    @Override
    public Type getLast() {
        return null;
    }

    @Override
    public Type peekFirst() {
        return null;
    }

    @Override
    public Type peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean add(Type t) {
        return false;
    }

    @Override
    public boolean offer(Type t) {
        return false;
    }

    @Override
    public Type remove() {
        return null;
    }

    @Override
    public Type poll() {
        return null;
    }

    @Override
    public Type element() {
        return null;
    }

    @Override
    public Type peek() {
        return null;
    }

    @Override
    public boolean addAll(Collection<? extends Type> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public void push(Type t) {

    }

    @Override
    public Type pop() {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Iterator<Type> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public Iterator<Type> descendingIterator() {
        return null;
    }

//    @Override
//    public Object[] getContainerByIndex(int index) {
//        return new Object[0];
//    }
}
