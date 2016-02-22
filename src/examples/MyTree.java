package examples;

import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class MyTree<E> implements Tree<E> {

	// auxiliary class for the tree nodes
	class TNode implements Position<E> {
		E elem;
		TNode parent;
		MyLinkedList<TNode> children = new MyLinkedList<>();
		Position<TNode> childrenPos; // Position in the children LinkedList
		Object creator = MyTree.this;

		@Override
		public E element() {
			return elem;
		}

	}

	// instance variables

	private TNode root;
	private int size;

	// instance methods

	private TNode castToTNode(Position<E> p) {
		TNode n;
		try {
			n = (TNode) p;
		} catch (ClassCastException e) {
			throw new RuntimeException(
					"This is not a Position belonging to myTree");
		}
		if (n.creator == null)
			throw new RuntimeException("position was allready deleted!");
		if (n.creator != this)
			throw new RuntimeException(
					"position belongs to another MyTree instance!");
		return n;
	}

	@Override
	public Position<E> root() {
		return root;
	}

	@Override
	public Position<E> createRoot(E o) {
		root = new TNode();
		root.elem = o;
		return root;
	}

	@Override
	public Position<E> parent(Position<E> child) {
		TNode n = castToTNode(child);
		return n.parent;
	}

	@Override
	public Iterator<Position<E>> childrenPositions(Position<E> parent) {
		TNode n = castToTNode(parent);
		return new Iterator<Position<E>>() {
			Iterator<TNode> it = n.children.elements();

			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			@Override
			public Position<E> next() {
				return it.next();
			}
		};
	}

	@Override
	public Iterator<E> childrenElements(Position<E> parent) {
		TNode n = castToTNode(parent);
		return new Iterator<E>() {
			Iterator<TNode> it = n.children.elements();

			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			@Override
			public E next() {
				return it.next().elem;
			}
		};
	}

	@Override
	public int numberOfChildren(Position<E> parent) {
		TNode n = castToTNode(parent);
		return n.children.size();
	}

	@Override
	public Position<E> insertParent(Position<E> p, E o) {
		TNode n = castToTNode(p);
		TNode newP = new TNode();
		newP.elem = o;
		if (n == root) {
			root = newP;
		} else {
			// newP takes the former role of n:
			newP.parent = n.parent;
			newP.childrenPos = n.childrenPos; // we take the position of p
			newP.parent.children.replaceElement(newP.childrenPos, newP);
		}
		// make 'p' a child of the new position
		n.parent = newP;
		n.childrenPos = newP.children.insertFirst(n);
		size++;
		return newP;
	}

	@Override
	public Position<E> addChild(Position<E> parent, E o) {
		TNode n = castToTNode(parent);
		TNode newN = new TNode();
		newN.elem = o;
		newN.parent = n;
		newN.childrenPos = n.children.insertLast(newN);
		size++;
		return newN;
	}

	@Override
	public Position<E> addChildAt(int pos, Position<E> parent, E o) {
		TNode p = castToTNode(parent);
		if (pos > p.children.size() || pos < 0)
			throw new RuntimeException("invalid rank");
		TNode n = new TNode();
		n.elem = o;
		n.parent = p;
		Position<TNode> linkedListPosition = null;
		if (pos == 0)
			linkedListPosition = p.children.insertFirst(n);
		else if (pos == p.children.size())
			linkedListPosition = p.children.insertLast(n);
		else {
			Iterator<Position<TNode>> it = p.children.positions();
			// skip pos-2 nodes
			for (int i = 0; i < pos - 1; i++) {
				it.next();
			}
			Position<TNode> lPos = (it.next()); // lPos is the
												// LinkedList-position before
												// the insertion point
			linkedListPosition = p.children.insertAfter(lPos, n);
		}
		n.childrenPos = linkedListPosition;
		size++;
		return n;
	}

	@Override
	public Position<E> addSiblingAfter(Position<E> sibling, E o) {
		TNode sib = castToTNode(sibling);
		if (sib == root)
			throw new RuntimeException("root can not have siblings!");
		TNode newN = new TNode();
		newN.elem = o;
		newN.parent = sib.parent;
		newN.childrenPos = sib.parent.children.insertAfter(sib.childrenPos,
				newN);
		size++;
		return newN;
	}

	@Override
	public Position<E> addSiblingBefore(Position<E> sibling, E o) {
		TNode sib = castToTNode(sibling);
		if (sib == root)
			throw new RuntimeException("root can not have siblings!");
		TNode newN = new TNode();
		newN.elem = o;
		newN.parent = sib.parent;
		newN.childrenPos = sib.parent.children.insertBefore(sib.childrenPos,
				newN);
		size++;
		return newN;
	}

	@Override
	public void remove(Position<E> p) {
		TNode n = castToTNode(p);
		if (n.children.size() != 0)
			throw new RuntimeException("cannot remove node with children!");
		n.creator = null;
		size--;
		if (n == root)
			root = null;
		else
			n.parent.children.remove(n.childrenPos);
	}

	public void removeSubtree(Position<E> p) {
		Iterator<Position<E>> it = childrenPositions(p);
		while (it.hasNext())
			removeSubtree(it.next());
		remove(p);
	}

	@Override
	public boolean isExternal(Position<E> p) {
		TNode n = castToTNode(p);
		return n.children.size() == 0;
	}

	@Override
	public boolean isInternal(Position<E> p) {
		TNode n = castToTNode(p);
		return n.children.size() > 0;
	}

	@Override
	public int size() {
		return size;
	}

	public void print() {
		if (root != null)
			print(root, "");
	}

	private void print(Position<E> r, String ind) {
		System.out.println(ind + r.element());
		Iterator<Position<E>> it = childrenPositions(r);
		while (it.hasNext())
			print(it.next(), ind + "..");
	}

	@Override
	public E replaceElement(Position<E> p, E o) {
		TNode n = castToTNode(p);
		E old = n.elem;
		n.elem = o;
		return old;
	}

	public String toString() {
		if (size == 0)
			return "";
		StringBuilder sb = new StringBuilder("--- begin tree ---\n");
		buildString(root, "", sb);
		sb.append("---  end tree  ---");
		return sb.toString();
	}

	private void buildString(Position<E> r, String in, StringBuilder sb) {
		sb.append(in);
		sb.append(r.element().toString());
		sb.append("\n");
		Iterator<Position<E>> it = childrenPositions(r);
		while (it.hasNext())
			buildString(it.next(), ".." + in, sb);
	}

	public int height() {
		return height(root);
	}

	private int height(Position<E> r) {
		int h = 0;
		Iterator<Position<E>> it = childrenPositions(r);
		while (it.hasNext()) {
			int height = height(it.next());
			if (height > h)
				h = height;
		}
		return h + 1;
	}

	public static void main(String[] args) {
		MyTree<String> t = new MyTree<String>();
		Position<String> tit = t.createRoot("Buch");
		Position<String> k1 = t.addChild(tit, "Kapitel 1");
		Position<String> k2 = t.addChild(tit, "Kapitel 2");
		Position<String> k3 = t.addChild(tit, "Kapitel 3");
		Position<String> k1u1 = t.addChild(k1, "Abschnitt 1");
		Position<String> k2u1a1 = t.addChild(k1u1, "Satz 1");
		t.addChild(k1, "Abschnitt 2");
		t.addChild(k1, "Abschnitt 3");
		t.addChild(k1, "Abschnitt 4");
		t.addChild(k1, "Abschnitt 5");
		// t.print();
		System.out.println(t);
		// System.out.println(t.height());
		// System.out.println(t.maxMultPos().element());
		System.out.println("Breite " + breadth(t));
		System.out.println("Höhe " + t.height());
		System.out.println("Distanz von Kapitel 1 Abschnitt 1 zum Kapitel 1: "
				+ dist(t, k2u1a1, k2));
		// System.out
		// .println("Anzahl externer Knoten: " + externalNodes(t).length);
	}

	static Position deepest;
	static int maxDepth;

	static Position deepestPos(Tree t) {
		maxDepth = 0;
		deepest = t.root();
		if (t.size() == 0)
			return null;
		deepestPos(t, t.root(), 0);
		return deepest;
	}

	static void deepestPos(Tree t, Position p, int lev) {
		if (lev > maxDepth) {
			maxDepth = lev;
			deepest = p;
		}
		Iterator<Position> it = t.childrenPositions(p);
		while (it.hasNext())
			deepestPos(t, it.next(), lev + 1);
	}

	public static int breadth(Tree t) {
		int breadth = 1;
		int[] cnt = new int[t.size()];
		visitAllTraceBreadth(t, t.root(), 0, cnt);
		for (int i = 0; i < cnt.length; i++) {
			if (cnt[i] > breadth)
				breadth = cnt[i];
		}
		return breadth;
	}

	public static void visitAllTraceBreadth(Tree t, Position p, int depth,
			int[] cnt) {
		cnt[depth]++;
		Iterator<Position> it = t.childrenPositions(p);
		while (it.hasNext())
			visitAllTraceBreadth(t, it.next(), depth + 1, cnt);
	}

	public static int dist(Tree t, Position p1, Position p2) {
		ArrayList<Position> p1Path = new ArrayList<Position>();
		ArrayList<Position> p2Path = new ArrayList<Position>();

		p1Path.add(p1);
		p2Path.add(p2);

		while (t.parent(p1) != null) {
			p1Path.add(t.parent(p1));
			p1 = t.parent(p1);
		}

		while (t.parent(p2) != null) {
			p2Path.add(t.parent(p2));
			p2 = t.parent(p2);
		}

		int i = 0, j = 0;
		for (i = 0; i < p1Path.size(); i++) {
			for (j = 0; j < p2Path.size(); j++) {
				if (p1Path.get(i).equals(p2Path.get(j)))
					return i + j;
			}
		}
		return 0;
	}

	public static Position[] externalNodes(Tree t) {
		Position[] externals = new Position[(t.size() / 2) + 1];
		visitAllTraceExternals(t, t.root(), 0, externals);
		return externals;

	}

	public static int visitAllTraceExternals(Tree t, Position p, int arrayPos,
			Position[] externals) {
		if (t.isExternal(p)) {
			externals[arrayPos] = p;
			arrayPos++;
		} else {
			Iterator<Position> it = t.childrenElements(p);
			while (it.hasNext()) {
				arrayPos = visitAllTraceExternals(t, it.next(), arrayPos,
						externals);
			}
		}
		return arrayPos;
	}

	public static void removeAll(Tree t, int depth) {
		visitAll(t, t.root(), depth, 0);
	}

	public static void visitAll(Tree t, Position p, int delDepth, int curDepth) {
		if (delDepth == curDepth)
			p = null;
		Iterator<Position> it = t.childrenPositions(p);
		while (it.hasNext())
			visitAll(t, it.next(), delDepth, curDepth + 1);
	}

}
