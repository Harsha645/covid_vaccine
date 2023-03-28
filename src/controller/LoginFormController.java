package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static java.util.Objects.requireNonNull;


public class LoginFormController {
    public AnchorPane root;
    public PasswordField txtPassword;

    public TextField txtNic;
    public static String nic;

    public void initialize(){
        txtNic.requestFocus();
    }

    public void txtNicOnAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) {
        login();
    }

    public void btnLoginOnAction(ActionEvent actionEvent) {
        login();
    }

    public void lblCreateNewAccountOnMouseClicked(MouseEvent mouseEvent) throws IOException {;
        Parent parent = FXMLLoader.load(requireNonNull(getClass().getResource("../view/CreateNewAccountForm.fxml")));

        Scene scene = new Scene(parent);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Register Form");
        stage.centerOnScreen();
    }
    public  void  login(){


        nic = txtNic.getText();
        final String password = txtPassword.getText();

        final Connection connection = DBConnection.getInstance().getConnection();

        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("select * from user where nic = ? and password = ?");
            preparedStatement.setObject(1,nic);
            preparedStatement.setObject(2,password);

            final ResultSet resultSet = preparedStatement.executeQuery();
            final boolean next = resultSet.next();

            if(next){
                Parent parent = FXMLLoader.load(requireNonNull(getClass().getResource("../view/HomeForm.fxml")));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) root.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Home Page");
                stage.centerOnScreen();

            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Invalid Username or Password");
                alert.showAndWait();
                txtNic.clear();
                txtPassword.clear();

                txtNic.requestFocus();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }


}
