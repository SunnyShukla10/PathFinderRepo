import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
public class Frontend implements FrontendInterface<String, Double> {
	private Scanner userInput;
	private Backend backend;
	public Frontend(Scanner userInput, Backend backend) {
		this.backend = backend;
		this.userInput = userInput;
	}
	/**
	 * Extra method that prints a line to make the user UI more organized
	 */
	private void printLine() {
		System.out.println("--------------------------------------------------------------------------------");
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
			case 'P':
				printLine();
				findShortestPath(getLocationsForShortestPath()); // gets the shortest path of a //
																	// // start and end location
				printLine();
				break;
			case 'M':
				printLine();
				findShortestPathWithMidpoint(getLocationsForShortestPathWithMidpoint());
				printLine();
				break;
				
			case 'Q':
				printLine();
				System.out.println("Thank you for using PathFinder");
				userInput.close();
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
		System.out.println("[P] To find path and distance of two locations");
		System.out.println("[M] To find path and distance of two locations while going throught a midpoint location");
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
			backend.readFile(new File(filename));
			System.out.println("Data successfully loaded into the program.");
		} catch (FileNotFoundException e) { // Implement the file not found exception
			System.out.println("Error: Could not find or load file: " + filename);
		}
	}
	/**
	 * Prints out all the information regarding the shortest path.
	 */
	@Override
	public void findShortestPath(List<String> locations) {
		try {
			// get the string representation and split it into an array
			List<String> shortestPath = backend.findShortestPath(locations.get(0), locations.get(1)).getShortestPath();
			List<Double> timeSegments = backend.findShortestPath(locations.get(0), locations.get(1)).getWalkingTimes();
			Double totalTime = backend.findShortestPath(locations.get(0), locations.get(1)).getTotalPathCost();
			System.out.println("The Shortest Path from " + locations.get(0) + " to " + locations.get(1) + " is: "
					+ shortestPath.toString());
			System.out.println("The Walking Times in seconds between each building are:  " + timeSegments.toString());
			System.out.println("The Total Time it takes to walk from " + locations.get(0) + " to " + locations.get(1)
					+ " is: " + totalTime.toString() + " seconds");
		} catch (NoSuchElementException e) {
			System.out.println("Error one or more locations that were provided aren't in the graph");
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
	 * Placeholder for now Will have 3 locations, locations[0]-->start,
	 * locations[1]-->midpoint, locations[2]-->end
	 */
	@Override
	public void findShortestPathWithMidpoint(List<String> locations) {
		try {
			// get shortest path, time segment, and total time while going through midpoint
			List<String> shortestPath = backend
					.findShortestPathWithMidpoint(locations.get(0), locations.get(1), locations.get(2))
					.getShortestPath();
			List<Double> timeSegments = backend
					.findShortestPathWithMidpoint(locations.get(0), locations.get(1), locations.get(2))
					.getWalkingTimes();
			Double totalTime = backend
					.findShortestPathWithMidpoint(locations.get(0), locations.get(1), locations.get(2))
					.getTotalPathCost();
			// print statements
			System.out.println("The Shortest Path from " + locations.get(0) + " to " + locations.get(1)
					+ " while going to " + locations.get(2) + " is: " + shortestPath.toString());
			System.out.println("The Walking Times between each building are:  " + timeSegments.toString());
			System.out.println("The Total Time it takes to walk from " + locations.get(0) + " to " + locations.get(1)
					+ " while going to " + locations.get(2) + " is: " + totalTime.toString());
		} catch (NoSuchElementException e) {
			System.out.println("Error one or more locations that were provided aren't in the graph");
		}
	}
	@Override
	public List<String> getLocationsForShortestPathWithMidpoint() {
		List<String> locations = new ArrayList<String>();
		System.out.println("Enter the start location: ");
		locations.add(userInput.nextLine().trim());
		System.out.println("Enter the midpoint location: ");
		locations.add(userInput.nextLine().trim());
		System.out.println("Enter the end location: ");
		locations.add(userInput.nextLine().trim());
		System.out.println();
		return locations;
	}
	/**
	 * Used to start the program.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Frontend frontend = new Frontend(new Scanner(System.in),
				new Backend(new DijkstraGraph<String, Double>(new PlaceholderMap<>())));
		frontend.runCommandLoop();
	}
}

