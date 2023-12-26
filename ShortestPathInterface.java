import java.util.List;

/**
 * Interface with getter methods to store result of Shortest Path.
 */
public interface ShortestPathInterface<NodeType, EdgeType extends Number> {

  //Constructor
  //public ShortestPathInterface (List<NodeType> shortestPath, List<EdgeType> walkingTimes, EdgeType totalPathCost)

  /**
   * Getter method to retrieve the path.
   *
   * @return list of buildings along the path.
   */
  public List<NodeType> getShortestPath();

  /**
   * Getter method to retrieve a list of the walking times of the path segments.
   *
   * @return the list of times it takes to walk from one building to the next.
   */
  public List<EdgeType> getWalkingTimes();


  /**
   * Getter method to retrieve the total path cost.
   *
   * @return EdgeType is the estimated time to walk from start to destination building.
   */
  public EdgeType getTotalPathCost();

}



