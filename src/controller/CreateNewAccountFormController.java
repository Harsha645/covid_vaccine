package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class CreateNewAccountFormController {
    public Label lblUserID;
    public AnchorPane root;
    public PasswordField txtNewPassword;
    public PasswordField txtConfirmPassword;
    public Button btnRegister;
    public Label lblPasswordDoesNotMatched1;
    public Label lblPasswordDoesNotMatched2;
    public TextField txtUserName;
    public TextField txtEmail;
    public TextField txtNic;

    public void initialize(){
        setVisible(false);
        setDisable(true);
    }

    public void txtConfirmPasswordOnAction(ActionEvent actionEvent) {
        register();

    }

    public void btnAddUserOnAction(ActionEvent actionEvent) {
        setDisable(false);
        txtNic.requestFocus();
        txtNic.clear();
        txtEmail.clear();
        txtNewPassword.clear();
        txtConfirmPassword.clear();

        autoGenerateID();
    }
    public void autoGenerateID(){
         Connection connection = DBConnection.getInstance().getConnection();

        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select id from user order by id desc limit 1");

            boolean isExist = resultSet.next();

            if (isExist) {
                String userID = resultSet.getString(1);

                userID = userID.substring(1,userID.length());
                int intID = Integer.parseInt(userID);

                intID++;

                if (intID < 10) {
                    lblUserID.setText("U00" + intID);
                } else if (intID < 100)  {
                    lblUserID.setText("U0" + intID);
                }else {
                    lblUserID.setText("U" + intID);
                }

            }else {
                lblUserID.setText("U001");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnRegisterOnAction(ActionEvent actionEvent) throws IOException {
        register();
    }
    public void register(){
        final String newPassword = txtNewPassword.getText();
        final String confirmPassword = txtConfirmPassword.getText();

        if(newPassword.equals(confirmPassword)){
            borderColor("transparent");
            setVisible(false);

            final String userID = lblUserID.getText();
            final String nic = txtNic.getText();
            final String email = txtEmail.getText();

            final Connection connection = DBConnection.getInstance().getConnection();

            try {
                final PreparedStatement preparedStatement = connection.prepareStatement("insert into user values(?,?,?,?)");
                preparedStatement.setObject(1,userID);
                preparedStatement.setObject(2,nic);
                preparedStatement.setObject(3,email);
                preparedStatement.setObject(4,confirmPassword);

                final int i = preparedStatement.executeUpdate();

                if(i > 0){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Register Successful");
                    alert.showAndWait();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/LoginForm.fxml")));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) root.getScene().getWindow();
                stage.setScene(scene);
                stage.centerOnScreen();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        else {
            borderColor("red");

            setVisible(true);

            txtNewPassword.clear();
            txtConfirmPassword.clear();
            txtNewPassword.requestFocus();

        }
    }
    public void setVisible(boolean isVisible){
        lblPasswordDoesNotMatched1.setVisible(isVisible);
        lblPasswordDoesNotMatched2.setVisible(isVisible);
    }
    public void borderColor(String color){
        txtNewPassword.setStyle("-fx-border-color: " + color);
        txtConfirmPassword.setStyle("-fx-border-color: " + color );
    }
    public void setDisable(boolean isDisable){
        txtNic.setDisable(isDisable);
        txtEmail.setDisable(isDisable);
        txtNewPassword.setDisable(isDisable);
        txtConfirmPassword.setDisable(isDisable);
    }


}
