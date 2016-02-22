package examples;

public class TreeCheck {

	public static void main(String[] args) {
		// is tree
		int testATree[][] = { { 0, 1, 0, 0 }, { 1, 0, 1, 0 }, { 0, 1, 0, 1 },
				{ 0, 0, 1, 0 } };
		System.out.println(isTreeConnectedGraph(testATree));

		// no tree (has cycles)
		int testBNoTree[][] = { { 0, 1, 1 }, { 1, 0, 1 }, { 1, 1, 0 }, };
		System.out.println(isTreeConnectedGraph(testBNoTree));

		// is tree
		int testCTree[][] = { { 0, 1, 1, 0, 0 }, { 1, 0, 0, 0, 0 },
				{ 1, 0, 0, 1, 1 }, { 0, 0, 1, 0, 0 }, { 0, 0, 1, 0, 0 }, };
		System.out.println(isTreeConnectedGraph(testCTree));

	}

	/*
	 * examFS10, Aufgabe 4
	 * 
	 * Da gemäss Aufgabenstellung der Graph der übergebenen Adjazenzmatrix verbunden ist, 
	 * muss lediglich überprüft werden, ob Anzahl Kanten = Anzahl Knoten -1
	 */
	public static boolean isTreeConnectedGraph(int[][] adj) {
		int edgeCount = 0;

		for (int row = 0; row < adj.length; row++) {
			for (int col = 0; col < adj.length; col++) {
				if (adj[row][col] == 1) {
					edgeCount++;
				}
			}
		}
		
		// Erwartete Anzahl Kanten in Graph: adj.length - 1; 
		// Erwartete Anzahl Einsen in Matrix: 2 * Anzahl Verbindungen
		if (edgeCount == 2 * (adj.length - 1))
			return true;
		return false;
	}

}
