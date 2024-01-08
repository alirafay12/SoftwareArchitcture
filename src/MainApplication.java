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

