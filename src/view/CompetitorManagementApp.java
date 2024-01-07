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

