package tro.dieng.crossword;




import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

public class CrosswordSquare extends Label {

    private Character solution;
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

        setProperties();
        handleEvent();


    }


    public Character getSolution() {
        return solution;
    }

    public void setSolution(Character solution) {
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

    private void setProperties(){
        setPrefSize(30, 30);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(-10, 0, -10, 0));
    }

    private void  handleEvent(){
        setOnMouseClicked(event -> {
            if(!isBlack()) {
                requestFocus();
            }
        });

        setOnMouseEntered(event -> setCursor(Cursor.HAND));

        focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && !isBlack() ) {
                if(this.solution == this.propostion.get().charAt(0)){
                    setStyle("-fx-background-color: green; -fx-border-color: blue; -fx-border-width: 0.5;");
                } else {
                    setStyle("-fx-border-color: blue");
                }

            } else if(oldValue && !isBlack()) {
                setStyle("-fx-border-color: NONE;");
            }
        });

        this.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if(!isBlack()){
                String text = event.getText();
                if (text.matches("[A-Za-z]")) {
                    setText(text.toUpperCase());
                    this.setPropostion(String.valueOf(text.toUpperCase().charAt(0)));
                    this.getPropostion();
                } else {
                    event.consume();
                }
            }
        });
    }

    private boolean isBlack(){
        return this.solution == null;
    }




}
