/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package circularlinkedlist;
import java.util.Iterator;
/**
 *
 * @author Scott
 */
public class CircularLinkedList<E> implements Iterable<E> {

	
	
	// Your variables
	// You can include a reference to a tail if you want
	Node<E> head;
	int size;  // BE SURE TO KEEP TRACK OF THE SIZE

	
	// implement this constructor
	public CircularLinkedList() {
            head = null;
            size = 0;
	}


	// writing helper functions for add and remove, like the book did can help
	// but remember, the last element's next node will be the head!



	// attach a node to the end of the list
	// Be sure to handle the adding to an empty list
	// always returns true 
	public boolean add(E e) {
            Node<E> temp = new Node<>(e);
                if(size == 0 ){
			temp.next = head;
			head = temp;
		}
		else {
			Node<E> node = getNode(size - 1);
			temp.next = node.next;
			node.next =  temp;
                        temp.next = head;
		}
                size++;
		return true;

	}

	
	// need to handle
	// out of bounds
	// empty list
	// adding to front
	// adding to middle
	// adding to "end"
	// REMEMBER TO INCREMENT THE SIZE
	public boolean add(int index, E e){
            if(index < 0 || index >= size)
            {
                System.out.println("OutofBounds");
                return false;
            }
            
            Node<E> temp = new Node<>(e);
            if(index == 0 )
            {
                temp.next = head;
		head = temp;
            }
            else if (index != size - 1) 
            {
		Node<E> node = getNode(index - 1);
		temp.next = node.next;
		node.next =  temp;                        
            }
            else
            {
                Node<E> node = getNode(size - 1);
		temp.next = node.next;
		node.next =  temp;
                temp.next = head;
            }
                size++;
		return true;
		
	}

	// I highly recommend using this helper method
	// Return Node<E> found at the specified index
	// be sure to handle out of bounds cases
	private Node<E> getNode(int index ) {
            if(index < 0 || index >= size)
            {
                System.out.println("Out of bounds");
                return null;
            }
            
            Node<E> current = head;
            for(int i = 0; i < index; i++)
            {
                current = current.next;
            }
            return current;
	}

	
	
	// remove must handle the following cases
	// out of bounds
	// removing the only thing in the list
	// removing the first thing in the list (need to adjust the last thing in the list to point to the beginning)
	// removing the last thing (if you have a tail)
	// removing any other node.
	// REMEMBER TO DECREMENT THE SIZE
	public E remove(int index) {
            if(index < 0 || index >= size)
            {
                System.out.println("Outofbounds: " + index);
                return null;
            }
            
            E removed;
            
            if(index == 0)
            {
                Node <E> last = getNode(size - 1);
                removed = head.element;
                head = head.next;               
                last.next = head;
                size--;
            }
            else
            {
                Node <E> before = getNode(index - 1);
                removed = before.next.element;
                before.next = before.next.next;
                size--;
            }
            
            return removed;
	}
	
	
	
	
	// Turns your list into a string
	// Useful for debugging
	public String toString(){
		Node<E> current =  head;
		StringBuilder result = new StringBuilder();
		if(size == 0){
			return "Size is 0";
		}
		if(size == 1) {
			return head.getElement().toString();
			
		}
		else{
			do{
				result.append(current.getElement());
				result.append(" ==> ");
				current = current.next;
			} while(current != head);
		}
		return result.toString();
	}
	
	
	public Iterator<E> iterator() {
		return new ListIterator<E>();
	}
	
	// provided code
	// read the comments to figure out how this works and see how to use it
	// you should not have to change this
	// change at your own risk!
	private class ListIterator<E> implements Iterator<E>{
		
		Node<E> nextItem;
		Node<E> prev;
		int index;
		
		@SuppressWarnings("unchecked")
		//Creates a new iterator that starts at the head of the list
		public ListIterator(){
			nextItem = (Node<E>) head;
			index = 0;
		}

		// returns true if there is a next node
		// this is always should return true if the list has something in it
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return size != 0;
		}
		
		// advances the iterator to the next item
		// handles wrapping around back to the head automatically for you
		public E next() {
			// TODO Auto-generated method stub
			prev =  nextItem;
			nextItem = nextItem.next;
			index =  (index + 1) % size;
			return prev.getElement();
	
		}
		
		// removed the last node was visted by the .next() call 
		// for example if we had just created a iterator
		// the following calls would remove the item at index 1 (the second person in the ring)
		// next() next() remove()
		public void remove() {
			int target;
			if(nextItem == head) {
				target = size - 1;
			} else{ 
				target = index - 1;
				index--;
			}
			CircularLinkedList.this.remove(target); //calls the above class
		}
		
	}
	
	
	// Solve the problem in the main method
	// The answer of n = 13,  k = 2 is 
	// the 11th person in the ring (index 10)
	public static void main(String[] args){
		CircularLinkedList<Integer> l =  new CircularLinkedList<Integer>();
		int n = 13;
		int k = 2;
		
                for(int i = 0; i < n; i++)
                {
                   l.add(i+1);
                }

                System.out.println("START: " + l.toString());
 
		// use the iterator to iterate around the list
		Iterator<Integer> iter = l.iterator();
		while(l.size > 1)
                {                   
                    for(int i = 0; i < k; i++)
                    {
                        iter.next();
                    }
                    iter.remove();
                    System.out.println(l.toString());
                }
                System.out.println("FINAL: " + l.toString());
		
		
		
		
	}



	
}
