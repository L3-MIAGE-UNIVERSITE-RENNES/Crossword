package tro.dieng.crossword;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

public class CrosswordSquare extends Label {

    private char solution;
    private StringProperty propostion;
    private String horizontale;
    private String verticale;
    private boolean statut;

    public CrosswordSquare(char solution, String proposition, String horizontale, String verticale, boolean statut) {
        this.solution = solution;
        this.propostion = new SimpleStringProperty(proposition);
        this.horizontale = horizontale;
        this.verticale = verticale;
        this.statut = statut;


        setPrefSize(30, 30);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(-10, 0, -10, 0));

        setOnMouseClicked(event -> requestFocus());
        setOnMouseEntered(event -> setCursor(Cursor.HAND));

        focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                setStyle("-fx-border-color: blue");
            } else {
                setStyle("-fx-border-color: NONE;");
            }
        });

        // Ajouter un écouteur d'événements pour les événements de clavier
        this.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            String text = event.getText();
            if (text.matches("[A-Za-zÀ-ÿ]")) {
                setText(text.toUpperCase());
                this.setPropostion(String.valueOf(text.toUpperCase().charAt(0)));
                this.getPropostion();
            } else {
                event.consume();
            }
        });

    }

    public CrosswordSquare() {
    }


    public char getSolution() {
        return solution;
    }

    public void setSolution(char solution) {
        this.solution = solution;
    }

    public StringProperty getPropostion() {
        return propostion;
    }



    public void setPropostion(String propostion) {
        this.propostion.set(propostion);
    }



    public String getHorizontale() {
        return horizontale;
    }



    public void setHorizontale(String horizontale) {
        this.horizontale = horizontale;
    }



    public String getVerticale() {
        return verticale;
    }



    public void setVerticale(String verticale) {
        this.verticale = verticale;
    }



    public boolean getStatut() {
        return statut;
    }



    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    private void handleMouseInteractions(int row, int column) {
        setOnMouseClicked(event -> handleSquareClick(row, column));
        setOnMouseEntered(event -> handleMouseOver(row, column));
        setOnMouseExited(event -> setStyle("-fx-background-color: NONE"));
    }

    private void handleSquareClick(int row, int column) {

    }

    private void handleMouseOver(int row, int column) {
    }


}
