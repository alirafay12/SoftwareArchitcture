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

        Button savenclose = new Button("Save txt and close");

        savenclose.setOnAction(e -> {
            List<Competitor> competitors = CompetitorList.getAllCompetitors();

            if (competitors.isEmpty()) {
                showAlert("No competitors to generate a report.");
                return;
            }
            // Calculate the highest overall score and corresponding competitor
            int highestOverallScore = -1;
            Competitor highestScoringCompetitor = null;
            int totalScores = 0;
            int maxScore = Integer.MIN_VALUE;
            int minScore = Integer.MAX_VALUE;
            Map<Integer, Integer> frequencyReport = new HashMap<>();

            for (Competitor competitor : competitors) {
                List<Integer> scores = competitor.getScores();
                if (!scores.isEmpty()) {
                    int overallScore = competitor.getOverallScore();
                    totalScores += scores.size();
                    maxScore = Math.max(maxScore, Collections.max(scores));
                    minScore = Math.min(minScore, Collections.min(scores));
                    frequencyReport.compute(overallScore, (key, value) -> value == null ? 1 : value + 1);

                    if (overallScore > highestOverallScore) {
                        highestOverallScore = overallScore;
                        highestScoringCompetitor = competitor;
                    }
                }
            }

            // Calculate the average score
            double averageScore = (totalScores > 0) ? (double) totalScores / competitors.size() : 0.0;

            // Create the report message
            String reportMessage = "Report:\n\n";
            reportMessage += "Highest Scoring Competitor:\n" + highestScoringCompetitor.getFullDetails() + "\n\n";
            reportMessage += "Average Score: " + String.format("%.2f", averageScore) + "\n";
            reportMessage += "Max Score: " + maxScore + "\n";
            reportMessage += "Min Score: " + minScore + "\n";
            reportMessage += "Frequency Report:\n";
            for (Map.Entry<Integer, Integer> entry : frequencyReport.entrySet()) {
                reportMessage += entry.getKey() + " points: " + entry.getValue() + " times\n";
            }
            reportMessage += "-------------------------------------------------------------------------------------:\n\n";

            for (Competitor competitor : competitors) {
                reportMessage += competitor.getFullDetails() + "\n\n";}
            // Write the report to a report.txt file
            try (PrintWriter writer = new PrintWriter("src/report.txt")) {
                writer.println(reportMessage);
                showAlert("Report written successfully , press ok to close ");

                Platform.exit();
            } catch (FileNotFoundException ex) {
                showAlert("Error writing the report to a file: " + ex.getMessage());
            }






        });


        Button getReportButton = new Button("Get Report");

        getReportButton.setOnAction(e -> {
            List<Competitor> competitors = CompetitorList.getAllCompetitors();

            if (competitors.isEmpty()) {
                showAlert("No competitors to generate a report.");
                return;
            }

            // Calculate the highest overall score and corresponding competitor
            int highestOverallScore = -1;
            Competitor highestScoringCompetitor = null;
            int totalScores = 0;
            int maxScore = Integer.MIN_VALUE;
            int minScore = Integer.MAX_VALUE;
            Map<Integer, Integer> frequencyReport = new HashMap<>();

            for (Competitor competitor : competitors) {
                List<Integer> scores = competitor.getScores();
                if (!scores.isEmpty()) {
                    int overallScore = competitor.getOverallScore();
                    totalScores += scores.size();
                    maxScore = Math.max(maxScore, Collections.max(scores));
                    minScore = Math.min(minScore, Collections.min(scores));
                    frequencyReport.compute(overallScore, (key, value) -> value == null ? 1 : value + 1);

                    if (overallScore > highestOverallScore) {
                        highestOverallScore = overallScore;
                        highestScoringCompetitor = competitor;
                    }
                }
            }

            // Calculate the average score
            double averageScore = (totalScores > 0) ? (double) totalScores / competitors.size() : 0.0;

            // Create the report message
            String reportMessage = "Report:\n\n";
            reportMessage += "Highest Scoring Competitor:\n" + highestScoringCompetitor.getFullDetails() + "\n\n";
            reportMessage += "Average Score: " + String.format("%.2f", averageScore) + "\n";
            reportMessage += "Max Score: " + maxScore + "\n";
            reportMessage += "Min Score: " + minScore + "\n";
            reportMessage += "Frequency Report:\n";
            for (Map.Entry<Integer, Integer> entry : frequencyReport.entrySet()) {
                reportMessage += entry.getKey() + " points: " + entry.getValue() + " times\n";
            }

            // Display the report in a dialog
            showAlert(reportMessage);

            // Write the report to a report.txt file
            try (PrintWriter writer = new PrintWriter("src/report.txt")) {
                writer.println(reportMessage);
            } catch (FileNotFoundException ex) {
                showAlert("Error writing the report to a file: " + ex.getMessage());
            }
        });

        // Search components
        Label searchLabel = new Label("Competitor Number:");
        TextField searchField = new TextField();
        Button searchButton = new Button("Search Competitor");

        // Set up table columns
        TableColumn<Competitor, Integer> numberCol = new TableColumn<>("Competitor Number");
        numberCol.setCellValueFactory(new PropertyValueFactory<>("competitorNumber"));
        numberCol.setMinWidth(150);

        TableColumn<Competitor, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setMinWidth(150);

        TableColumn<Competitor, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setMinWidth(200);

        TableColumn<Competitor, String> countryCol = new TableColumn<>("Country");
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        countryCol.setMinWidth(200);


        TableColumn<Competitor, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryCol.setMinWidth(100);

        TableColumn<Competitor, String> levelCol = new TableColumn<>("Level");
        levelCol.setCellValueFactory(new PropertyValueFactory<>("level"));
        levelCol.setMinWidth(100);

        // Set up the table
        table.getColumns().addAll(numberCol, nameCol, emailCol,countryCol, categoryCol, levelCol);
        table.setItems(competitorData);

        // Update and Delete buttons
        Button updateButton = new Button("Update Competitor Details");
        Button deleteButton = new Button("Delete Competitor");

        Button showFullDetailsButton = new Button("Show Full Details");
        Button showShortDetailsButton = new Button("Show Short Details");

        // Create a dialog for displaying competitor details
        Dialog<String> detailsDialog = new Dialog<>();
        detailsDialog.setTitle("Competitor Details");
        detailsDialog.setHeaderText("Selected Competitor Details");

        // Set the button types (e.g., "Close")
        ButtonType closeButton = new ButtonType("Close");
        detailsDialog.getDialogPane().getButtonTypes().addAll(closeButton);







        // Create a text area for displaying details
        TextArea detailsTextArea = new TextArea();
        detailsTextArea.setEditable(false);
        detailsTextArea.setWrapText(true);

        // Add the details text area to the dialog
        detailsDialog.getDialogPane().setContent(detailsTextArea);

        showFullDetailsButton.setOnAction(e -> {
            Competitor selectedCompetitor = table.getSelectionModel().getSelectedItem();
            if (selectedCompetitor != null) {
                // Display the full details of the selected competitor in the dialog
                detailsTextArea.setText(selectedCompetitor.getFullDetails());
                detailsDialog.showAndWait();
            }
        });

        showShortDetailsButton.setOnAction(e -> {
            Competitor selectedCompetitor = table.getSelectionModel().getSelectedItem();
            if (selectedCompetitor != null) {
                // Display the short details of the selected competitor in the dialog
                detailsTextArea.setText(selectedCompetitor.getShortDetails());
                detailsDialog.showAndWait();
            }
        });

        // Load data from CompetitorList
        competitorData.addAll(CompetitorList.getAllCompetitors());

        // Handle search button click
        searchButton.setOnAction(e -> {
            String competitorNumber = searchField.getText();
            Competitor foundCompetitor = findCompetitorByNumber(Integer.parseInt(competitorNumber));
            if (foundCompetitor != null) {
                table.getSelectionModel().select(foundCompetitor);
                table.scrollTo(foundCompetitor);
            }
        });

        updateButton.setOnAction(e -> {
            update();
        });

        deleteButton.setOnAction(e -> {
            Competitor selectedCompetitor = table.getSelectionModel().getSelectedItem();
            if (selectedCompetitor != null) {
                competitorData.remove(selectedCompetitor);
                CompetitorList.deleteCompetitor(selectedCompetitor);
            }
        });

        HBox searchBox = new HBox(10, searchLabel, searchField, searchButton);
        grid.add(searchBox, 0, 0);
        grid.add(table, 0, 1);

        // Arrange components in the grid
        if(role.equals("STAFF")) {
            HBox buttonBox = new HBox(10, updateButton, deleteButton, addScoresButton);
            HBox detailsBox = new HBox(10, showFullDetailsButton, showShortDetailsButton, getReportButton,savenclose);
            grid.add(buttonBox, 0, 2);
            grid.add(detailsBox, 0, 3);
        } else if(role.equals("REFEREE")) {
            HBox buttonBox = new HBox(10, addScoresButton);
            HBox detailsBox = new HBox(10, showFullDetailsButton, showShortDetailsButton);
            grid.add(buttonBox, 0, 2);
            grid.add(detailsBox, 0, 3);
        } else {
            HBox detailsBox = new HBox(10, showFullDetailsButton, showShortDetailsButton);
            grid.add(detailsBox, 0, 2);
        }
        primaryStage.show();
    }

    private Competitor findCompetitorByNumber(int competitorNumber) {
        for (Competitor competitor : competitorData) {
            if (competitor.getCompetitorNumber() == competitorNumber) {
                return competitor;
            }
        }
        return null;
    }







    private void update() {
        Competitor selectedCompetitor = table.getSelectionModel().getSelectedItem();
        if (selectedCompetitor != null) {
            // Create a custom dialog for editing competitor details
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Edit Competitor Details");
            dialog.setHeaderText("Editing Competitor " + selectedCompetitor.getCompetitorNumber());

            // Set the button types (e.g., "Update" and "Cancel")
            ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

            // Create a GridPane to layout the text fields
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            // Create text fields for editing attributes
            TextField nameField = new TextField(selectedCompetitor.getName());
            TextField emailField = new TextField(selectedCompetitor.getEmail());
            TextField dobField = new TextField(selectedCompetitor.getDateOfBirth());
            TextField categoryField = new TextField(selectedCompetitor.getCategory());
            TextField levelField = new TextField(selectedCompetitor.getLevel());

            // Add text fields to the GridPane
            grid.add(new Label("Name:"), 0, 0);
            grid.add(nameField, 1, 0);
            grid.add(new Label("Email:"), 0, 1);
            grid.add(emailField, 1, 1);
            grid.add(new Label("Date of Birth:"), 0, 2);
            grid.add(dobField, 1, 2);
            grid.add(new Label("Category:"), 0, 3);
            grid.add(categoryField, 1, 3);
            grid.add(new Label("Level:"), 0, 4);
            grid.add(levelField, 1, 4);

            dialog.getDialogPane().setContent(grid);

            // Request focus on the name field by default
            Platform.runLater(() -> nameField.requestFocus());

            // Convert the result to a string when the Update button is clicked
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == updateButtonType) {
                    // Update the selected competitor's data with the new values
                    selectedCompetitor.setName(nameField.getText());
                    selectedCompetitor.setDateOfBirth(dobField.getText());
                    selectedCompetitor.setCategory(categoryField.getText());
                    selectedCompetitor.setLevel(levelField.getText());

                    String output = RegisterService.registerCompetitor(selectedCompetitor, true);
                    showAlert(output);
                    if (output.contains("Success:")) {
                        CompetitorList.updateCompetitor(selectedCompetitor);
                        FileUtility.updateCsvFile(CompetitorList.getAllCompetitors());
                    }
                    table.refresh();
                    return "Updated";
                }
                return null;
            });

            // Show the dialog and wait for the user response
            dialog.showAndWait();
        }
    }

    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getButtonTypes().setAll(new ButtonType("OK"));

        alert.showAndWait();
    }
}
