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
 * Window controller for the chooser of spawn location of the player
 *
 * @author Riccardo Murriero
 */
public class GUISpawnLocation extends GUIGenericWindow {

    @FXML
    private Label questionLabel;

    @FXML
    private HBox choicesBox;

    @FXML
    private Button skipButton;

    private String[] btnsNames;
    /**
     * Shows the player a list of available powerUps that can be discarded to spawn and wait for a selection
     *
     * @param powerUps  list of the available powerUps that can be discarded by the user
     *
     */
    public GUISpawnLocation(List<String> powerUps) {
        super();
        btnsNames = powerUps.toArray(new String[0]);
        result = "";
    }

    @Override
    void initialize(){
        for(int i = 0; i < choicesBox.getChildren().size(); i++){
            VBox currentChoiceObj = ((VBox)(choicesBox.getChildren().get(i)));
            if(i < btnsNames.length){
                String currentName = btnsNames[i];
                ((ImageView)currentChoiceObj.getChildren().get(0)).setImage(new Image(getURLOfImage("images/cards/" + btnsNames[i] + ".png")));
                ((Button)currentChoiceObj.getChildren().get(1)).setText(currentName);
                setOnActionEffect(((Button)currentChoiceObj.getChildren().get(1)), String.valueOf(i));
                currentChoiceObj.setVisible(true);
            }else{
                currentChoiceObj.setVisible(false);
            }
        }
        questionLabel.setText("Scegli il powerUp da scartare, verrai generato nel punto del colore corrispondente.");
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
