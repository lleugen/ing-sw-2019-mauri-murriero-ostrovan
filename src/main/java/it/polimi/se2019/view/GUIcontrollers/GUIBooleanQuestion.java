package it.polimi.se2019.view.GUIcontrollers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

public class GUIBooleanQuestion extends GUIGenericWindow {

    @FXML
    private Label questionLabel;

    @FXML
    private Button confirmButton;

    @FXML
    private Button delentButton;

    private String question, confirmString, delentString;

    public GUIBooleanQuestion(String question, String confirmString, String delentString) {
        super();
        this.question = question; this.confirmString = confirmString; this.delentString = delentString;
        result = false;
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
