package tro.dieng.crossword;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;


public class CrosswordController implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    //@FXML
    // private ListView<String> listHorizontal;

    // @FXML
    // private ListView<String> listVertical;
    private GridPane grid;
    private int row;
    private int col;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Database database = new Database();
            Crossword  crossword = Crossword.createPuzzle(database, Main.choice + 1);

            // Configure grids
            this.configureGrids(crossword);

            // Configure listView
            // configureListView(crossword);

            // grid.getChildren().get(0).requestFocus();

            // Current direction configuration
            for (int i = 0; i < crossword.getHeight(); i++) {
                for (int j = 0; j < crossword.getWidth(); j++) {
                    CrosswordSquare square = crossword.getCell(i+1, j+1);
                    square.setOnKeyReleased((e) -> {
                        releaseKey(e, crossword);
                    });

                    int finalI = i + 1;
                    int finalJ = j + 1;
                    square.getPropostion().addListener((observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            if(crossword.isHorizontalDirection() && finalJ + 1 <= crossword.getWidth()){
                                crossword.getCell(finalI, finalJ +1).requestFocus();
                            } else if(!crossword.isHorizontalDirection() && finalI + 1 <= crossword.getHeight()) {
                                crossword.getCell(finalI + 1, finalJ).requestFocus();
                            }
                        }
                    });

                }
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    private void configureGrids(Crossword crossword){
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
                if(crossword.isBlackSquare(i+1, j+1)){
                    System.out.println("black");
                    square.setStyle("-fx-background-color: black");
                }
                grid.add(square, j, i);
            }
        }

        // Set the anchor constraints to center the content
        AnchorPane.setTopAnchor(grid, 30.0);
        AnchorPane.setBottomAnchor(grid, 50.0);
        AnchorPane.setLeftAnchor(grid, 100.0);
        AnchorPane.setRightAnchor(grid, 50.0);

        anchorPane.getChildren().add(grid);
    }

//    private void configureListView(Crossword crossword){
//        // Ajouter les indices horizontaux
//        for (Clue element : crossword.getHorizontalClues()) {
//            listHorizontal.getItems().add(element.getClue() + " ("+ element.getRow() + "," + element.getColumn() + ")");
//        }
//
//        // Ajouter les indices verticaux
//        for (Clue element : crossword.getVerticalClues()) {
//            listVertical.getItems().add(element.getClue() + " ("+ element.getRow() + "," + element.getColumn() + ")");
//        }
//    }

    private void advanceCursor(Crossword crossword, KeyCode keyCode) {
        int width = crossword.getHeight();
        int height = crossword.getHeight();

        switch (keyCode) {
            case LEFT:
                if (col == 0 && row == 0) {
                    col = width - 1;
                    row = height - 1;
                } else if (col == 0) {
                    row--;
                    col = width - 1;
                } else {
                    col--;
                }
                crossword.setHorizontalDirection(true);
                break;

            case RIGHT:
                if (col == width - 1 && row == height - 1) {
                    col = 0;
                    row = 0;
                } else if (col == width - 1) {
                    row++;
                    col = 0;
                } else {
                    col++;
                }
                crossword.setHorizontalDirection(true);
                break;

            case DOWN:
                if (row == height) {
                    row = 0;
                } else {
                    row++;
                    System.out.println(row);
                }
                crossword.setHorizontalDirection(false);
                break;

            case UP:
                if (row == 0) {
                    row = height - 1;
                } else {
                    row--;
                }
                crossword.setHorizontalDirection(false);
                break;

            default:
                return; // Exit if the keyCode is not handled
        }

        // Ensure the cursor moves to the next non-black cell
//        while (crossword.isBlackSquare(row + 1, col + 1)) {
//            switch (keyCode) {
//                case LEFT:
//                    if (col == 0 && row == 0) {
//                        col = width - 1;
//                        row = height - 1;
//                    } else if (col == 0) {
//                        row--;
//                        col = width - 1;
//                    } else {
//                        col--;
//                    }
//                    break;
//
//                case RIGHT:
//                    if (col == width - 1 && row == height - 1) {
//                        col = 0;
//                        row = 0;
//                    } else if (col == width - 1) {
//                        row++;
//                        col = 0;
//                    } else {
//                        col++;
//                    }
//                    break;
//
//                case DOWN:
//                    if (row == height - 1) {
//                        row = 0;
//                    } else {
//                        row++;
//                    }
//                    break;
//
//                case UP:
//                    if (row == 0) {
//                        row = height - 1;
//                    } else {
//                        row--;
//                    }
//                    break;
//            }
//        }

        // Update the TextField style and focus
        Label label = crossword.getCell(row, col);
        label.requestFocus();
    }

    @FXML
    public void releaseKey(KeyEvent event, Crossword crossword) {

        Label label = (Label) event.getSource();
        row = ((int) label.getProperties().get("gridpane-row")) + 1;
        col = ((int) label.getProperties().get("gridpane-column")) + 1;

        KeyCode eventKC = event.getCode();
        switch (eventKC) {
            case ENTER:
                // afficherCasesCorrectes();
                System.out.println("Case correct");
                break;

            case DOWN:
                advanceCursor(crossword,   KeyCode.DOWN);
                break;

            case UP:
                advanceCursor(crossword, KeyCode.UP);
                break;

            case LEFT:
                advanceCursor(crossword, KeyCode.LEFT);
                break;

            case BACK_SPACE:
                // Supprimer et faire reculer le curseur ( avancer vers la gauche)
                label.setText("");
                advanceCursor(crossword, KeyCode.LEFT);
                break;

            case RIGHT:
                advanceCursor(crossword, KeyCode.RIGHT);
                break;

            case TAB:
                break;

            // default:verifycharacters(tf,event,lig,col);
            default:

                break;

        }
    }

}
