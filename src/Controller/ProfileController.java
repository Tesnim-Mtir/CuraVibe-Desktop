package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private Label nameLabel;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private DatePicker dobDatePicker;

    @FXML
    private TextField addressTextField;

    @FXML
    private ImageView profil;

    @FXML
    private TextField phoneTextField;

    @FXML
    private Label statusLabel;

    private Connection connection;



    public ProfileController() {
        connection = Connexion.getInstance().getCnx();
    }

    public void UserSpace(ActionEvent event) {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File profilFile = new File("src/img/téléchargement.png");
        Image profilImage = new Image(profilFile.toURI().toString());
        profil.setImage(profilImage);

        // Retrieve and display user data
        loadUserData();
    }

    private void loadUserData() {
        String emailUtilisateur = UserSession.getInstance().getUserEmail(); // Get the email from the session
        System.out.println("Email from session: " + emailUtilisateur); // Debugging statement

        if (emailUtilisateur == null || emailUtilisateur.isEmpty()) {
            System.out.println("No user email found in session.");
            return;
        }

        try {
            String sql = "SELECT name, last, email, dob, phone, address FROM users WHERE email = ?";
            System.out.println("Executing SQL: " + sql); // Debugging statement
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, emailUtilisateur);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("name");
                String lastName = resultSet.getString("last");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");

                // Check if dob column is NULL
                LocalDate dob = null;
                Date dobDate = resultSet.getDate("dob");
                if (dobDate != null) {
                    dob = dobDate.toLocalDate();
                }

                // Update the UI with the retrieved data
                nameLabel.setText(firstName); // Assuming nameLabel displays the first name
                emailTextField.setText(email);
                firstNameTextField.setText(firstName);
                lastNameTextField.setText(lastName);
                dobDatePicker.setValue(dob); // Set value to dob or null if dob is null
                addressTextField.setText(address);
                phoneTextField.setText(phone);

                System.out.println("User data retrieved and set in UI."); // Debugging statement
            } else {
                System.out.println("User not found."); // Debugging statement
            }
            // Close resources
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserDetails() {
        String newEmail = emailTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        LocalDate dob = dobDatePicker.getValue();
        String address = addressTextField.getText();
        String phone = phoneTextField.getText();

        try {
            // Update email in the users table
            String updateUserSql = "UPDATE users SET email = ? WHERE email = ?";
            PreparedStatement updateUserStatement = connection.prepareStatement(updateUserSql);
            updateUserStatement.setString(1, newEmail);
            updateUserStatement.setString(2, UserSession.getInstance().getUserEmail());
            updateUserStatement.executeUpdate();
            updateUserStatement.close();

            // Update other details in the users table (not profiles)
            String updateProfileSql = "UPDATE users SET name = ?, last = ?, dob = ?, address = ?, phone = ? WHERE email = ?";
            PreparedStatement updateProfileStatement = connection.prepareStatement(updateProfileSql);
            updateProfileStatement.setString(1, firstName);
            updateProfileStatement.setString(2, lastName);
            updateProfileStatement.setDate(3, java.sql.Date.valueOf(dob)); // Convert LocalDate to java.sql.Date
            updateProfileStatement.setString(4, address);
            updateProfileStatement.setString(5, phone);
            updateProfileStatement.setString(6, newEmail); // Use the updated email here
            updateProfileStatement.executeUpdate();
            updateProfileStatement.close();

            statusLabel.setText("User details updated successfully.");
            statusLabel.setStyle("-fx-text-fill: green;");
            UserSession.getInstance().setUserEmail(newEmail); // Update session email if changed
        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Failed to update user details.");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }
}
