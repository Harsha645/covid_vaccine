package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tm.VaccineDataTM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class AddNewVaccineDataFormController {
    public AnchorPane root;
    public TextField txtName;
    public TextField txtAge;
    public TextField txtContactNumber;
    public ComboBox txtGender;
    public TextField txtDistrict;
    public TextField txtMohArea;
    public ComboBox txtDose;
    public DatePicker txtDate;
    public ComboBox txtVaccine;
    public TextField txtAddress;
    public Label lblNIC;
    public TableView<VaccineDataTM> tblVaccine;

    public void initialize() throws SQLException {

        tblVaccine.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("dose"));
        tblVaccine.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("vaccine"));
        tblVaccine.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblVaccine.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("mohArea"));


        txtGender.setItems(FXCollections.observableArrayList("Male","Female"));
        txtVaccine.setItems(FXCollections.observableArrayList("Pfizer","Sainopharm","Sputnic","Moderna"));
        txtDose.setItems(FXCollections.observableArrayList("1","2","3","4","5"));
        lblNIC.setText(LoginFormController.nic);

        loadList();

    }

    public void btnSaveDataOnAction(ActionEvent actionEvent) {
        try (Connection connection = DBConnection.getInstance().getConnection();) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("insert into vaccine_card values(?,?,?,?,?,?,?,?,?,?,?)")) {
                preparedStatement.setObject(1,lblNIC.getText());
                preparedStatement.setObject(2,txtName.getText());
                preparedStatement.setObject(3,txtAddress.getText());
                preparedStatement.setObject(4,txtGender.getValue());
                preparedStatement.setObject(5,txtAge.getText());
                preparedStatement.setObject(6,txtContactNumber.getText());
                preparedStatement.setObject(7,txtVaccine.getValue());
                preparedStatement.setObject(8,txtDose.getValue());
                preparedStatement.setObject(9,txtDate.getValue());
                preparedStatement.setObject(10,txtDistrict.getText());
                preparedStatement.setObject(11,txtMohArea.getText());

                final int i = preparedStatement.executeUpdate();

                if(i > 0){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Information saved");
                    alert.showAndWait();
                    tblVaccine.refresh();
                    loadList();

                }
                else{
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Information Not saved");
                    alert.showAndWait();
                    tblVaccine.refresh();
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do You Want to Log Out",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get().equals(ButtonType.YES)){
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/LoginForm.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        }
    }
    public void loadList() throws SQLException {
        ObservableList<VaccineDataTM> items = tblVaccine.getItems();
        items.clear();

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from vaccine_card where nic = ?");

        preparedStatement.setObject(1,lblNIC.getText());
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            String dose = resultSet.getString(8);
            String vaccine = resultSet.getString(7);
            String date = resultSet.getString(9);
            String mohArea = resultSet.getString(11);

            VaccineDataTM vaccineDataTM = new VaccineDataTM(dose, vaccine, date, mohArea);
            items.add(vaccineDataTM);
        }
        tblVaccine.refresh();

    }
    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/HomeForm.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    public static void printHarsha(){
        System.out.println("harsha pramod");
    }
}
