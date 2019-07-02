package it.polimi.se2019.view.GUIcontrollers;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class GUIGameBoard extends GUIGenericWindow{

    @FXML
    private HBox blueWeaponsBox;

    @FXML
    private HBox yellowWeaponsBox;

    @FXML
    private HBox redWeaponsBox;

    @FXML
    private GridPane squaresGrid;

    @FXML
    private HBox deathsBox;

    @FXML
    private Pane doubleKillPane;

    private List<ArrayList<ArrayList<String>>> mapInfo;
    private List<ArrayList<String>> killScoreBoardInfo;

    public GUIGameBoard(){

    }

    @Override
    void initialize(){

    }

    public void setMapInfo(List<ArrayList<ArrayList<String>>> mapInfo){
        //[x][y] -> [blue, red, yellow, powerUp, players...]
    }

    public void setKillScoreBoardInfo(List<ArrayList<String>> killScoreBoardInfo) {
        // [0] -> kill+overkill?
        // [1] -> doublekill
    }

    @Override
    public Object getResult(){
        return result;
    }

    public void closeWindow(){
        Stage stage = (Stage) squaresGrid.getScene().getWindow();
        stage.close();
    }
}
