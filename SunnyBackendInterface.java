import java.io.FileNotFoundException;
import java.util.List;
public interface BackendInterface {
	// public Backend(PathNavInterface shortest, DataReaderInterface dataReader);

	// find the ShortestPath between the two locations given.
	//public Backend(PathNavInterface shortest, DataReaderInterface dataReader);

	//find the ShortestPath between the two locations given.
	public String findShortestPath (List<String> locations);

	//Find min path to a location going through a specified place
	public String findShortestPathWithMidpoint(List<String> locations);

	//Find the farthestNode of the starting node
	public String getFarthestNode(String start);

	//Find the closestNode of the starting node
    	public String getClosestNode(String start);


	// Deleting locations from the graph
	public boolean deleteLocations(List<String> locationsToDelete);

	// Find neighbors for a given location;
	public List<String> findNeighbors(String location);

	// Load data
	public void loadData(String filename) throws FileNotFoundException;

	

}
