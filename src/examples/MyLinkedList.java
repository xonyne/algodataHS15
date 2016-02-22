package examples;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<E> implements List<E> {
	
	// auxiliary class for the nodes
	class LNode implements Position<E>{
		E elem;
		LNode prev,next;
		Object creator = MyLinkedList.this;
		
		@Override
		public E element() {
			return elem;
		}		
	}
	

	// instance variables
	private LNode first,last;
	private int size;

	
	/**
	 * @param p a position which should belong to this MyLinkedList instance
	 * @return the casted object 'p' which (is tested for validity)
	 */
	private LNode castToLNode(Position p){
		LNode n;
		try {
			n = (LNode) p;
		} catch (ClassCastException e) {
			throw new RuntimeException("This is not a Position belonging to MyLinkedList"); 
		}
		if (n.creator == null) throw new RuntimeException("position was allready deleted!");
		if (n.creator != this) throw new RuntimeException("position belongs to another MyLinkedList instance!");			
		return n;
	}

	@Override
	public Position<E> first() {
		return first;
	}

	@Override
	public Position<E> last() {
		return last;
	}

	@Override
	public boolean isFirst(Position<E> p) {
		return castToLNode(p)==first;
	}

	@Override
	public boolean isLast(Position<E> p) {
		return castToLNode(p)==last;
	}

	@Override
	public Position<E> next(Position<E> p) {
		LNode n = castToLNode(p);
		if (n==last) throw new RuntimeException("There is no next position!");
		return n.next;
	}

	@Override
	public Position<E> previous(Position<E> p) {
		LNode n = castToLNode(p).prev;
		if (n==first) throw new RuntimeException("There is no previous position!");
		return n.prev;

	}

	@Override
	public E replaceElement(Position<E> p, E o) {
		LNode n = castToLNode(p);
		E old = n.elem;
		n.elem = o;
		return old;
	}

	@Override
	public Position<E> insertFirst(E o) {
		LNode n = new LNode();
		n.elem = o;
		n.next = first;
		if (first == null) last=n;
		else first.prev = n;
		first = n;
		size++;
		return n;
	}

	@Override
	public Position<E> insertLast(E o) {
		LNode n = new LNode();
		n.elem = o;
		n.prev = last;
		if (last == null) first=n;
		else last.next = n;
		last = n;
		size++;
		return n;		
	}

	@Override
	public Position<E> insertBefore(Position<E> p, E o) {
		LNode n = castToLNode(p);
		LNode newN = new LNode();
		newN.elem = o;
		newN.next = n;
		if (n.prev==null) first = newN ;
		else {
			newN.prev = n.prev;
			newN.prev.next = newN;
		}
		n.prev = newN;
		size++;
		return newN;	
	}

	@Override
	public Position<E> insertAfter(Position<E> p, E o) {
		LNode n = castToLNode(p);
		LNode newN = new LNode();
		newN.elem = o;
		newN.prev = n;
		if (n.next==null) last = newN ;
		else {
			newN.next = n.next;
			newN.next.prev = newN;
		}
		n.next = newN;
		size++;
		return newN;	
	}

	@Override
	public void remove(Position<E> p) {
		LNode n = castToLNode(p);
		size--;
		n.creator = null; // invalidate p!
		
		// left side:
		if (n==first) {
			first=n.next;
			if (first != null) first.prev = null;
		}
		else n.prev.next = n.next;
		
		// right side:
		if (n==last) {
			last =n.prev;
			if (last!=null) last.next = null;
		}
		else n.next.prev = n.prev;
	}

	@Override
	public Iterator<Position<E>> positions() {
		return new Iterator<Position<E>>(){
			LNode currentNode = first;

			@Override
			public boolean hasNext() {
				return currentNode != null;
			}

			@Override
			public Position<E> next() {
				if (currentNode == null) throw new NoSuchElementException("there are no more elments in this LinkedList");
				LNode ret = currentNode;
				currentNode = currentNode.next;
				return ret;
			}			
		};
	}

	@Override
	public Iterator<E> elements() {
		return new Iterator<E>(){
			LNode currentNode = first;

			@Override
			public boolean hasNext() {
				return currentNode != null;
			}

			@Override
			public E next() {
				if (currentNode == null) throw new NoSuchElementException("there are no more elments in this LinkedList");
				LNode ret = currentNode;
				currentNode = currentNode.next;
				return ret.elem;
			}
		};
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	public static void main(String[] args) {
		List<String> li = new MyLinkedList<>();
		Position<String> p =li.insertFirst("hans");
		li.insertFirst("beat");
		p = li.insertFirst("ida");
		System.out.println(li.next(li.next(p)).element());
		li.insertBefore(p,"hans 1");
		
		// li.remove(p);
		li.insertAfter(p, "bla");
		li.remove(p);
		System.out.println("--");
		Iterator<Position<String>> it = li.positions();
		while (it.hasNext()){
			Position<String> pi = it.next();
			if (pi.element().equals("bla")) li.remove(pi);
			System.out.println(pi.element());
		}
		System.out.println("--");
		Iterator<String> it2 = li.elements();  
		while (it2.hasNext()){
			System.out.println(it2.next());
		}
		
				
	}

}
