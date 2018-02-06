package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {


    private DbController db = new DbController();
    private Stage stage;

    @FXML
    private Button btCheck;
    @FXML
    private TextField sNumber;
    @FXML
    private TextField sSurname;
    @FXML
    private TextField numberSheets;
    @FXML
    private Button closeButton;

    //1017103
    @FXML
    private void onButtonClick() {
        if (!"".equals(sNumber.getText()) && !"".equals(sSurname.getText()) && !"".equals(numberSheets.getText())) {
            if (db.check_for_validity(Integer.parseInt(sNumber.getText()), sSurname.getText().toLowerCase())) {
                db.update_access_data(Integer.parseInt(sNumber.getText()),Integer.parseInt(numberSheets.getText()));
                //give_access();
            }
        }
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
        stage = (Stage) closeButton.getScene().getWindow();
        stage.hide();
        final long start = System.currentTimeMillis();
        final long duration = 4*60*1000;
        while (System.currentTimeMillis() < start + duration) {
        }
        stage.show();
    }
}
