package controller;

import db.DBConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
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

public class SearchVaccineDataFormController {
    public TableView<VaccineDataTM> tblVaccine;
    public Label lblContact;
    public Label lblAge;
    public Label lblGender;
    public Label lblNic2;
    public Label lblName;
    public Label lblNic;
    public AnchorPane root;

    public void initialize() throws SQLException {
        lblNic.setText(LoginFormController.nic);
        lblNic2.setText(LoginFormController.nic);

        tblVaccine.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("dose"));
        tblVaccine.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("vaccine"));
        tblVaccine.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblVaccine.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("mohArea"));



        loadList();

    }
    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/HomeForm.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
    }
    public void loadList() throws SQLException {


        ObservableList<VaccineDataTM> items = tblVaccine.getItems();
        items.clear();

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from vaccine_card where nic = ?");

        preparedStatement.setObject(1,lblNic.getText());
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
}
