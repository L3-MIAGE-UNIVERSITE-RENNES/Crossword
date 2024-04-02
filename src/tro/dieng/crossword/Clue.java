package tro.dieng.crossword;

public class Clue {
    private String clue;
    private int row;
    private int column;
    private boolean horizontal;

    public Clue(String clue, int row, int column, boolean horizontal){
        this.clue = clue;
        this.row = row;
        this.column = column;
        this.horizontal = horizontal;
    }
    public String getClue(){
        return this.clue;
    }
    public int getRow(){
        return this.row;
    }

    public int getColumn(){
        return this.column;
    }


    @Override
    public String toString(){
        return clue;
    }
}
