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

/**
 * Window controller for the chooser of witch of the given unload weapons the user wants to reload
 *
 * @author Riccardo Murriero
 */
public class GUIWeaponLoader extends GUIGenericWindow {

    @FXML
    private Label questionLabel;

    @FXML
    private HBox choicesBox;

    @FXML
    private Button skipButton;

    private String[] btnsNames;
    /**
     * Shows the player a list of unload weapons that can be reloaded and wait for a selection
     *
     * @param availableWeapons  a list of available unload weapons
     *
     */
    public GUIWeaponLoader(List<String> availableWeapons) {
        super();
        btnsNames = availableWeapons.toArray(new String[0]);
    }

    @Override
    void initialize(){
        for(int i = 0; i < choicesBox.getChildren().size(); i++){
            VBox currentChoiceObj = ((VBox)(choicesBox.getChildren().get(i)));
            if(i < btnsNames.length){
                ((ImageView)currentChoiceObj.getChildren().get(0)).setImage(new Image(getURLOfImage("images/cards/" + btnsNames[i] + ".png")));
                ((Button)currentChoiceObj.getChildren().get(1)).setText(btnsNames[i]);
                setOnActionEffect(((Button)currentChoiceObj.getChildren().get(1)), btnsNames[i]);
                currentChoiceObj.setVisible(true);
            }else{
                currentChoiceObj.setVisible(false);
            }
        }
        questionLabel.setText("Seleziona l'arma che vuoi ricaricare.");
        skipButton.setText("NON RICARICARE");
        setOnActionEffect(skipButton, "");
        skipButton.setVisible(true);
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
