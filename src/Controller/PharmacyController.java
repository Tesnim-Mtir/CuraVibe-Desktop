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
public class PharmacyController implements Initializable {

    @FXML
    private ChoiceBox<String> choiceBoxMedicament;
    @FXML
    private TextField quantityTextField;
    @FXML
    private TableView<Pharmacy> tableViewStock;
    @FXML
    private TableColumn<Pharmacy, String> medicamentTableColumn;
    @FXML
    private TableColumn<Pharmacy, String> quantityTableColumn;

    private ObservableList<Pharmacy> stockList;
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

    // Other methods...

    @FXML
    private void deleteStock(ActionEvent event) {
        // Get the selected item from the TableView
        Pharmacy selectedItem = tableViewStock.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            // Remove the selected item from the TableView
            tableViewStock.getItems().remove(selectedItem);

            // Delete the item from the database based on its ID
            deleteStockFromDatabase(selectedItem.getId());
        }
    }


    private void deleteStockFromDatabase(int stockId) {
        Connection connection = Db.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM stocks WHERE id = ?");
            ps.setInt(1, stockId);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Stock with ID " + stockId + " deleted from the database.");
            } else {
                System.out.println("Failed to delete stock with ID " + stockId + " from the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void addStock(ActionEvent event) {
        String selectedMedicament = choiceBoxMedicament.getValue();
        String quantity = quantityTextField.getText();
        if (selectedMedicament != null && !selectedMedicament.isEmpty() &&
                quantity != null && !quantity.isEmpty()) {
            Pharmacy pharmacy = new Pharmacy(0, selectedMedicament, quantity);
            stockList.add(pharmacy);
            insertStockIntoDatabase(selectedMedicament, quantity);
            choiceBoxMedicament.setValue(null);
            quantityTextField.clear();
        }
    }

    private void insertStockIntoDatabase(String medicament, String quantity) {
        Connection connection = Db.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO stocks (name_medicament, quantite) VALUES (?, ?)");
            ps.setString(1, medicament);
            ps.setString(2, quantity);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Stock inserted into the database.");
            } else {
                System.out.println("Failed to insert stock into the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        medicamentTableColumn.setCellValueFactory(cellData -> cellData.getValue().medicamentProperty());
        quantityTableColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());


        stockList = FXCollections.observableArrayList();
        tableViewStock.setItems(stockList);
        loadExistingStocks();
        loadMedicaments();
        editTraitementData();
    }
    private void loadExistingStocks() {
        Connection connection = Db.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT id , name_medicament,quantite FROM stocks");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String medicament = rs.getString("name_medicament");
                String quantity = rs.getString("quantite");


                Pharmacy pharmacy = new Pharmacy(id, medicament, quantity);
                stockList.add(pharmacy);
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
        quantityTableColumn.setCellFactory(TextFieldTableCell.<Pharmacy>forTableColumn());
        quantityTableColumn.setOnEditCommit(event -> {
            Pharmacy pharmacy = event.getTableView().getItems().get(event.getTablePosition().getRow());
            String newQuantity = event.getNewValue();
            int stockId = pharmacy.getId();
            pharmacy.setQuantity(newQuantity);

            // Update the database
            updateStockInDatabase(stockId, newQuantity);
        });


    }
    private void updateStockInDatabase(int stockId, String newQuantity) {
        Connection connection = Db.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE stocks SET quantite = ? WHERE id = ?");
            ps.setString(1, newQuantity);

            ps.setInt(2, stockId);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Traitement with ID " + stockId + " updated in the database.");
            } else {
                System.out.println("Failed to update traitement with ID " + stockId + " in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
