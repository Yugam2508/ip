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

    @Override
    public void start(Stage stage) {
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        dialogContainer.setSpacing(10);
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        stage.setScene(scene);
        stage.setTitle("Jarvis");
        stage.setMinHeight(600);
        stage.setMinWidth(400);

        mainLayout.setPrefSize(400, 600);
        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325);
        sendButton.setPrefWidth(55);

        AnchorPane.setTopAnchor(scrollPane, 1.0);
        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);
        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        Label welcomeLabel = new Label("Hello! I'm Jarvis\nWhat can I do for you?");
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
        dialogContainer.getChildren().add(userLabel);

        String response = getResponse(input);
        Label jarvisLabel = new Label("Jarvis: " + response);
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