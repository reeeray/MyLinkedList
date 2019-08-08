import java.util.*;

public class MyLinkedList<T> implements List<T> {


    private Item<T> firstInList = null;

    private Item<T> lastInList = null;

    private int size;

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(final Object o) {
        // BEGIN (write your solution here)
        Item<T> current = firstInList;
        if(firstInList != null) {
            while(true) {
                if(o == current.element) {
                    return true;
                }
                if(current.equals(lastInList)) {
                    break;
                }
                current = current.getNextItem();
            }
        }
        return false;
        // END
    }

    @Override
    public Iterator<T> iterator() {
        return new ElementsIterator(0);
    }

    @Override
    public Object[] toArray() {
        // BEGIN (write your solution here)
        final Object [] array = new Object [this.size()];
        Item<T> current = firstInList;
        for(int i=0; i<this.size(); i++) {
            array[i] = current.element;
            current = current.getNextItem();
        }
        return array;
        // END
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        // BEGIN (write your solution here)
        if(a.length < size) {
            return (T1[]) Arrays.copyOf(toArray(), size, a.getClass());
        }
        System.arraycopy(toArray(), 0, a, 0, size);
        if(size < a.length) {
            a[size] = null;
        }
        return a;
        // END
    }

    @Override
    public boolean add(final T newElement) {
        // BEGIN (write your solution here)
        final Item<T> element = lastInList;
        final Item<T> newItem = new Item<>(newElement, element, null);
        lastInList = newItem;
        if(element == null) {
            firstInList = newItem;
        } else {
            element.nextItem = newItem;
        }
        this.size++;
        return true;
        // END
    }

    @Override
    public boolean remove(final Object o) {
        // BEGIN (write your solution here)
        if(this.contains(o)){
            Item<T> current = this.firstInList;
            while (true){
                if(current.element == o){
                    if(current == firstInList) {
                        firstInList = current.nextItem;
                        current = null;
                    } else if(current == lastInList) {
                        lastInList = current.prevItem;
                        current = null;
                    } else {
                        current.prevItem.nextItem = current.nextItem;
                        current.nextItem.prevItem = current.prevItem;
                    }
                    this.size--;
                    return true;
                }
                if(current == lastInList) {
                    break;
                }
                current = current.getNextItem();

            }
        }
        return false;
        // END
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        for (final Object item : c) {
            if (!this.contains(item)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(final Collection<? extends T> c) {
        for (final T item : c) {
            add(item);
        }
        return true;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        for (final Object item : c) {
            remove(item);
        }
        return true;
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        for (final T item : this) {
            if (!c.contains(item)) this.remove(item);
        }
        return true;
    }

    @Override
    public void clear() {
        // BEGIN (write your solution here)
        this.firstInList = null;
        this.lastInList = null;
        this.size = 0;
        // END
    }

    @Override
    public T remove(final int index) throws IndexOutOfBoundsException{
        // BEGIN (write your solution here)
        T element = this.get(index);
        this.remove(element);
        return element;
        // END
    }


    private void remove(final Item<T> current) {
        // BEGIN (write your solution here)
        this.remove(current.element);
        // END
    }

    @Override
    public List<T> subList(final int start, final int end) {
        return null;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ElementsIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(final int index) {
        return new ElementsIterator(index);
    }

    @Override
    public int lastIndexOf(final Object target) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(final Object o) {
        // BEGIN (write your solution here)
        final ListIterator<T> iterator = this.listIterator();

        while(iterator.hasNext()) {
            int index = iterator.nextIndex();
            T element = iterator.next();
            if(element.equals(o)) {
                return index;
            }
        }
        return -1;
        // END
    }

    @Override
    public void add(final int index, final T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(final int index, final Collection elements) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T set(final int index, final T element) throws IndexOutOfBoundsException{
        // BEGIN (write your solution here)
        Item<T> item = getItemByIndex(index);
        T old = item.element;
        item.element = element;
        return old;
        // END
    }

    @Override
    public T get(final int index) throws IndexOutOfBoundsException {
        if(this.size() <= index || index < 0)
            throw new IndexOutOfBoundsException();
        // BEGIN (write your solution here)
        final ListIterator<T> iterator = this.listIterator();
        while(iterator.hasNext()) {
            int elementINdex = iterator.nextIndex();
            if(elementINdex == index) {
                return iterator.next();
            }
            iterator.next();
        }
        return null;
        // END
    }

    private Item<T> getItemByIndex(final int index) throws IndexOutOfBoundsException{
        // BEGIN (write your solution here) An auxiliary method for searching for node by nextINdex.
        if(index == 0) {
            return this.firstInList;
        }
        if(this.size() <= index || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Item<T> current = this.firstInList;

       for(int i=0;i<index;i++) {
           current = current.nextItem;
       }
       return current;
        // END
    }

    private class ElementsIterator implements ListIterator<T> {

        private Item<T> currentItemInIterator;

        private Item<T> lastReturnedItemFromIterator;

        private int nextINdex;

        ElementsIterator(final int nextINdex) {
            // BEGIN (write your solution here)
            this.currentItemInIterator = (nextINdex == size) ? null : getItemByIndex(nextINdex);
            this.nextINdex = nextINdex;
            // END
        }

        @Override
        public boolean hasNext() {
            // BEGIN (write your solution here)
            return nextINdex < size;
            // END
        }

        @Override
        public T next() {
            // BEGIN (write your solution here)
            if(!hasNext()) throw new NoSuchElementException();
            nextINdex++;
            lastReturnedItemFromIterator = currentItemInIterator;
            currentItemInIterator = currentItemInIterator.getNextItem();
            return lastReturnedItemFromIterator.element;
            // END
        }

        @Override
        public boolean hasPrevious() {
            // BEGIN (write your solution here)
            return nextINdex > 0;
            // END
        }

        @Override
        public T previous() {
            // BEGIN (write your solution here)
            if(!hasPrevious()) throw new NoSuchElementException();
            nextINdex--;
            lastReturnedItemFromIterator = currentItemInIterator = (currentItemInIterator == null) ? lastInList : currentItemInIterator.prevItem;
            return this.lastReturnedItemFromIterator.element;
            // END
        }

        @Override
        public void add(final T element) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(final T element) {
            // BEGIN (write your solution here)
            if(lastReturnedItemFromIterator == null) throw new IllegalStateException();
            lastReturnedItemFromIterator.element = element;
            // END
        }

        @Override
        public int previousIndex(){
            // BEGIN (write your solution here)
            return nextINdex -1;
            // END
        }
        @Override
        public int nextIndex() {
            // BEGIN (write your solution here)
            return nextINdex;
            // END
        }


        @Override
        public void remove() {
            // BEGIN (write your solution here)
            if(lastReturnedItemFromIterator == null) throw new IllegalStateException();
            MyLinkedList.this.remove(lastReturnedItemFromIterator);
            nextINdex--;
            lastReturnedItemFromIterator=null;
            // END
        }
    }

    private static class Item<T> {

        private T element;

        private Item<T> nextItem;

        private Item<T> prevItem;

        Item(final T element, final Item<T> prevItem, final Item<T> nextItem) {
            this.element = element;
            this.nextItem = nextItem;
            this.prevItem = prevItem;
        }


        public Item<T> getNextItem() {
            return nextItem;
        }

        public Item<T> getPrevItem() {
            return prevItem;
        }
    }
}
