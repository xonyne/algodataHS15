package examples;

import java.util.Stack;

public class Tiefensuche {

    public static void main(String[] args) {
        // is tree
        int testATree[][] = { { 0, 1, 0, 0 }, { 1, 0, 1, 0 }, { 0, 1, 0, 1 },
                { 0, 0, 1, 0 } };
        System.out.println(isTree(testATree));
 
        // has cycles
        int testBNoTree[][] = { { 0, 1, 1 }, { 1, 0, 1 }, { 1, 1, 0 }, };
        System.out.println(isTree(testBNoTree));
 
        // forest
        int testNoCTree[][] = { { 0, 1, 0, 0, 0, 0 }, { 1, 0, 1, 1, 0, 0 },
                { 0, 1, 0, 1, 0, 0 }, { 0, 1, 1, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 1, 0 }, };
        System.out.println(isTree(testNoCTree));
 
        // is tree
        int yesTree1[][] = { { 0, 1, 1, 1 }, { 1, 0, 0, 0 }, { 1, 0, 0, 0 },
        		{ 1, 0, 0, 0 } };
        System.out.println(isTree(yesTree1));
        
        // is tree
        int yesTree2[][] = { { 0,1,1,0,0 }, { 1,0,0,1,1 }, { 1,0,0,0,0 },
        		{ 0,1,0,0,0 }, { 0,1,0,0,0 } };
        System.out.println(isTree(yesTree2));
        
        // no tree
        int noTree1[][] = { { 0,0,0,0,0 }, { 1,0,0,1,1 }, { 1,0,0,0,0 },
        		{ 0,1,0,0,0 }, { 0,0,0,0,0 } };
        System.out.println(isTree(noTree1));
        
        // no tree
        int noTree2[][] = { { 0,1,1,0,0 }, { 1,0,0,1,1 }, { 1,0,0,0,0 },
        		{ 0,1,0,0,0 }, { 0,1,1,0,0 } };
        System.out.println(isTree(noTree2));
    }
 
    /*
     * To make sure the graph is a tree, check the following conditions:
     * 
     * 1) check ob zusammenhängend
     * 2) check ob zirkulär
     */
    public static boolean isTree(int[][] adj) {
    	// Boolean-Array initialisieren mit Länge = Adjazenzmatrix.length 
    	Boolean[] visited = new Boolean[adj.length];
    	// alle auf false setzen (=unbesucht)
    	for (int i = 0; i < adj.length; i++) {
    		visited[i] = false;
    	}
    	// Stack initialisieren, dieser speichert alle Knoten, welche zwar bereits visited sind, aber von welchen noch nicht die Kinder geholt wurden
    	Stack stack = new Stack();
    	
    	// beliebigen Startpunkt, für diesen visited[] auf true setzen und auf Stack pushen
    	int startNode = 0;
    	visited[startNode] = true;
    	stack.push(startNode);
    	
    	// um zirkularität zu berechnen müssen wir wissen, wie viele Kanten es gibt
    	int kanten = 0;
    	
        // check 1 : Zusammenhängend
    	while (!stack.empty()){
    	    // Entfernt das oberste Objekt vom Stack
    	    int currentNode = (int) stack.pop();
    	    
    	    
    	    for(int i=0;i< adj[0].length; i++) {
	    	    if (adj[currentNode][i] == 1) {
	    	    	if (visited[i] == false) {
	    	    		visited[i] = true;
	    	    		stack.push(i);
	    	    		kanten++;
	    	    	}
	    	    }
    	    }
    	}
        System.out.println("\nkanten: " + kanten + "\nadj.length: " + adj.length);
    	for (int i = 0; i < adj.length; i++) {
    		if(visited[i] == false) return false;
    	}
    	
        // check 2 : Zirkularität
        if (kanten == (adj.length - 1))
            return true;
        return false;
    }
 
}