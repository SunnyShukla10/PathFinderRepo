import java.io.FileNotFoundException;

public interface DataReaderInterface<NodeType, EdgeType extends Number>{
    
    public PathNavInterface<NodeType, EdgeType> readLocationsFromFile(String fileName) throws FileNotFoundException;

}
