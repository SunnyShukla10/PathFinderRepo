import java.util.List;

public interface FrontendInterface<NodeType, EdgeType extends Number>  {
	// public frontend(Scanner userInput, BackendInterface backend);
	public void runCommandLoop();

	public char mainMenuPrompt();

	public void loadDataCommand();

	public String findShortestPath(List<String> locations); // start //is locations(0) and end is locations(1)

	public List<String> getLocationsForShortestPath();

	public String findShortestPathWithMidpoint(List<String> locations);

	public List<String> getLocationsForShortestPathWithMidpoint(); // start is locations(0) and end is locations(2) and
																	// midPoint is //locations(1)

	// finds the farthest point while still taking the most optimal route
	public String farthestNode(String start);
	public String getFarthestNode();

	// finds the closest point while taking the most optimal route
	public String closestNode(String start);
	public String getClosestNode();


	public List<String> getLocationsToDelete();

	public void deleteLocations(List<String> locations);

	public String getLocationFromUser();

	public String getLocationNeighbors(String location);

	public void loadData(String filename);

}
	

