package Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Fruit;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class HistoriqueController implements Initializable {
    @FXML
    private Button deleteData;
    @FXML
    private Button ajouterButton ;
    @FXML
    private Button historiqueButton;
    @FXML
    private Button donButton1;
    @FXML
    private TableView<Fruit> tableView;


    @FXML
    private TableColumn<Fruit,String> description;


    @FXML
    private TableColumn<Fruit,String> etat;

    @FXML
    private TableColumn<Fruit,String> name;

    @FXML
    private TableColumn<Fruit,String> quantite;

    @FXML
    private GridPane grid;
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
    public void handleTraitementButtonAction(ActionEvent event) {
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



    @FXML
    private void deleteData(ActionEvent event) {
        System.out.println("Delete button clicked");

        TableView.TableViewSelectionModel<Fruit> selectionModel = tableView.getSelectionModel();
        ObservableList<Fruit> selectedItems = selectionModel.getSelectedItems();
        System.out.println("Selected items: " + selectedItems);

        if (selectionModel.isEmpty()) {
            System.out.println("Select data before deleting");
        } else {
            Connection connection = null; // Declare the connection outside the try-catch block
            try {
                connection = Db.getConnection(); // Initialize the connection
                // Remove selected items from the database
                for (Fruit fruit : selectedItems) {
                    int fruitId = fruit.getId();
                    try (PreparedStatement statement = connection.prepareStatement("DELETE FROM don WHERE id = ?")) {
                        statement.setInt(1, fruitId);
                        int rowsAffected = statement.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Data with ID " + fruitId + " deleted from the database");
                            // Remove the deleted item from the TableView
                            tableView.getItems().remove(fruit);
                        } else {
                            System.out.println("Data with ID " + fruitId + " not found in the database");
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error deleting data from the database: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        donButton1.setOnAction(event -> {
            try {
                // Load the FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/market.fxml"));
                Parent root = loader.load();
                // Create a new scene
                Scene scene = new Scene(root);
                // Get the stage information
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                // Set the new scene on the stage
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ajouterButton.setOnAction(event -> {
            try {
                // Load the FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/add.fxml"));
                Parent root = loader.load();
                // Create a new scene
                Scene scene = new Scene(root);
                // Get the stage information
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                // Set the new scene on the stage
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        // Set up the cell value factories for each column
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        etat.setCellValueFactory(new PropertyValueFactory<>("state"));
        quantite.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));


        // Populate TableView with data from donation_table
        populateTableView();
        editData();

    }
    private void populateTableView() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = Db.getConnection();
            String query = "SELECT id, nom, etat, quantite, description  FROM don";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String donationName = resultSet.getString("nom");
                String state = resultSet.getString("etat");
                int quantity = resultSet.getInt("quantite");
                String description = resultSet.getString("description");


                // Create a new Fruit object with retrieved data

                Fruit fruit = new Fruit();
                fruit.setId(id);
                fruit.setName(donationName);
                fruit.setState(state);
                fruit.setQuantity(quantity);
                fruit.setDescription(description);



                // Add the Fruit object to the TableView
                tableView.getItems().add(fruit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private void editData(){
        name.setCellFactory(TextFieldTableCell.<Fruit>forTableColumn());
        name.setOnEditCommit(event -> {
            Fruit fruit = event.getTableView().getItems().get(event.getTablePosition().getRow());
            String newName = event.getNewValue();
            fruit.setName(newName);
            int fruitId = fruit.getId();
            Connection connection = null;
            PreparedStatement statement = null;
            try {
                connection = Db.getConnection();
                String query = "UPDATE don SET nom = ? WHERE id = ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, newName);
                statement.setInt(2, fruitId);
                statement.executeUpdate();
                System.out.println("Base de données mise à jour avec le nouveau nom.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        etat.setCellFactory(TextFieldTableCell.<Fruit>forTableColumn());
        etat.setOnEditCommit(event -> {
            Fruit fruit = event.getTableView().getItems().get(event.getTablePosition().getRow());
            String newEtat = event.getNewValue();
            int fruitId = fruit.getId();
            fruit.setState(newEtat);
            Connection connection = null;
            PreparedStatement statement = null;
            try {
                connection = Db.getConnection();
                String query = "UPDATE don SET etat = ? WHERE id = ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, newEtat);
                statement.setInt(2, fruitId);
                statement.executeUpdate();
                System.out.println("Base de données mise à jour avec le nouveau etat.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        description.setCellFactory(TextFieldTableCell.<Fruit>forTableColumn());
        description.setOnEditCommit(event -> {
            Fruit fruit = event.getTableView().getItems().get(event.getTablePosition().getRow());
            String newDescription = event.getNewValue();
            fruit.setDescription(newDescription);
            int fruitId = fruit.getId();

            Connection connection = null;
            PreparedStatement statement = null;
            try {
                connection = Db.getConnection();
                String query = "UPDATE don SET description = ? WHERE id = ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, newDescription);
                statement.setInt(2, fruitId);
                statement.executeUpdate();
                System.out.println("Base de données mise à jour avec le nouveau Description.");
                statement.close(); // Close the statement
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}