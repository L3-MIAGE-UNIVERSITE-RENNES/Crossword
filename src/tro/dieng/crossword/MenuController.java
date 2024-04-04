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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                int [] dim = getDimension(gridList.getSelectionModel().getSelectedItem());
                int heigh = 0;
                int width = 0;
                if(dim.length > 1){
                    heigh = dim[0];
                    width = dim[1];
                }
                playGame(e,"Crossword.fxml", heigh, width);
    }

    private void playGame(ActionEvent e, String urlFXML, int h, int w) {
        try {
            FXMLLoader loader = new FXMLLoader(MenuController.class.getResource(urlFXML));

            Parent root = (Parent) loader.load();
            Scene scene;
            if(h == 15 && w == 15){
                scene = new Scene(root,  750, 500);
            } else {
                scene = new Scene(root);
            }

            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            KeyCombination closeCombination = new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN);
            scene.setOnKeyPressed(event -> {
                if (closeCombination.match(event)) {
                    stage.close();
                }
            });
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
           ex.printStackTrace();
        }
    }
    private int[] getDimension(String texte){
        System.out.println(texte);
        Pattern pattern = Pattern.compile("(\\d+)x(\\d+)");

        Matcher matcher = pattern.matcher(texte);

        // Si aucune correspondance n'est trouvée, afficher un message
        if (!matcher.find()) {
            System.out.println("Le format du texte est incorrect.");
            return new int[]{};
        }

        int taille = Integer.parseInt(matcher.group(1));
        int largeur = Integer.parseInt(matcher.group(2));

        return new int[]{taille, largeur};
    }
}
