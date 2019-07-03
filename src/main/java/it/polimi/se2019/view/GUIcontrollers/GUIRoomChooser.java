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

public class GUIRoomChooser extends GUIGenericWindow {

    @FXML
    private Label questionLabel;

    @FXML
    private HBox choicesBox;

    @FXML
    private Button skipButton;

    private String[] btnsNames;
    private String mapName;
    /**
     * Shows the player the current actions that he can select and wait for a selection
     */
    public GUIRoomChooser(String mapName, List<String> availableRooms) {
        super();
        btnsNames = (String[])availableRooms.toArray();
        this.mapName = mapName;
        result = "";
    }

    @Override
    void initialize(){
        for(int i = 0; i < choicesBox.getChildren().size(); i++){
            VBox currentChoiceObj = ((VBox)(choicesBox.getChildren().get(i)));
            if(i < btnsNames.length){
                ((ImageView)currentChoiceObj.getChildren().get(0)).setImage(new Image(getURLOfImage("images/maps/" + mapName + "/rooms/" + btnsNames[i] + ".png")));
                ((Button)currentChoiceObj.getChildren().get(1)).setText(btnsNames[i]);
                setOnActionEffect(((Button)currentChoiceObj.getChildren().get(1)), btnsNames[i]);
                currentChoiceObj.setVisible(true);
            }else{
                currentChoiceObj.setVisible(false);
            }
        }
        questionLabel.setText("Seleziona una stanza tra quelle proposte.");
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
