package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {


    private DbController db = new DbController();

    @FXML
    private Button btCheck;
    @FXML
    private TextField sNumber;
    @FXML
    private TextField sSurname;
    @FXML
    private Button closeButton;

    //1017103
    @FXML
    private void onButtonClick() {
        if (!"".equals(sNumber.getText()) && !"".equals(sSurname.getText())) {
            btCheck.setText(db.checkforvalidity(Integer.parseInt(sNumber.getText()), sSurname.getText().toLowerCase()).toString());
        }
    }
    @FXML
    private void closeButtonAction(){
        KeyHook.unblockWindowsKey();
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
