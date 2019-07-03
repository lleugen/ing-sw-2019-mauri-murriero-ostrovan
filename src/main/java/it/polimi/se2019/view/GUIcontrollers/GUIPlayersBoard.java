package it.polimi.se2019.view.GUIcontrollers;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GUIPlayersBoard extends GUIGenericWindow{

    @FXML
    private VBox playersSheets;

    private List<ArrayList<String>> playerInfo = null;
    private PlayersNamesKeeper playersCharacters;

    private String localPlayerName;

    public GUIPlayersBoard(String localPlayerName, PlayersNamesKeeper playersCharacters){
        this.localPlayerName = localPlayerName;
        this.playersCharacters = playersCharacters;
    }

    @Override
    void initialize(){
        for(Node n : playersSheets.getChildren())
            n.setVisible(false);

        //updateGraphic();
    }

    private void updateGraphic(){
        //[0] damages, [1] marks, [2] deaths, [3][0] nickname, [3][1] frenzy/normal

        ObservableList<Node> players = playersSheets.getChildren();
        String currentPlayerName = playerInfo.get(3).get(0);
        Integer index = playersCharacters.findIndex(currentPlayerName);
        PlayersNamesKeeper.PlayerNamesData currentData = playersCharacters.find(currentPlayerName);

        AnchorPane currentPlayer = (AnchorPane)players.get(index);
        currentPlayer.setVisible(false);

        ObservableList<Node> currentBoardElements = currentPlayer.getChildren();
        ((ImageView)currentBoardElements.get(0)).setImage(new Image("images/characters/" + currentData.getFolder() + "/" + playerInfo.get(3).get(1) + "sheet.png"));
        ((Label)currentBoardElements.get(1)).setText(currentData.getName());
        (currentBoardElements.get(2)).setVisible(false); //PER ORA NON LO PASSIAMO

        Pane segPane = (Pane)currentBoardElements.get(3);   //danni
        for(int i = 0; i < segPane.getChildren().size(); i++){
            if(i < playerInfo.get(0).size()) {
                ((ImageView) segPane.getChildren().get(i)).setImage(new Image("images/characters/" + playersCharacters.findFolder(playerInfo.get(0).get(i)) + "/damage.png"));
                segPane.getChildren().get(i).setVisible(true);
            }else
                segPane.getChildren().get(i).setVisible(false);
        }

        segPane = (Pane)currentBoardElements.get(4);   //marchi
        for(int i = 0; i < segPane.getChildren().size(); i++){
            if(i < playerInfo.get(1).size()) {
                ((ImageView) segPane.getChildren().get(i)).setImage(new Image("images/characters/" + playersCharacters.findFolder(playerInfo.get(0).get(i)) + "/damage.png"));
                segPane.getChildren().get(i).setVisible(true);
            }else
                segPane.getChildren().get(i).setVisible(false);
        }

        segPane = (Pane)currentBoardElements.get(5);   //morti
        for(int i = 0; i < segPane.getChildren().size(); i++){
            if(i < Integer.getInteger(playerInfo.get(2).get(0))) {
                segPane.getChildren().get(i).setVisible(true);
            }else
                segPane.getChildren().get(i).setVisible(false);
        }

        currentBoardElements.get(7).setVisible(currentData.getName().equals(localPlayerName));
        currentBoardElements.get(8).setVisible(false); //<TURN> string, per ora non la utiliziamo

        currentPlayer.setVisible(true);
    }

    public void setPlayerInfo(List<ArrayList<String>> playerInfo){
        this.playerInfo = playerInfo;
        updateGraphic();
    }

    @Override
    public Object getResult(){
        return result;
    }

    public void closeWindow(){
        Stage stage = (Stage) playersSheets.getScene().getWindow();
        stage.close();
    }

}
