package codsoft.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Random;

public class Task1 extends Application {

    private int randomNumber;
    private int attempts;
    private int maxAttempts = 5;
    private int score = 0;
    private int round = 1;
    private Random random = new Random();
    private Label feedbackLabel;
    private TextField guessField;
    private Button guessButton;
    private Button playAgainButton;
    private Label scoreLabel;
    private Label roundLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Number Guessing Game");

        feedbackLabel = new Label("Guess a number between 1 and 100:");
        guessField = new TextField();
        guessButton = new Button("Guess");
        playAgainButton = new Button("Play Again");
        playAgainButton.setDisable(true);
        scoreLabel = new Label("Score: 0");
        roundLabel = new Label("Round: 1");

        guessButton.setOnAction(e -> handleGuess());
        playAgainButton.setOnAction(e -> startNewRound());

        VBox layout = new VBox(10, roundLabel, scoreLabel, feedbackLabel, guessField, guessButton, playAgainButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20,550,20,550));
        layout.setPrefSize(300, 200);

        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        Screen screen = Screen.getPrimary();
        javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.show();

        startNewRound();
    }

    private void startNewRound() {
        randomNumber = random.nextInt(100) + 1;
        attempts = 0;
        feedbackLabel.setText("Guess a number between 1 and 100:");
        guessField.clear();
        guessButton.setDisable(false);
        playAgainButton.setDisable(true);
        roundLabel.setText("Round: " + round);
    }

    private void handleGuess() {
        try {
            int guess = Integer.parseInt(guessField.getText());
            attempts++;

            if (guess < randomNumber) {
                feedbackLabel.setText("Too low!!! Try again:");
            } else if (guess > randomNumber) {
                feedbackLabel.setText("Too high!!! Try again:");
            } else {
                feedbackLabel.setText("Correct!!! You've guessed the number.");
                score += maxAttempts - attempts + 1;
                scoreLabel.setText("Score: " + score);
                guessButton.setDisable(true);
                playAgainButton.setDisable(false);
                round++;
                return;
            }

            if (attempts >= maxAttempts) {
                feedbackLabel.setText("Out of attempts!!! The number was " + randomNumber);
                guessButton.setDisable(true);
                playAgainButton.setDisable(false);
                round++;
            }

        } catch (NumberFormatException e) {
            feedbackLabel.setText("Please enter a valid number:");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
