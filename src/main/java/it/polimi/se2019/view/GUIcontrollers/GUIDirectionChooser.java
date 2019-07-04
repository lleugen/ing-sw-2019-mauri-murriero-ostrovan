package it.polimi.se2019.view.GUIcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.List;

/**
 * Window controller for the chooser of a moving direction
 *
 * @author Riccardo Murriero
 */
public class GUIDirectionChooser extends GUIGenericWindow {

    @FXML
    private Button stayButton;

    @FXML
    private Button northButton;

    @FXML
    private Button southButton;

    @FXML
    private Button westButton;

    @FXML
    private Button estButton;

    private List<Integer> availableDirections;

    public GUIDirectionChooser(List<Integer> availableDirections) {
        super();
        this.availableDirections = availableDirections;
        result = -1;
    }

    @Override
    void initialize(){
        stayButton.setVisible(availableDirections.contains(-1));
        northButton.setVisible(availableDirections.contains(0));
        estButton.setVisible(availableDirections.contains(1));
        southButton.setVisible(availableDirections.contains(2));
        westButton.setVisible(availableDirections.contains(3));
    }

    @Override
    public Object getResult(){
        return result;
    }

    public void closeWindow(){
        Stage stage = (Stage) stayButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void doNotMove(ActionEvent event) {
        result = -1;
        closeWindow();
    }

    @FXML
    void moveEst(ActionEvent event) {
        result = 1;
        closeWindow();
    }

    @FXML
    void moveNorth(ActionEvent event) {
        result = 0;
        closeWindow();
    }

    @FXML
    void moveSouth(ActionEvent event) {
        result = 2;
        closeWindow();
    }

    @FXML
    void moveWest(ActionEvent event) {
        result = 3;
        closeWindow();
    }
}
