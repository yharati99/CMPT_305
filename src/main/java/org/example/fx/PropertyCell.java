package org.example.fx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.text.NumberFormat;
import java.util.Locale;

public class PropertyCell extends ListCell<PropertyAssessment> {

    // 1. Declare the labels
    private Label addressLabel;
    private Label neighborhoodLabel;
    private Label assessmentClassLabel;
    private Label rentLabel;
    private HBox layout; // We'll put all text inside a vertical stack, then that in a horizontal layout

    public PropertyCell() {
        // 2. Initialize the labels and apply visual styling
        addressLabel = new Label();
        addressLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #3c4043;");

        neighborhoodLabel = new Label();
        // Mute the neighborhood text
        neighborhoodLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #70757a;");

        assessmentClassLabel = new Label();
        assessmentClassLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #9aa0a6; -fx-background-color: #f1f3f4; -fx-background-radius: 4px; -fx-padding: 2px 4px;");

        rentLabel = new Label();
        // Bold and highlight the rent
        rentLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1a73e8; -fx-padding: 0 0 0 10px;"); // Right padding

        // 3. Define the multi-line text layout (vertical stack)
        VBox textStack = new VBox(2); // 2px spacing between lines
        textStack.getChildren().addAll(addressLabel, neighborhoodLabel, assessmentClassLabel);
        textStack.setAlignment(Pos.CENTER_LEFT);
        textStack.setPadding(new Insets(5, 5, 5, 5));

        // 4. Arrange the text and the rent horizontally
        layout = new HBox(10); // 10px spacing
        layout.getChildren().addAll(textStack, rentLabel);
        layout.setAlignment(Pos.CENTER_LEFT);

        // Ensure the text takes up available space, pushing rent to the far right
        HBox.setHgrow(textStack, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(rentLabel, javafx.scene.layout.Priority.NEVER); // Rent stays compact

        // Subtle alternating background colors
        this.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                this.setStyle("-fx-background-color: #e8f0fe;"); // Highlight selected cell
            } else if (this.getIndex() % 2 == 0) {
                this.setStyle("-fx-background-color: #ffffff;"); // Even cell color
            } else {
                this.setStyle("-fx-background-color: #f8f9fa;"); // Odd cell color
            }
        });
    }

    // 5. This method is called whenever JavaFX needs to update a single cell
    @Override
    protected void updateItem(PropertyAssessment item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
            setText(null);
        } else {
            // 6. Map the data from the PropertyAssessment to the Labels
            addressLabel.setText(item.getAddress());
            neighborhoodLabel.setText(item.getNeighbourhood());
            assessmentClassLabel.setText(item.getAssessmentClass().toUpperCase());

            // 7. Format and display the Rent currency
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);
            rentLabel.setText(currencyFormat.format(item.getAdjustedValue()) + "/mo");

            // 8. Set the visual card layout as the graphic for this cell
            setGraphic(layout);
            setText(null); // Explicitly clear the plain text
        }
    }
}