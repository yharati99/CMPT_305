package org.example.fx;

import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class SearchByCoordinate {

    // Calculate distance using coordinates
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    // Filter properties
    public List<PropertyAssessment> findProperties(double schoolLat, double schoolLon, double radiusKm) {
        List<PropertyAssessment> results = new ArrayList<>();
        String filePath = "Available_Properties.csv";

        StringBuilder line = null;

        try (Scanner fileScanner = new Scanner(new File(filePath))) {
            if (fileScanner.hasNextLine()) fileScanner.nextLine();

            while (fileScanner.hasNextLine()) {
                line = new StringBuilder(fileScanner.nextLine());

                try {
                    String[] row = CsvParser.parseCSVLine(line.toString());

                    double propLat = Double.parseDouble(row[16]);
                    double propLon = Double.parseDouble(row[17]);

                    double distance = calculateDistance(schoolLat, schoolLon, propLat, propLon);

                    if (distance <= radiusKm) {
                        PropertyAssessment property = new PropertyAssessment(row);
                        if (property.isResidential()) {
                            results.add(property);
                        }

                    }
                } catch (Exception e) {
                    System.out.println("Skipped row due to: " + e);
                }
            }

        } catch (Exception e) {
            System.out.println("Skipped row due to: " + e);
            assert line != null;
            System.out.println("  Failing line: [" + line.substring(0, Math.min(150, line.length())) + "]");
        }
        return results;
    }

    //Display properties
    public void displayResults(List<PropertyAssessment> properties) {
        if (properties.isEmpty()) {
            System.out.println("No properties found in that radius.");
            return;
        }

        System.out.println("Found " + properties.size() + " properties:");
        for (PropertyAssessment p : properties) {
            System.out.println(p.toString());
        }
    }
}
