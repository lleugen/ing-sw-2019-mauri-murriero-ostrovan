package it.polimi.se2019.view.GUIcontrollers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Window controller for the background window
 *
 * @author Riccardo Murriero
 */
public class GUIMainWindow {

    public GUIMainWindow(){

    }

    @FXML
    void closeApplication(ActionEvent event) {
        Platform.exit();
    }

}