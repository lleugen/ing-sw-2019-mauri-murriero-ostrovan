package it.polimi.se2019.view.GUIcontrollers;

//import com.sun.scenario.effect.impl.sw.java.JSWBlend_COLOR_BURNPeer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Window controller for the chooser of witch of the given powerUps the user want to use
 *
 * @author Riccardo Murriero
 */
public class GUIPowerUpsChooser extends GUIGenericWindow {

    @FXML
    private Label questionLabel;

    @FXML
    private HBox choicesBox;

    @FXML
    private Button skipButton;

    private String[] btnsNames;
    private List<Integer> taken;
    /**
     * Shows the player a list of powerUps he can choose to use and wait for a selection
     *
     * @param powerUps  list of the available powerUps
     * @param taken     list of integers that defines witch of the powerUps has been already chosen prev
     *
     */
    public GUIPowerUpsChooser(List<String> powerUps, List<Integer> taken) {
        super();
        btnsNames = (String[])powerUps.toArray();
        this.taken = taken;
        result = "";
    }

    @Override
    void initialize(){
        for(int i = 0; i < choicesBox.getChildren().size(); i++){
            VBox currentChoiceObj = ((VBox)(choicesBox.getChildren().get(i)));
            if((i < btnsNames.length) && (!taken.contains(i))){
                ((ImageView)currentChoiceObj.getChildren().get(0)).setImage(new Image(getURLOfImage("images/cards/" + btnsNames[i] + ".png")));
                ((Button)currentChoiceObj.getChildren().get(1)).setText(btnsNames[i].substring(0, btnsNames[i].indexOf('_')-1)
                        + " (" + btnsNames[i].substring(btnsNames[i].indexOf('_') + 1) + ")");
                setOnActionEffect(((Button)currentChoiceObj.getChildren().get(1)), String.valueOf(i));
                currentChoiceObj.setVisible(true);
            }else{
                currentChoiceObj.setVisible(false);
            }
        }
        questionLabel.setText("Scegli un PowerUp.");
        skipButton.setText("FINE SCELTA");
        setOnActionEffect(skipButton, "-1");
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
