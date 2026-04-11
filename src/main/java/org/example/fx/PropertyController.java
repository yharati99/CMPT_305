package org.example.fx;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PropertyController {

    @FXML private ComboBox<School> uniComboBox;
    @FXML private Spinner<Integer> priceSpinner;
    @FXML private Spinner<Integer> distSpinner;
    @FXML private ListView<PropertyAssessment> propertyList;
    @FXML private WebView webView;
    @FXML private Hyperlink sortLink;
    @FXML private Hyperlink distSortLink;

    private boolean distAscending = true;
    private boolean ascending = true;
    private final SearchByCoordinate searchEngine = new SearchByCoordinate();

    // Initializes
    @FXML
    public void initialize() {
        if (uniComboBox != null) {
            uniComboBox.setItems(FXCollections.observableArrayList(School.values()));
        }

        if (propertyList != null) {
            propertyList.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
                if (newValue != null && webView != null) {
                    webView.getEngine().executeScript(
                            "highlightMarker(" + newValue.getLat() + ", " + newValue.getLon() + ");"
                    );
                }
            });
        }

        if (webView != null) {
            WebEngine engine = webView.getEngine();
            engine.setJavaScriptEnabled(true);

            URL url = getClass().getResource("map.html");
            if (url == null) {
                System.out.println("ERROR: map.html not found! Check your folder structure.");
            } else {
                engine.load(url.toExternalForm());
            }
        }

        if (propertyList != null) {
            propertyList.setCellFactory(_ -> new PropertyCell());

            propertyList.setStyle("-fx-background-color: transparent; -fx-control-inner-background: #f8f9fa; -fx-border-color: #f1f3f4;");
        }
    }

    // Handles the search
    @FXML
    private void handleSearch() {
        School selectedSchool = uniComboBox.getValue();

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
                    .peek(PropertyAssessment::setAdjustedValue)
                    .filter(p -> p.getAdjustedValue() <= maxPrice)
                    .filter(p -> !p.getAddress().isEmpty())
                    .collect(Collectors.toList());

            propertyList.setItems(FXCollections.observableArrayList(filteredResults));

            webView.getEngine().executeScript(
                    "zoomToLocation(" + selectedSchool.getLat() + ", " + selectedSchool.getLon() + ");"
            );

            WebEngine engine = webView.getEngine();

            engine.executeScript("clearMarkers();");

            engine.executeScript(
                    "addSchoolMarker(" + selectedSchool.getLat() + ", " + selectedSchool.getLon() + ", '" + selectedSchool + "');"
            );

            for (PropertyAssessment p : filteredResults) {
                engine.executeScript(
                        "addMarker(" + p.getLat() + ", " + p.getLon() + ", '" + p.getNeighbourhood() + "');"
                );
            }

        } catch (Exception e) {
            showError("An error occurred during search: " + e.getMessage());
        }
    }

    // Handles sorting by price
    @FXML
    private void handleSort() {
        var items = propertyList.getItems();
        if (items == null || items.isEmpty()) return;

        ascending = !ascending;

        if (ascending) {
            sortLink.setText("^ $ Sort");
            items.sort(Comparator.comparingLong(PropertyAssessment::getAssessedValue));
        } else {
            sortLink.setText("v $ Sort");
            items.sort((p1, p2) -> Long.compare(p2.getAssessedValue(), p1.getAssessedValue()));
        }
    }

    // Handles sorting by distance
    @FXML
    private void handleDistSort() {
        var items = propertyList.getItems();
        School school = uniComboBox.getValue();
        if (items == null || items.isEmpty() || school == null) return;

        distAscending = !distAscending;
        distSortLink.setText(distAscending ? "^ Dist Sort" : "v Dist Sort");

        items.sort((p1, p2) -> {
            double d1 = calculateHaversine(p1.getLat(), p1.getLon(), school.getLat(), school.getLon());
            double d2 = calculateHaversine(p2.getLat(), p2.getLon(), school.getLat(), school.getLon());
            return distAscending ? Double.compare(d1, d2) : Double.compare(d2, d1);
        });
    }

    // Calculates distance using coords
    private double calculateHaversine(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // Earth radius in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Search Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
