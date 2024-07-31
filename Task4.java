package codsoft.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class Task4 extends Application {

    private ComboBox<String> baseCurrencyComboBox;
    private ComboBox<String> targetCurrencyComboBox;
    private TextField amountField;
    private Label resultLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Currency Converter");

        baseCurrencyComboBox = new ComboBox<>();
        targetCurrencyComboBox = new ComboBox<>();
        amountField = new TextField();
        Button convertButton = new Button("Convert");
        resultLabel = new Label();

        baseCurrencyComboBox.getItems().addAll("USD", "EUR", "GBP", "INR");
        targetCurrencyComboBox.getItems().addAll("USD", "EUR", "GBP", "INR");
        baseCurrencyComboBox.setValue("USD");
        targetCurrencyComboBox.setValue("EUR");

        amountField.setPromptText("Amount");

        convertButton.setOnAction(e -> convertCurrency());

        HBox currencyBox = new HBox(10, baseCurrencyComboBox, new Label("to"), targetCurrencyComboBox);
        currencyBox.setAlignment(Pos.CENTER);
        
        VBox layout = new VBox(10, currencyBox, amountField, convertButton, resultLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20,550,20,550));
        layout.setPrefSize(300, 200);

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

    private void convertCurrency() {
        String baseCurrency = baseCurrencyComboBox.getValue();
        String targetCurrency = targetCurrencyComboBox.getValue();
        String amountText = amountField.getText();

        try {
            double amount = Double.parseDouble(amountText);
            double exchangeRate = fetchExchangeRate(baseCurrency, targetCurrency);

            if (exchangeRate != 0) {
                double convertedAmount = amount * exchangeRate;
                resultLabel.setText(String.format("%.2f %s", convertedAmount, targetCurrency));
            } else {
                resultLabel.setText("Unable to fetch exchange rate.");
            }
        } catch (NumberFormatException e) {
            resultLabel.setText("Please enter a valid amount.");
        }
    }

    private double fetchExchangeRate(String baseCurrency, String targetCurrency) {
        try {
            
            String apiUrl = String.format("https://api.exchangerate-api.com/v4/latest/%s", baseCurrency);
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                StringBuilder inline = new StringBuilder();
                Scanner scanner = new Scanner(connection.getInputStream());
                while (scanner.hasNext()) {
                    inline.append(scanner.nextLine());
                }
                scanner.close();

                JSONObject data = new JSONObject(inline.toString());
                JSONObject rates = data.getJSONObject("rates");
                return rates.getDouble(targetCurrency);
            }
        } catch (IOException | org.json.JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
