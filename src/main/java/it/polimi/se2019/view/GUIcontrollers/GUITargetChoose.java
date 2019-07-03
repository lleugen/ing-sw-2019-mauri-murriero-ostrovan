package it.polimi.se2019.view.GUIcontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class GUITargetChoose extends GUIGenericWindow {

    @FXML
    private Label questionLabel;

    @FXML
    private HBox choicesBox;

    @FXML
    private Button skipButton;

    private String[] btnsNames;
    private String[] foldersNames;
    /**
     * Shows the player the current actions that he can select and wait for a selection
     */
    public GUITargetChoose(List<String> targets, List<String> folders) {
        super();
        btnsNames = (String[])targets.toArray();
        foldersNames = (String[])folders.toArray();
        result = "";
    }

    @Override
    void initialize(){
        for(int i = 0; i < choicesBox.getChildren().size(); i++){
            VBox currentChoiceObj = ((VBox)(choicesBox.getChildren().get(i)));
            if(i < btnsNames.length){
                ((ImageView)currentChoiceObj.getChildren().get(0)).setImage(new Image(getURLOfImage("images/characters/" + foldersNames[i] + "/icon.png")));
                ((Button)currentChoiceObj.getChildren().get(1)).setText(btnsNames[i]);
                setOnActionEffect(((Button)currentChoiceObj.getChildren().get(1)), btnsNames[i]);
                currentChoiceObj.setVisible(true);
            }else{
                currentChoiceObj.setVisible(false);
            }
        }
        questionLabel.setText("Seleziona quale giocatore vorresti bersagliare.");
        skipButton.setVisible(false);
    }

    @Override
    public Object getResult(){
        return result;
    }

    public void closeWindow(){
        Stage stage = (Stage) choicesBox.getScene().getWindow();
        stage.close();
    }
}
