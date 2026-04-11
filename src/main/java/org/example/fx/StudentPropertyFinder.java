package org.example.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

// Main JavaFX app class that constructs the GUI.
// Loads the FXML file and configures the main app window.
public class StudentPropertyFinder extends Application {

    // Initializes and displays the primary app window.
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StudentPropertyFinder.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Student Property Finder");
        stage.setScene(scene);
        stage.show();
    }
}