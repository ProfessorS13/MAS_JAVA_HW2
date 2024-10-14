package org.example;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class TripletDeque<T> implements Deque<T>, Containerable{
    private Conteiner<T> first;
    private Conteiner<T> last;
    private int sizeContainerMax = 5;
    private int sizeDequeMax = 1000;
    private int sizeDeque = 0;

    public TripletDeque(){

    }

    public TripletDeque(int  sizeDeque){
        this.sizeDequeMax = sizeDeque;
    }

    public TripletDeque(int sizeDeque, int sizeContainer){
        this.sizeDequeMax = sizeDeque;
        this.sizeContainerMax = sizeContainer;
    }

    @Override
    public Object[] getContainerByIndex(int cIndex) {
        int indexConteiner = 0;
        Conteiner<T> conteiner = this.first;
        while (indexConteiner < cIndex){
            conteiner = conteiner.next;
            if (conteiner == null){ // если следующего нет, то null
                return null;
            }
            indexConteiner++;
        }
        return conteiner.elements;
    }

    private class Conteiner<T>{
        private Conteiner<T> next;
        private Conteiner<T> prev;
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
            this.first = new Conteiner<T>();
            this.last = this.first;
            if (this.sizeDeque++ > sizeDequeMax){
                throw new IllegalStateException("Нельзя выходить за допустимые пределы очереди");
            }
        }
        if (this.first == this.last && this.first.elements[0] != null){
            this.first.prev = new Conteiner<T>(); //  создаем контейнер леевее
            this.first.prev.elements[this.sizeContainerMax-1] = t;
            this.first.prev.next = this.first; // у нового контейнера ссылка на следующий
            this.first = this.first.prev; // первый контейнер получает ссылку на предыдущий
            this.sizeDeque += 1;
        }
        else {
            // цикл по контейнеру
            for (int i = this.sizeContainerMax-1; i >= 0; i--){ // так как addFirst заполняет контейнер справа налево i = sizeContainerMax-1
                if (this.first.elements[i] == null){
                    this.first.elements[i] = t;
                    this.sizeDeque += 1;
                    //TODO проверить будет ли работать (мб i = 0)
                 i = 0;
//                    break;
                }
                else if (i == 0){
                    this.first.prev = new Conteiner<T>();
                    this.first.prev.elements[this.sizeContainerMax-1] = t;
                    this.first.prev.next = this.first;
                    this.first = this.first.prev;
                    this.sizeDeque += 1;
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
            this.last = new Conteiner<T>();
            this.first = this.last;
        }
        if (this.last == this.first && this.last.elements[this.sizeContainerMax - 1] != null){
            this.last.next = new Conteiner<T>(); //  создаем контейнер правее
            this.last.next.elements[0] = t;
            this.last.next.prev = this.last; // у нового контейнера ссылка на предыдущий
            this.last = this.last.next; // старый контейнер получает ссылку на следующий
            this.sizeDeque += 1;
        }
        else {
            // цикл по контейнеру
            for (int i = 0; i < this.sizeContainerMax; i++) { // так как addLast заполняет контейнер слева направо i = 0
                if (this.last.elements[i] == null) {
                    this.first.elements[i] = t;
                    this.sizeDeque += 1;
                    //TODO проверить будет ли работать (мб i = this.sizeContainerMax) //создание нового  контейнера
                    i = this.sizeContainerMax;
//                    break;
                } else {
                    if (i == this.sizeContainerMax) {
                        this.last.next = new Conteiner<T>();
                        this.last.next.elements[0] = t;
                        this.last.next.prev = this.last; // у нового контейнера ссылка на предыдущий
                        this.last = this.last.next; // старый контейнер получает ссылку на следующий
                        this.sizeDeque += 1;
                    }
                }
            }
        }
    }

    @Override
    public boolean offerFirst(T t) {
        return false;
    }

    @Override
    public boolean offerLast(T t) {
        return false;
    }

    @Override
    public T removeFirst() {
        boolean flg = false;
        T removeElement = null;
        if (this.first == null){
            throw new NoSuchElementException("Пустая очередь");
        }
        else{
            for (int i = 0; i < this.sizeContainerMax; i++){
                if (this.first.elements[i] != null){
                    removeElement = (T) this.first.elements[i];
                    this.first.elements[i] = null;
                    this.sizeDeque--;
                    flg = true;
                }
                if (flg == true && (this.first.elements[this.sizeContainerMax - 1] == null || this.sizeDeque == 0)){
                    this.first.next.prev = null; // удаляем контейнер
                    this.first = this.first.next;
                }
            i = this.sizeContainerMax;
            }
        }
        return removeElement;
    }

    @Override
    public T removeLast() {
        return null;
    }

    @Override
    public T pollFirst() {
        return null;
    }

    @Override
    public T pollLast() {
        return null;
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
            for (int i = 0; i < sizeContainerMax; i++){
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
        Conteiner<T> current = this.first;
        while (current != null){
            for (int i = 0; i < this.sizeContainerMax; i++){
                if (current.elements[i].equals(o)){
                    current.elements[i] = null;
                    for (int j = i + 1; j < this.sizeContainerMax; j++) {
                        current.elements[j] = current.elements[j - 1];
                    }
                    if (current.elements[this.sizeContainerMax - 1] == null && current == this.first){
                        this.first = current.next;
                        if (this.first != null){
                            this.first.prev = null;
                        }
                    }
                    else if (current == this.last){
                        this.last = current.prev;
                        if (this.last != null){
                            this.last.next = null;
                        }
                    }
                    else {
                        current.prev.next = current.next;
                        current.next.prev = current.prev;
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
            throw new NullPointerException("Вы добавляете null");
        }
        else {
            if (this.first != null){
                for (int i = 0; i < this.sizeContainerMax; i++){
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
        return this.sizeDeque;
    }

    @Override
    public boolean isEmpty() {
        return this.sizeDeque == 0;
    }

    private class TripletDequeIterator<T> implements Iterator<T>{
        private TripletDeque<T> tripletDeque;
        private Conteiner<T> conteiner;
        private int conteinerIndex;

        public TripletDequeIterator(TripletDeque<T> tripletDeque){
            this.tripletDeque = tripletDeque;
            this.conteiner = (Conteiner<T>) this.tripletDeque.first;
            this.conteinerIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return this.conteiner != null;
        }

        @Override
        public T next() {
            if (!this.hasNext()){
                throw new NoSuchElementException("Контейнера не существует");
            }
            T nexElement = (T) this.conteiner.elements[conteinerIndex++];
            if (this.conteinerIndex == 4){
                this.conteiner = this.conteiner.next;
                if (this.conteiner != null){
                    this.conteinerIndex = 0;
                }
            }
            return nexElement;
        }
    }
    @Override
    public Iterator<T> iterator() {
        if (this.first == null){
            throw new NullPointerException("Очередь пуста");
        }
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
