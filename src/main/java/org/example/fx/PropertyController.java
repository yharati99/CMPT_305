package org.example.fx;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;
import java.util.stream.Collectors;

public class PropertyController {
    @FXML private TextField uniField; // User types: UofA, MacEwan, NAIT, or NorQuest
    @FXML private Spinner<Integer> priceSpinner;
    @FXML private Spinner<Integer> distSpinner;
    @FXML private ListView<PropertyAssessment> propertyList;

    private final SearchByCoordinate searchEngine = new SearchByCoordinate();

    @FXML
    private void handleSearch() {
        String input = uniField.getText().trim();

        try {
            // 1. Match the text input to your School Enum
            School selectedSchool = School.valueOf(input);

            // 2. Get values from the UI
            double radiusKm = distSpinner.getValue() / 1000.0; // Convert Meters to KM for your Haversine logic
            long maxPrice = priceSpinner.getValue().longValue();

            // 3. Use your SearchByCoordinate findProperties method
            List<PropertyAssessment> rawResults = searchEngine.findProperties(
                    selectedSchool.getLat(),
                    selectedSchool.getLon(),
                    radiusKm
            );

            // 4. Filter by Max Price (as required by your original logic)
            List<PropertyAssessment> filteredResults = rawResults.stream()
                    .filter(p -> p.getAssessedValue() <= maxPrice)
                    .collect(Collectors.toList());

            // 5. Update the UI
            propertyList.setItems(FXCollections.observableArrayList(filteredResults));

        } catch (IllegalArgumentException e) {
            showError("Invalid University. Please enter: UofA, MacEwan, NAIT, or NorQuest");
        } catch (Exception e) {
            showError("An error occurred during search: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Search Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}