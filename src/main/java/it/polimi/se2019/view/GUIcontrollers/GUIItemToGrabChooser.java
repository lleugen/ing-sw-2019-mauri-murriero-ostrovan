package it.polimi.se2019.view.GUIcontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Window controller for the chooser of a grabbable item
 *
 * @author Riccardo Murriero
 */
public class GUIItemToGrabChooser extends GUIGenericWindow {

    @FXML
    private Label questionLabel;

    @FXML
    private HBox choicesBox;

    @FXML
    private Button skipButton;

    private String[] btnsNames;
    /**
     * Shows the player the current actions that he can select and wait for a selection
     */
    public GUIItemToGrabChooser() {
        super();
        btnsNames = new String[] {"1", "2", "3"};
    }

    @Override
    void initialize(){
        for(int i = 0; i < choicesBox.getChildren().size(); i++){
            VBox currentChoiceObj = ((VBox)(choicesBox.getChildren().get(i)));
            if(i < btnsNames.length){
                ((ImageView)currentChoiceObj.getChildren().get(0)).setVisible(false);
                ((Button)currentChoiceObj.getChildren().get(1)).setText(btnsNames[i]);
                setOnActionEffect(((Button)currentChoiceObj.getChildren().get(1)), String.valueOf(i));
                currentChoiceObj.setVisible(true);
            }else{
                currentChoiceObj.setVisible(false);
            }
        }
        questionLabel.setText("Quale arma vuoi raccogliere?");
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