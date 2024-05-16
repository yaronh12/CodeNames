package ui.game;

import engine.engine.Engine;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;
import java.util.Scanner;

/**
 * This class is responsible for initializing game settings and loading game data from XML files.
 */
public class GameInitializer {

    /**
     * Prompts the user to input the file path to an XML file and attempts to load it into the game engine.
     * The method continues to prompt the user until a valid file path is provided and the file is successfully loaded.
     * This method handles various exceptions to provide specific feedback about loading errors, enhancing user experience
     * and debuggability.
     *
     * @param game The game engine where the XML data will be loaded.
     */
    public static void loadFile(Engine game){
        Scanner in = new Scanner(System.in);
        boolean isFileValid = false;
        do {
            System.out.println("\nPlease enter the full path to your XML file (e.g., C:\\Users\\YourName\\Documents\\example.xml on Windows)");
            try {
                game.readXmlFile(in.nextLine());
                isFileValid = true; // Set true if file successfully read
            } catch (FileNotFoundException e) {
                System.out.println("The file was not found. Please check the path and try again.");
            } catch (InvalidPathException e) {
                System.out.println("The file path is invalid. Please enter a correct path.");
            } catch (JAXBException e) {
                System.out.println("JAXB Exception - Error in processing the XML file.");
            } catch (RuntimeException e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        } while (!isFileValid);
    }
}