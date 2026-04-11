package org.example.fx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.text.NumberFormat;
import java.util.Locale;

// Defines a visual layout for the single rows inside the list view of the app.
public class PropertyCell extends ListCell<PropertyAssessment> {

    private final Label addressLabel;
    private final Label neighborhoodLabel;
    private final Label assessmentClassLabel;
    private final Label rentLabel;
    private final HBox layout;

    // Initializes the text labels, applies CSS styling, structures the layout, etc.
    public PropertyCell() {
        addressLabel = new Label();
        addressLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #3c4043;");

        neighborhoodLabel = new Label();
        neighborhoodLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #70757a;");

        assessmentClassLabel = new Label();
        assessmentClassLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #9aa0a6; -fx-background-color: #f1f3f4; -fx-background-radius: 4px; -fx-padding: 2px 4px;");

        rentLabel = new Label();
        rentLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1a73e8; -fx-padding: 0 0 0 10px;");

        VBox textStack = new VBox(2);
        textStack.getChildren().addAll(addressLabel, neighborhoodLabel, assessmentClassLabel);
        textStack.setAlignment(Pos.CENTER_LEFT);
        textStack.setPadding(new Insets(5, 5, 5, 5));

        layout = new HBox(10); // 10px spacing
        layout.getChildren().addAll(textStack, rentLabel);
        layout.setAlignment(Pos.CENTER_LEFT);

        HBox.setHgrow(textStack, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(rentLabel, javafx.scene.layout.Priority.NEVER);

        this.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                this.setStyle("-fx-background-color: #e8f0fe;");
            } else if (this.getIndex() % 2 == 0) {
                this.setStyle("-fx-background-color: #ffffff;");
            } else {
                this.setStyle("-fx-background-color: #f8f9fa;");
            }
        });
    }

    // Automatically called by JavaFX whenever a call needs to be drawn or refreshed.
    // Puts the specific property's data into the UI labels.
    @Override
    protected void updateItem(PropertyAssessment item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
            setText(null);
        } else {
            addressLabel.setText(item.getAddress());
            neighborhoodLabel.setText(item.getNeighbourhood());
            assessmentClassLabel.setText(item.getAssessmentClass().toUpperCase());

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);
            rentLabel.setText(currencyFormat.format(item.getAdjustedValue()) + "/mo");

            setGraphic(layout);
            setText(null);
        }
    }
}