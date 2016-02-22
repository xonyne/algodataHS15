package examples;

import java.util.PriorityQueue;
import java.util.Queue;

public class Breitensuche {

	public static void main(String[] args) {
		// is tree
		int testATree[][] = { { 0, 1, 0, 0 }, { 1, 0, 1, 0 }, { 0, 1, 0, 1 },
				{ 0, 0, 1, 0 } };
		System.out.println(distance(testATree, 0, 3));

		// has cycles
		int testBNoTree[][] = { { 0, 1, 1 }, { 1, 0, 1 }, { 1, 1, 0 }, };
		System.out.println(distance(testBNoTree, 0, 0));

		// forest
		int testNoCTree[][] = { { 0, 1, 0, 0, 0, 0 }, { 1, 0, 1, 1, 0, 0 },
				{ 0, 1, 0, 1, 0, 0 }, { 0, 1, 1, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 1, 0 }, };
		System.out.println(distance(testNoCTree, 0, 0));

		// is tree
		int yesTree1[][] = { { 0, 1, 1, 1 }, { 1, 0, 0, 0 }, { 1, 0, 0, 0 },
				{ 1, 0, 0, 0 } };
		System.out.println(distance(yesTree1, 0, 0));

		// is tree
		int yesTree2[][] = { { 0, 1, 1, 0, 0 }, { 1, 0, 0, 1, 1 },
				{ 1, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0 }, { 0, 1, 0, 0, 0 } };
		System.out.println(distance(yesTree2, 0, 0));

		// no tree
		int noTree1[][] = { { 0, 0, 0, 0, 0 }, { 1, 0, 0, 1, 1 },
				{ 1, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0 } };
		System.out.println(distance(noTree1, 0, 0));

		// no tree
		int noTree2[][] = { { 0, 1, 1, 0, 0 }, { 1, 0, 0, 1, 1 },
				{ 1, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0 }, { 0, 1, 1, 0, 0 } };
		System.out.println(distance(noTree2, 0, 0));
	}

	/*
	 * Find the distance between two vertices in an undirected graph
	 */
	public static int distance(int[][] adj, int from, int to) {
		Boolean[] visited = new Boolean[adj.length];
		for (int i = 0; i < adj.length; i++) {
			visited[i] = false;
		}
		Queue queue = new PriorityQueue();

		int startNode = from;
		visited[startNode] = true;
		queue.add(startNode);

		int depth[] = new int[adj.length];

		while (!queue.isEmpty()) {
			int currentNode = (int) queue.poll();
			for (int nodeToCheck = 0; nodeToCheck < adj.length; nodeToCheck++) {
				if (adj[currentNode][nodeToCheck] == 1) {
					if (visited[nodeToCheck] == false) {
						visited[nodeToCheck] = true;
						queue.add(nodeToCheck);
						depth[nodeToCheck] = depth[currentNode] + 1;
					}
				}
			}
			if (currentNode == to)
				break;
		}
		System.out.println("\ndist: " + depth[to] + "\nadj.length: "
				+ adj.length);
		return depth[to];
	}

}