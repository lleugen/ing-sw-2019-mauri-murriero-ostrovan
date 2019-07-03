package it.polimi.se2019.view.GUIcontrollers;

import it.polimi.se2019.view.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GUILogin extends GUIGenericWindow {
    @FXML
    private TextField nickname;

    @FXML
    private ComboBox<String> character;

    @FXML
    private Button connectButton;

    public static String[] charactersNames = {"Banshee", ":D-STRUTT-OR3", "Dozer", "Sprog", "Violetta"};
    private String nicknameFirstValue;
    private ObservableList<String> comboItems = FXCollections.observableArrayList(charactersNames);

    public GUILogin(String nicknameValue){
        this.nicknameFirstValue = nicknameValue;
    }

    @Override
    void initialize(){
        character.setItems(comboItems);
        nickname.setText(nicknameFirstValue);
    }

    @FXML
    void connectToServer(ActionEvent event) {
        result = nickname.getText() + "%" + character.getValue();
        closeWindow();
    }

    @Override
    public Object getResult(){
        return result;
    }

    public void closeWindow(){
        Stage stage = (Stage) nickname.getScene().getWindow();
        stage.close();
    }

    public static int indexOfCharacter(String characterName){
        for(int i = 0; i < charactersNames.length; i++)
            if(characterName.equals(charactersNames[i]))
                return i;
            return 0;
    }
}
