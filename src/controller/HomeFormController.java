package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static javafx.fxml.FXMLLoader.load;

public class HomeFormController {
    public AnchorPane root;

    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do You Want to Log Out", ButtonType.YES,ButtonType.NO);
        final Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get().equals(ButtonType.YES)){
            Parent parent = load(Objects.requireNonNull(getClass().getResource("../view/LoginForm.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        }
    }

    public void btnSearchYourVaccineCardOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = load(Objects.requireNonNull(getClass().getResource("../view/SearchVaccineDataForm.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    public void EnterYourVaccineDetailsOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = load(Objects.requireNonNull(getClass().getResource("../view/AddNewVaccineDataForm.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Enter Vaccine Data");
        stage.centerOnScreen();
    }
}
