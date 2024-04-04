package tro.dieng.crossword;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private ChoiceBox<String> gridList;

    @FXML
    private Button play;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Database chargeGrid = new Database();
        try {
            Map<Integer, String> grids = chargeGrid.availableGrids();
            ObservableList<String> availableGrids = FXCollections.observableArrayList(grids.values());
            gridList.getItems().addAll(availableGrids);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void play(ActionEvent e) {
                Main.choice = gridList.getSelectionModel().getSelectedIndex();
                playGame(e,"Crossword.fxml");
    }

    public void playGame(ActionEvent e, String urlFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(MenuController.class.getResource(urlFXML));

            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
