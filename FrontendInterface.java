import java.util.Scanner;
//Mykhailo Pustovit
//Rashiv Ray
//Diego  Raudales
public interface FrontendInterface {

    /*                                                                                                                                                                                 
    private Backend backend;                                                                                                                                                           
    private Scanner scanner;                                                                                                                                                           
                                                                                                                                                                                       
    public IndividualFrontendInterface(Backend b, Scanner s) {                                                                                                                         
                                                                                                                                                                                       
        this.backend = b;                                                                                                                                                              
        this.scanner = s;                                                                                                                                                              
                                                                                                                                                                                       
    }                                                                                                                                                                                  
    */

    // Starts the main menu loop                                                                                                                                                       
    public void startMenuLoop();

    // Displays summary of overall data                                                                                                                                                
    public void	dataSummary();

    // Main loop that displays a menu with options                                                                                                                                     
    // This method should get user input when needed                                                                                                                                   
    // And call other methods using the input                                                                                                                                          
    public boolean displayMenu();

    // Helper method that reads user input                                                                                                                                             
    // @return the String representing what the user has typed                                                                                                                         
    public String getUserInput();

    // Attempts to load a data file based on user input                                                                                                                                
    // @param fileName the String representing the file name to be opened                                                                                                              
    public void loadFile(String fileName);

    // Given an origin and destination prints the following:                                                                                                                           
    // Shortest path, including all locations along the way                                                                                                                            
    // Estimated time for each segment, and total time                                                                                                                                 
    // @param location1 the origin location                                                                                                                                            
    // @param location2 the destination location                                                                                                                                       
    public void findShortestPath(String location1, String location2);

    // Exits the app                                                                                                                                                                   
    public void exitApp();

    //Makes a sub Menu for option of summary and shortest path
    public boolean subMenu();

}

