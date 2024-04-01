package tro.dieng.crossword;


import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private Connection connexion;
    public Database(){
        try {
            connexion = connecterBD();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Connection getConnexion() {
        return connexion;
    }

    public  static Connection connecterBD() throws SQLException {
        Connection connect;
        String url = "jdbc:mysql://localhost:8889/crossword";
        String username = "root";
        String password = "root";
        connect = DriverManager.getConnection(url, username, password);
        return connect;

    }

    public Map<Integer, String> availableGrids() throws SQLException {
        Map<Integer, String> grids  = grids = new HashMap<>();
        String query = "SELECT * FROM GRID";
        Statement statement = connexion.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            String nomGrille = rs.getString("nom_grille");
            Integer numeroGrille = rs.getInt("numero_grille");
            int hauteur = rs.getInt("hauteur");
            int largeur = rs.getInt("largeur");
            grids.put(numeroGrille, nomGrille + " ("+ hauteur + "x" + largeur +")");
        }
        return grids;
    }

}
