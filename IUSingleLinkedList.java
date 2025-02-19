import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Single-linked node implementation of IndexedUnsortedList.
 * An Iterator with working remove() method is implemented, but
 * ListIterator is unsupported.
 * 
 * @author 
 * 
 * @param <T> type to store
 */
public class IUSingleLinkedList<T> implements IndexedUnsortedList<T> {
	private Node<T> head, tail;
	private int size;
	private int modCount;
	
	/** Creates an empty list */
	public IUSingleLinkedList() {
		head = tail = null;
		size = 0;
		modCount = 0;
	}

	@Override
	public void addToFront(T element) {
		if(element == null)
        {
            throw new java.lang.NullPointerException();
        }
        head = new Node<T>(element);
        size++;
	}

	@Override
	public void addToRear(T element) {
		if(element == null)
        {
            throw new java.lang.NullPointerException();
        }
        Node<T> node = new Node<T>(element);
        tail.setNext(node);
        tail = node;
        size++;
	}

	@Override
	public void add(T element) {
		addToRear(element);
	}

	@Override
	public void addAfter(T element, T target) {
		int index = indexOf(element);

        if((index + 1 < 0) || (index + 1 > size))
        {
            throw new java.lang.IndexOutOfBoundsException();
        }
        else if(index == size - 1)
        {
            addToRear(element);
        }
        else
        {
            Node<T> temp = head;
            int i = 0;

            while(i  < index - 1)
            {
                temp = temp.getNext();
                i++;
            }

            Node<T> node = new Node<T>(element);
            temp.setNext(node);
            size++;
        } 
		
	}

	@Override
	public void add(int index, T element) {
		if((index < 0) || (index > size))
        {
            throw new java.lang.IndexOutOfBoundsException();
        }

        if(index == 0)
        {
            addToFront(element);
        }
        else if(index == size)
        {
            addToRear(element);
        }
        else
        {
            Node<T> temp = head;
            int i = 0;

            while(i  < index - 1)
            {
                temp = temp.getNext();
                i++;
            }

            Node<T> node = new Node<T>(element);
            temp.setNext(node);
            size++;
        }
	}

	@Override
	public T removeFirst() {
		if(head==null)
        {
            return null;
        }

        T data = head.getElement();
        head=head.getNext();
        return data;
	}

	@Override
	public T removeLast() {
		if(head==null)
        {
            return null;
        }
        
        Node<T> temp=head;
        while(temp.getNext()!=tail)
        {
            temp=temp.getNext();
        }
        T data=tail.getElement();
        temp.setNext(null);
        return data;
	}

	@Override
	public T remove(T element) {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		
		boolean found = false;
		Node<T> previous = null;
		Node<T> current = head;
		
		while (current != null && !found) {
			if (element.equals(current.getElement())) {
				found = true;
			} else {
				previous = current;
				current = current.getNext();
			}
		}
		
		if (!found) {
			throw new NoSuchElementException();
		}
		
		if (size() == 1) { //only node
			head = tail = null;
		} else if (current == head) { //first node
			head = current.getNext();
		} else if (current == tail) { //last node
			tail = previous;
			tail.setNext(null);
		} else { //somewhere in the middle
			previous.setNext(current.getNext());
		}
		
		size--;
		modCount++;
		
		return current.getElement();
	}

	@Override
	public T remove(int index) {
		if((index < 0) || (index > size))
        {
            throw new java.lang.IndexOutOfBoundsException();
        }
        
        if(index==0)
        {
            if(head==null)
            {
                return null;
            }
            T data=head.getElement();
            head=head.getNext();
            return data;
        }
        else
        {
            Node<T> temp = head;
            int i=0;

            while(i < index - 1)
            {
                temp = temp.getNext();
                i++;
            }

            Node<T> indexNode = temp.getNext();
            T data=(T) indexNode.getElement();
            temp.setNext(indexNode.getNext());
            return data;
        }
	}

	@Override
	public void set(int index, T element) {
		if((index < 0) || (index > size))
        {
            throw new java.lang.IndexOutOfBoundsException();
        } 
		else
        {
            Node<T> temp = head;
            Node<T> node = new Node<T>(element);
            temp.setNext(node);
        }
	}

	@Override
	public T get(int index) {
		if(index < 0 || index > size)
        {
            throw new java.lang.IndexOutOfBoundsException();
        }
        
        if(index==0)
        {
            if(head==null)
            {
            return null;
            }
            return head.getElement();
        }
        else if(index==size-1)
        {
            return tail.getElement();
        }
        else
        {
            Node<T> temp = head;
            int i=0;
            while(i<index)
            {
                temp=temp.getNext();
                i++;
            }
            return (T) temp.getElement();
        }
	}

	@Override
	public int indexOf(T element) {
		int index = -1;
		
		if (!isEmpty()) {
			int i = 0;
			while (index == -1 && i < size) 
            {
				if (element.equals(get(i))) 
                {
					index = i;
				} 
                else 
                {
					i++;
				}
			}
		}
		
		return index;
	}

	@Override
	public T first() {
		if (size >= 1)
        {
            return get(0);
        }

		return null;
	}

	@Override
	public T last() {
		if (size >= 1)
        {
            return get(size - 1);
        }

		return null;
	}

	@Override
	public boolean contains(T target) {
		if (size >= 1)
        {
            int i = 0;
			while (i < size) 
            {
				if (target.equals(get(i))) 
                {
					return true;
				} 
                else 
                {
					i++;
				}
			}
        } 

		return false;
	}

	@Override
	public boolean isEmpty() {
	if(head == null)
    {
        return true;
    }

    return false;
    }

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<T> iterator() {
		return new SLLIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}

	/** Iterator for IUSingleLinkedList */
	private class SLLIterator implements Iterator<T> {
		private Node<T> nextNode;
		private int iterModCount;
		
		/** Creates a new iterator for the list */
		public SLLIterator() {
			nextNode = head;
			iterModCount = modCount;
		}

		@Override
		public boolean hasNext() {
            Node<T> temp = head;

            if (temp.getNext() != null)
            {
                return true;
            }

			return false;
		}

		@Override
		public T next() {
            Node<T> next = head.getNext();
            T data = next.getElement();
            return data;
		}
		
		@Override
		public void remove() {
			head = null;
		}
	}
}