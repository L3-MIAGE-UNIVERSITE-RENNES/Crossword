package tro.dieng.crossword;


import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CrosswordSquare extends Label {

    private char solution;
    private char propostion;
    private String horizontale;
    private String verticale;
    private boolean statut;

    public CrosswordSquare(char solution, char propostion, String horizontale, String verticale, boolean statut) {
        this.solution = solution;
        this.propostion = propostion;
        this.horizontale = horizontale;
        this.verticale = verticale;
        this.statut = statut;
    }

    public CrosswordSquare() {
    }


    public char getSolution() {
        return solution;
    }

    public void setSolution(char solution) {
        this.solution = solution;
    }

    public char getPropostion() {
        return propostion;
    }



    public void setPropostion(char propostion) {
        this.propostion = propostion;
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
}
