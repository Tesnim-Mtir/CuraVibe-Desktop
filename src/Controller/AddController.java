package Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddController implements Initializable {
    @FXML
    private Label imageLabel;
    @FXML
    private Button historiqueButton;

    @FXML
    private TextField donationNameField;

    @FXML
    private TextField quantityField;

    @FXML
    private ChoiceBox<String> stateChoiceBox;

    @FXML
    private TextArea descriptionArea;
    @FXML
    private Button donateButton;

    @FXML
    private Button donButton;
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



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        donateButton.setOnAction(event -> {
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
        historiqueButton.setOnAction(event -> {
            try {
                // Load the FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/historique.fxml"));
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

    }
    @FXML
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Set the full path of the selected file to the imageLabel
            String fileName = selectedFile.getName();
            imageLabel.setText(fileName);
        }
    }
    @FXML
    private void handleAddDonation() {
        String donationName = donationNameField.getText();
        String quantity = quantityField.getText();
        String state = stateChoiceBox.getValue();
        String description = descriptionArea.getText();
        String imagePath = imageLabel.getText();
        if (donationName.isEmpty() || quantity.isEmpty() || state == null || description.isEmpty() || imagePath.isEmpty()) {
            System.out.println("Veuillez remplir tous les champs !");
            return;
        }
        Connection connection = Db.getConnection();
        if (connection != null) {
            try {
                String sql = "INSERT INTO don (nom, quantite, etat, description, image) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, donationName);
                statement.setString(2, quantity);
                statement.setString(3, state);
                statement.setString(4, description);
                statement.setString(5, imagePath);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Une nouvelle donation a été insérée avec succès !");
                    // You can clear the form fields here if needed
                    donationNameField.clear();
                    quantityField.clear();
                    descriptionArea.clear();
                    imageLabel.setText(""); // Clear the image label
                    // Handle any other actions after successful insertion
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle SQL exceptions and file not found exceptions
            }

        } else {
            System.out.println("Échec de l'établissement d'une connexion à la base de données !");
        }
    }

}
