package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {


    private DbController db = new DbController();

    @FXML
    private Button btCheck;
    @FXML
    private TextField sNumber;
    @FXML
    private TextField sSurname;

    //1017103
    @FXML
    public void onButtonClick() {
        btCheck.setText(db.checkforvalidity(Integer.parseInt(sNumber.getText()), sSurname.getText().toLowerCase()).toString());
    }
}
