package Controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;

public class Traitement {
    private int id; // Add a field to store the ID
    private final SimpleStringProperty medicament;
    private final SimpleStringProperty periodeTraitement;
    private final SimpleObjectProperty<LocalDate> startDate;
    private final SimpleObjectProperty<LocalDate> endDate;

    public Traitement(int id, String medicament, String periodeTraitement, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.medicament = new SimpleStringProperty(medicament);
        this.periodeTraitement = new SimpleStringProperty(periodeTraitement);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.endDate = new SimpleObjectProperty<>(endDate);
    }

    // Getter and Setter for ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public SimpleStringProperty getMedicament() {
        return medicament;
    }

    // Setter method for medicament
    public void setMedicament(String medicament) {
        this.medicament.set(medicament);
    }



    public void setStartDate(LocalDate startDate) {
        this.startDate.set(startDate);
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate.set(endDate);
    }

    public void setPeriodeTraitement(String periodeTraitement) {
        this.periodeTraitement.set(periodeTraitement);
    }
    public SimpleStringProperty medicamentProperty() {
        return medicament;
    }

    public String getPeriodeTraitement() {
        return periodeTraitement.get();
    }

    public SimpleStringProperty periodeTraitementProperty() {
        return periodeTraitement;
    }

    public LocalDate getStartDate() {
        return startDate.get();
    }

    public SimpleObjectProperty<LocalDate> startDateProperty() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate.get();
    }

    public SimpleObjectProperty<LocalDate> endDateProperty() {
        return endDate;
    }
}
