import java.util.Scanner;
import java.io.IOException;
import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the school to search (UofA, MacEwan, NAIT, NorQuest): ");
        String schoolInput = input.nextLine().trim();

        School selectedSchool = null;

        // Using a switch to match the input to the Enum
        switch (schoolInput.toUpperCase()) {
            case "UOFA":
                selectedSchool = School.UofA;
                break;
            case "MACEWAN":
                selectedSchool = School.MacEwan;
                break;
            case "NAIT":
                selectedSchool = School.NAIT;
                break;
            case "NORQUEST":
                selectedSchool = School.NorQuest;
                break;
            default:
                System.out.println("Error: School not recognized.");
                return; // Exit if the school is wrong
        }

        System.out.print("Enter search radius in km (e.g., 3.0): ");
        // It's safer to use double here
        double radius = input.nextDouble();

        input.nextLine();

        // Now call your logic class using the data from the Enum
        SearchByCoordinate searcher = new SearchByCoordinate();

        List<PropertyAssessment> results = searcher.findProperties(selectedSchool.getLat(), selectedSchool.getLon(), radius);

        System.out.println("\nSearch complete. Found " + results.size() + " properties.");

        if (results.size() > 0) {
            System.out.print("Would you like to print all results to the terminal? (yes/no): ");
            String answer = input.nextLine().trim().toLowerCase();

            if (answer.equals("yes")) {
                searcher.displayResults(results);
            } if (answer.equals("no")) {
                System.out.println("Printing cancelled. Returning to menu...");
            }
        }

        input.close();
    }
}