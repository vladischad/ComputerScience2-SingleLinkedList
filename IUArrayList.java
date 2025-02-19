import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Array-based implementation of IndexedUnsortedList.
 * An Iterator with working remove() method is implemented, but
 * ListIterator is unsupported. 
 * 
 * @author 
 *
 * @param <T> type to store
 */
public class IUArrayList<T> implements IndexedUnsortedList<T> {
	private static final int DEFAULT_CAPACITY = 10;
	private static final int NOT_FOUND = -1;
	
	private T[] array;
	private int rear;
	private int modCount;
	
	/** Creates an empty list with default initial capacity */
	public IUArrayList() {
		this(DEFAULT_CAPACITY);
		array = (T[])(new Object[DEFAULT_CAPACITY]);
		rear = 0;//count
		modCount = 0;
	}
	
	/** 
	 * Creates an empty list with the given initial capacity
	 * @param initialCapacity
	 */
	@SuppressWarnings("unchecked")
	public IUArrayList(int initialCapacity) {
		array = (T[])(new Object[initialCapacity]);
		rear = 0;
		modCount = 0;
	}
	
	/** Double the capacity of array */
	private void expandCapacity() {
		array = Arrays.copyOf(array, array.length*2);
	}

	@Override
	public void addToFront(T element) 
	{
		array = Arrays.copyOf(array, array.length + 1);
            for(int i = rear; i > 0; i--) 
            {
                array[i] = array[i-1];
            }
        array[0] = element;
        rear++;
        modCount++;		
	}

	@Override
	public void addToRear(T element) {
		array = Arrays.copyOf(array, array.length + 1);
        array[array.length - 1] = element;
        rear++;
        modCount++; 
	}

	@Override
	public void add(T element) {
		array = Arrays.copyOf(array, array.length + 1);
        array[array.length - 1] = element;
        rear++;
        modCount++;
	}

	@Override
	public void addAfter(T element, T target) {
		int index = indexOf(target);
        if(index == -1) 
        {
            throw new NoSuchElementException();
        } 
        else 
        {
            array = Arrays.copyOf(array, array.length + 1);
            add(index + 1, element);
        }
	}

	@Override
	public void add(int index, T element) {
		if (index < 0 || index > rear) 
        {
            throw new IndexOutOfBoundsException();
        } 
        else 
        {
            array = Arrays.copyOf(array, array.length + 1);
            for(int i = rear; i > index; i--) 
            {
                array[i] = array[i-1];
            }
            array[index] = element;
            rear++;
            modCount++;
        } 
	}

	@Override
	public T removeFirst() 
	{
		if(this.isEmpty() == true) 
        {
            throw new NoSuchElementException();
        } 
        else 
        {
            return this.remove(0);
        } 
		//return null;
	}

	@Override
	public T removeLast() 
	{
		if(this.isEmpty() == true) 
        {
            throw new NoSuchElementException();
        } 
        else 
        {
            return this.remove(array.length - 1);
        } 
		//return null;
	}

	@Override
	public T remove(T element) {
		int index = indexOf(element);
		if (index == NOT_FOUND) {
			throw new NoSuchElementException();
		}
		
		T retVal = array[index];
		
		rear--;
		//shift elements
		for (int i = index; i < rear; i++) {
			array[i] = array[i+1];
		}
		array[rear] = null;
		modCount++;
		
		return retVal;
	}

	@Override
	public T remove(int index) {
		if (index < 0 || index >= array.length) 
        {
            throw new IndexOutOfBoundsException();
        } 
        else 
        {
            T oldObject = array[index];
            for(int i = index; i < array.length - 1; i++) 
            {
                array[i] = array[i + 1];
            }
            array[array.length - 1] = null;
            rear--;
            modCount++;
            return oldObject;
        } 
		//return null;
	}

	@Override
	public void set(int index, T element) {
		if (index < 0 || index >= array.length) 
        {
            throw new IndexOutOfBoundsException();
        } 
        else 
        {
            array[index] = element;
            modCount++;
        }
	}

	@Override
	public T get(int index) 
	{
		if (index < 0 || index >= array.length) 
        {
            throw new IndexOutOfBoundsException();
        } 
        else 
        {
            return array[index];
        } 
		//return null;
	}

	@Override
	public int indexOf(T element) 
	{
		int index = NOT_FOUND;
		
		if (!isEmpty()) {
			int i = 0;
			while (index == NOT_FOUND && i < rear) {
				if (element.equals(array[i])) {
					index = i;
				} else {
					i++;
				}
			}
		}
		
		return index;
	}

	@Override
	public T first() {
		if(isEmpty() == true) 
        {
            throw new NoSuchElementException();
        } 
		else 
        {
            T output = array[0];
            return output;
        } 
		//return null;
	}

	@Override
	public T last() {
		if(isEmpty() == true) 
        {
            throw new NoSuchElementException();
        } 
        else 
        {
            T output = array[array.length - 1];
            if (output != null) 
			{
            return output;
        	} 
        } 
		return null;
	}

	@Override
	public boolean contains(T target) {
		return (indexOf(target) != NOT_FOUND);
	}

	@Override
	public boolean isEmpty() 
	{
		if(array.length == 0 && modCount == 0) 
        {
            return true;
        } 
		return false;
	}

	@Override
	public int size() 
	{
		return array.length;
	}

	@Override
	public Iterator<T> iterator() 
	{
		return new ALIterator();
	}

	@Override
	public ListIterator<T> listIterator() 
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) 
	{
		throw new UnsupportedOperationException();
	}

	/** Iterator for IUArrayList */
	private class ALIterator implements Iterator<T> {
		private int nextIndex;
		private int iterModCount;
		
		public ALIterator() {
			nextIndex = 0;
			iterModCount = modCount;
		}

		@Override
		public boolean hasNext() {
			if (nextIndex < rear) 
			{
				return true;
			}
			return false;
		}

		@Override
		public T next() {
			if(!hasNext()) 
            {
                throw new NoSuchElementException();
            } 
            else 
            {
                T object = array[nextIndex];
                nextIndex++;
                return object;
            } 
			//return null;
		}
		
		@Override
		public void remove() {
			if(nextIndex != 0 && array[nextIndex - 1] != null) 
            {
                array[nextIndex - 1] = null;
            } 
            else 
            {
                throw new IllegalStateException();
            }
		}
	}
}