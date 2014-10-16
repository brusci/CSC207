package e2solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;


/**
 * Implementation of an undirected Graph.
 */
public class Graph implements IGraph {

	/**
	 * Adjacency list
	 */
	private Map<Node, Set<Node>> nodeToNeighbours;
	
	public Graph(){
		nodeToNeighbours = new TreeMap<Node, Set<Node>>();
	}


	@Override
	public void addEdge(Node node1, Node node2) {
		
		if (node1.compareTo(node2) == 0 || node1 == node2) {
			return;
		}
		
		Set<Node> nodeSet1 =new TreeSet<>();
		Set<Node> nodeSet2 =new TreeSet<>();
		
		nodeSet1.add(node2); // node1 = Node(2) and node2 = Node(3)
		nodeSet2.add(node1);		
		
		if (nodeToNeighbours.get(node1) == null) {
			nodeToNeighbours.put(node1, nodeSet1);
		}
		else {
			//if (nodeToNeighbours.get(node1).contains(node2)); {
				
			//}
			//else {

			//Set<Node> nodeSetTemp = new TreeSet<>();
			//nodeSetTemp = nodeToNeighbours.get(node1);
			//nodeSetTemp.add(node2);
			nodeToNeighbours.get(node1).add(node2);
			
			//}
		}
		
		if  (nodeToNeighbours.get(node2) == null) {
			nodeToNeighbours.put(node2, nodeSet2);
		}
		else {
			nodeToNeighbours.get(node2).add(node1);
		}
	}

	@Override
	public boolean areAdjacent(Node node1, Node node2) throws Exception {
		
		if (nodeToNeighbours.get(node2) == null || nodeToNeighbours.get(node1) == null){
			throw new Exception("both nodes have no edges");
			}
		else if (nodeToNeighbours.get(node2).retainAll(nodeToNeighbours.get(node1))){
			//s1.retainAll(s2)
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Set<Node> getNodes() {
		Set<Node> nodeSet = new TreeSet<>();
		for (Node key: nodeToNeighbours.keySet()) {
			nodeSet.add(key);
		}
		return nodeSet;
	}

	@Override
	public Set<Node> getNeighbours(Node node) {
		// TODO Auto-generated method stub
		return nodeToNeighbours.get(node);
	}

	@Override
	public int getNumNodes() {
		return nodeToNeighbours.size();
	}

	@Override
	public int getNumEdges() {
		Set<Node> nodeSet = new TreeSet<>();
		Set<Node> allNodeSet = new TreeSet<>();
		int counter = 0;
		
		for (Node key: nodeToNeighbours.keySet()) {
			nodeSet.add(key);
		}
		for (Node key: nodeSet) {
			allNodeSet.add(key);
		}
		counter = allNodeSet.size();
		if (counter == 0) {
			return 0;
		}
		else {
			return counter - 1;
		}
	}
	public String toString(){
		Set<Node> nodeSet = new TreeSet<>();
		nodeSet = getNodes();
		
		SortedSet<Node> sortedCopyOfSet1 = new TreeSet<>(nodeSet);
		String line1 = String.format("Number of nodes: %d\n", getNumNodes());
		String line2 = String.format("Number of edges: %d", getNumEdges());
		String big = "";
		
		for (Node nodes : sortedCopyOfSet1) {
			big += "\n";
						//System.out.println("outer key iterator: " + nodes);
			big += nodes + " is adjacent to: ";
			
			Set<Node> tempSet = new TreeSet<>(nodeSet);
			tempSet = nodeToNeighbours.get(nodes);
			SortedSet<Node> sortedCopyOfSet2 = new TreeSet<>(tempSet);
			
			
			for (Node values: sortedCopyOfSet2) {
						//System.out.println("Inner key iterator_" + values);
				big = big + values + " ";
			}
			
		}

		return line1 + line2 + big + "\n";
	}
	
}
