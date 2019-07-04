package it.polimi.se2019.view.GUIcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Window controller for the chooser of a generic boolean
 *
 * @author Riccardo Murriero
 */
public class GUIBooleanQuestion extends GUIGenericWindow {

    @FXML
    private Label questionLabel;

    @FXML
    private Button confirmButton;

    @FXML
    private Button delentButton;

    private String question;
    private String confirmString;
    private String delentString;

    public GUIBooleanQuestion(String question, String confirmString, String delentString) {
        super();
        this.question = question; this.confirmString = confirmString; this.delentString = delentString;
    }

    @Override
    void initialize(){
        questionLabel.setText(question);
        confirmButton.setText(confirmString);
        delentButton.setText(delentString);
    }

    @Override
    public Object getResult(){
        return result;
    }

    public void closeWindow(){
        Stage stage = (Stage) questionLabel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void confirmAction(ActionEvent event) {
        result = true;
        closeWindow();
    }

    @FXML
    void delentAction(ActionEvent event) {
        result = false;
        closeWindow();
    }
}
