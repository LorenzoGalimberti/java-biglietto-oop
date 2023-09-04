/*
Creare una classe Biglietteria, che contiene il metodo main in cui:

    ● chiedere all’utente di inserire il numero di km e l’età del passeggero
    ● con quei dati provare a creare un nuovo Biglietto (gestire eventuali eccezioni con try-catch)
    ● stampare a video il prezzo del biglietto calcolato
* */

package biglietteria;
import biglietteria.Biglietto;

import java.util.Scanner;
import java.math.BigDecimal;
public class Biglietteria {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        try {
            System.out.print("Inserisci il kilometraggio della tratta : ");
            int chilometraggio = scan.nextInt();

            System.out.print("Inserisci l'età del passeggero : ");
            int etaPasseggero = scan.nextInt();
            Biglietto biglietto = new Biglietto(chilometraggio, etaPasseggero);

            // Calcola il prezzo del biglietto chiamando il metodo calcolaPrezzo
            BigDecimal prezzo = biglietto.calcolaPrezzo(etaPasseggero,chilometraggio);

            // Stampa il prezzo a video
            System.out.println("Hai prenotato un biglietto di " + chilometraggio + " chilometri per un passeggero di età " + etaPasseggero);
            System.out.println("Il prezzo del biglietto è: " + prezzo);

            //System.out.println("il prezzo finale del biglietto è " +)
        }catch (IllegalArgumentException e){
            System.out.println("Errore durante la prenotazione: " + e.getMessage());
        }
    }
}
