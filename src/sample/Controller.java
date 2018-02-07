package sample;

import com.sun.jna.platform.win32.WinDef;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
    private Button closeButton;
    @FXML
    private Label status;
    @FXML
    private Label statusexit;

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
    private void closeButtonAction() {
        if (!"".equals(sNumber.getText())) {
            if (1 == Integer.parseInt(sNumber.getText())) {
                KeyHook.unblockWindowsKey();
                // get a handle to the stage
                stage = (Stage) closeButton.getScene().getWindow();
                // do what you have to do
                stage.close();
                System.exit(1);
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

    private void give_access() {
        stage = (Stage) closeButton.getScene().getWindow();
        stage.hide();
        final long start = System.currentTimeMillis();
        final long duration = 4 * 60 * 1000;
        while (System.currentTimeMillis() < start + duration) {
        }
        stage.show();
    }
}