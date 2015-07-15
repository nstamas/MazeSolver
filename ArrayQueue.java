///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Maze Solver
// File:             ArrayQueue.java
// Semester:         CS367 Spring 2013
//
// Author:           Calvin Hareng, hareng@wisc.edu
// CS Login:         hareng
// Lecturer's Name:  Jim Skrentny
// Lab Section:      -
//
//                   PAIR PROGRAMMERS COMPLETE THIS SECTION
// Pair Partner:     Nick Stamas
// CS Login:         stamas
// Lecturer's Name:  Jim Skrentny
// Lab Section:      -
//
//                   STUDENTS WHO GET HELP FROM ANYONE OTHER THAN THEIR PARTNER
// Credits:          (list anyone who helped you write your program)
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * This class implements the QueueADT and represents a queue.
 * It follows the FIFO (first in first out) data structure.
 * The queue is stored in the form of a circular array, and
 * the array is expanded each time the items inside it fill
 * up the array.  The front of the array is initialized
 * to -1 and the rear is initialized to 0 because it is a 
 * circular array.
 * @author hareng
 *
 * @param <E>
 */
public class ArrayQueue<E> implements QueueADT<E> {
	final int INITIALSIZE = 10;
	int front; /////////////
	int rear;
	E[] queueArray;
	boolean Empty;
	int size;
	public ArrayQueue(){
		front = -1; // initializes front and rear so that rear != front
		rear = 0;
		queueArray = (E[]) new Object [INITIALSIZE];
		Empty = true;
		size = 0;
	}	
	/**
	 * This method adds an item of type E to the end
	 * of the queue. If the front == rear then the array
	 * is full and the array will be expanded to twice its original
	 * size.  The size is increased each time through
	 * 
	 *  @param (E item) This is the item that is added to the end of the queue. 
	 */
	public void enqueue(E item){
		// The array is full
		if(front == rear){
			// creates a temporary array
			E[] queueArray1 = (E[]) new Object [queueArray.length];
			// copies the original array into the temporary array
			System.arraycopy(queueArray, front, queueArray1, 0, 
					queueArray.length);
			// Doubles the original array length
			queueArray = (E[]) new Object [queueArray1.length*2];
			// copies the array elements back into array, which was doubled
			// in size.
			System.arraycopy(queueArray1, 0, queueArray, 0, queueArray1.length);
			front = 0;
			rear = queueArray1.length;
		}
		else{
			// the item is added to the rear of the array
			queueArray[rear] = item;
			// if the rear is at the last position in the array, the rear
			// is set to zero because the array is circular.
			if(rear >= queueArray.length - 1){
				rear = 0;
			}
			else{
				rear++;
			}
			if(front == -1){
				front = 0; // If there is 1 item added to an empty array
			}
		}
		size++;
		// array will never be empty after an item is added to it
		Empty = false;
	}
	/**
	 * This method dequeues, or removes, the front item in the queue
	 * and returns this item.  If the front and rear variables are
	 * equal, this indicates that the array is empty.
	 * 
	 * @return the item that is dequeued is returned.  It is the front item in
	 * 		   the queue.
	 */
	public E dequeue(){ 
		// stores the item that is dequeued from the array
		E removed = queueArray[front];
		// sets the spot where the item was dequeued to null
		queueArray[front] = null;
		// if the front is at the last position in the array, the front
		// is set to zero because the array is circular.
		if(front >= queueArray.length - 1){
			front = 0;
		}
		else{
			front++;
		}
		
		// This indicates that there are no more items in the array and
		// the variable empty is set to true and front and rear are
		// initialized to their previous values.
		if(front == rear){
			front = -1;
			rear = 0;
			Empty = true;			
		}
		else{
			Empty = false;
		}
        // decrements the size each time an item is dequeued.
		size--;
		return removed;
	}
	/**
	 * This method checks to see if the array, which represents a queue,
	 * is empty.
	 * 
	 * @return the boolean variable Empty is returned.
	 */
	public boolean isEmpty(){
		return Empty;
	}
	/**
	 * This method returns the amount of items in the queue (size of array).
	 * 
	 * @return the integer value size is returned.
	 */
	public int size(){
		return size;
	}
	
	
}
