package org.example;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class TripletDeque<T> implements Deque<T>, Containerable{
    private Container<T> first;
    private Container<T> last;
    private int sizeContainerMax = 5;
    private int sizeDequeMax = 1000;
    private int numberElems = 0;

    public TripletDeque(){

    }

    public TripletDeque(int sizeDequeMax){
        this.sizeDequeMax = sizeDequeMax;
    }

    public TripletDeque(int sizeDequeMax, int sizeContainer){
        this.sizeDequeMax = sizeDequeMax;
        this.sizeContainerMax = sizeContainer;
    }

    @Override
    public Object[] getContainerByIndex(int cIndex) {
        int indexConteiner = 0;
        Container<T> container = this.first;
        while (indexConteiner < cIndex){
            container = container.next;
            if (container == null){ // если следующего нет, то null
                return null;
            }
            indexConteiner++;
        }
        return container.elements;
    }

    private class Container<T>{
        private Container<T> next;
        private Container<T> prev;
        private Object[] elements = new Object[sizeContainerMax];

        public boolean equals(Object o){
            for (int i = 0; i < elements.length; i++){
                if (elements[i].equals(o)){
                    return true;
                }
            }
        return false;
        }
    }

    @Override
    public void addFirst(T t) {
        if (t == null) {
            throw new NullPointerException("Cannot add null element");
        }
        if (this.first == null) {
            this.first = new Container<T>();
            this.last = this.first;
        }
        if (this.first == this.last && this.first.elements[0] != null){
            this.first.prev = new Container<T>(); //  создаем контейнер левее
            this.first.prev.elements[this.sizeContainerMax-1] = t;
            this.numberElems += 1;
            this.first.prev.next = this.first; // у нового контейнера ссылка на следующий
            this.first = this.first.prev; // первый контейнер получает ссылку на предыдущий
            if (this.numberElems > this.sizeDequeMax){
                throw new IllegalStateException("Нельзя выходить за допустимые пределы очереди");
            }
        }
        else {
            // цикл по контейнеру
            for (int i = this.sizeContainerMax-1; i >= 0; i--){ // так как addFirst заполняет контейнер справа налево i = sizeContainerMax-1
                if (this.first.elements[i] == null){
                    this.first.elements[i] = t;
                    this.numberElems += 1;
                    i = 0;
                }
                else if (i == 0){
                    this.first.prev = new Container<T>();
                    this.first.prev.elements[this.sizeContainerMax-1] = t;
                    this.numberElems += 1;
                    this.first.prev.next = this.first;
                    this.first = this.first.prev;
                }
            }
        }
    }

    @Override
    public void addLast(T t) {
        if (t == null) {
            throw new NullPointerException("Cannot add null element");
        }
        if (this.last == null) {
            this.last = new Container<T>();
            this.first = this.last;
        }
        if (this.last == this.first && this.last.elements[this.sizeContainerMax - 1] != null){
            this.last.next = new Container<T>(); // создаем контейнер правее
            this.last.next.elements[0] = t;
            this.numberElems += 1;
            this.last.next.prev = this.last; // у нового контейнера ссылка на предыдущий
            this.last = this.last.next; // старый контейнер получает ссылку на следующий
            if (this.numberElems > this.sizeDequeMax){
                throw new IllegalStateException("Нельзя выходить за допустимые пределы очереди");
            }
        }
        else {
            // цикл по контейнеру
            for (int i = 0; i < this.sizeContainerMax; i++) { // так как addLast заполняет контейнер слева направо i = 0
                if (this.last.elements[i] == null) {
                    this.last.elements[i] = t;
                    this.numberElems += 1;
                    i = this.sizeContainerMax;
                }
                else if (i == this.sizeContainerMax - 1) {
                    this.last.next = new Container<T>();
                    this.last.next.elements[0] = t;
                    this.numberElems += 1;
                    this.last.next.prev = this.last; // у нового контейнера ссылка на предыдущий
                    this.last = this.last.next; // старый контейнер получает ссылку на следующий
                }
            }
        }
    }

    @Override
    public boolean offerFirst(T t) {
        if (t == null) {
            throw new NullPointerException("Cannot add null element");
        }
        if (this.numberElems > this.sizeDequeMax){
            return false;
        }
        this.addFirst(t);
        return true;
    }

    @Override
    public boolean offerLast(T t) {
        if (t == null) {
            throw new NullPointerException("Cannot add null element");
        }
        if (this.numberElems > this.sizeDequeMax){
            return false;
        }
        this.addLast(t);
        return true;
    }

    @Override
    public T removeFirst() {
        T removeElement = null;
        if (this.first == null) {
            throw new NoSuchElementException("Пустой контейнер");
        } else {
            for (int i = 0; i < this.sizeContainerMax; i++) {
                if (this.first.elements[i] != null) {
                    removeElement = (T) this.first.elements[i];
                    this.first.elements[i] = null;
                    this.numberElems -= 1;

                    if (this.first.elements[this.sizeContainerMax - 1] == null && this.first != this.last) {
                        this.first = this.first.next;
                        this.first.prev = null;
                    }
                    else if (this.numberElems == 0){
                        this.first = null;
                        this.last = null;
                    }
                    i = this.sizeContainerMax;
                }
            }
            return removeElement;
        }
    }

    @Override
    public T removeLast() {
        T removeElement = null;
        if (this.last == null) {
            throw new NoSuchElementException("Пустой контейнер");
        } else {
            for (int i = this.sizeContainerMax - 1; i >= 0; i--) {
                if (this.last.elements[i] != null) {
                    removeElement = (T) this.last.elements[i];
                    this.last.elements[i] = null;
                    this.numberElems -= 1;

                    if (this.last.elements[0] == null && this.last != this.first) {
                        this.last = this.last.prev;
                        this.last.next = null;
                    }
                    else if (this.numberElems == 0){
                        this.last = null;
                        this.first = null;
                    }
                    i = -1;
                }
            }
            return removeElement;
        }
    }

    @Override
    public T pollFirst() {
        if (this.last == null) {
            return null;
        }
        return removeFirst();
    }

    @Override
    public T pollLast() {
        if (this.last == null) {
            return null;
        }
        return removeLast();
    }

    @Override
    public T getFirst() {
        T elementFind = null;
        if (this.first == null){
            System.out.println("Пустой контейнер");
//            return null;
            //TODO Exception
            throw new  NoSuchElementException("Пустой контейнер");
        }
        else {
            for (int i = 0; i < this.sizeContainerMax; i++){
                if (this.first.elements[i] != null){
                    elementFind = (T) this.first.elements[i];
//                    i = this.first.elements.length;
                    break;
                }
            }
        }
        return elementFind;
    }

    @Override
    public T getLast() {
        T elementFind = null;
        if (this.last == null){
            System.out.println("Пустой контейнер");
//            return null;
            //TODO Exception
            throw new  NoSuchElementException("Пустой контейнер");
        }
        else {
            for (int i = this.last.elements.length - 1; i >= 0; i--){
                if (this.last.elements[i] != null){
                    elementFind = (T) this.last.elements[i];
//                    i = -1;
                    break;
                }
            }
        }
        return elementFind;
    }

    @Override
    public T peekFirst() {
        return null;
    }

    @Override
    public T peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        if (o == null){
            throw new NullPointerException("Нельзя удалять значение null");
        }
        Container<T> current = this.first;
        while (current != null){
            for (int i = 0; i < this.sizeContainerMax; i++){
                if (current.elements[i] != null && current.elements[i].equals(o)){
                    current.elements[i] = null;
                    if (i != this.sizeContainerMax - 1){
                        for (int j = i + 1; j > 0; j--){ ////Работает при удалении любого элемента очереди кроме последнего
                            if (current.elements[j] == null){
                                current.elements[j] = current.elements[j-1];
                                current.elements[j - 1] = null;
                            }
                        }
                    }
                    else {
                        for (int j = this.sizeContainerMax-1; j >= 1; j--) {//Работает при удалении последнего элемента очереди
                        current.elements[j] = current.elements[j - 1];
                        current.elements[j - 1] = null;
                        }
                    }
                    this.numberElems -= 1;
                    if (current.elements[this.sizeContainerMax - 1] == null && current == this.first){
                        this.first = current.next;
                        if (this.first != null){
                            this.first.prev = null;
                        }
                    }
                    else if (current == this.last && this.last.prev != null){
                        for (int j = 0; j < this.sizeContainerMax; j++){
                            if (this.last.elements[j] == null){
                                this.last.elements[j] = this.last.prev.elements[this.sizeContainerMax - j - 1];
                                this.last.prev.elements[this.sizeContainerMax - j - 1] = null;
                            }
                            for (int a = this.sizeContainerMax - 1; a >= 1; a--) {//Работает при удалении последнего элемента очереди
                                this.last.prev.elements[a] = this.last.prev.elements[a - 1];
                                this.last.prev.elements[a - 1] = null;
                            }
                        }
                        if(this.last.prev.elements[this.sizeContainerMax - 1] == null){
                            this.last.prev = null;
                        }
                        this.last = current.prev;
                        if (this.last != null){
                            this.last.next = null;
                        }
                    }
                    else {
                        if (current.prev != null){
                            current.prev.next = current.next;
                            current.next.prev = current.prev;
                        }
                    }
                    return true;
                }
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        if (o == null){
            throw new NullPointerException("Нельзя удалять значение null");
        }
        Container<T> current = this.last;
        while (current != null){
            for (int i = this.sizeContainerMax - 1; i >=0; i--){
                if (current.elements[i].equals(o)){
                    current.elements[i] = null;
                    for (int j = i; j < this.sizeContainerMax - 1; j++) {
                        current.elements[j - 1] = current.elements[j];
                    }
                    if (current.elements[0] == null && current == this.last){
                        this.last = current.prev;
                        if (this.last != null){
                            this.last.next = null;
                        }
                    }
                    else if (current == this.first){
                        this.first = current.next;
                        if (this.first != null){
                            this.first.prev = null;
                        }
                    }
                    else {
                        current.next.prev = current.prev;
                        current.prev.next = current.next;
                    }
                    return true;
                }
            }
            current = current.prev;
        }
        return false;
    }

    @Override
    public boolean add(T t) {
        return false;
    }

    @Override
    public boolean offer(T t) {
        return false;
    }

    @Override
    public T remove() {
        return null;
    }

    @Override
    public T poll() {
        return null;
    }

    @Override
    public T element() {
        return null;
    }

    @Override
    public T peek() {
        return null;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
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
    public void push(T t) {

    }

    @Override
    public T pop() {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null){
            throw new NullPointerException("Cannot remove null element");
        }
        return this.removeFirstOccurrence(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null){
            throw new NullPointerException("Вы проверяете null");
        }
        else {
            while (this.first != null){
                for (int i = 0; i < this.sizeContainerMax; i++){
                    if (this.first.elements[i] == null){
                        continue;
                    }
                    if (this.first.elements[i].equals(o)){
                        return true;
                    }
                }
                this.first = this.first.next;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return this.numberElems;
    }

    @Override
    public boolean isEmpty() {
        return this.numberElems == 0;
    }

    private class TripletDequeIterator<T> implements Iterator<T>{
        private TripletDeque<T> tripletDeque;
        private Container<T> container;
        private int conteinerLength;

        public TripletDequeIterator(TripletDeque<T> tripletDeque){
            this.tripletDeque = tripletDeque;
            this.container = (Container<T>) this.tripletDeque.first;
            this.conteinerLength = 0;
        }

        @Override
        public boolean hasNext() {
            return this.container != null;
        }

        @Override
        public T next() {
            if (!this.hasNext()){
                throw new NoSuchElementException("Контейнера не существует");
            }
            T nexElement = (T) this.container.elements[this.conteinerLength++];
            if (this.conteinerLength == 5){
                this.container = this.container.next;
                if (this.container != null){
                    this.conteinerLength = 0;
                }
            }
            return nexElement;
        }
    }
    @Override
    public Iterator<T> iterator() {
//        if (this.first == null){
//            throw new NullPointerException("Очередь пуста");
//        }
        return new TripletDequeIterator<>(this);
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
    public Iterator<T> descendingIterator() {
        return null;
    }
}
