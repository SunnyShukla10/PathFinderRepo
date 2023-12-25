import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Backend implements BackendInterface{

	protected PathNavAE<String,Double> pathNav;
	protected DataReaderInterface dataReader;

	// The constructor with PathNavBD and DataReaderInterface instances as parameters
	public Backend(PathNavAE shortest, DataReaderInterface dataReader) {
		this.pathNav = shortest;
		this.dataReader = dataReader;
	}

	/**
	 * Method to find the shortest path between two locations
	 */
	@Override
	public String findShortestPath(List<String> locations) {
		// If the locations provided by user contains exactly 2 elements
		if (locations.size() != 2) {
			return "Please provide two locations to find the shortest path.";
		}

		// Take start and end locations from locations array
		String start = locations.get(0);
		String end = locations.get(1);

		// Return the shortest path as a String
		return this.pathNav.shortestPathData(start,end).toString();
	}

	/**
	 * Method to find the shortest path between two locations with a midpoint
	 */
	@Override
	public String findShortestPathWithMidpoint(List<String> locations) {
		// Check if the locations provided by user contains exactly 3 elements
		if (locations.size() != 3) {
			return "Please provide three locations to find shortest path with midpoint.";
		}

		// Take start, midpoint and end locations from locations array
		String start = locations.get(0);
		String midpoint = locations.get(1);
		String end = locations.get(2);

		// Find the shortest path with the given midpoint by user
		List<String> shortestPath = pathNav.shortestPathMidpointData(start, midpoint, end);
		if (shortestPath == null) {
			return "Not able to find shortest path with midpoint.";
		}

		// Returns the shortest path as a String
		return shortestPath.toString();
	}

	/**
	 * Method to find the farthest node from the given start location
	 */
	@Override
	public String getFarthestNode(String start) {
		// Check if the user provided start location is valid
		if (start == null || start.isEmpty()) {
			return "Please provide a valid start location.";
		}

		// find the farthest node
		String farthest = (String) pathNav.farthestNode(start);

		//Check if the farthest node was found or not
		if (farthest == null) {
			return "Farthest node from the given start location cannot be found.";
		}

		// Returns the farthest node
		return farthest;
	}

	/**
	 * Method to find the closest node from the given start location
	 */
	@Override
	public String getClosestNode(String start) {
		// Check if the user provided start location is valid
		if (start == null || start.isEmpty()) {
			return "Please provide a valid start location.";
		}

		// find the closest node
		String closest = (String) pathNav.closestNode(start);

		//Check if the closest node was found or not
		if (closest == null) {
			return "Closest node from the given start location cannot be found.";
		}

		// Returns the closest node
		return closest;
	}

	@Override
	public boolean deleteLocations(List<String> locationsToDelete) {
		if (locationsToDelete == null || locationsToDelete.isEmpty()) {
			return false;
		}

		// Iterate through the list and remove each location from the graph
		for (String locationName : locationsToDelete) {
			pathNav.removeNode(locationName);
		}

		// return true if location was able to be deleted
		return true;
	}

	@Override
	public List<String> findNeighbors(String location) {
		if (location == null || location.isEmpty()) {
			// returns an empty list when input is not valid
			return new ArrayList<>();
		}

		List<String> neighbors = new ArrayList<>();

		
		// For outgoing edges
		for (BaseGraph.Edge edge : this.pathNav.nodes.get(location).edgesLeaving) {
			neighbors.add(edge.successor.data.toString());
		}

		
		return neighbors;
	}

	@Override
	public void loadData(String fileName) throws FileNotFoundException {
		// Data Wrangler implements below commented code in readLocationsFromFile method 
		// in DataReader class.

		//		try {
		//			this.graph = (PathNavBD) dataReader.readLocationsFromFile(fileName);
		//			System.out.println("Data loaded successfully.");
		//		} catch (FileNotFoundException e) {
		//			System.out.println("Error: File not found.");
		//			throw e;
		//		}
		this.pathNav = (PathNavAE) dataReader.readLocationsFromFile(fileName);
	}

}
