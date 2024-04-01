package tro.dieng.crossword;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.net.URL;
import java.util.ResourceBundle;


public class CrosswordController implements Initializable {
    @FXML
    private AnchorPane anchorPane;
    private GridPane grid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Configure grids
            Database database = new Database();
            Crossword  crossword = Crossword.createPuzzle(database, Main.choice + 1);
            grid = new GridPane();
            grid.setPrefHeight(crossword.getHeight());
            grid.setPrefWidth(crossword.getWidth());

            for (int i = 0; i < crossword.getHeight(); i++) {
                grid.getRowConstraints().add(new RowConstraints(30));
            }

            for (int j = 0; j < crossword.getWidth(); j++) {
                grid.getColumnConstraints().add(new ColumnConstraints(30));
            }
            grid.setGridLinesVisible(true);

            for (int i = 0; i < crossword.getHeight(); i++) {
                for (int j = 0; j < crossword.getWidth(); j++) {
                    CrosswordSquare square = crossword.getCell(i+1, j+1);
                    grid.add(square, j, i);
                }
            }
            // Set the anchor constraints to center the content
            AnchorPane.setTopAnchor(grid, 30.0);
            AnchorPane.setBottomAnchor(grid, 50.0);
            AnchorPane.setLeftAnchor(grid, 100.0);
            AnchorPane.setRightAnchor(grid, 50.0);
            anchorPane.getChildren().add(grid);
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
