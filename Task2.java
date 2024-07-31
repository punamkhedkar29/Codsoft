package codsoft.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Task2 extends Application {

    private TextField[] marksFields;
    private Label totalMarksLabel;
    private Label averagePercentageLabel;
    private Label gradeLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Grade Calculator");

        int numberOfSubjects = 5;
        marksFields = new TextField[numberOfSubjects];
        VBox marksBox = new VBox(10);
        marksBox.setAlignment(Pos.CENTER);
        for (int i = 0; i < numberOfSubjects; i++) {
            marksFields[i] = new TextField();
            marksFields[i].setPromptText("Marks for subject " + (i + 1));
            marksBox.getChildren().add(marksFields[i]);
        }

        Button calculateButton = new Button("Calculate");
        calculateButton.setOnAction(e -> calculateResults());

        totalMarksLabel = new Label("Total Marks: ");
        averagePercentageLabel = new Label("Average Percentage: ");
        gradeLabel = new Label("Grade: ");

        VBox layout = new VBox(10, marksBox, calculateButton, totalMarksLabel, averagePercentageLabel, gradeLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20,550,20,550));
        layout.setPrefSize(300, 400);

        Scene scene = new Scene(layout);
        Screen screen = Screen.getPrimary();
        javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    private void calculateResults() {
        int totalMarks = 0;
        int validSubjects = 0;

        for (TextField marksField : marksFields) {
            try {
                int marks = Integer.parseInt(marksField.getText());
                if (marks >= 0 && marks <= 100) {
                    totalMarks += marks;
                    validSubjects++;
                } else {
                    marksField.setStyle("-fx-border-color: red;");
                }
            } catch (NumberFormatException e) {
                marksField.setStyle("-fx-border-color: red;");
            }
        }

        if (validSubjects == marksFields.length) {
            double averagePercentage = (double) totalMarks / validSubjects;
            String grade = calculateGrade(averagePercentage);

            totalMarksLabel.setText("Total Marks: " + totalMarks);
            averagePercentageLabel.setText(String.format("Average Percentage: %.2f%%", averagePercentage));
            gradeLabel.setText("Grade: " + grade);

            for (TextField marksField : marksFields) {
                marksField.setStyle(null); 
            }
        } else {
            totalMarksLabel.setText("Total Marks: Invalid input detected");
            averagePercentageLabel.setText("Average Percentage: Invalid input detected");
            gradeLabel.setText("Grade: Invalid input detected");
        }
    }

    private String calculateGrade(double averagePercentage) {
        if (averagePercentage >= 90) {
            return "A";
        } else if (averagePercentage >= 80) {
            return "B";
        } else if (averagePercentage >= 70) {
            return "C";
        } else if (averagePercentage >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
