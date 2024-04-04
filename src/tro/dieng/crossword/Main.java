package tro.dieng.crossword;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application {
    public static int choice;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        // Create a scene with the text node as content
        Scene scene = new Scene(root);

        // Set the stage title and scene
        stage.setTitle("Crossword Puzzle");
        stage.setResizable(false);
        stage.setScene(scene);
        KeyCombination closeCombination = new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN);
        scene.setOnKeyPressed(event -> {
            if (closeCombination.match(event)) {
                stage.close();
            }
        });
        // Show the stage
        stage.show();

    }

}
