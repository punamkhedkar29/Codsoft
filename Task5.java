package codsoft.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Task5 extends Application {

    private Task5_BankAccount account;
    private Label balanceLabel;
    private TextField amountField;
    private Label messageLabel;

    @Override
    public void start(Stage primaryStage) {
        account = new Task5_BankAccount(1000.0); 

        primaryStage.setTitle("ATM Machine");

        balanceLabel = new Label("Balance: Rs" + account.getBalance());
        amountField = new TextField();
        amountField.setPromptText("Enter amount");

        Button withdrawButton = new Button("Withdraw");
        Button depositButton = new Button("Deposit");
        Button checkBalanceButton = new Button("Check Balance");

        withdrawButton.setOnAction(e -> withdraw());
        depositButton.setOnAction(e -> deposit());
        checkBalanceButton.setOnAction(e -> checkBalance());

        messageLabel = new Label();

        VBox layout = new VBox(10, balanceLabel, amountField, withdrawButton, depositButton, checkBalanceButton, messageLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20,550,20,550));
        layout.setPrefSize(300, 250);

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

    private void withdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (account.withdraw(amount)) {
                messageLabel.setText("Withdrawal successful.");
            } else {
                messageLabel.setText("Insufficient balance or invalid amount.");
            }
            updateBalance();
        } catch (NumberFormatException e) {
            messageLabel.setText("Please enter a valid amount.");
        }
    }

    private void deposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            account.deposit(amount);
            messageLabel.setText("Deposit successful.");
            updateBalance();
        } catch (NumberFormatException e) {
            messageLabel.setText("Please enter a valid amount.");
        }
    }

    private void checkBalance() {
        updateBalance();
        messageLabel.setText("Balance checked.");
    }

    private void updateBalance() {
        balanceLabel.setText("Balance: Rs" + account.getBalance());
        amountField.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
