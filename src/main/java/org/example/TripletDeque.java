package org.example;

import javax.swing.*;
import java.util.*;

public class TripletDeque<T> implements Deque<T>, Containerable {
    Container<T> first;
    Container<T> last;
    private int sizeContainerMax = 5;
    private int sizeDequeMax = 1000;
    private int numberElems = 0;

    public TripletDeque() {

    }

    @Override
    public String toString() {
        String str = "";
        Container current = this.first;

        while (current.next != null) {
            str += Arrays.toString(current.elements) + " ";
            current = current.next;
        }

        str += Arrays.toString(current.elements);

        return str;
    }

    public TripletDeque(int sizeDequeMax) {
        this.sizeDequeMax = sizeDequeMax;
    }

    public TripletDeque(int sizeDequeMax, int sizeContainer) {
        this.sizeDequeMax = sizeDequeMax;
        this.sizeContainerMax = sizeContainer;
    }

    @Override
    public Object[] getContainerByIndex(int cIndex) {
        int indexConteiner = 0;
        Container<T> container = this.first;
        while (indexConteiner < cIndex) {
            container = container.next;
            if (container == null) { // если следующего нет, то null
                return null;
            }
            indexConteiner++;
        }
        return container.elements;
    }

    private class Container<T> {
        private Container<T> next;
        private Container<T> prev;
        private Object[] elements = new Object[sizeContainerMax];

        public boolean equals(Object o) {
            for (int i = 0; i < elements.length; i++) {
                if (elements[i].equals(o)) {
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
        if (this.first == this.last && this.first.elements[0] != null) {
            this.first.prev = new Container<T>(); //  создаем контейнер левее
            this.first.prev.elements[this.sizeContainerMax - 1] = t;
            this.numberElems += 1;
            this.first.prev.next = this.first; // у нового контейнера ссылка на следующий
            this.first = this.first.prev; // первый контейнер получает ссылку на предыдущий
            if (this.numberElems > this.sizeDequeMax) {
                throw new IllegalStateException("Нельзя выходить за допустимые пределы очереди");
            }
        } else {
            // цикл по контейнеру
            for (int i = this.sizeContainerMax - 1; i >= 0; i--) { // так как addFirst заполняет контейнер справа налево i = sizeContainerMax-1
                if (this.first.elements[i] == null) {
                    this.first.elements[i] = t;
                    this.numberElems += 1;
                    i = 0;
                } else if (i == 0) {
                    this.first.prev = new Container<T>();
                    this.first.prev.elements[this.sizeContainerMax - 1] = t;
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
        if (this.last == this.first && this.last.elements[this.sizeContainerMax - 1] != null) {
            this.last.next = new Container<T>(); // создаем контейнер правее
            this.last.next.elements[0] = t;
            this.numberElems += 1;
            this.last.next.prev = this.last; // у нового контейнера ссылка на предыдущий
            this.last = this.last.next; // старый контейнер получает ссылку на следующий
            if (this.numberElems > this.sizeDequeMax) {
                throw new IllegalStateException("Нельзя выходить за допустимые пределы очереди");
            }
        } else {
            // цикл по контейнеру
            for (int i = 0; i < this.sizeContainerMax; i++) { // так как addLast заполняет контейнер слева направо i = 0
                if (this.last.elements[i] == null) {
                    this.last.elements[i] = t;
                    this.numberElems += 1;
                    i = this.sizeContainerMax;
                } else if (i == this.sizeContainerMax - 1) {
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
            throw new NullPointerException("Нельзя добавить значение null");
        }
        if (this.numberElems + 1 > this.sizeDequeMax) {
            return false;
        }
        this.addFirst(t);
        return true;
    }

    @Override
    public boolean offerLast(T t) {
        if (t == null) {
            throw new NullPointerException("Нельзя добавить значение null");
        }
        if (this.numberElems + 1 > this.sizeDequeMax) {
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
                    } else if (this.numberElems == 0) {
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
                    } else if (this.numberElems == 0) {
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
        if (this.first == null) {
            System.out.println("Пустой контейнер");
            throw new NoSuchElementException("Пустой контейнер");
        } else {
            for (int i = 0; i < this.sizeContainerMax; i++) {
                if (this.first.elements[i] != null) {
                    elementFind = (T) this.first.elements[i];
                    break;
                }
            }
        }
        return elementFind;
    }

    @Override
    public T getLast() {
        T elementFind = null;
        if (this.last == null) {
            System.out.println("Пустой контейнер");
            throw new NoSuchElementException("Пустой контейнер");
        } else {
            for (int i = this.last.elements.length - 1; i >= 0; i--) {
                if (this.last.elements[i] != null) {
                    elementFind = (T) this.last.elements[i];
                    break;
                }
            }
        }
        return elementFind;
    }

    @Override
    public T peekFirst() {
        if (this.isEmpty()) {
            return null;
        }
        return getFirst();
    }


    @Override
    public T peekLast() {
        if (this.isEmpty()) {
            return null;
        }
        return getLast();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        if (o == null) {
            throw new NullPointerException("Нельзя удалять значение null");
        }

        Container<T> current = this.first;
        while (current != null) {
            for (int i = 0; i < this.sizeContainerMax; i++) {
                if (current.elements[i] != null && current.elements[i].equals(o)) {
                    current.elements[i] = null;
                    if (i != this.sizeContainerMax - 1) {
                        for (int j = i + 1; j > 0; j--) {
                            if (current.elements[j] == null) {
                                current.elements[j] = current.elements[j - 1];
                                current.elements[j - 1] = null;
                            }
                        }
                    }

                    this.numberElems -= 1;
                    if (current.elements[this.sizeContainerMax - 1] == null && current == this.first) {
                        int idx = this.findIndexOfNull((T[]) current.elements);

                        for (int j = idx; j > 0; j--) {
                            current.elements[j] = current.elements[j - 1];
                        }

                        if (current.elements[this.sizeContainerMax - 1] == null) {
                            if (current.next != null) {
                                current = current.next;
                                current.prev = null;
                                this.first = current;
                            } else {
                                this.first = null;
                                this.last = null;
                            }
                        }

                        while (current.next != null) {
                            int idx2 = findIndexOfNull((T[]) current.elements);

                            if (idx2 != -1) {
                                current.elements[this.sizeContainerMax - 1] = current.next.elements[0];
                                current = current.next;
                                this.move(0, (T[]) current.elements);
                            }
                        }

                        if (current == this.last && current.elements[0] == null) {
                            current.prev.next = null;
                        } else if (this.first != this.last) {
                            this.move(0, (T[]) current.elements);
                        }
                    } else if (current == this.last && this.last.prev != null) {
                        int idx = this.findIndexOfNull((T[]) current.elements);

                        this.move(idx, (T[]) current.elements);
                    } else {
                        int idx = this.findIndexOfNull((T[]) current.elements);
                        this.move(idx, (T[]) current.elements);

                        while (current.next != null) {
                            current.elements[this.sizeContainerMax - 1] = current.next.elements[0];
                            current = current.next;
                            this.move(0, (T[]) current.elements);
                        }

                        if (current.elements[0] == null) {
                            current.prev.next = null;
                        }
                    }

                    return true;
                }
            }
            current = current.next;
        }
        return false;
    }

    private int findIndexOfNull(T[] array) {
        int i = this.sizeContainerMax - 1;

        while (i >= 0) {
            if (array[i] == null) {
                return i;
            } else {
                i--;
            }
        }

        return i;
    }

    private void move(int idx, T[] array) {
        for (int i = idx; i < this.sizeContainerMax - 1; i++) {
            array[i] = array[i + 1];
        }

        array[this.sizeContainerMax - 1] = null;

//        System.out.println("after moving");
//        System.out.println(Arrays.toString(array));
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        if (o == null) {
            throw new NullPointerException("Нельзя удалять значение null");
        }
        Container<T> current = this.last;

        while (current != null) {
            int iidx = -1;

            for (int i = this.sizeContainerMax - 1; i >= 0; i--) {
                if (current.elements[i] != null && current.elements[i].equals(o)) {
                    current.elements[i] = null;
                    iidx = i;
                    break;
                }
            }

            if (iidx == -1) {
                current = current.prev;
            } else {
                if (current == this.first) {
                    for (int j = iidx; j > 0; j--) {
                        current.elements[j] = current.elements[j - 1];
                    }

                    if (current.elements[this.sizeContainerMax - 1] == null) {
                        this.first = current.next;
                        this.first.prev = null;
                    }
                } else {
                    this.move(iidx, (T[]) current.elements);

                    while (current.next != null) {
                        current.elements[this.sizeContainerMax - 1] = current.next.elements[0];
                        current = current.next;
                        this.move(0, (T[]) current.elements);
                    }

                    if (current.elements[0] == null) {
                        current.prev.next = null;
                        this.last = current.prev;
                    }
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean add(T t) {
        if (this.numberElems + 1 > this.sizeDequeMax) {
            throw new IllegalStateException("Объем очереди достиг предела");
        } else {
            this.addLast(t);
            return true;
        }
    }

    @Override
    public boolean offer(T t) {
        if (this.numberElems + 1 > this.sizeDequeMax) {
            return false;
        } else {
            this.addLast(t);
            return true;
        }
    }

    @Override
    public T remove() {
        return this.removeFirst();
    }

    @Override
    public T poll() {
        if (this.isEmpty()) {
            return null;
        }
        return this.removeFirst();
    }

    @Override
    public T element() {
        return this.getFirst();
    }

    @Override
    public T peek() {
        if (this.isEmpty()) {
            return null;
        }
        return this.getFirst();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean added = false;
        for (T s : c) {
            addLast(s);
            added = true;
        }
        return added;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        this.first = null;
        this.last = null;
        this.numberElems = 0;
    }

    @Override
    public void push(T t) {
        if (t == null) {
            throw new NullPointerException("Cannot add null element");
        }
        if (this.numberElems + 1 > this.sizeDequeMax) {
            throw new IllegalStateException("Объем очереди достиг предела");
        }
        this.addFirst(t);
    }

    @Override
    public T pop() {
        return removeFirst();
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            throw new NullPointerException("Нельзя вернуть значение null");
        }
        return this.removeFirstOccurrence(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            throw new NullPointerException("Вы проверяете null");
        } else {
            while (this.first != null) {
                for (int i = 0; i < this.sizeContainerMax; i++) {
                    if (this.first.elements[i] == null) {
                        continue;
                    }
                    if (this.first.elements[i].equals(o)) {
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

    private class TripletDequeIterator<T> implements Iterator<T> {
        private TripletDeque<T> tripletDeque;
        private Container<T> container;
        private int indexElem;
        private int conteinerLength;

        public TripletDequeIterator(TripletDeque<T> tripletDeque) {
            this.conteinerLength = 5;
            this.tripletDeque = tripletDeque;
            this.container = (Container<T>) this.tripletDeque.first;
            this.indexElem = findFirst(container);
        }

        private int findFirst(Container<T> container) {
            for (int i = 0; i < this.conteinerLength; i++) {
                if (container != null) {
                    if (container.elements[i] != null) {
                        return i;
                    }
                }
            }
            return 0;
        }

        @Override
        public boolean hasNext() {
            if (this.container != null) {
                return this.container.elements[indexElem] != null;
            } else return false;
        }

        @Override
        public T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException("Контейнера не существует");
            }
            T nexElement = (T) this.container.elements[this.indexElem++];
            if (this.indexElem == 5) {
                this.container = this.container.next;
                if (this.container != null) {
                    this.indexElem = 0;
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
        throw new UnsupportedOperationException();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> descendingIterator() {
        throw new UnsupportedOperationException();
    }
}
