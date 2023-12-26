import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

public class Frontend implements FrontendInterface {

	private Backend backend;
	private Scanner scanner;

	public Frontend(Backend b, Scanner s) {
		this.backend = b;
		this.scanner = s;
	}

	public void startMenuLoop() {
		System.out.println("UW Path Finder");
		displayMenu();
	}

	public boolean displayMenu() {
		boolean continueLoop = true;
		while (continueLoop) {
			System.out.println("Options\n1. Load File\n2. Display Data Summary\n3. Find Shortest Path\n4. Quit");
			int input = Integer.parseInt(getUserInput());
			if (input == 1) {
				System.out.print("Enter file name: ");
				loadFile(getUserInput());

			} else if (input == 2)
				dataSummary();
			else if (input == 3) {
				System.out.print("Enter the origin location: ");
				String input1 = getUserInput();
				System.out.print("Enter the destination location: ");
				String input2 = getUserInput();
				findShortestPath(input1, input2);
			} else if (input == 4)
				continueLoop = false;
			else {
				System.out.println("Not a valid input. Please enter a digit 1 through 4:");
			}
		}
		exitApp();
		return continueLoop;
	}

	public void loadFile(String fileName) {
		try {
			backend.readFile(fileName);
			System.out.println("File loaded.");
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
	}

	public void dataSummary() {
		System.out.println(backend.getStatistics());
	}

	public String getUserInput() {
		return scanner.nextLine();
	}

	public void findShortestPath(String location1, String location2) {
		try {
            PathInterface p = backend.getShortestPath(location1, location2);
            List<String> pathList = p.getPath();
            List<Double> timeList = p.getSegmentTimes();
                double totalCost = p.getTotalLength();

            System.out.println(pathList.toString());
            System.out.println(timeList.toString());
            System.out.println("Total walking time: " + totalCost + " seconds");
        } catch (NoSuchElementException e) {
            System.out.println("The place names do not exist.");
        }
    }

	public boolean subMenu() {
		return true;
	}

	public void exitApp() {
		System.out.println("Exiting app...");
		scanner.close();
	}

	public static void main(String[] args) {
		Frontend frontend = new Frontend(new Backend(new DijkstraGraph<String, Double>(new PlaceholderMap())),
				new Scanner(System.in));
		frontend.startMenuLoop();
	}
}
