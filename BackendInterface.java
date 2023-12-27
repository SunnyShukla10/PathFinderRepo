import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

/**
 * Interface that contains functions to implement Backend functionality.
 */
public interface BackendInterface<NodeType, EdgeType extends Number> {

	// Constructor
	// public BackendInterface (GraphADT <String, Double> graph);

	/**
	 * Reads data from the DOT file.
	 *
	 * @param filePath The path to the DOT file containing the data.
	 * @throws FileNotFoundException If the file at the specified path is not found.
	 */
	public void readFile(File filePath) throws FileNotFoundException;

	/**
	 * Calculates the shortest path between two buildings in the dataset.
	 *
	 * @param startBuilding       The starting building for the path.
	 * @param destinationBuilding The destination building for the path.
	 * @return An instance of ShortestPath containing the shortest path information.
	 * @throws NoSuchElementException if start or destination building is not present in the file given
	 */
	public ShortestPathInterface<NodeType, EdgeType> findShortestPath(NodeType startBuilding,
			NodeType destinationBuilding) throws NoSuchElementException;

	/**
	 * Gets statistics about the dataset, including the number of nodes (buildings),
	 * the number of edges, and the total walking time (sum of weights) for all
	 * edges in the graph.
	 *
	 * @return A string with dataset statistics.
	 */
	public String getStatistics();
	
	
	/**
	 * Added new method 
	 * This method finds the path while going through a specified midpoint
	 *  
	 * @param startBuilding - start building
	 * @param midpointBuilding - midpoint building it has to visit
	 * @param destinationBuilding - dest building
	 * @return An instance of ShortestPath containing the shortest path info
	 * @throws NoSuchElementException
	 */
	public ShortestPathInterface<NodeType, EdgeType> findShortestPathWithMidpoint(NodeType startBuilding, NodeType midpointBuilding,
			NodeType destinationBuilding) throws NoSuchElementException;

}


