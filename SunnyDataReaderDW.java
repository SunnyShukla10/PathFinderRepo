import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class DataReaderDW implements DataReaderInterface<String, Double>{

    public PathNavInterface<String, Double> readLocationsFromFile(String fileName) throws FileNotFoundException{

        PathNavInterface<String, Double> pathNav = new PathNavAE<>();
        try{
            File file = new File(fileName);
            Scanner data = new Scanner(file);
            List<String> locationsInFile = new LinkedList<>();
            while (data.hasNextLine()){
                String line = data.nextLine();
                String[] places = line.trim().split(" ");
                if (places.length >4){
                    places[0] = places[0].replace('_', ' ');
                    if (!locationsInFile.contains(places[0].trim())){
                        pathNav.insertNode(places[0].trim());
                    }
                    places[2] = places[2].replace('_', ' ');
                    if (!locationsInFile.contains(places[2].trim())){
                        pathNav.insertNode(places[2].trim());
                    }
                    String distance = line.substring(line.indexOf('=')+1, line.indexOf(']')).trim();
                    Double dist = Double.parseDouble(distance);
                    pathNav.insertEdge(places[0], places[2], dist);
                    pathNav.insertEdge(places[2],places[0],dist);
                    
                }
            }
            data.close();
        } catch (FileNotFoundException e){
            throw e;
        }
        return pathNav;
    }
}