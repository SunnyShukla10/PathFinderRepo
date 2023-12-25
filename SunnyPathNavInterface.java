import java.util.List;

public interface PathNavInterface<NodeType,EdgeType extends Number> extends GraphADT<NodeType,EdgeType>{

	// finds the farthest point while still taking the most optimal route
	public NodeType farthestNode(NodeType start);

	// finds the closest point while taking the most optimal route
	public NodeType closestNode(NodeType start);

	public List<NodeType> shortestPathMidpointData(NodeType startNode, NodeType midpoint, NodeType endNode);

	public double shortestPathMidpointCost(NodeType startNode, NodeType midpoint, NodeType endNode);

	public NodeType getLocation(NodeType locationToFind);


}
