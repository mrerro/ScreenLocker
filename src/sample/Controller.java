package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {


    private DbController db = new DbController();
    private Stage stage;

    @FXML
    private TextField sNumber;
    @FXML
    private TextField sSurname;
    @FXML
    private TextField numberSheets;
    @FXML
    private Button adminButton;
    @FXML
    private Label status;
    @FXML
    private Label statusexit;
    @FXML
    private Button closeButton;
    @FXML
    private Button backButton;
    @FXML
    private TextArea report;

    //1017103
    @FXML
    private void onButtonClick() {
        if (!"".equals(sNumber.getText()) && !"".equals(sSurname.getText()) && !"".equals(numberSheets.getText())) {
            if (db.check_for_validity(Integer.parseInt(sNumber.getText()), sSurname.getText().toLowerCase())) {
                db.update_access_data(Integer.parseInt(sNumber.getText()), Integer.parseInt(numberSheets.getText()));
                status.setText("");
                statusexit.setText("");
                sNumber.setText("");
                sSurname.setText("");
                numberSheets.setText("");
                give_access();
            } else {
                status.setText("You Shall Not Pass!!! Вы ошиблись в фамилии или номере студенческого");
                status.setTextFill(Color.DARKRED);
            }
        } else {
            statusexit.setText("");
            status.setText("Сначало заполни все поля!");
            status.setTextFill(Color.DARKORANGE);
        }
    }

    @FXML
    private void adminButtonAction() throws IOException {
        if (!"".equals(sNumber.getText())) {
            if (1 == Integer.parseInt(sNumber.getText())) {
                statusexit.setText("");
                status.setText("");
                sNumber.setText("");
                sSurname.setText("");
                numberSheets.setText("");
                stage = (Stage) adminButton.getScene().getWindow();
                Parent adminpanel = FXMLLoader.load(getClass().getResource("adminpanel.fxml"));
                stage.setScene(new Scene(adminpanel ,1024,768));
                stage.setAlwaysOnTop(false);
                stage.setMaximized(false);
            } else {
                statusexit.setTextFill(Color.DARKCYAN);
                statusexit.setText("Скажи друг и входи..");
                status.setText("");
                sNumber.setText("");
                sSurname.setText("");
                numberSheets.setText("");
            }
        }
    }

    @FXML
    private void aboutButtonAction(){
        report.setText("Screen locker\nAutor: John Dyakov\nDyakov.hellvisionstudio@gmail.com");
    }

    @FXML
    private void backButtonAction() throws IOException {
        stage = (Stage) backButton.getScene().getWindow();
        Parent sample = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stage.setScene(new Scene(sample ,1024,768));
        stage.setAlwaysOnTop(true);
        stage.setMaximized(true);
    }

    @FXML
    private void closeButtonAction() {
        KeyHook.unblockWindowsKey();
        // get a handle to the stage
        stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
        System.exit(1);
    }

    private void give_access() {
        stage = (Stage) adminButton.getScene().getWindow();
        stage.hide();
        final long start = System.currentTimeMillis();
        final long duration = 4 * 60 * 1000;
        while (System.currentTimeMillis() < start + duration) {
        }
        stage.show();
    }
}