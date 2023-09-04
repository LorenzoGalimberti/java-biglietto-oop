package biglietteria;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Biglietto {
    // ATTRIBUTI
    private int kilometers;
    private int passengerAge;
    private LocalDate data;
    private boolean flessibile;
    //  costanti
    private static final BigDecimal COSTO_CHILOMETRICO = new BigDecimal("0.21");
    private static final BigDecimal SCONTO_OVER_65 = new BigDecimal("0.40");
    private static final BigDecimal SCONTO_MINORENNI = new BigDecimal("0.20");
    private static final int DURATA_NORMALE = 30;
    private static final int DURATA_FLESSIBILE = 90;

    // COSTRUTTORE

    public Biglietto(int kilometers, int passengerAge, boolean flessibile) throws IllegalArgumentException,RuntimeException{
        this.data=LocalDate.now();
        // validazione parametri
        if (!isValidKm(kilometers) || !isValidEta(passengerAge)){
            throw new IllegalArgumentException("kilometri  ed età del passeggero devono essere valori  positivi !");
        }



        this.kilometers = kilometers;
        this.passengerAge = passengerAge;

        this.flessibile=flessibile;
    }

    // GET AND SET

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public boolean isFlessibile() {
        return flessibile;
    }

    public void setFlessibile(boolean flessibile) {
        this.flessibile = flessibile;
    }

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
    // calcola data di scadenza

    public LocalDate calcolaDataScadenza() {
        LocalDate dataScadenza = data;

        if (flessibile) {
            dataScadenza = data.plusDays(DURATA_FLESSIBILE);
        } else {
            dataScadenza = data.plusDays(DURATA_NORMALE);
        }

        return dataScadenza;
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
        // bilgietto flessibile
        if (flessibile) {
            prezzoConSconto = prezzoConSconto.multiply(new BigDecimal("1.10"));
        }
        // due cifre decimali
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
