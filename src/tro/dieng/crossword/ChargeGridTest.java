package tro.dieng.crossword;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChargeGridTest {
    Database grids;
    Crossword crossword;
    @BeforeEach
    void setUp(){
        grids = new Database();
    }

    @Test
    void test() throws SQLException {
        Crossword cross = Crossword.createPuzzle(grids, 10);
        char[][] grille = {
                {'A', 'R', 'C', 'H', 'E', 'R'},
                {'D', 'O', 'U', 'A', 'N', 'E'},
                {'J', 'U', 'I', 'N', ' ', 'B'},
                {'O', 'N', 'T', ' ', 'V', 'O'},
                {'I', 'D', ' ', 'G', 'I', 'N'},
                {'N', ' ', 'R', 'E', 'N', 'D'},
                {'T', 'I', 'F', 'O', 'S', 'I'}
        };

        // Boucle pour itérer sur chaque ligne de la grille
        for (int i = 0; i < grille.length; i++) {
            // Boucle pour itérer sur chaque colonne de la ligne courante
            for (int j = 0; j < grille[i].length; j++) {
                // Appeler la méthode setProposition avec les indices de ligne et de colonne
                // et la valeur de la cellule courante
                cross.setProposition(i + 1, j + 1, grille[i][j]);
            }
        }

        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[i].length; j++) {
                char s = cross.getSolution(i+1, j+1);
                char p = cross.getProposition(i + 1, j + 1);
                assertEquals(s, p);
            }
        }

    }
}
