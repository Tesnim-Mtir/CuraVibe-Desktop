package Controller;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Users;
import Service.DBConnexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 *
 */
public class UtilisateurController implements Initializable {
    Connection con = null;
    PreparedStatement st=null ;
    ResultSet rs=null;



    private Button btnSave;


    @FXML
    private Button dashboard_btn;



    @FXML
    private TableColumn<Users, Integer> colEmail;





    @FXML
    private TableColumn<Users, String> colNom;

    @FXML
    private TableColumn<Users, Integer> colid;


    @FXML
    private TableView<Users> purchase_tableView;
    public UtilisateurController() {
        // Your default constructor logic here (if needed)
    }

    public UtilisateurController(TableColumn<Users, Integer> colEmail) {
        this.colEmail = colEmail;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showUsers();
    }



    public  void showUsers(){
        ObservableList <Users> list = getUsers();
        table.setItems(list);
        colid.setCellValueFactory(new PropertyValueFactory<Users,Integer> ("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<Users,String>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Users, Integer>("email"));
    }

    private ObservableList<Users> getUsers() {
        ObservableList<Users> users = FXCollections.observableArrayList();

        String query = "select * from users";
        con = DBConnexion.getCon();
        try {
            st=con.prepareStatement(query);
            rs= st.executeQuery();
            while (rs.next()) {
                Users user = new Users();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));

                users.add(user);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }


    @FXML
    private TextField tAMM;

    @FXML
    private TextField tClasse;

    @FXML
    private TextField tDCI;

    @FXML
    private TextField tEmail;

    @FXML
    private TextField tDuree;

    @FXML
    private TextField tForme;

    @FXML
    private TextField tName;



    @FXML
    private TableView<Users> table;
    int id=0;
    @FXML
    private Label username;



    @FXML
    void deleteMedicament(ActionEvent event) {
        Users selectedUser = table.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            int idToDelete = selectedUser.getId();
            String delete = "delete from users where id=?";
            con = DBConnexion.getCon();
            try {
                st = con.prepareStatement(delete);
                st.setInt(1, idToDelete);
                st.executeUpdate();
                showUsers();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Handle case where no row is selected
            // For example, display an error message
            System.out.println("Please select a user to delete.");
        }
    }

    public void logout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GestionUtilisateur.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleMedicamentButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Medicaments.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleStatsButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DashbordeStat.fxml"));
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
    void minimize(ActionEvent event) {

    }

    @FXML
    void purchaseAdd(ActionEvent event) {

    }

    @FXML
    void switchForm(ActionEvent event) {

    }




}
