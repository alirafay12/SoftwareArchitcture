import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.FileUtility;
import view.CompetitorManagementApp;
import view.CompetitorRegistrationApp;

public class MainApplication extends Application {
    private static final String STAFF_PASSWORD = "staff"; // Replace with your actual password


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Competitor Application");

        Button registerButton = new Button("Register Competitor");
        Button staffButton = new Button("Staff Operations");
        Button refereeButton = new Button("Referee");
        Button competitorButton = new Button("Competitor");

        registerButton.setMinWidth(300);
        registerButton.setMinHeight(70);

        staffButton.setMinWidth(300);
        staffButton.setMinHeight(70);

        refereeButton.setMinWidth(300);
        refereeButton.setMinHeight(70);

        competitorButton.setMinWidth(300);
        competitorButton.setMinHeight(70);

        registerButton.setOnAction(e -> openCompetitorRegistration());
        staffButton.setOnAction(e -> showStaffPasswordDialog());
        refereeButton.setOnAction(e -> openCompetitorManagement("REFEREE"));
        competitorButton.setOnAction(e -> openCompetitorManagement("COMPETITOR"));

        VBox layout = new VBox();
        layout.getChildren().addAll(registerButton, staffButton, refereeButton, competitorButton);

        Scene scene = new Scene(layout, 300, 280);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void openCompetitorRegistration() {
        CompetitorRegistrationApp registrationApp = new CompetitorRegistrationApp();
        registrationApp.start(new Stage());
    }
    private void openCompetitorManagement(String role) {
        CompetitorManagementApp managementApp = new CompetitorManagementApp(role);
        managementApp.start(new Stage());
    }

    private void showStaffPasswordDialog() {
        TextInputDialog passwordDialog = new TextInputDialog();
        passwordDialog.setTitle("Staff Password");
        passwordDialog.setHeaderText("Enter 'staff' as Password:");
        passwordDialog.setContentText("Password:");

        // Set placeholder text for the password field
        passwordDialog.getEditor().setPromptText("Password is 'staff'");

        // Show the dialog and wait for the result
        passwordDialog.showAndWait().ifPresent(password -> {
            if (password.equals(STAFF_PASSWORD)) {
                openCompetitorManagement("STAFF");
            } else {
                // Incorrect password handling, show a separate dialog
                showIncorrectPasswordDialog();
                showStaffPasswordDialog();

            }
        });
    }
    private void showIncorrectPasswordDialog() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect Password");
        alert.setHeaderText("Incorrect staff password");
        alert.setContentText("Please enter the correct password.");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        FileUtility.readTrackEventCompetitors();
        FileUtility.readFieldEventCompetitors();
        launch(args);
    }
}


