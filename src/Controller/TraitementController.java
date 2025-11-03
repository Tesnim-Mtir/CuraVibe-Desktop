package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.converter.LocalDateStringConverter;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TraitementController implements Initializable {

    @FXML
    private ChoiceBox<String> choiceBoxMedicament;
    @FXML
    private TextField periodeTraitementTextField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TableView<Traitement> tableViewTraitement;
    @FXML
    private TableColumn<Traitement, String> medicamentTableColumn;
    @FXML
    private TableColumn<Traitement, String> periodeTableColumn;
    @FXML
    private TableColumn<Traitement, LocalDate> startDateTableColumn;
    @FXML
    private TableColumn<Traitement, LocalDate> endDateTableColumn;



    private ObservableList<Traitement> traitementList;
    private ObservableList<String> medicamentList;

    public void handlePharmacieButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/pharmacy.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleAllergieButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/allergies.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Deconnexion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleProfileButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Profil.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleUserButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/traitement.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleDonButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/add.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleRecycleButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/historiqueRecycle.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteTraitement(ActionEvent event) {
        // Get the selected item from the TableView
        Traitement selectedItem = tableViewTraitement.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            // Remove the selected item from the TableView
            tableViewTraitement.getItems().remove(selectedItem);

            // Delete the item from the database based on its ID
            deleteTraitementFromDatabase(selectedItem.getId());
        }
    }

    private void deleteTraitementFromDatabase(int traitementId) {
        Connection connection = Db.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM traitments WHERE id = ?");
            ps.setInt(1, traitementId);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Traitement with ID " + traitementId + " deleted from the database.");
            } else {
                System.out.println("Failed to delete traitement with ID " + traitementId + " from the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addTraitement(ActionEvent event) {
        String selectedMedicament = choiceBoxMedicament.getValue();
        String periodeTraitement = periodeTraitementTextField.getText();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (selectedMedicament != null && !selectedMedicament.isEmpty() &&
                periodeTraitement != null && !periodeTraitement.isEmpty() &&
                startDate != null && endDate != null) {
            Traitement traitement = new Traitement(0, selectedMedicament, periodeTraitement, startDate, endDate);
            traitementList.add(traitement);

            // You may want to save the treatment data to the database here

            // Clear input fields
            choiceBoxMedicament.setValue(null);
            periodeTraitementTextField.clear();
            startDatePicker.setValue(null);
            endDatePicker.setValue(null);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        medicamentTableColumn.setCellValueFactory(cellData -> cellData.getValue().medicamentProperty());
        periodeTableColumn.setCellValueFactory(cellData -> cellData.getValue().periodeTraitementProperty());
        startDateTableColumn.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
        endDateTableColumn.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());

        traitementList = FXCollections.observableArrayList();
        tableViewTraitement.setItems(traitementList);

        loadMedicaments();
        loadExistingTreatments();
        editTraitementData();
    }

    private void loadExistingTreatments() {
        Connection connection = Db.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT t.id, m.name AS medicament_name, t.periode_traitment, t.start_date, t.end_date\n" +
                    "FROM traitments t " +
                    "JOIN medicaments m ON t.medicament_id = m.id");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String medicament = rs.getString("medicament_name");
                String periodeTraitement = rs.getString("periode_traitment");

                // Check for null values before converting to LocalDate
                LocalDate startDate = rs.getDate("start_date") != null ? rs.getDate("start_date").toLocalDate() : null;
                LocalDate endDate = rs.getDate("end_date") != null ? rs.getDate("end_date").toLocalDate() : null;

                Traitement traitement = new Traitement(id, medicament, periodeTraitement, startDate, endDate);
                traitementList.add(traitement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void loadMedicaments() {
        Connection connection = Db.getConnection();
        medicamentList = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT name FROM medicaments");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                medicamentList.add(rs.getString("name"));
            }

            choiceBoxMedicament.setItems(medicamentList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void editTraitementData() {
        periodeTableColumn.setCellFactory(TextFieldTableCell.<Traitement>forTableColumn());
        periodeTableColumn.setOnEditCommit(event -> {
            Traitement traitement = event.getTableView().getItems().get(event.getTablePosition().getRow());
            String newPeriodeTraitement = event.getNewValue();
            int traitementId = traitement.getId();
            traitement.setPeriodeTraitement(newPeriodeTraitement);

            // Update the database
            updateTraitementInDatabase(traitementId, newPeriodeTraitement, null, null);
        });

        startDateTableColumn.setCellFactory(TextFieldTableCell.<Traitement, LocalDate>forTableColumn(new LocalDateStringConverter()));
        startDateTableColumn.setOnEditCommit(event -> {
            Traitement traitement = event.getTableView().getItems().get(event.getTablePosition().getRow());
            LocalDate newStartDate = event.getNewValue();
            int traitementId = traitement.getId();
            traitement.setStartDate(newStartDate);

            // Update the database
            updateTraitementInDatabase(traitementId, null, newStartDate, null);
        });

        endDateTableColumn.setCellFactory(TextFieldTableCell.<Traitement, LocalDate>forTableColumn(new LocalDateStringConverter()));
        endDateTableColumn.setOnEditCommit(event -> {
            Traitement traitement = event.getTableView().getItems().get(event.getTablePosition().getRow());
            LocalDate newEndDate = event.getNewValue();
            int traitementId = traitement.getId();
            traitement.setEndDate(newEndDate);

            // Update the database
            updateTraitementInDatabase(traitementId, null, null, newEndDate);
        });
    }
    private void updateTraitementInDatabase(int traitementId, String newPeriodeTraitement, LocalDate newStartDate, LocalDate newEndDate) {
        Connection connection = Db.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE traitments SET periode_traitment = ?, start_date = ?, end_date = ? WHERE id = ?");
            ps.setString(1, newPeriodeTraitement);
            if (newStartDate != null) {
                ps.setDate(2, java.sql.Date.valueOf(newStartDate));
            } else {
                ps.setNull(2, Types.DATE);
            }
            if (newEndDate != null) {
                ps.setDate(3, java.sql.Date.valueOf(newEndDate));
            } else {
                ps.setNull(3, Types.DATE);
            }
            ps.setInt(4, traitementId);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Traitement with ID " + traitementId + " updated in the database.");
            } else {
                System.out.println("Failed to update traitement with ID " + traitementId + " in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
