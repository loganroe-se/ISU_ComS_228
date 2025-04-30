package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Implementation of the list interface based on linked nodes that store multiple items per node.  Rules for adding and removing
 * elements ensure that each node (except possibly the last one) is at least half full.
 * An example StoutList may be: ([A, B, C, -], [D, E, -, -]).
 * The above example is where index 3 (offset 3 of node one), index 6 (offset 2 of node two), index 7 (offset 3 of node two) are all null (empty).
 * @author Logan Roe
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E>
{
  /**
   * Default number of elements that may be stored in each node.
   */
  private static final int DEFAULT_NODESIZE = 4;
  
  /**
   * Number of elements that can be stored in each node.
   */
  private final int nodeSize;
  
  /**
   * Dummy node for head.
   */
  public Node head;
  
  /**
   * Dummy node for tail.
   */
  private Node tail;
  
  /**
   * Number of elements in the list.
   */
  private int size;
  
  /**
   * Constructs an empty list with the default node size.
   */
  public StoutList()
  {
    this(DEFAULT_NODESIZE);
  }

  /**
   * Constructs an empty list with the given node size. 
   * It will throw an IllegalArgumentException if nodeSize is either less than or equal to 0 or if it is an odd number.
   * @param nodeSize - number of elements that may be stored in each node, must be an even number
   */
  public StoutList(int nodeSize)
  {
    if (nodeSize <= 0 || nodeSize % 2 != 0) throw new IllegalArgumentException();
    
    // Dummy nodes
    head = new Node();
    tail = new Node();
    
    // Set head's next and tail's previous nodes
    head.next = tail;
    tail.previous = head;
    
    // Set instance variable: nodeSize
    this.nodeSize = nodeSize;
  }
  
  /**
   * Constructor for grading only.  Fully implemented. 
   * @param head
   * @param tail
   * @param nodeSize
   * @param size
   */
  public StoutList(Node head, Node tail, int nodeSize, int size)
  {
	  this.head = head; 
	  this.tail = tail; 
	  this.nodeSize = nodeSize; 
	  this.size = size; 
  }

  /**
   * Returns the size of the StoutList in its current state.
   * @return size of StoutList
   */
  @Override
  public int size()
  {
    return size;
  }
  
  /**
   * Adds an element to the StoutList at the end of the StoutList.
   * It will create a new node, if necessary.
   * May throw a NullPointerException if item is null.
   * @param item - the item to be added
   * @return returns true if the element was added
   */
  @Override
  public boolean add(E item)
  {
	if (item == null) {
		throw new NullPointerException();
	}
	  
	// If StoutList is empty or if the node right before the tail is full, create a new node.
	if (size == 0) {
		Node temp = new Node();
		head.next = temp;
		tail.previous = temp;
		temp.next = tail;
		temp.previous = head;
	} else if (tail.previous.count == nodeSize) {
		Node temp = new Node();
		Node current = tail.previous;
		temp.previous = current;
		temp.next = current.next;
		current.next.previous = temp;
		current.next = temp;
	}

	// Add item to the last node of the StoutList. Then increase size by one.
	tail.previous.addItem(item);
	size++;
    return true;
  }

  /**
   * Adds an item at a specified position.
   * Can throw a NullPointerException if the item to be added is null.
   * Can also throw an IndexOutOfBoundsException if pos is either less than 0 or equal to size + (nodeSize - tail.previous.count).
   * @param pos - The position at which to add the item.
   * @param item - The item to add.
   */
  @Override
  public void add(int pos, E item)
  {
	if (item == null) {
		throw new NullPointerException();
	}
	
	if (pos < 0 || (pos == (size + (nodeSize - tail.previous.count)))) {
		throw new IndexOutOfBoundsException("" + pos);
	}
	
	// Find the node and offset at which pos exists.
	NodeInfo findInfo = find(pos);
	
	// Call a private helper method to add the item in the specified node at the specified offset.
	add(findInfo.node, findInfo.offset, item);
	size++;
  }
  
  /**
   * Takes in a node and offset at which to add the item. 
   * 
   * Rules:
   * If size is 0, then create a new node and add the item to the new node. 
   * Otherwise, if offset is 0 and either n has a predecessor with less than nodeSize elements (not the head) then put item in n's predecessor.
   * If n is the tail node and n's predecessor has nodeSize elements, create a new node and put item in the start of that node.
   * Otherwise, if n has space, put item in node n at the given offset.
   * Otherwise, split the node(s). Move the last (nodeSize / 2) elements of n to a new successor node n2. 
   * Then, if offset is less than or equal to (nodeSize / 2), put item in node n at the given offset. 
   * If not, put item in n's successor node at offset (offset - (nodeSize / 2)).
   * 
   * Note: This method assumes that the parameters are already valid.
   * @param n - The node to add the element in.
   * @param offset - The offset at which to add the element in the given node.
   * @param item - The item to add.
   * @return Returns the node and offset at which the item was added.
   */
  private NodeInfo add(Node n, int offset, E item) {
	  if (size == 0) {
		  // Create a new node, link it properly, then add the item to the new node at offset 0.
		  Node temp = new Node();
		  head.next = temp;
		  tail.previous = temp;
	      temp.next = tail;
		  temp.previous = head;
		  temp.addItem(0, item);
		  return new NodeInfo(temp, 0);
	  } else if (offset == 0 && ((n.previous != head && n.previous.count < nodeSize) || (n == tail && n.previous.count == nodeSize))) {
		  if (n.previous != head && n.previous.count < nodeSize) {
			  // Add the item to n's predecessor.
			  n.previous.addItem(n.previous.count, item);
			  return new NodeInfo(n.previous, n.previous.count - 1);
		  } else {
			  // Create a new node, link it, then add the item to this new node at offset 0.
			  Node temp = new Node();
			  Node current = tail.previous;
			  temp.previous = current;
			  temp.next = current.next;
			  current.next.previous = temp;
			  current.next = temp;
			  temp.addItem(0, item);
			  return new NodeInfo(temp, 0);
		  }
	  } else if (n.count != nodeSize) {
		  // Add the item to the given node at the given offset.
		  n.addItem(offset, item);
		  return new NodeInfo(n, offset);
	  } else {
		  // Create a new node and link it.
		  Node n2 = new Node();
		  n2.previous = n;
		  n2.next = n.next;
		  n.next.previous = n2;
		  n.next = n2;
		  
		  // Move the last (nodeSize / 2) elements of n into n2.
		  for (int i = (nodeSize / 2); i < nodeSize; i++) {
			  n2.addItem((n.data)[i]);
			  n.data[i] = null;
			  n.count--;
		  }
		  
		  // Based on the value of offset, referenced to (nodeSize / 2), add the item to the proper node.
		  if (offset <= (nodeSize / 2)) {
			  n.addItem(offset, item);
			  return new NodeInfo(n, offset);
		  } else {
			  n2.addItem((offset - (nodeSize / 2)), item);
			  return new NodeInfo(n2, (offset - (nodeSize / 2)));
		  }
	  }
  }

  /**
   * Remove the element at the given position, assuming the position is in a valid range.
   * If position is not in the valid range, then throw an IndexOutOfBoundsException.
   * 
   * Rules:
   * If node n containing the element to be removed is the last node and only has said element, delete the node.
   * Otherwise, if n is the last node or if n has more than (nodeSize / 2) elements, remove the item at pos from n.
   * Otherwise, n2 (n's successor) must be used to perform a merge of sorts.
   * If n2 has more than (nodeSize / 2) elements, move the first element from n2 to n.
   * Else, move all elements from n2 to n and fully delete n2.
   * 
   * @param pos - The position of the element to be removed.
   * @return Returns the element that was removed.
   */
  @Override
  public E remove(int pos)
  {
	if (pos < 0 || pos == size) {
		throw new IndexOutOfBoundsException("" + pos);
	}
	
	// Find the node and offset at which pos exists.
	NodeInfo nInfo = find(pos);
	
	if (nInfo.node.next == tail && nInfo.node.count == 1) {
		// Remove the item at the given pos. Then remove this node.
		nInfo.node.removeItem(nInfo.offset);
		nInfo.node.previous.next = tail;
		tail.previous = nInfo.node.previous;
	} else if (nInfo.node.next == tail || (nInfo.node.count > (nodeSize / 2))) {
		// Remove the item at the given pos.
		nInfo.node.removeItem(nInfo.offset);
	} else {
		// Obtain n's successor node, n2.
		Node n2 = nInfo.node.next;
		E temp;
		
		if (n2.count > (nodeSize / 2)) {
			// Remove the item at the given pos.
			nInfo.node.removeItem(nInfo.offset);
			// Store the value at the 0th index of n2. Remove this item.
			temp = n2.data[0];
			n2.removeItem(0);
			// Add the temp value to n.
			nInfo.node.addItem(temp);
		} else {
			// Remove the item at the given pos and store the number of elements in n2.
			nInfo.node.removeItem(nInfo.offset);
			int n2Count = n2.count;
			
			// Move all items in n2 to n.
			for (int i = 0; i < n2Count; i++) {
				temp = n2.data[0];
				n2.removeItem(0);
				nInfo.node.addItem(temp);
			}
			
			// Remove n2.
			nInfo.node.next = n2.next;
			n2.next.previous = nInfo.node;
		}
	}
	
	// Decrement size and return the item that was removed.
	size--;
    return nInfo.node.data[nInfo.offset];
  }
  
  /**
   * This helper method will find the node and the offset within that node at which a specific position occurs.
   * It does this in a fast fashion by skipping nodes that are not necessary to fully explore.
   * It is assumed that pos will not be less than -1 or greater than size + (nodeSize - tail.previous.count).
   * In other words, it is assumed that pos is within its bounds.
   * @param pos - The position at which the wanted element is.
   * @return Return the node and the offset of said node at which pos exists.
   */
  private NodeInfo find(int pos) {
	  // If pos is -1, then return NodeInfo with head as the node and -2 to represent that it is the head.
	  // If pos is too large, then return NodeInfo with tail as the node and -1 to represent that it is the tail.
	  if (pos == -1) {
		  return new NodeInfo(head, -2);
	  } else if (pos == (size + (nodeSize - tail.previous.count))) {
		  return new NodeInfo(tail, -1);
	  }
	  
	  Node current = head;
	  int totalSize = 0;
	  
	  // Find the node at which the pos is in by added the number of elements in each previous node.
	  while (totalSize <= pos && current.next != tail) {
		  current = current.next;
		  totalSize += current.count;
	  }
	  
	  // Find the size and the offset of the wanted item in the previously found node.
	  int currSize = current.count;
	  int offset = pos - (totalSize - currSize);
	  
	  // Return the node and the offset at which the wanted item exists in said node.
	  return new NodeInfo(current, offset);
  }

  /**
   * Sort all elements in the stout list in the NON-DECREASING order. 
   * Traverse the list and copy its elements into an array, deleting every visited node along 
   * the way.  Then, sort the array by calling the insertionSort() method.  Finally, copy all 
   * elements from the array back to the stout list, creating new nodes for storage. 
   * After sorting, all nodes but (possibly) the last one must be full of elements.  
   *  
   * Comparator<E> is used to compare the two elements.   
   */
  public void sort()
  {
	  // Initialize an E[] array to store all the values from StoutList.
	  E[] arr = (E[]) new Comparable[size];
	  Node current = head.next;
	  Node temp;
	  int currCount = 0;
	  int j = 0;
	  
	  // Iterate through all of the nodes to obtain the values within.
	  while (current != tail) {
		  currCount = current.count;
		  
		  // Iterate through all of the values within the current node and store these values in the array.
		  for (int i = 0; i < currCount; i++) {
				arr[j++] = current.data[0];
				current.removeItem(0);
		  }
		  
		  // Remove the node that information was just gathered from as it is now useless.
		  temp = current.next;
		  current.previous.next = current.next;
		  current.next.previous = current.previous;
		  current = temp;
	  }
	  
	  // Initiate a comparator to compare the two different elements.
	  Comparator<? super E> comp = new Comparator<>() {
		@Override
		public int compare(E o1, E o2) {
			return o1.compareTo(o2);
		}
	  };
	  
	  // Calls a private sort method to sort the array of elements in non-decreasing order using insertion sort.
	  insertionSort(arr, comp);
	  
	  // Iterate through the length of the sorted array of elements.
	  for (int i = 0; i <= arr.length; i++) {
		  // If no nodes exist (other than the head and tail) or if the node right before the tail is full, create a new node.
		  if (tail.previous == head || tail.previous.count == nodeSize) {
			  temp = new Node();
			  temp.previous = tail.previous;
			  temp.next = tail;
			  tail.previous.next = temp;
			  tail.previous = temp;
		  }
		  
		  // Iterate through the array for a number (nodeSize) of values to add to the current node.
		  for (int k = 0; k < nodeSize; k++) {
			  // If i equals the length of the array, break out of the for loop.
			  if (i == arr.length) {
				  break;
			  }
			  
			  tail.previous.addItem(arr[i++]);
		  }
		  
		  // If i does not equal the length of the array, decrement i. 
		  // This is done so that no elements are skipped from the array.
		  if (i != arr.length) {
			  i--;
		  }
	  }
  }
  
  /**
   * Sort all elements in the stout list in the NON-INCREASING order. Call the bubbleSort()
   * method.  After sorting, all but (possibly) the last nodes must be filled with elements.  
   */
  public void sortReverse() 
  {
	  // Initialize an E[] array to store all the values from StoutList.
	  E[] arr = (E[]) new Comparable[size];
	  Node current = head.next;
	  Node temp;
	  int currCount = 0;
	  int j = 0;
	  
	  // Iterate through all of the nodes to obtain the values within.
	  while (current != tail) {
		  currCount = current.count;
		  
		  // Iterate through all of the values within the current node and store these values in the array.
		  for (int i = 0; i < currCount; i++) {
				arr[j++] = current.data[0];
				current.removeItem(0);
		  }
		  
		  // Remove the node that information was just gathered from as it is now useless.
		  temp = current.next;
		  current.previous.next = current.next;
		  current.next.previous = current.previous;
		  current = temp;
	  }
	  
	  // Initiate a comparator to compare the two different elements.
	  Comparator<? super E> comp = new Comparator<>() {
		@Override
		public int compare(E o1, E o2) {
			return o1.compareTo(o2);
		}
	  };
	  
	  // Calls a private sort method to sort the array of elements in non-increasing order using bubble sort.
	  bubbleSort(arr);
	  
	  // Iterate through the length of the sorted array of elements.
	  for (int i = 0; i <= arr.length; i++) {
		  // If no nodes exist (other than the head and tail) or if the node right before the tail is full, create a new node.
		  if (tail.previous == head || tail.previous.count == nodeSize) {
			  temp = new Node();
			  temp.previous = tail.previous;
			  temp.next = tail;
			  tail.previous.next = temp;
			  tail.previous = temp;
		  }
		  
		  // Iterate through the array for a number (nodeSize) of values to add to the current node.
		  for (int k = 0; k < nodeSize; k++) {
			  // If i equals the length of the array, break out of the for loop.
			  if (i == arr.length) {
				  break;
			  }
			  
			  tail.previous.addItem(arr[i++]);
		  }
		  
		  // If i does not equal the length of the array, decrement i. 
		  // This is done so that no elements are skipped from the array.
		  if (i != arr.length) {
			  i--;
		  }
	  }
  }
  
  /**
   * This method simply returns a new StoutListIterator.
   * @return Returns a new StoutListIterator at 0.
   */
  @Override
  public Iterator<E> iterator()
  {
    return new StoutListIterator();
  }

  /**
   * This method simply returns a new StoutListIterator.
   * @return Returns a new StoutListIterator at 0.
   */
  @Override
  public ListIterator<E> listIterator()
  {
    return new StoutListIterator();
  }

  /**
   * This method simply returns a new StoutListIterator at the given index.
   * @param index - The index at which the iterator will start at.
   * @return Returns a new StoutListIterator at a given index.
   */
  @Override
  public ListIterator<E> listIterator(int index)
  {
    return new StoutListIterator(index);
  }
  
  /**
   * Returns a string representation of this list showing the internal structure of the nodes.
   * @return Returns a string that represents the StoutList.
   */
  public String toStringInternal()
  {
    return toStringInternal(null);
  }

  /**
   * Returns a string representation of this list showing the internal
   * structure of the nodes and the position of the iterator.
   *
   * @param iter - an iterator for this list
   */
  public String toStringInternal(ListIterator<E> iter) 
  {
      int count = 0;
      int position = -1;
      if (iter != null) {
          position = iter.nextIndex();
      }

      StringBuilder sb = new StringBuilder();
      sb.append('[');
      Node current = head.next;
      while (current != tail) {
          sb.append('(');
          E data = current.data[0];
          if (data == null) {
              sb.append("-");
          } else {
              if (position == count) {
                  sb.append("| ");
                  position = -1;
              }
              sb.append(data.toString());
              ++count;
          }

          for (int i = 1; i < nodeSize; ++i) {
             sb.append(", ");
              data = current.data[i];
              if (data == null) {
                  sb.append("-");
              } else {
                  if (position == count) {
                      sb.append("| ");
                      position = -1;
                  }
                  sb.append(data.toString());
                  ++count;

                  // iterator at end
                  if (position == size && count == size) {
                      sb.append(" |");
                      position = -1;
                  }
             }
          }
          sb.append(')');
          current = current.next;
          if (current != tail)
              sb.append(", ");
      }
      sb.append("]");
      return sb.toString();
  }


  /**
   * Node type for this list.  Each node holds a maximum
   * of nodeSize elements in an array.  Empty slots
   * are null.
   */
  private class Node
  {
    /**
     * Array of actual data elements.
     */
    // Unchecked warning unavoidable.
    public E[] data = (E[]) new Comparable[nodeSize];
    
    /**
     * Link to next node.
     */
    public Node next;
    
    /**
     * Link to previous node;
     */
    public Node previous;
    
    /**
     * Index of the next available offset in this node, also 
     * equal to the number of elements in this node.
     */
    public int count;

    /**
     * Adds an item to this node at the first available offset.
     * Precondition: count < nodeSize
     * @param item element to be added
     */
    void addItem(E item)
    {
      if (count >= nodeSize)
      {
        return;
      }
      data[count++] = item;
    }
  
    /**
     * Adds an item to this node at the indicated offset, shifting
     * elements to the right as necessary.
     * 
     * Precondition: count < nodeSize
     * @param offset array index at which to put the new element
     * @param item element to be added
     */
    void addItem(int offset, E item)
    {
      if (count >= nodeSize)
      {
    	  return;
      }
      for (int i = count - 1; i >= offset; --i)
      {
        data[i + 1] = data[i];
      }
      ++count;
      data[offset] = item;
    }

    /**
     * Deletes an element from this node at the indicated offset, 
     * shifting elements left as necessary.
     * Precondition: 0 <= offset < count
     * @param offset - The offset value at which the item that should be removed is in the given node.
     */
    void removeItem(int offset)
    {
      E item = data[offset];
      for (int i = offset + 1; i < nodeSize; ++i)
      {
        data[i - 1] = data[i];
      }
      data[count - 1] = null;
      --count;
    }    
  }
  
  /**
   * A private class that allows for easy storing of node information.
   * It will store the node and the offset within said node that a given position/element exists.
   * @author Logan Roe
   */
  private class NodeInfo {
	  public Node node;
	  public int offset;
	  
	  /**
	   * Constructor that sets the instance variables to the corresponding parameters.
	   * @param node - The node in which a specific element/position exists.
	   * @param offset - The offset at which a specific element/position exists in a given node.
	   */
	  public NodeInfo(Node node, int offset) {
		  this.node = node;
		  this.offset = offset;
	  }
  }
  
  /**
   * The iterator that can go both forward and reverse throughout the StoutList.
   * It is also capable of changing the value of elements and removing and adding elements.
   * @author Logan Roe
   */
  private class StoutListIterator implements ListIterator<E>
  {
	// Constants for the direction of the cursor.
	private static final int BEHIND = -1;
	private static final int AHEAD = 1;
	private static final int NONE = 0;
	  
	// The position of the cursor which includes a node and an offset value.
	private NodeInfo cursor;
	
	// The position that the cursor is in the StoutList.
	private int index;
	
	// The direction that the cursor is "facing".
	private int direction;
	  
    /**
     * Default constructor 
     * Calls the other constructor to start an iterator at 0.
     */
    public StoutListIterator()
    {
    	this(0);
    }

    /**
     * Constructor finds node at a given position so that it can start at the given position.
     * May throw IndexOutOfBoundsException if the given position is less than 0 or greater than size.
     * @param pos - The position at which the iterator starts.
     */
    public StoutListIterator(int pos)
    {
    	if (pos < 0 || pos > size) {
    		throw new IndexOutOfBoundsException("" + pos);
    	}
    	
    	// Finds a node and an offset within the node at which the given position exists. Sets it to cursor.
    	cursor = find(pos);
    	
    	// Sets index to the given position and direction to NONE.
    	index = pos;
    	direction = NONE;
    }

    /**
     * The next value exists if index is less than size. It is relative to the cursor.
     * @return Returns whether there is a next value.
     */
    @Override
    public boolean hasNext()
    {
    	return index < size;
    }

    /**
     * Goes to the next value in the StoutList, if possible.
     * Can throw a NoSuchElementException if there is no next value.
     * @return Returns the element that was just passed by the iterator.
     */
    @Override
    public E next()
    {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		
		// Store the element that is being passed.
		E data = (cursor.node.data)[cursor.offset];
		// Find the next value. Then, set the direction to BEHIND.
		cursor = find(++index);
		direction = BEHIND;
		return data;
    }

    /**
     * Removes the element either before or at the cursor's index.
     * Which it does depends upon which direction the cursor has.
     * If the direction is NONE, then an IllegalStateException will be thrown.
     */
    @Override
    public void remove()
    {
    	if (direction == NONE) {
    		throw new IllegalStateException();
    	}
    	
    	// If the direction is AHEAD (meaning previous was just called), then remove the value at the current index.
    	// If the direction is BEHIND (meaning next was just called), then remove the value at the decremented current index.
    	if (direction == AHEAD) {
    		StoutList.this.remove(index);
    	} else {
    		StoutList.this.remove(--index);
    	}
    	
    	// Update cursor by finding the new NodeInfo values using the new index value. Set direction to NONE.
    	cursor = find(index);
    	direction = NONE;
    }

    /**
     * This method simply returns whether or not the cursor can go to a previous value.
     * @return Returns whether or not there is a previous value.
     */
	@Override
	public boolean hasPrevious() {
		return index > 0;
	}

	/**
	 * Goes to the previous value in the StoutList, if possible.
	 * Can throw a NoSuchElementException if there is no previous value.
	 * @return Returns the value that the cursor is currently at.
	 */
	@Override
	public E previous() {
		if (!hasPrevious()) {
			throw new NoSuchElementException();
		}
		
		// Create a temporary NodeInfo value at index - 1.
		NodeInfo temp = find(index - 1);
		
		// Store the element value. Then find a new cursor value at a decremented index.
		E data = (temp.node.data)[temp.offset];
		cursor = find(--index);
		// Set the direction to AHEAD.
		direction = AHEAD;
		return data;
	}

	/**
	 * Returns what the next index is in the StoutList.
	 * @return Returns the index that the cursor is at.
	 */
	@Override
	public int nextIndex() {
		return index;
	}

	/**
	 * Returns what the previous index is in the StoutList.
	 * @return Returns the index that the cursor is at - 1.
	 */
	@Override
	public int previousIndex() {
		return index - 1;
	}

	/**
	 * Sets the current element to the given value, e. 
	 * The value that it changes is based upon whether the direction is AHEAD or BEHIND.
	 * May throw an IllegalStateException if the direction is NONE.
	 * @param e - The value to set the current element to.
	 */
	@Override
	public void set(E e) {
		if (direction == NONE) {
			throw new IllegalStateException();
		}
		
    	// If the direction is AHEAD (meaning previous was just called), then set the value at the current index.
    	// If the direction is BEHIND (meaning next was just called), then set the value at the decremented current index.
		if (direction == AHEAD) {
			(cursor.node.data)[cursor.offset] = e;
		} else {
			NodeInfo temp = find(index - 1);
			(temp.node.data)[temp.offset] = e; 
		}
	}

	/**
	 * Adds a new element to the StoutList at the current node and offset.
	 * @param e - The value to add to the list.
	 */
	@Override
	public void add(E e) {
		// Calls the helper method from StoutList to handle adding the item.
		// Updates cursor with the returned value from add().
		cursor = StoutList.this.add(cursor.node, cursor.offset, e);
		// Update instant variables and the cursor's offset.
		cursor.offset++;
		index++;
		size++;
		// Set the direction to NONE.
		direction = NONE;
	}
  }
  

  /**
   * Sorts an array arr[] using the insertion sort algorithm in the NON-DECREASING order. 
   * @param arr - array storing elements from the list 
   * @param comp - comparator used in sorting 
   */
  private void insertionSort(E[] arr, Comparator<? super E> comp)
  {
	  for (int i = 1; i < arr.length; i++) {
		  // Store a temporary E value.
		  E temp = arr[i];
		  int j = i - 1;
		  
		  // If the current E value is less than the temp E value, then move the current E value to the left one in the array of E values.
		  while (j > -1 && (comp.compare(temp, arr[j]) < 0)) {
			  arr[j + 1] = arr[j];
			  j--;
		  }
		  
		  arr[j + 1] = temp;
	  }
  }
  
  /**
   * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order.
   * @param arr - array holding elements from the list
   */
  private void bubbleSort(E[] arr)
  {
	  // Initiate a comparable object to compare two different elements.
	  Comparable<? super E> comp = new Comparable<>() {
		@Override
		public int compareTo(E o) {
			return this.compareTo(o);
		} 
	  };
	  
	  // Iterates through the entire array, except the last value.
	  for (int i = 0; i < (arr.length - 1); i++) {
		  // Iterates through the entire array - i - 1.
		  for (int j = 0; j < (arr.length - i - 1); j++) {
			  // If arr[j] is "less" than arr[j + 1], then swap arr[j] and arr[j + 1].
			  if (arr[j].compareTo(arr[j + 1]) < 0) {
				  E temp = arr[j];
				  arr[j] = arr[j + 1];
				  arr[j + 1] = temp;
			  }
		  }
	  }
  }
}