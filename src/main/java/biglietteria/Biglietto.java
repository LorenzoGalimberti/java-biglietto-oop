package biglietteria;
import java.math.BigDecimal;
public class Biglietto {
    // ATTRIBUTI
    private int kilometers;
    private int passengerAge;

    // Definisci le costanti
    private static final BigDecimal COSTO_CHILOMETRICO = new BigDecimal("0.21");
    private static final BigDecimal SCONTO_OVER_65 = new BigDecimal("0.40");
    private static final BigDecimal SCONTO_MINORENNI = new BigDecimal("0.20");

    // COSTRUTTORE

    public Biglietto(int kilometers, int passengerAge) throws IllegalArgumentException,RuntimeException{
        // validazione parametri
        if (!isValidKm(kilometers) || !isValidEta(passengerAge)){
            throw new IllegalArgumentException("kilometri  ed età del passeggero devono essere valori  positivi !");
        }



        this.kilometers = kilometers;
        this.passengerAge = passengerAge;
    }

    // GET AND SET

    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    public int getPassengerAge() {
        return passengerAge;
    }

    public void setPassengerAge(int passengerAge) {
        this.passengerAge = passengerAge;
    }

    // METODI
    // età positivq
    private boolean isValidEta(int age){
        boolean flag= false;
        if (age>=0){
            flag=true;
        }
        return flag;
    }
    // kiloetraggio positivo
    private boolean isValidKm(int km){
        boolean flag= false;
        if (km>=0){
            flag=true;
        }
        return flag;
    }
    // calcola biglietto
    public BigDecimal calcolaPrezzo(int age, int km){
        // Calcola il costo chilometrico senza sconti
        BigDecimal costoKm = new BigDecimal("0.21");
        BigDecimal prezzoSenzaSconto = costoKm.multiply(new BigDecimal(km));

        // Calcola lo sconto
        BigDecimal scontoCalcolato = calcolaSconto(age);

        // Applica lo sconto al prezzo senza sconto
        BigDecimal prezzoConSconto = prezzoSenzaSconto.subtract(prezzoSenzaSconto.multiply(scontoCalcolato));
        // Limita il risultato a due cifre decimali
        prezzoConSconto = prezzoConSconto.setScale(2, BigDecimal.ROUND_HALF_UP);
        return prezzoConSconto;
    }

    // calcola sconto
    private BigDecimal calcolaSconto(int age){
        BigDecimal sconto=BigDecimal.ZERO;
        if (age>65){
            sconto = new BigDecimal("0.4");
        } else if (age<18) {
            sconto = new BigDecimal("0.2");
        }
        return sconto;
    }
}
