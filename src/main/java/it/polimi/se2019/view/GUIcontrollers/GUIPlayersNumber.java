package it.polimi.se2019.view.GUIcontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIPlayersNumber extends GUIGenericWindow {

    @FXML
    private ComboBox<String> playersNumberCombo;

    private int[] btnsEquiv = {1, 2, 3, 4, 5};
    private String[] btnsValues = {"1: Gioco da solo", "2: Giochiamo in coppia", "3: Minimo in tre", "4: Quattro numero perfetto", "5: Tutti insieme!"};
    /**
     * Shows the player the current actions that he can select and wait for a selection
     */
    public GUIPlayersNumber() {
        super();
        result = 1;
    }

    @Override
    void initialize(){
        ObservableList<String> comboList = FXCollections.observableArrayList(btnsValues);
    }

    @Override
    public Object getResult(){
        return result;
    }

    public void closeWindow(){
        Stage stage = (Stage) playersNumberCombo.getScene().getWindow();
        stage.close();
    }

    @FXML
    void doneAction(ActionEvent event) {
        for(int i = 0; i < btnsValues.length; i++) {
            if (btnsValues[i].equals(playersNumberCombo.getValue()))
                result = btnsEquiv[i];
        }
        result = 1;
        closeWindow();
    }
}
