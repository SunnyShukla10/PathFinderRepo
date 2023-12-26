import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Tests the implementation of the Backend and ShortestPath for UW Path Finder
 */
public class BackendDeveloperTests {

	/**
	 * Tests if the readFile method in the BackendInterface accepts a valid file
	 * path.
	 */
	@Test
	void testValidLoadFile() {

		DijkstraGraph<String, Double> dijkstraGraph = new DijkstraGraph<>(new PlaceholderMap<>());
		BackendInterface<String, Double> backend = new Backend(dijkstraGraph);

		File file = new File("campus.dot");

		try {

			backend.readFile(file);

			// getStatistics() method called to ensure that methods run as expected after
			// reading file
			String expected = "Total Nodes: 160, Total Edges: 400, Total Walking Time: 110675.49999999997\n";
			assertEquals(backend.getStatistics(), expected);

			// exception should not be thrown
		} catch (FileNotFoundException e) {

			fail();
		} catch (Exception e) {

			// other exceptions should not be thrown
			fail();
		}

	}

	/**
	 * Tests if the readFile method in the BackendInterface does not accept a valid
	 * file path.
	 * 
	 * @throws FileNotFoundException is thrown if file is not
	 */
	@Test
	void testInvalidLoadFile() {

		DijkstraGraph<String, Double> dijkstraGraph = new DijkstraGraph<>(new PlaceholderMap<>());
		BackendInterface<String, Double> backend = new Backend(dijkstraGraph);

		File file = new File("invalidFile.dot");

		try {

			backend.readFile(file);

			// exception should be thrown
			fail();

		} catch (FileNotFoundException e) {

		} catch (Exception e) {

			// other exceptions should not be thrown
			fail();
		}

	}

	/**
	 * Tests if the findShortestPath method in the BackendInterface correctly
	 * identifies the smallest path between two buildings.
	 */
	@Test
	void testFindShortestPath() {

		DijkstraGraph<String, Double> dijkstraGraph = new DijkstraGraph<>(new PlaceholderMap<>());
		BackendInterface<String, Double> backend = new Backend(dijkstraGraph);

		File file = new File("campus.dot");

		// valid input for start and destination building
		{
			try {
				backend.readFile(file);

				List<String> expected = new ArrayList<>();
				expected.add("Memorial Union");
				expected.add("Radio Hall");
				expected.add("Education Building");
				expected.add("South Hall");
				expected.add("Law Building");
				expected.add("X01");
				expected.add("Luther Memorial Church");
				expected.add("Noland Hall");
				expected.add("Meiklejohn House");
				expected.add("Computer Sciences and Statistics");

				List<String> actual = backend.findShortestPath("Memorial Union", "Computer Sciences and Statistics")
					.getShortestPath();

				// check size of two lists first
				assertEquals(expected.size(), actual.size());

				// check each contents of the two arrays
				for (int i = 0; i < actual.size(); i++) {

					assertEquals(expected.get(i), actual.get(i));
				}

			} catch (FileNotFoundException e) {
				fail();
			} catch (NoSuchElementException e) {
				fail();
			} catch (Exception e) {
				fail();
			}
		}

		// when input for start building is not present in graph
		{

			try {
				backend.readFile(file);

				backend.findShortestPath("invalid input", "Computer Sciences and Statistics").getShortestPath();

				// should throw exception
				fail();

			} catch (FileNotFoundException e) {
				fail();
			} catch (NoSuchElementException e) {
				// should throw this exception
			} catch (Exception e) {
				fail();
			}

		}

	}

	/**
	 * Tests if the getStatistics method in the BackendInterface correctly outputs
	 * the number of nodes (buildings), the number of edges, and the total walking
	 * time (sum of weights) for all edges in the graph
	 */
	@Test
	void testGetStatistics() {

		DijkstraGraph<String, Double> dijkstraGraph = new DijkstraGraph<>(new PlaceholderMap<>());
		BackendInterface<String, Double> backend = new Backend(dijkstraGraph);

		File file = new File("campus.dot");

		try {
			backend.readFile(file);
		} catch (FileNotFoundException e) {
			fail();
		} catch (Exception e) {
			fail();
		}

		String expected = "Total Nodes: 160, Total Edges: 400, Total Walking Time: 110675.49999999997\n";
		assertEquals(backend.getStatistics(), expected);

	}

	/**
	 * Tests if all the methods in the ShortestPathInterface (getShortestPath,
	 * getWalkingTimes, getTotalPathCost) return the path, time, and cost.
	 */
	@Test
	void testShortestPathInterface() {

		DijkstraGraph<String, Double> dijkstraGraph = new DijkstraGraph<>(new PlaceholderMap<>());
		BackendInterface<String, Double> backend = new Backend(dijkstraGraph);

		File file = new File("campus.dot");

		try {
			backend.readFile(file);

			{
				// check if correct shortest path is outputted
				// Memorial Union, Radio Hall, Education Building
				List<String> expectedPath = new ArrayList<>();
				expectedPath.add("Memorial Union");
				expectedPath.add("Radio Hall");
				expectedPath.add("Education Building");

				List<String> actualPath = backend.findShortestPath("Memorial Union", "Education Building")
					.getShortestPath();

				// check size of two lists first
				assertEquals(expectedPath.size(), actualPath.size());

				// check each contents of the two arrays
				for (int i = 0; i < actualPath.size(); i++) {

					assertEquals(expectedPath.get(i), actualPath.get(i));
				}
			}

			{
				// check walking times between each building
				List<Double> expectedTimes = new ArrayList<>();
				expectedTimes.add(176.7);
				expectedTimes.add(113.0);

				List<Double> actualTimes = backend.findShortestPath("Memorial Union", "Education Building")
					.getWalkingTimes();

				// check size of two lists first
				assertEquals(expectedTimes.size(), actualTimes.size());

				// check each contents of the two arrays
				for (int i = 0; i < actualTimes.size(); i++) {

					assertEquals(expectedTimes.get(i), actualTimes.get(i));
				}
			}

			{
				// check total time it takes to travel from start to end
				Double expectedTotalTime = 289.7;

				Double actualTotalTime = backend.findShortestPath("Memorial Union", "Education Building")
					.getTotalPathCost();

				assertEquals(expectedTotalTime, actualTotalTime);
			}

		} catch (FileNotFoundException e) {
			fail();
		} catch (NoSuchElementException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
	}

	/**
	 * Tests the getStatistics method in backend when no file is read.
	 */
	@Test
	void getStatisticsNoFile() {

		DijkstraGraph<String, Double> dijkstraGraph = new DijkstraGraph<>(new PlaceholderMap<>());
		BackendInterface<String, Double> backend = new Backend(dijkstraGraph);

		String expected = "Total Nodes: 0, Total Edges: 0, Total Walking Time: 0.0\n";
		assertEquals(backend.getStatistics(), expected);

	}

	/**
	 * Finds the walking times of a different path (Memorial Union and Computer
	 * Sciences and Statistics).
	 */
	@Test
	void differentShortestPathWalkingTimes() {

		DijkstraGraph<String, Double> dijkstraGraph = new DijkstraGraph<>(new PlaceholderMap<>());
		BackendInterface<String, Double> backend = new Backend(dijkstraGraph);

		File file = new File("campus.dot");

		try {
			backend.readFile(file);
			// check walking times between each building
			List<Double> expectedTimes = new ArrayList<>();
			expectedTimes.add(176.7);
			expectedTimes.add(113.0);
			expectedTimes.add(187.6);
			expectedTimes.add(112.80000000000001);
			expectedTimes.add(174.7);
			expectedTimes.add(65.5);
			expectedTimes.add(183.50000000000003);
			expectedTimes.add(124.19999999999999);
			expectedTimes.add(164.20000000000002);

			List<Double> actualTimes = backend.findShortestPath("Memorial Union", "Computer Sciences and Statistics")
				.getWalkingTimes();

			// check size of two lists first
			assertEquals(expectedTimes.size(), actualTimes.size());

			// check each contents of the two arrays
			for (int i = 0; i < actualTimes.size(); i++) {

				assertEquals(expectedTimes.get(i), actualTimes.get(i));
			}

		} catch (FileNotFoundException e) {
			fail();
		} catch (NoSuchElementException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
	}

	/**
	 * Tests if NoSuchElmentException is thrown when trying to find the shortest
	 * path when no start and end building were given.
	 */
	@Test
	public void noInputShortestPathIntegrationTest() {

		DijkstraGraph<String, Double> dijkstraGraph = new DijkstraGraph<>(new PlaceholderMap<>());
		BackendInterface<String, Double> backend = new Backend(dijkstraGraph);

		File file = new File("campus.dot");

		try {
			backend.readFile(file);

			backend.findShortestPath("", "").getShortestPath();

			// should throw exception
			fail();

		} catch (FileNotFoundException e) {
			fail();
		} catch (NoSuchElementException e) {
			// should throw this exception
		} catch (Exception e) {
			fail();
		}
	}

	/**
	 * Finds the total time of a different path (Memorial Union and Computer
	 * Sciences and Statistics).
	 */
	@Test
	public void loadShortestPathTotalTimeIntegrationTest() {

		DijkstraGraph<String, Double> dijkstraGraph = new DijkstraGraph<>(new PlaceholderMap<>());
		BackendInterface<String, Double> backend = new Backend(dijkstraGraph);

		File file = new File("campus.dot");

		try {
			backend.readFile(file);

			// check total time it takes to travel from start to end
			Double expectedTotalTime = 1302.2;

			Double actualTotalTime = backend.findShortestPath("Memorial Union", "Computer Sciences and Statistics")
				.getTotalPathCost();

			assertEquals(expectedTotalTime, actualTotalTime);

		} catch (FileNotFoundException e) {
			fail();
		} catch (NoSuchElementException e) {
			fail();
		} catch (Exception e) {
			fail();
		}

	}

}

