package Controller;

import javafx.beans.property.SimpleStringProperty;

public class Pharmacy {
    private int id; // Add a field to store the ID
    private final SimpleStringProperty medicament;
    private final SimpleStringProperty quantity;

    public Pharmacy(int id, String medicament, String quantity) {
        this.id = id;
        this.medicament = new SimpleStringProperty(medicament);
        this.quantity = new SimpleStringProperty(quantity);
    }

    // Getter and Setter for ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity.get();
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }

    public String getMedicament() {
        return medicament.get();
    }

    public void setMedicament(String medicament) {
        this.medicament.set(medicament);
    }

    public SimpleStringProperty medicamentProperty() {
        return medicament;
    }

    public SimpleStringProperty quantityProperty() {
        return quantity;
    }
}
