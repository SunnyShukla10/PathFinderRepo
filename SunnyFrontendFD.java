import java.util.NoSuchElementException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FrontendFD<NodeType, EdgeType extends Number> implements FrontendInterface<NodeType, EdgeType> {

	private Scanner userInput;
	private BackendInterface backend;

	/**
	 * 
	 * @param userInput
	 * @param backend
	 */
	public FrontendFD(Scanner userInput, BackendInterface backend) {
		this.backend = backend;
		this.userInput = userInput;

	}

	/**
	 * Runs the commandLoop of this class
	 */
	@Override
	public void runCommandLoop() {
		printLine();

		System.out.println("Welcome to the PathFinder Program");
		char command = '\0';
		// Keeps going until the [Q]uit button is pressed
		while (command != 'Q') {
			command = mainMenuPrompt();
			switch (command) {
			case 'L':
				printLine();
				loadDataCommand(); // Loads the data
				printLine();
				break;
			case 'D':
				printLine();
				deleteLocations(getLocationsToDelete()); // Deletes the location
				printLine();
				break;
			case 'T':
				printLine();
				System.out.println(getLocationNeighbors(getLocationFromUser())); // Gets the neighbors of a certain
																				// location
				printLine();
				break;
			case 'M':
				printLine();
				System.out.println(findShortestPathWithMidpoint(getLocationsForShortestPathWithMidpoint())); // gets the
																												// shortest
																												// path
																												// with
																												// a
																												// midpoint
				printLine();
				break;
			case 'F':
				printLine();
				String farthest = farthestNode(getFarthestNode()); // gets the farthest node of a path with a start node
																	// that is inputted
				System.out.println(
						"The farthest node of the path using the specified node that was passed is: " + farthest);
				printLine();
				break;
			case 'C':
				printLine();
				String closest = closestNode(getClosestNode()); // find the closest node to a inputted node
				System.out.println("The closest node to the node that was passed is: " + closest);
				printLine();
				break;
			case 'P':
				printLine();
				System.out.println(findShortestPath(getLocationsForShortestPath())); // gets the shortest path of a
																						// start and end location
				printLine();
				break;
			case 'Q':
				printLine();
				System.out.println("Thank you for using PathFinder");
				printLine();
				break;
			default:
				System.out
						.println("Did not recognize command. Please type one of the letters represented in brackets.");
				break;
			}

		}

	}

	/**
	 * Extra method that prints a line to make the user UI more organized
	 */
	private void printLine() {
		System.out.println("--------------------------------------------------------------------------------");
	}

	/**
	 * this is the main menu and geths the char input to give the runLoop
	 * 
	 * @return a char input
	 */
	@Override
	public char mainMenuPrompt() {
		// display menu of choices
		System.out.println("");
		System.out.println("Type the character in bracket to perform a command below: ");
		System.out.println("[L] To load location file");
		System.out.println("[D] To delete a location");
		System.out.println("[T] To see neighbors of a certain location");
		System.out.println("[M] To see a minimum path to a location through a specified place");
		System.out.println("[F] Find the farthest node of a path with a specified location");
		System.out.println("[C] Find the closest node to a specified location");
		System.out.println("[P] To find path and distance of two locations");
		System.out.println("[Q] To quit program");
		// read in user's choice, and trim away any leading or trailing whitespace
		System.out.print("Choose command: ");
		System.out.println("");
		String input = userInput.nextLine().trim();
		if (input.length() == 0 || input.length() > 1) { // if user's choice is not a single character return null
			return '\0';
		}
		// otherwise, return an uppercase version of the first character in input
		return Character.toUpperCase((input.charAt(0)));
	}

	/**
	 * a getter for the loadData using scanned input
	 */
	@Override
	public void loadDataCommand() {
		System.out.println("");
		System.out.println("Enter the name of the file you'd like to read into the program: ");
		String filename = userInput.nextLine().trim();

		loadData(filename);

	}

	/**
	 * Loads the data using the input that was passed
	 */
	@Override
	public void loadData(String filename) {
		// Load the data from the filename that is given, catch if a FNFE is thrown
		try {
			backend.loadData(filename);
			System.out.println("Data successfully loaded into the program.");
		} catch (FileNotFoundException e) { // Implement the filenot found exception
			System.out.println("Error: Could not find or load file: " + filename);
		}
	}
/**
	 * Makes a string representation of the shortest path that was found
	 * 
	 * @return a string representation of the path: EX) a->b->c
	 */
	@Override
	public String findShortestPath(List<String> locations) {

		try {
			// get the string representation and split it into an array
			String shortestPath = backend.findShortestPath(locations);
			String[] arr = shortestPath.split("->");
			String s = "";
	
			System.out.print("The Shortest Path from " + arr[0] + " to " + arr[arr.length - 1] + " is: ");
			// Loop until the end and add the paths in the array that was passed
			for (int i = 0; i < arr.length - 1; ++i) {
				s += arr[i] + " -> ";
			}
			// Lastly add the last element to the string so we dont have a extra "->"
			s += arr[arr.length - 1];
	
			return s;
		} catch (NoSuchElementException e) {
			return "Error one or more locations that were provided aren't in the graph";
		}
	}

	/**
	 * gets the locations from inputs to pass to the findShortestPath method, start
	 * is locations[0] and end is locations[1]
	 * 
	 * @return the list of the locations to get the shortest path
	 */
	@Override
	public List<String> getLocationsForShortestPath() {
		// List that stores the locations
		List<String> locations = new ArrayList<String>();

		System.out.println("Enter the start location: ");
		locations.add(userInput.nextLine().trim());

		System.out.println("Enter the end location: ");
		locations.add(userInput.nextLine().trim());

		System.out.println();

		return locations;
	}

	/**
	 * makes a string representation of the shortes path while passing through a
	 * specified location
	 * 
	 * @return a string reprentation of a path with a specified midpoint: EX)
	 *         a->b->c->d
	 */
	@Override
	public String findShortestPathWithMidpoint(List<String> locations) {
		try {
			// get the shortest path and split it into an array
			String shortestPath = backend.findShortestPathWithMidpoint(locations);
			String[] arr = shortestPath.split("->");
			String s = "";
	
			System.out.print("The Shortest Path from " + arr[0] + " to " + arr[arr.length - 1] + " is: ");
			// Loop through and add into to the string to return
			for (int i = 0; i < arr.length - 1; ++i) {
				s += arr[i] + " -> ";
			}
			s += arr[arr.length - 1];
	
			return s;
		} catch (NoSuchElementException e) {
			return "Error one or more locations that were provided aren't in the graph";
		}
	}
	/**
	 * gets the input locations to pass to the shortestPath with midpoint method,
	 * Start is locations[0], midpoint is locations[1], and end is locations[2]
	 * 
	 * @return the list of locations to pass to the getLocationsWithMidpoint method
	 */
	@Override
	public List<String> getLocationsForShortestPathWithMidpoint() {
		// make an arraylist that stores the locations
		List<String> locations = new ArrayList<String>();

		// Input the specifed info
		System.out.println("Enter the start location: ");
		locations.add(userInput.nextLine().trim()); // May have to change**

		System.out.println("Enter the midpoint location: ");
		locations.add(userInput.nextLine().trim());

		System.out.println("Enter the end location: ");
		locations.add(userInput.nextLine().trim());

		System.out.println("");

		return locations;
	}

	/**
	 * get's the input as a list to send to the delete method
	 * 
	 * @return a list of locations to delete
	 */
	@Override
	public List<String> getLocationsToDelete() {
		// make an list to store all the locations to delete
		List<String> locationsToDelete = new ArrayList<String>();
		// Keep going until a enter is pressed only
		while (true) {
			System.out.println();
			System.out.println("Choose a location to delete: ");
			String input = userInput.nextLine().trim();
			if (input.length() == 0) {
				return locationsToDelete;
			} else {
				locationsToDelete.add(input);
				System.out.println("To run search simply press enter.");
				System.out.println("If desired, add another location you want to delete.");
			}
		}
	}

	/**
	 * deletes the specified locations and returns true if deleted, false otherwise
	 */
	@Override
	public void deleteLocations(List<String> locations) {
		if (locations.isEmpty()) {
			System.out.println("There are no provided locations please try again.");
			return;
		}

		// Call the backend to delete the locations that were specified
		boolean b = backend.deleteLocations(locations);

		// Certain line is printed based on it if was successful or not
		if (b) {
			System.out.println("Locations successfully deleted!");
		} else {
			System.out.println("Locations weren't deleted because they couldn't be found! Please try again.");
		}
	}

	/**
	 * gets the locations to get the neighbors
	 * 
	 * @return the location that we wannt the neighbors of
	 */
	@Override
	public String getLocationFromUser() {

		String location = "";
		// Get the specified input from the user
		System.out.println("Please enter the name of the location to get its neighbors: ");
		location = userInput.nextLine().trim();

		return location;
	}

	/**
	 * Gives a string representation of the neightbors
	 * 
	 * @return a string representation of all the neighbors of a certain location
	 */
	@Override
	public String getLocationNeighbors(String location) {
		if (location.isBlank()) {
			System.out.println("There are no locations procided, please try again.");
			return "";
		}

		// Get the list of the locations from the backend
		List<String> locations = backend.findNeighbors(location);
		String s = "";
		System.out.println();
		// Iterate until the end of the list and keep adding the neighbors into s
		for (int i = 0; i < locations.size(); i++) {
			s += "- " + locations.get(i) + "\n";
		}
		return s;
	}

	/**
	 * gets the farthest node of a specifed start node's path
	 * 
	 * @return a string of thr farthest node
	 */
	@Override
	public String farthestNode(String start) {
		// return the farhest node
		return backend.getFarthestNode(start);
	}

	/**
	 * gets the input of the node we want to pass
	 * 
	 * @return a string of the input to pass to the fathestNode
	 */
	@Override
	public String getFarthestNode() {
		// get the location of the furthest node we want in it's path
		System.out.println("Choose a location to get the farthest node in its path: ");
		String input = userInput.nextLine().trim();

		return input;
	}

	/**
	 * gets the closest node to the node that was passed
	 * 
	 * @return a string of the closest node that was found
	 */
	@Override
	public String closestNode(String start) {
		// Return what the closest node that we got
		return backend.getClosestNode(start);
	}

	/**
	 * gets the closest node to start from as an input to send to the closestNode
	 * 
	 * @return the inputted closest node to the closestNode method
	 */
	@Override
	public String getClosestNode() {
		// get the location we want to get the closest node from
		System.out.println("Choose a location to get the closest node in its path: ");
		String input = userInput.nextLine().trim();

		return input;
	}

 
}
