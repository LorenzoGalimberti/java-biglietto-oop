
package biglietteria;
import biglietteria.Biglietto;

import java.util.Scanner;
import java.math.BigDecimal;
import java.time.LocalDate;
public class Biglietteria {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        try {
            System.out.print("Inserisci il kilometraggio della tratta : ");
            int chilometraggio = scan.nextInt();

            System.out.print("Inserisci l'età del passeggero : ");
            int etaPasseggero = scan.nextInt();
            System.out.print("Il biglietto è flessibile? (true/false): ");
            boolean isFlessibile = scan.nextBoolean();

            scan.close();
            Biglietto biglietto = new Biglietto(chilometraggio, etaPasseggero,isFlessibile);

            // Calcola il prezzo del biglietto
            BigDecimal prezzo = biglietto.calcolaPrezzo(etaPasseggero,chilometraggio);

            // info biglietto
            System.out.println("Hai prenotato un biglietto di " + chilometraggio + " chilometri per un passeggero di età " + etaPasseggero);
            System.out.println("Il prezzo del biglietto è: " + prezzo);

            //data di scadenza
            LocalDate dataScadenza = biglietto.calcolaDataScadenza();
            System.out.println("La data di scadenza è: " + dataScadenza);

        }catch (IllegalArgumentException e){
            System.out.println("Errore durante la prenotazione: " + e.getMessage());
        }
    }
}
