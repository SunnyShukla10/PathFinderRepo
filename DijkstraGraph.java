import java.util.PriorityQueue;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;

//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Assertions;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number> extends BaseGraph<NodeType, EdgeType>
		implements GraphADT<NodeType, EdgeType> {

	/**
	 * While searching for the shortest path between two nodes, a SearchNode
	 * contains data about one specific path between the start node and another node
	 * in the graph. The final node in this path is stored in its node field. The
	 * total cost of this path is stored in its cost field. And the predecessor
	 * SearchNode within this path is referened by the predecessor field (this field
	 * is null within the SearchNode containing the starting node in its node
	 * field).
	 *
	 * SearchNodes are Comparable and are sorted by cost so that the lowest cost
	 * SearchNode has the highest priority within a java.util.PriorityQueue.
	 */
	protected class SearchNode implements Comparable<SearchNode> {
		public Node node;
		public double cost;
		public SearchNode predecessor;

		public SearchNode(Node node, double cost, SearchNode predecessor) {
			this.node = node;
			this.cost = cost;
			this.predecessor = predecessor;
		}

		public int compareTo(SearchNode other) {
			if (cost > other.cost)
				return +1;
			if (cost < other.cost)
				return -1;
			return 0;
		}
	}

	/**
	 * Constructor that sets the map that the graph uses.
	 * 
	 * @param map the map that the graph uses to map a data object to the node
	 *            object it is stored in
	 */
	public DijkstraGraph(MapADT<NodeType, Node> map) {
		super(map);
	}

	/**
	 * This helper method creates a network of SearchNodes while computing the
	 * shortest path between the provided start and end locations. The SearchNode
	 * that is returned by this method is represents the end of the shortest path
	 * that is found: it's cost is the cost of that shortest path, and the nodes
	 * linked together through predecessor references represent all of the nodes
	 * along that shortest path (ordered from end to start).
	 *
	 * @param start the data item in the starting node for the path
	 * @param end   the data item in the destination node for the path
	 * @return SearchNode for the final end node within the shortest path
	 * @throws NoSuchElementException when no path from start to end is found or
	 *                                when either start or end data do not
	 *                                correspond to a graph node
	 */
	protected SearchNode computeShortestPath(NodeType start, NodeType end) throws NoSuchElementException {
		// implement in step 5.3

		// keep track of visited nodes
		MapADT<NodeType, SearchNode> visitedData = new PlaceholderMap<>();

		// priority queue to find nodes with lowest cost first
		PriorityQueue<SearchNode> pq = new PriorityQueue<>();

		// checks if start value is valid
		if (start == null || !super.containsNode(start)) {

			throw new NoSuchElementException("Start node is not found");
		}

		// checks if end value is valid
		if (end == null || !super.containsNode(end)) {

			throw new NoSuchElementException("End node is not found");
		}

		// initialize the start node
		pq.add(new SearchNode(nodes.get(start), 0, null));

		while (!pq.isEmpty()) {

			// get node with lowest cost
			SearchNode currNode = pq.poll();

			// current node not visited
			if (!visitedData.containsKey(currNode.node.data)) {

				// mark nodes as visited
				visitedData.put(currNode.node.data, currNode);

				// find neighbors
				for (Edge edge : currNode.node.edgesLeaving) {

					// add neighbors to priority queue if it has not been visited
					if (!visitedData.containsKey(edge.successor.data)) {
						pq.add(new SearchNode(edge.successor, currNode.cost + edge.data.doubleValue(), currNode));
					}
				}
			}
		}

		// outputs shortest map
		if (visitedData.containsKey(end)) {

			return visitedData.get(end);
		} else {

			// if no path exists from start node
			throw new NoSuchElementException("No path found!");

		}
	}

	/**
	 * Returns the list of data values from nodes along the shortest path from the
	 * node with the provided start value through the node with the provided end
	 * value. This list of data values starts with the start value, ends with the
	 * end value, and contains intermediary values in the order they are encountered
	 * while traversing this shorteset path. This method uses Dijkstra's shortest
	 * path algorithm to find this solution.
	 *
	 * @param start the data item in the starting node for the path
	 * @param end   the data item in the destination node for the path
	 * @return list of data item from node along this shortest path
	 */
	public List<NodeType> shortestPathData(NodeType start, NodeType end) {
		// implement in step 5.4

		SearchNode currNode = computeShortestPath(start, end);
		
		List<NodeType> path = new LinkedList<>();

		while (currNode != null) {

			path.add(0, currNode.node.data);
			currNode = currNode.predecessor;

		}

		return path;
	}

	/**
	 * Returns the cost of the path (sum over edge weights) of the shortest path
	 * from the node containing the start data to the node containing the end data.
	 * This method uses Dijkstra's shortest path algorithm to find this solution.
	 *
	 * @param start the data item in the starting node for the path
	 * @param end   the data item in the destination node for the path
	 * @return the cost of the shortest path between these nodes
	 */
	public double shortestPathCost(NodeType start, NodeType end) {
		// implement in step 5.4
		SearchNode destination = computeShortestPath(start, end);

		return destination.cost;
	}

}


