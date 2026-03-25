package org.example.fx;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class PropertyController {

    // UI Elements
    @FXML private ComboBox<School> uniComboBox;
    @FXML private Spinner<Integer> priceSpinner;
    @FXML private Spinner<Integer> distSpinner;
    @FXML private ListView<PropertyAssessment> propertyList;
    @FXML private WebView webView;

    private final SearchByCoordinate searchEngine = new SearchByCoordinate();

    @FXML
    public void initialize() {
        // 1. Load the Enums into the dropdown
        if (uniComboBox != null) {
            uniComboBox.setItems(FXCollections.observableArrayList(School.values()));
        }

        // 2. Load the Map
        if (webView != null) {
            WebEngine engine = webView.getEngine();
            engine.setJavaScriptEnabled(true);

            // Using the path that worked for your setup
            URL url = getClass().getResource("map.html");
            if (url == null) {
                System.out.println("CRITICAL: map.html not found! Check your folder structure.");
            } else {
                engine.load(url.toExternalForm());
            }
        }
    } // <-- This was the brace that went missing!

    @FXML
    private void handleSearch() {
        School selectedSchool = uniComboBox.getValue();

        // Safety check: Did they click search without picking a school?
        if (selectedSchool == null) {
            showError("Please select a university from the dropdown.");
            return;
        }

        try {
            double radiusKm = distSpinner.getValue() / 1000.0;
            long maxPrice = priceSpinner.getValue().longValue();

            List<PropertyAssessment> rawResults = searchEngine.findProperties(
                    selectedSchool.getLat(),
                    selectedSchool.getLon(),
                    radiusKm
            );

            List<PropertyAssessment> filteredResults = rawResults.stream()
                    .filter(p -> p.getAssessedValue() <= maxPrice)
                    .collect(Collectors.toList());

            propertyList.setItems(FXCollections.observableArrayList(filteredResults));

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