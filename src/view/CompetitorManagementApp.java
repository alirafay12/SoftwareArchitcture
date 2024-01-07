package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Competitor;
import model.CompetitorList;
import service.RegisterService;
import util.FileUtility;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class CompetitorManagementApp extends Application {
    private ObservableList<Competitor> competitorData = FXCollections.observableArrayList();
    private TableView<Competitor> table = new TableView<>();

    private String role;

    public CompetitorManagementApp(String role) {
        this.role = role;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Competitor Management");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setHgap(10);
        grid.setVgap(10);

        Scene scene = new Scene(grid, 1000, 400);
        primaryStage.setScene(scene);

        Button addScoresButton = new Button("Add Scores");

        addScoresButton.setOnAction(e -> {
            Competitor selectedCompetitor = table.getSelectionModel().getSelectedItem();
            if (selectedCompetitor != null) {
                // Create a new dialog for adding scores
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Add Scores");
                dialog.setHeaderText("Add Scores for " + selectedCompetitor.getName());

                // Create 10 small text fields for scores
                VBox scoreFields = new VBox(10);
                TextField[] scoreTextFields = new TextField[10];
                for (int i = 0; i < 10; i++) {
                    scoreTextFields[i] = new TextField();
                    scoreTextFields[i].setPromptText("Score " + (i + 1));
                    scoreFields.getChildren().add(scoreTextFields[i]);
                }

                // Prefill scores if they exist
                List<Integer> existingScores = selectedCompetitor.getScores();
                if (existingScores.size() > 0) {
                    for (int i = 0; i < Math.min(existingScores.size(), 10); i++) {
                        scoreTextFields[i].setText(String.valueOf(existingScores.get(i)));
                    }
                }

                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
                dialog.getDialogPane().setContent(scoreFields);

                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == ButtonType.OK) {
                        List<Integer> scores = new ArrayList<>();

                        for (TextField scoreTextField : scoreTextFields) {
                            String scoreText = scoreTextField.getText();
                            if (!scoreText.isEmpty()) {
                                try {
                                    int score = Integer.parseInt(scoreText);
                                    if (score <= 100) {
                                        scores.add(score);
                                    } else {
                                        showAlert("Total score exceeds 100.");
                                        return null;
                                    }
                                } catch (NumberFormatException ex) {
                                    showAlert("Invalid score input.");
                                    return null;
                                }
                            }
                        }

                        // Add the scores to the selected competitor
                        selectedCompetitor.setScores(scores);
                        FileUtility.updateCsvFile(CompetitorList.getAllCompetitors());
                        table.refresh();
                    }
                    return null;
                });

                dialog.showAndWait();
            }
        });

