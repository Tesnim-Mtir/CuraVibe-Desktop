package com.example.final_version.Controllers;


import com.example.final_version.Models.Medicament;
import com.example.final_version.Services.DBConnexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 *
 */
public class MedicamentController implements Initializable {
    Connection con = null;
    PreparedStatement st=null ;
    ResultSet rs=null;


    @FXML
    private Button addMed_btn;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button close;

    @FXML
    private Button dashboard_btn;


    @FXML
    private TableColumn<Medicament, String> colAMM;

    @FXML
    private TableColumn<Medicament, String> colClasse;

    @FXML
    private TableColumn<Medicament, String> colDCI;

    @FXML
    private TableColumn<Medicament, String> colDosage;

    @FXML
    private TableColumn<Medicament, Integer> colDuree;

    @FXML
    private TableColumn<Medicament, String> colForme;

    @FXML
    private TableColumn<Medicament, String> colNom;

    @FXML
    private TableColumn<Medicament, Integer> colid;


    @FXML
    private TableView<Medicament> purchase_tableView;

    public MedicamentController(TableColumn<Medicament, Integer> colDuree) {
        this.colDuree = colDuree;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showMedicaments();
    }



    public  void showMedicaments(){
        ObservableList <Medicament> list = getMedicaments();
        table.setItems(list);
        colid.setCellValueFactory(new PropertyValueFactory<Medicament,Integer> ("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<Medicament,String>("Nom"));
        colDosage.setCellValueFactory(new PropertyValueFactory<Medicament,String>("Dosage"));
        colForme.setCellValueFactory(new PropertyValueFactory<Medicament,String>("Forme"));
        colDCI.setCellValueFactory(new PropertyValueFactory<Medicament,String>("DCI"));
        colClasse.setCellValueFactory(new PropertyValueFactory<Medicament,String>("Classe"));
        colAMM.setCellValueFactory(new PropertyValueFactory<Medicament,String>("AMM"));
        colDuree.setCellValueFactory(new PropertyValueFactory<Medicament,Integer>("Durée"));
    }

    private ObservableList<Medicament> getMedicaments() {
        ObservableList<Medicament> medicaments = FXCollections.observableArrayList();

        String query = "select * from medicaments";
        con = DBConnexion.getCon();
        try {
            st=con.prepareStatement(query);
            rs= st.executeQuery();
            while (rs.next()){
                Medicament md = new Medicament();
                md.setId(rs.getInt("id"));
                md.setNom(rs.getString("Nom"));
                md.setDosage(rs.getString("Dosage"));
                md.setForme(rs.getString("Forme"));
                md.setDCI(rs.getString("DCI"));
                md.setClasse(rs.getString("Classe"));
                md.setAMM(rs.getString("AMM"));
                md.setDuree(Integer.parseInt(rs.getString("Duree")));
                medicaments.add(md);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
return medicaments;
    }


    @FXML
    private TextField tAMM;

    @FXML
    private TextField tClasse;

    @FXML
    private TextField tDCI;

    @FXML
    private TextField tDosage;

    @FXML
    private TextField tDuree;

    @FXML
    private TextField tForme;

    @FXML
    private TextField tNom;



    @FXML
    private TableView<Medicament> table;
    int id=0;
    @FXML
    private Label username;

    @FXML
    void addMedicament(ActionEvent event) {
        String insert= "insert into medicaments ( Nom, Dosage, Forme, DCI, Classe, AMM, Duree) values (?,?,?,?,?,?,?)";
        con = DBConnexion.getCon();
        try {
            st=con.prepareStatement(insert);
            st.setString(1, tNom.getText());
            st.setString(2, tDosage.getText());
            st.setString(3, tForme.getText());
            st.setString(4, tDCI.getText());
            st.setString(5, tClasse.getText());
            st.setString(6, tAMM.getText());
            st.setString(7, tDuree.getText());
            st.executeUpdate();
            clear();
            showMedicaments();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void getData(MouseEvent event) {
    Medicament medicament= table.getSelectionModel().getSelectedItem();
    id = medicament.getId();
    tNom.setText(medicament.getNom());
    tDosage.setText(medicament.getDosage());
    tForme.setText(medicament.getForme());
    tDCI.setText(medicament.getDCI());
    tClasse.setText(medicament.getClasse());
    tAMM.setText(medicament.getAMM());
    tDuree.setText(String.valueOf(medicament.getDuree()));
    btnSave.setDisable(true);
    }


    @FXML
    void clearField(ActionEvent event) {
    clear();
    }

    @FXML
    void close(ActionEvent event) {

    }
void clear (){
        tNom.setText(null);
        tDosage.setText(null);
        tForme.setText(null);
        tDCI.setText(null);
        tClasse.setText(null);
        tAMM.setText(null);
        tDuree.setText(null);
        btnSave.setDisable(false);
}
    @FXML
    void deleteMedicament(ActionEvent event) {
    String delete = "delete from medicaments where id=?";
    con = DBConnexion.getCon();
        try {
            st=con.prepareStatement(delete);
            st.setInt(1,id);
            st.executeUpdate();
            clear();
            showMedicaments();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void logout(ActionEvent event) {

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

    @FXML
    void updateMedicament(ActionEvent event) {
    String update = "update medicaments set Nom=? ,Dosage=? , Forme=? , DCI=? ,Classe=? , AMM=? ,  Duree=?  ";
    con=DBConnexion.getCon();
        try {
            st=con.prepareStatement(update);
            st.setString(1, tNom.getText());
            st.setString(2, tDosage.getText());
            st.setString(3, tForme.getText());
            st.setString(4, tDCI.getText());
            st.setString(5, tClasse.getText());
            st.setString(6, tAMM.getText());
            st.setString(7, tDuree.getText());
           // st.setInt(8,id);
            st.executeUpdate();
            clear();
            showMedicaments();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
