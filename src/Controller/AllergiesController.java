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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Allergie;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AllergiesController implements Initializable {

    @FXML
    private ChoiceBox<String> choiceBoxAllergies;
    @FXML
    private TableView<Allergie> tableViewAllergie;
    @FXML
    private TableColumn<Allergie, Integer> idTableColumn;
    @FXML
    private TableColumn<Allergie, String> allergyIdTableColumn;

    private ObservableList<Allergie> allergieList;
    private ObservableList<String> allergiesList;

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
    private void addAllergies(ActionEvent event) {
        String selectedAllergy = choiceBoxAllergies.getValue();
        if (selectedAllergy != null && !selectedAllergy.isEmpty()) {
            Connection connection = Db.getConnection();
            try {
                String userEmail = UserSession.getInstance().getUserEmail();

                // Retrieve user ID based on user email
                PreparedStatement psFindUserId = connection.prepareStatement("SELECT id FROM users WHERE email = ?");
                psFindUserId.setString(1, userEmail);
                ResultSet rsUser = psFindUserId.executeQuery();
                if (rsUser.next()) {
                    int userId = rsUser.getInt("id");

                    // Find allergy_id based on the selected allergy name
                    PreparedStatement psFindAllergyId = connection.prepareStatement("SELECT id FROM allergies WHERE name = ?");
                    psFindAllergyId.setString(1, selectedAllergy);
                    ResultSet rsAllergy = psFindAllergyId.executeQuery();
                    if (rsAllergy.next()) {
                        int allergyId = rsAllergy.getInt("id");

                        // Insert the selected allergy for the user
                        PreparedStatement psInsert = connection.prepareStatement("INSERT INTO allergy_users (user_id, allergy_id) VALUES (?, ?)");
                        psInsert.setInt(1, userId);
                        psInsert.setInt(2, allergyId);
                        psInsert.executeUpdate();

                        // Reload allergies to refresh the table view
                        loadAllergies();
                    } else {
                        System.out.println("Allergy ID not found");
                    }
                } else {
                    System.out.println("User ID not found for email: " + userEmail);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        allergyIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("allergy"));

        // Load data into TableView and ChoiceBox
        loadAllergies();
        loadAllergyNames();
    }

    public void loadAllergies() {
        Connection connection = Db.getConnection();
        allergieList = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT allergy_users.id, allergies.name AS allergy " +
                            "FROM allergy_users " +
                            "JOIN allergies ON allergy_users.allergy_id = allergies.id"
            );
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Allergie allergie = new Allergie();
                allergie.setId(rs.getInt("id"));
                allergie.setAllergy(rs.getString("allergy"));

                allergieList.add(allergie);
            }

            tableViewAllergie.setItems(allergieList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadAllergyNames() {
        Connection connection = Db.getConnection();
        allergiesList = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT name FROM allergies");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                allergiesList.add(rs.getString("name"));
            }

            choiceBoxAllergies.setItems(allergiesList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
