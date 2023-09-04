package biglietteria;



import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        // variabili in cui salvo i parametri di connessione al db
        String url = "jdbc:mysql://localhost:3306/biglietteria";
        String user = "root";
        String password = "root";
        //mostra tratte
        mostraTratte(scan, url, user, password);
        // richiesta all' utente
        System.out.println("Inserici la destinazione di partenza : ");
        String partenza = scan.nextLine();

        System.out.println("Inserici la destinazione di arrivo : ");
        String arrivo = scan.nextLine();

        // controllo tratta
        if (valutaPresenza(partenza, arrivo, url, user, password)) {
            System.out.println("tratta presente !! ");
            System.out.println("Inserici la tua età : ");
            int costumerAge = scan.nextInt();
            System.out.print("Il biglietto è flessibile? (true/false): ");
            boolean isFlessibile = scan.nextBoolean();

            // dettagli biglietto
            System.out.println("il prezzo del biglietto da "+ partenza +" a "+arrivo+" è di "+ticketDetails(partenza, arrivo,costumerAge,isFlessibile, url, user, password)+" €");

        }else{
            System.out.println("tratta non presente !");
        }

    }

    // mostra tratte
    public static void mostraTratte(Scanner scan, String url, String user, String password) {
        // preparo la variabile che contiene la connessione

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            String sql = "select * from tratte";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                // prima di eseguire il prepared statement faccio il binding dei parametri
                //ps.setString(1, "%"+search+"%"); // il primo parametro (1) vale quello che c'è nella variabile search

                // eseguo la query e la inserisco in un oggetto ResultSet
                try (ResultSet rs = ps.executeQuery()) {
                    // posso iterare sul result set
                    while (rs.next()) {
                        String id = rs.getString("id");
                        String partenza = rs.getString("partenza"); // o indice della colonna  o nome della colonna
                        String arrivo = rs.getString("arrivo");
                        String km = rs.getString("km");
                        System.out.println(id + ". " + partenza + " - " + arrivo + " (" + km + " Km)");
                    }
                }
            }


        } catch (SQLException e) {
            System.out.println("OOPS an error occurred");
        }
    }

    // valuta presenza
    public static boolean valutaPresenza(String start, String end, String url, String user, String password) {
        boolean flag = false;
        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            String sql = "select count(*) from tratte where partenza = ? and arrivo = ? ";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                // prima di eseguire il prepared statement faccio il binding dei parametri
                ps.setString(1, start);
                ps.setString(2, end);
                // eseguo la query e la inserisco in un oggetto ResultSet
                try (ResultSet rs = ps.executeQuery()) {
                    // posso iterare sul result set
                    while (rs.next()) {
                        int count = rs.getInt(1);
                        if (count > 0) {
                            flag = true;
                        }

                    }
                }
            }


        } catch (SQLException e) {
            System.out.println("OOPS an error occurred");
        }

        return flag;
    }
    // calcola biglietto

    public static BigDecimal ticketDetails(String start, String end, int costumerAge , boolean isFlessible, String url, String user, String password) {
        BigDecimal finalPrice = BigDecimal.ZERO;
        int totalKm=0;
        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            String sql = "select km from tratte where partenza = ? and arrivo = ? ";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                // prima di eseguire il prepared statement faccio il binding dei parametri
                ps.setString(1, start);
                ps.setString(2, end);
                // eseguo la query e la inserisco in un oggetto ResultSet
                try (ResultSet rs = ps.executeQuery()) {
                    // posso iterare sul result set
                    while (rs.next()) {
                        totalKm = rs.getInt(1);

                    }
                }
            }


        } catch (SQLException e) {
            System.out.println("OOPS an error occurred");
        }
        Biglietto ticket= new Biglietto(totalKm,costumerAge,isFlessible);
        finalPrice=ticket.calcolaPrezzo(costumerAge,totalKm);
        return finalPrice;
    }
}