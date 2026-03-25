package org.example.fx;

import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the school to search (UofA, MacEwan, NAIT, NorQuest): ");
        String schoolInput = input.nextLine().trim();

        School selectedSchool;

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
                return;
        }

        System.out.print("Enter search radius in km (e.g., 3.0): ");
        // It's safer to use double here
        double radius = input.nextDouble();
        input.nextLine();

        // Now call your logic class using the data from the Enum
        SearchByCoordinate searcher = new SearchByCoordinate();

        List<PropertyAssessment> results = searcher.findProperties(selectedSchool.getLat(), selectedSchool.getLon(), radius);

        System.out.println("\nSearch complete. Found " + results.size() + " properties.");

        if (!results.isEmpty()) {
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
