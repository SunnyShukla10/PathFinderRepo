import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
// import BaseGraph.Edge;
// import DijkstraGraph.SearchNode;
// import BaseGraph.Edge;
// import DijkstraGraph.SearchNode;
import java.util.PriorityQueue;



/**
 * this class add additional methods to DijkStraGraph
 *
 * @author ngsch
 *
 * @param <NodeType>
 * @param <EdgeType>
 */
public class PathNavAE<NodeType, EdgeType extends Number> extends DijkstraGraph<NodeType, EdgeType>
    implements PathNavInterface<NodeType, EdgeType> {



  /**
   * 
   * finds the closest node by taking the most optimal path
   * 
   * @param the starting node
   * @return the closest node
   */
  @Override
  public NodeType closestNode(NodeType start) {

    if (!this.containsNode(start)) {
      throw new NoSuchElementException("Start node isnt in the graph!");
    }

    // creating the priority q and the node tracker
    Hashtable<NodeType, SearchNode> nodeTracker = new Hashtable();

    PriorityQueue<SearchNode> nodeQueue = new PriorityQueue();


    // adding first search node to the PQ
    nodeQueue.add(new SearchNode(nodes.get(start), 0, null));



    while (!nodeQueue.isEmpty()) {


      // current search node we are checking
      SearchNode snode = nodeQueue.remove();


      // check if we have visted this node yet
      if (!nodeTracker.containsKey(snode.node.data)) {

        // visting node
        nodeTracker.put(snode.node.data, snode);

        // add the edges leaving this node to pq if they havent been visted yet
        for (Edge e : snode.node.edgesLeaving) {

          if (!nodeTracker.containsKey(e.successor.data)) {
            nodeQueue.add(new SearchNode(e.successor, e.data.doubleValue() + snode.cost, snode));
          }
        }

      }



    }

    Set<NodeType> keys = nodeTracker.keySet();


    for (NodeType key : keys) {


      nodeQueue.add(nodeTracker.get(key));

    }

    // have to get rid of first node because it is itself
    nodeQueue.remove();
    NodeType smallest = nodeQueue.remove().node.data;

    return smallest;



  }

  /**
   * 
   * Finds the farthest node from the start node, taking the most optimal path
   * 
   * @param start- node where we are starting
   * @return the farthest node
   * 
   */
  @Override
  public NodeType farthestNode(NodeType start) {

    if (!this.containsNode(start)) {
      throw new NoSuchElementException("Start node isnt in the graph!");
    }

    // creating the priority q and the node tracker
    Hashtable<NodeType, SearchNode> nodeTracker = new Hashtable();

    PriorityQueue<SearchNode> nodeQueue = new PriorityQueue();


    // adding first search node to the PQ
    nodeQueue.add(new SearchNode(nodes.get(start), 0, null));



    while (!nodeQueue.isEmpty()) {


      // current search node we are checking
      SearchNode snode = nodeQueue.remove();


      // check if we have visted this node yet
      if (!nodeTracker.containsKey(snode.node.data)) {

        // visting node
        nodeTracker.put(snode.node.data, snode);

        // add the edges leaving this node to pq if they havent been visted yet
        for (Edge e : snode.node.edgesLeaving) {

          if (!nodeTracker.containsKey(e.successor.data)) {
            nodeQueue.add(new SearchNode(e.successor, e.data.doubleValue() + snode.cost, snode));
          }
        }

      }



    }

    Set<NodeType> keys = nodeTracker.keySet();


    for (NodeType key : keys) {


      nodeQueue.add(nodeTracker.get(key));

    }

    NodeType largest = nodeQueue.remove().node.data;;


    while (!nodeQueue.isEmpty()) {

      largest = nodeQueue.remove().node.data;
    }



    return largest;



  }



  /*
   * private SearchNode minSpanningHelper(NodeType start) throws NoSuchElementException {
   * 
   * if (!this.containsNode(start)) {
   * 
   * throw new NoSuchElementException("Start node is not in graph!"); }
   * 
   * 
   * DijkstraGraph mst = new DijkstraGraph<>(); Node startNode = nodes.get(start);
   * Hashtable<NodeType, Node> tracker = new Hashtable<NodeType, Node>();
   * 
   * ArrayList<Edge> edges = new ArrayList<Edge>(); tracker.put(start, startNode);
   * 
   * 
   * for (Edge e : startNode.edgesLeaving) {
   * 
   * edges.add(e); }
   * 
   * 
   * while (!edges.isEmpty()) {
   * 
   * Edge min = edges.get(0); for (Edge e : edges) { if (e.data.doubleValue() <
   * min.data.doubleValue()) { min = e; }
   * 
   * edges.remove(min);
   * 
   * if (!tracker.contains(min.successor.data)) { tracker.put(min.successor.data, min.successor);
   * mst.insertNode(min.predecessor); mst.insertNode(min.successor); mst.insertEdge(min.predecessor,
   * min.successor, min.data); mst.insertEdge(min.successor, min.predecessor, min.data);
   * 
   * for (Edge ed : min.successor.edgesLeaving) {
   * 
   * edges.add(ed); }
   * 
   * }
   * 
   * } }
   * 
   * 
   * return null; }
   */


  /**
   * 
   * finds the shortest path through a given midpoint
   * 
   * @param startNode - the starting node
   * @param midpoint  - the midpoint we want to pass through
   * @param endNode-  the end point
   * 
   * @return the list of nodes in this path
   * 
   */
  @Override
  public List<NodeType> shortestPathMidpointData(NodeType startNode, NodeType midpoint,
      NodeType endNode) {


    List<NodeType> firstPath = this.shortestPathData(startNode, midpoint);


    List<NodeType> secondPath = this.shortestPathData(midpoint, endNode);

    // we remove the first one on the secondpath to avoid double count of the vertice
    secondPath.remove(0);

    List<NodeType> totalPath = new ArrayList<NodeType>();

    totalPath.addAll(firstPath);
    totalPath.addAll(secondPath);



    return totalPath;
  }


  /**
   * 
   * finds the shortest path's cost through a given midpoint
   * 
   * @param startNode - the starting node
   * @param midpoint  - the midpoint we want to pass through
   * @param endNode-  the end point
   * 
   * @return the cost of this path
   * 
   */
  @Override
  public double shortestPathMidpointCost(NodeType startNode, NodeType midpoint, NodeType endNode) {
    // TODO Auto-generated method stub

    Double firstPath = this.shortestPathCost(startNode, midpoint);
    Double secondPath = this.shortestPathCost(midpoint, endNode);


    return firstPath + secondPath;
  }


  /**
   * returns a specific node if it is in the graph
   * 
   */
  @Override
  public NodeType getLocation(NodeType locationToFind) throws NoSuchElementException {
    // TODO Auto-generated method stub

    if (!this.containsNode(locationToFind)) {
      throw new NoSuchElementException("Location to find doesn't exist");
    }

    Set<NodeType> key = nodes.keySet();


    for (NodeType n : key) {

      if (n.equals(locationToFind)) {

        return nodes.get(n).data;

      }
    }



    // should never reach here
    return null;
  }

}
