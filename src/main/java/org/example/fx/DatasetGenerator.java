package org.example.fx;

import java.io.File;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class DatasetGenerator {

    // Configuration
    private static final double MAX_RADIUS_KM = 10.0; // Change this to set absolute max search area
    private static final double CHANCE_TO_BE_LISTED = 0.05;
    private static final String INPUT_FILE = "Property_Assessment_Data_2025.csv";
    private static final String OUTPUT_FILE = "Available_Properties.csv";

    public static void main(String[] args) {
        Random random = new Random();
        int totalProcessed = 0;
        int totalListed = 0;

        System.out.println("Generating new dataset");

        try (Scanner scanner = new Scanner(new File(INPUT_FILE));
             PrintWriter writer = new PrintWriter(new File(OUTPUT_FILE))) {

            // Read and copy the exact header row so your parser still works
            if (scanner.hasNextLine()) {
                String header = scanner.nextLine();
                writer.println(header);
            }

            // Process each row
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                totalProcessed++;

                try {
                    String[] row = CsvParser.parseCSVLine(line);

                    // Skip rows that don't have enough columns
                    if (row.length < 18) continue;

                    double propLat = Double.parseDouble(row[16]);
                    double propLon = Double.parseDouble(row[17]);

                    // Check if the building is within MAX_RADIUS_KM of ANY school
                    boolean isNearSchool = false;
                    for (School school : School.values()) {
                        double distance = calculateDistance(school.getLat(), school.getLon(), propLat, propLon);
                        if (distance <= MAX_RADIUS_KM) {
                            isNearSchool = true;
                            break; // It's near at least one school
                        }
                    }

                    // Apply the 5% chance if it passed the location check
                    if (isNearSchool) {
                        if (random.nextDouble() < CHANCE_TO_BE_LISTED) {
                            writer.println(line); // Write the original line exactly as it was
                            totalListed++;
                        }
                    }

                } catch (Exception _) {}
            }

            System.out.println("Dataset generation complete!");
            System.out.println("Total records checked: " + totalProcessed);
            System.out.println("Total properties 'listed for sale': " + totalListed);
            System.out.println("Saved to: " + OUTPUT_FILE);

        } catch (Exception e) {
            System.out.println("Error generating dataset: " + e.getMessage());
        }
    }

    private static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}