import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Loads in the file given by user, finds the shortest path between to
 * buildings, and also outputs the statistics of file given.
 */
public class Backend implements BackendInterface<String, Double> {

	// private DijkstraGraph<String, Double> graph;
	private GraphADT<String, Double> graph;

	// tracks the total edge weight costs in entire graph
	private double totalTimeGraph = 0.0;

	public Backend(GraphADT<String, Double> graph) {

		this.graph = graph;
	}

	/**
	 * Reads data from the DOT file.
	 *
	 * @param filePath The path to the DOT file containing the data.
	 * @throws FileNotFoundException If the file at the specified path is not found.
	 */
	@Override
	public void readFile(File filePath) throws FileNotFoundException {

		try {

			Scanner scnr = new Scanner(filePath);

			while (scnr.hasNextLine()) {

				String line = scnr.nextLine();

				// ["", "Memorial Union", " -- ", "Second Hall", " [seconds=105.8];"
				String[] lineData = line.trim().split("\"");

				// valid lines should be split and contain 5 elements in lineData
				if (lineData.length == 5) {

					String locationOne = lineData[1].trim();
					String locationTwo = lineData[3].trim();

					graph.insertNode(locationOne);

					graph.insertNode(locationTwo);

					// add edge
					String weight = line.substring(line.indexOf("=") + 1, line.indexOf("]")).trim();
					Double distance = Double.parseDouble(weight);

					graph.insertEdge(locationOne, locationTwo, distance);

					// update total cost of graph
					totalTimeGraph += distance;

				}
			}

			scnr.close();

		} catch (FileNotFoundException e) {

			// file errors should be handled by Frontend
			throw new FileNotFoundException("Could not read file!");
		}
	}

	/**
	 * Calculates the shortest path between two buildings in the dataset.
	 *
	 * @param startBuilding       The starting building for the path.
	 * @param destinationBuilding The destination building for the path.
	 * @return An instance of ShortestPath containing the shortest path information.
	 * @throws NoSuchElementException if start or destination building is not
	 *                                present in the file given
	 */
	@Override
	public ShortestPathInterface<String, Double> findShortestPath(String startBuilding, String destinationBuilding)
			throws NoSuchElementException {

		// checks if valid start and destination buildings are given
		if (!graph.containsNode(startBuilding) || !graph.containsNode(destinationBuilding)) {

			throw new NoSuchElementException("The start or destination building could not be found!f");
		}

		// use Dijkstra's algorithm to find shortest path and total time
		List<String> shortestPath = graph.shortestPathData(startBuilding, destinationBuilding);
		double totalTime = graph.shortestPathCost(startBuilding, destinationBuilding);

		// find walking times between each building visited
		List<Double> walkingTimes = walkingTimesHelper(shortestPath);

		return new ShortestPath(shortestPath, walkingTimes, totalTime);
	}

	/**
	 * Helper method for the findShortestPath. Used to find the walking times
	 * between each building in the shortest path.
	 * 
	 * @param path is a list that contains the shortest path between two buildings
	 *             which begins the startBuilidng and ends with the
	 *             destinationBuilding and may contain intermediary buildings in
	 *             between
	 * @return A list of walking times (in seconds) that correspond to each walking
	 *         time between two buildings in the path
	 */
	private List<Double> walkingTimesHelper(List<String> path) {

		// stores the times for each segment
		List<Double> times = new ArrayList<>();

		for (int i = 0; i < path.size() - 1; i++) {

			String firstBuilding = path.get(i);
			String secondBuilding = path.get(i + 1);

			// find the edge weight between the two buildings
			double walkingTime = graph.getEdge(firstBuilding, secondBuilding);

			times.add(walkingTime);

		}

		return times;

	}

	/**
	 * Gets statistics about the dataset, including the number of nodes (buildings),
	 * the number of edges, and the total walking time (sum of weights) for all
	 * edges in the graph.
	 *
	 * @return A string with dataset statistics.
	 */
	@Override
	public String getStatistics() {

		int totalNodes = graph.getNodeCount();

		// total edges for undirected graph
		int totalEdges = graph.getEdgeCount() / 2;

		String stats = "Total Nodes: " + totalNodes + ", Total Edges: " + totalEdges + ", Total Walking Time: "
				+ totalTimeGraph + "\n";

		return stats;
	}

	/**
	 * This method will find a path that must include the midpoint. Instead of going
	 * from A->B it will go from A->C->B Will return the Path from A->C and then
	 * C->B.
	 * 
	 * @param start    - the start node
	 * @param midpoint - the node that the path must go through
	 * @param end      - the destination
	 * @return - a List of nodes that include the start midpoint and end nodes
	 * @throws NoSuchElementException
	 */
	public List<String> shortestPathMidpoint(String start, String midpoint, String end) throws NoSuchElementException {
		List<String> first = graph.shortestPathData(start, midpoint);
		List<String> second = graph.shortestPathData(midpoint, end);
		second.remove(0);
		List<String> total = new ArrayList<String>();
		total.addAll(first);
		total.addAll(second);
		return total;
	}

}
