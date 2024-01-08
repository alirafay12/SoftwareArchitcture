package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.CompetitorList;
import model.FieldEvent;
import model.FieldEventCompetitor;
import model.TrackEventCompetitor;
import service.RegisterService;
import util.CompetitorNumberGenerator;
import util.FileUtility;

public class CompetitorRegistrationApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Competitor Registration");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setHgap(10);
        grid.setVgap(10);

        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);

        Label titleLabel = new Label("Register a Competitor");
        titleLabel.setStyle("-fx-font-size: 16px;");
        GridPane.setConstraints(titleLabel, 0, 0);
        GridPane.setColumnSpan(titleLabel, 2);

        Label eventLabel = new Label("Select Event:");
        GridPane.setConstraints(eventLabel, 0, 1);

        ComboBox<String> eventChoice = new ComboBox<>();
        eventChoice.getItems().addAll("Track Event", "Field Event");
        eventChoice.setValue("Track Event"); // Default selection
        GridPane.setConstraints(eventChoice, 1, 1);

        // Labels and input fields for Track Event
        Label fieldEventLabel = new Label("Select Field Event:");
        GridPane.setConstraints(fieldEventLabel, 0, 2);
        ComboBox<String> fieldEventChoice = new ComboBox<>();
        fieldEventChoice.getItems().addAll("SHOTPUT", "DISCUS", "JAVELIN", "LONG_JUMP", "HIGH_JUMP", "TRIPLE_JUMP");
        fieldEventChoice.setValue("SHOTPUT"); // Default selection
        GridPane.setConstraints(fieldEventChoice, 1, 2);

        // Labels and input fields for Field Event
        Label distanceLabel = new Label("Distance (m):");
        GridPane.setConstraints(distanceLabel, 0, 2);
        TextField distanceField = new TextField();
        GridPane.setConstraints(distanceField, 1, 2);

        // Additional labels and input fields for common competitor attributes
        Label nameLabel = new Label("Name:");
        GridPane.setConstraints(nameLabel, 0, 3);
        TextField nameField = new TextField();
        GridPane.setConstraints(nameField, 1, 3);

        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 4);
        TextField emailField = new TextField();
        GridPane.setConstraints(emailField, 1, 4);

        Label countryLabel = new Label("Country:");
        GridPane.setConstraints(countryLabel, 0, 5);
        TextField countryField = new TextField();
        GridPane.setConstraints(countryField, 1, 5);

        Label dobLabel = new Label("Date of Birth:");
        GridPane.setConstraints(dobLabel, 0, 6);
        DatePicker dobPicker = new DatePicker();
        GridPane.setConstraints(dobPicker, 1, 6);

        Label categoryLabel = new Label("Category:");
        GridPane.setConstraints(categoryLabel, 0, 7);
        ComboBox<String> categoryChoice = new ComboBox<>();
        categoryChoice.getItems().addAll("Novice", "Expert"); // Add more categories as needed
        categoryChoice.setValue("Novice"); // Default selection
        GridPane.setConstraints(categoryChoice, 1, 7);

