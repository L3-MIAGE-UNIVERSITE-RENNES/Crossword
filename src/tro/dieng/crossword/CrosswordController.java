package tro.dieng.crossword;


import javafx.animation.ScaleTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.util.Callback;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;


public class CrosswordController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ListView<String> listHorizontal;

    @FXML
    private ListView<String> listVertical;
    private GridPane grid;
    private int row;
    private int col;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Database database = new Database();
            Crossword  crossword = Crossword.createPuzzle(database, Main.choice + 1);

            configureGrids(crossword);

            configureListView(crossword);

            configureCurrentDirection(crossword);

            selectClue(crossword);

            displayLetters(crossword);

            updateSelectedClueColor(listHorizontal);
            updateSelectedClueColor(listVertical);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updateSelectedClueColor(ListView<String> list){
        list.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item);

                        if (isSelected()) {
                            setStyle("-fx-background-color: red;");
                        } else {
                            setStyle("");
                        }
                    }
                };
            }
        });
    }

    private void selectClue(Crossword crossword){
        for (int i = 1; i <= crossword.getHeight(); i++) {
            for (int j = 1; j <= crossword.getWidth(); j++) {
                CrosswordSquare square = crossword.getCell(i, j);
                int i1 = i;
                int j2 = j;
                square.focusedProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue) {
                        String t = listHorizontal.getItems().get(i1-1);
                        listHorizontal.scrollTo(i1-1);
                        listHorizontal.getSelectionModel().select(i1-1);
                        listVertical.scrollTo(j2-1);
                        listVertical.getSelectionModel().select(j2-1);
                        this.clueDelimiter(t, crossword);
                    }
                });
            }
        }
    }

    private void configureCurrentDirection(Crossword crossword){
        for (int i = 1; i <= crossword.getHeight(); i++) {
            for (int j = 1; j <= crossword.getWidth(); j++) {
                CrosswordSquare square = crossword.getCell(i, j);
                square.setOnKeyReleased((e) -> {
                    releaseKey(e, crossword);
                });
                int finalI = i;
                int finalJ = j;
                square.getPropostion().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        if(crossword.isHorizontalDirection() && finalJ<= crossword.getWidth() && finalJ + 1 <= crossword.getWidth() && !crossword.isBlackSquare(finalI , finalJ+1)){
                            crossword.getCell(finalI, finalJ +1).requestFocus();
                        } else if(!crossword.isHorizontalDirection() && finalI<= crossword.getHeight() && finalI +1 <= crossword.getHeight() && !crossword.isBlackSquare(finalI+1, finalJ)) {
                            crossword.getCell(finalI + 1, finalJ).requestFocus();
                        }
                    }
                });

            }
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
                    square.setStyle("-fx-background-color: black");
                }

                grid.add(square, j, i);
            }
        }

        // Set the anchor constraints to center the content
        if(crossword.getHeight() >= 15) {
            AnchorPane.setTopAnchor(grid, 20.0);
            AnchorPane.setLeftAnchor(grid, 20.0);
            AnchorPane.setBottomAnchor(grid, 20.0);
            AnchorPane.setRightAnchor(grid, 20.0);
        } else {
            AnchorPane.setTopAnchor(grid, 50.0);
            AnchorPane.setLeftAnchor(grid, 200.0);
            AnchorPane.setBottomAnchor(grid, 50.0);
            AnchorPane.setRightAnchor(grid, 50.0);
        }


        anchorPane.getChildren().add(grid);
    }

    private void configureListView(Crossword crossword){
        // Ajouter les indices horizontaux
        for (Clue element : crossword.getHorizontalClues()) {
            listHorizontal.getItems().add(element.getClue() + " ("+ element.getRow() + "," + element.getColumn() + ")");
        }

        // Ajouter les indices verticaux
        for (Clue element : crossword.getVerticalClues()) {
            listVertical.getItems().add(element.getClue() + " ("+ element.getRow() + "," + element.getColumn() + ")");
        }
    }

    private void advanceCursor(Crossword crossword, KeyCode keyCode) {
        int width = crossword.getWidth();
        int height = crossword.getHeight();

        switch (keyCode) {
            case LEFT:
                if(col != 1){
                    col--;
                }
                crossword.setHorizontalDirection(true);
                break;

            case RIGHT:
                if (col != width ) {
                    col++;
                }
                crossword.setHorizontalDirection(true);
                break;

            case DOWN:
                if (row != height) {
                    row++;
                }
                crossword.setHorizontalDirection(false);
                break;

            case UP:
                if (row != 1) {
                    row--;
                }
                crossword.setHorizontalDirection(false);
                break;

            default:
                return;
        }

        // Ensure the cursor moves to the next non-black cell
        while (crossword.isBlackSquare(row, col)) {
            switch (keyCode) {
                case LEFT:
                    if (col != 1 ) {
                        col--;
                    }
                    break;

                case RIGHT:
                    if (col != width) {
                        col++;
                    }
                    break;

                case DOWN:
                    if (row != height) {
                        row = 1;
                    }
                    break;

                case UP:
                    if (row != 1) {
                        row--;
                    }
                    break;
            }
        }

        // set focus on the next label
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
                displayCorrectLetters(crossword);
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
                label.setText("");
                advanceCursor(crossword, KeyCode.LEFT);
                break;

            case RIGHT:
                advanceCursor(crossword, KeyCode.RIGHT);
                break;

            case TAB:
                break;
            default:

                break;

        }
    }

    private void displayCorrectLetters(Crossword crossword){
        for (int i = 0; i < crossword.getHeight(); i++) {
            for (int j = 0; j < crossword.getWidth(); j++) {
                CrosswordSquare square = crossword.getCell(i+1, j+1);
                if(!crossword.isBlackSquare(i+1, j+1) && square.getSolution() == square.getPropostion().get().charAt(0)){
                    square.setStyle("-fx-background-color: green; -fx-border-color: black; -fx-border-width: 0.5;");
                }
            }
        }
    }

    private void clueDelimiter(String clue, Crossword crossword){
        for (Clue element : crossword.getHorizontalClues()) {
            if(!clue.startsWith(clue) && element.getRow() == 1){
                System.out.println("lig: " + 1 + " "+ element.getClue());
            }
        }
    }

    private void displayLetters(Crossword crossword){
        for (int i = 0; i < crossword.getHeight(); i++) {
            for (int j = 0; j < crossword.getWidth(); j++) {
                CrosswordSquare square = crossword.getCell(i+1, j+1);
                square.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(500));
                        scaleTransition.setFromX(0);
                        scaleTransition.setToX(1);
                        scaleTransition.setFromY(0);
                        scaleTransition.setToY(1);
                        scaleTransition.setNode(square);
                        scaleTransition.play();
                    }
                });
            }
        }
    }

}
