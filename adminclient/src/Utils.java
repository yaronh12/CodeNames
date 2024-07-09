import java.util.InputMismatchException;
import java.util.Scanner;

public class Utils {
    public static int getValidInteger(int lowerLimit, int upperLimit) {
        Scanner scanner = new Scanner(System.in);
        int userChoice = 0;
        boolean isAnInt = false;

        do {
            try {
                userChoice = scanner.nextInt();
                isAnInt = true;
                if (userChoice < lowerLimit || userChoice > upperLimit) {
                    System.out.println("Error: Please enter a valid number between " + lowerLimit + " and " + upperLimit + ".");
                }
            } catch (InputMismatchException ime) {
                scanner.nextLine(); // Consume the invalid input and reset
                System.out.println("Invalid input (not a number). Please enter a valid number.");
            }
        } while (userChoice < lowerLimit || userChoice > upperLimit || !isAnInt);

        return userChoice;
    }
}
