package jarvis;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main JavaFX application class for Jarvis.
 */
public class Main extends Application {

    private Jarvis jarvis = new Jarvis("./data/jarvis.txt");
    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    /**
     * Initializes and displays the JavaFX GUI.
     * Sets up the layout, styling, and event handlers for the application.
     *
     * @param stage The primary stage for this application.
     */
    @Override
    public void start(Stage stage) {
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        dialogContainer.setSpacing(10);
        dialogContainer.setStyle("-fx-padding: 10;");
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        userInput.setPromptText("Enter command here...");
        sendButton = new Button("Send");
        sendButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);
        mainLayout.setStyle("-fx-background-color: #f5f5f5;");

        scene = new Scene(mainLayout);

        stage.setScene(scene);
        stage.setTitle("Jarvis Task Manager");
        stage.setMinHeight(600);
        stage.setMinWidth(400);

        mainLayout.setPrefSize(400, 600);
        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #f5f5f5;");

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325);
        sendButton.setPrefWidth(55);

        AnchorPane.setTopAnchor(scrollPane, 1.0);
        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);
        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        Label welcomeLabel = new Label("Hello! I'm Jarvis\nWhat can I do for you?");
        welcomeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #2196F3; -fx-font-weight: bold;");
        dialogContainer.getChildren().add(welcomeLabel);

        sendButton.setOnMouseClicked((event) -> {
            handleUserInput();
        });

        userInput.setOnAction((event) -> {
            handleUserInput();
        });

        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));

        stage.show();
    }

    /**
     * Handles user input and displays response.
     */
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.trim().isEmpty()) {
            return;
        }

        Label userLabel = new Label("You: " + input);
        userLabel.setStyle("-fx-background-color: #E3F2FD; -fx-padding: 8; -fx-background-radius: 5;");
        dialogContainer.getChildren().add(userLabel);

        String response = getResponse(input);
        Label jarvisLabel = new Label("Jarvis: " + response);

        if (response.startsWith("OOPS") || response.startsWith("Error")) {
            jarvisLabel.setStyle("-fx-background-color: #FFEBEE; -fx-padding: 8; -fx-background-radius: 5; -fx-text-fill: #C62828;");
        } else {
            jarvisLabel.setStyle("-fx-background-color: #E8F5E9; -fx-padding: 8; -fx-background-radius: 5;");
        }

        dialogContainer.getChildren().add(jarvisLabel);

        userInput.clear();

        if (input.trim().equalsIgnoreCase("bye")) {
            javafx.application.Platform.exit();
        }
    }

    /**
     * Gets response from Jarvis based on user input.
     *
     * @param input The user's input command.
     * @return The response from Jarvis.
     */
    private String getResponse(String input) {
        try {
            return jarvis.getResponse(input);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}