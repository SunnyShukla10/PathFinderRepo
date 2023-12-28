import java.util.List;

/*
 * Returns information regarding the shortest path between two buildings.
 */
public class ShortestPath implements ShortestPathInterface<String, Double> {

	private List<String> shortestPath;
	private List<Double> walkingTimes;
	private Double totalPathCost;

	public ShortestPath (List<String> shortestPath, List<Double> walkingTimes, Double totalPathCost) {

		this.shortestPath = shortestPath;
		this.walkingTimes = walkingTimes;
		this.totalPathCost = totalPathCost;

	}

	/**
	 * Getter method to retrieve the path.
	 *
	 * @return list of buildings along the path.
	 */
	@Override
	public List<String> getShortestPath() {

		return shortestPath;
	}

	/**
	 * Getter method to retrieve a list of the walking times of the path segments.
	 *
	 * @return the list of times (in seconds) it takes to walk from one building to the next.
	 */
	@Override
	public List<Double> getWalkingTimes() {

		return walkingTimes;
	}

	/**
	 * Getter method to retrieve the total path cost.
	 *
	 * @return Double is the estimated time to walk from start to destination
	 *         building.
	 */
	@Override
	public Double getTotalPathCost() {

		return totalPathCost;
	}

}

