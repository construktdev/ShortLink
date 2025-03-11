package de.construkter.shortlink;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;

public class ShortLinkApp extends Application {
    private final LinkService linkService = new LinkService();

    @Override
    public void start(Stage primaryStage) {
        // Input-Feld für den Link
        TextField linkInput = new TextField();
        linkInput.setPromptText("Gib deinen Link hier ein");
        linkInput.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        linkInput.setPrefWidth(350);  // Setzt die bevorzugte Breite für das Textfeld

        // Button zum Kürzen des Links
        Button submitButton = new Button("Link kürzen");
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        submitButton.setPrefWidth(350);  // Setzt die bevorzugte Breite für den Button
        submitButton.setOnAction(event -> {
            String originalLink = linkInput.getText();
            if (!originalLink.isEmpty() && originalLink.matches("https?://.*")) {
                String shortLink = linkService.uploadShortLink(originalLink);
                String finalLink = shortLink + ".html";
                showSuccess(finalLink);
                copyToClipboard(finalLink);
            } else {
                showError("Bitte gib einen gültigen Link ein.");
            }
        });

        // Resultatfeld, das den gekürzten Link anzeigt
        Label resultField = new Label();
        resultField.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        // Layout des Fensters
        VBox layout = new VBox(15, linkInput, submitButton, resultField);
        layout.setPadding(new Insets(15));
        layout.setStyle("-fx-background-color: #f4f4f9;");

        // Szene und Stage
        Scene scene = new Scene(layout, 380, 200);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setTitle("Link kürzer machen Maschine");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showSuccess(String shortLink) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Link gekürzt");
        alert.setHeaderText(null);
        alert.setContentText("Dein gekürzter Link lautet: " + shortLink);
        alert.showAndWait();
    }

    private void showError(String errorMessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    private void copyToClipboard(String text) {
        StringSelection selection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
