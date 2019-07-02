package it.polimi.se2019.view.GUIcontrollers;

import com.sun.xml.internal.xsom.util.DeferedCollection;
import it.polimi.se2019.model.grabbable.Ammo;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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

    private PlayersNamesKeeper playersInfo;

    private int[] blueCord = {0, 2}, redCord = {1, 0}, yellowCord = {2, 3}, localPlayerCoords;
    private String[] blueWeapons, redWeapons, yellowWeapons;

    public GUIGameBoard(PlayersNamesKeeper playersInfo){
        this.playersInfo = playersInfo;
    }

    @Override
    void initialize(){
        squaresGrid.setVisible(false);
    }

    public void setLocalPlayerCoords(String discardedPowerUp){
        if(discardedPowerUp.toLowerCase().contains("red"))
            localPlayerCoords = redCord;
        else if(discardedPowerUp.toLowerCase().contains("blue"))
            localPlayerCoords = blueCord;
        else
            localPlayerCoords = yellowCord;
    }

    public void localPlayerMoved(int[] incs){
        localPlayerCoords[0] += incs[0];
        localPlayerCoords[1] += incs[1];
    }

    public void setMapInfo(List<ArrayList<ArrayList<String>>> mapInfo){
        //[x][y] -> [blue, red, yellow, powerUp, players...]
        this.mapInfo = mapInfo;
        List<String> ammosTypes = new ArrayList<>();
        ammosTypes.add("red"); ammosTypes.add("blue"); ammosTypes.add("yellow"); ammosTypes.add("powerUp");

        ObservableList<Node> cells = squaresGrid.getChildren();
        for(int i = 0; i < cells.size(); i++){
            int x = GridPane.getRowIndex(cells.get(i)), y = GridPane.getColumnIndex(cells.get(i));
            if(!mapInfo.get(x).get(y).get(0).equals("NR")){
                AnchorPane pane = (AnchorPane)cells.get(i);
                Pane inRoomPane = (Pane)pane.getChildren().get(0);


                // AMMOS AND WEAPONS PART
                int playerFirstIndex = 0;
                if(ammosTypes.contains(mapInfo.get(x).get(y).get(0))){
                    //AMMO SQUARE
                    String[] ammoAmount = {"0", "0", "0", "0"};
                    for(int z = 0; z < 3; z++){
                        int index = ammosTypes.indexOf(mapInfo.get(x).get(y).get(z));
                        ammoAmount[index] = String.valueOf(Integer.getInteger(ammoAmount[index])+1);
                    }
                    String ammoName = ammoAmount[0]+ammoAmount[1]+ammoAmount[2]+ammoAmount[3];
                    ImageView ammoImage = (ImageView)pane.getChildren().get(1);
                    ammoImage.setImage(new Image(getURLOfImage("images/ammo/" + ammoName + ".png")));
                    playerFirstIndex = 3;
                }else if(playersInfo.isPresent(mapInfo.get(x).get(y).get(0))){
                    //AMMO SQUARE WITHOUT AMMOS
                    ImageView ammoImage = (ImageView)pane.getChildren().get(1);
                    ammoImage.setImage(new Image(getURLOfImage("images/ammo/0000.png")));
                }else{
                    //SPAWN SQUARE
                    playerFirstIndex = Integer.getInteger(mapInfo.get(x).get(y).get(0))+1;
                    HBox toUpdateWeaponsBox = null;
                    String[] toUpdateNames = null;
                    if(blueCord == new int[] {x, y}){
                        toUpdateWeaponsBox = blueWeaponsBox;
                        blueWeapons = new String[Integer.getInteger(mapInfo.get(x).get(y).get(0))];
                        for(int z = 0; z < Integer.getInteger(mapInfo.get(x).get(y).get(0)); i++)
                            blueWeapons[z] = mapInfo.get(x).get(y).get(z+1);
                        toUpdateNames = blueWeapons;
                    }else if(redCord == new int[] {x, y}){
                        toUpdateWeaponsBox = redWeaponsBox;
                        redWeapons = new String[Integer.getInteger(mapInfo.get(x).get(y).get(0))];
                        for(int z = 0; z < Integer.getInteger(mapInfo.get(x).get(y).get(0)); i++)
                            redWeapons[z] = mapInfo.get(x).get(y).get(z+1);
                        toUpdateNames = redWeapons;
                    }else if(yellowCord == new int[] {x, y}){
                        toUpdateWeaponsBox = yellowWeaponsBox;
                        yellowWeapons = new String[Integer.getInteger(mapInfo.get(x).get(y).get(0))];
                        for(int z = 0; z < Integer.getInteger(mapInfo.get(x).get(y).get(0)); i++)
                            yellowWeapons[z] = mapInfo.get(x).get(y).get(z+1);
                        toUpdateNames = yellowWeapons;
                    }

                    for(int z = 0; z < 3; z++){
                        if(z < toUpdateNames.length)
                            ((ImageView)toUpdateWeaponsBox.getChildren().get(z)).setImage(new Image(getURLOfImage("images/cards/" + toUpdateNames[z] + ".png")));
                        else
                            ((ImageView)toUpdateWeaponsBox.getChildren().get(z)).setImage(new Image(getURLOfImage("images/cards/AD_weapons_IT_0225.png")));
                    }
                }

                //PLAYERS PART
                int playersPresent = mapInfo.get(x).get(y).size() - playerFirstIndex;
                for(int z = 0; z < inRoomPane.getChildren().size(); z++){
                    if(z < playersPresent){
                        ((ImageView)inRoomPane.getChildren().get(z)).setImage(new Image(getURLOfImage("images/characters/" + playersInfo.findFolder(mapInfo.get(x).get(y).get(z+playerFirstIndex)) + "/icon.png")));
                        inRoomPane.getChildren().get(z).setVisible(true);
                    }else
                        inRoomPane.getChildren().get(z).setVisible(false);
                }

                cells.get(i).setVisible(true);
            }else
                cells.get(i).setVisible(false);
        }
        squaresGrid.setVisible(true);
    }

    public void setKillScoreBoardInfo(List<ArrayList<String>> killScoreBoardInfo) {
        // [0] -> kill+overkill?
        // [1] -> doublekill
        this.killScoreBoardInfo = killScoreBoardInfo;
        Integer maxDeaths = 5, shownDeaths = 0;
        ObservableList<Node> killsSegs = deathsBox.getChildren();

        //DEATHS

        for(int i = 0; i < killsSegs.size() - maxDeaths - 1; i++)
            killsSegs.get(i).setVisible(false);
        for(int i = killsSegs.size() - maxDeaths - 1; i < killsSegs.size()-1; i++){
            Pane currentPane = (Pane)killsSegs.get(i);
            if(shownDeaths < killScoreBoardInfo.get(0).size()){
                //kills in range of maxDeaths
                ((ImageView)currentPane.getChildren().get(1)).setImage(new Image(getURLOfImage("/images/" + playersInfo.findFolder(killScoreBoardInfo.get(0).get(shownDeaths)) + "/damage.png")));
                ((ImageView)currentPane.getChildren().get(2)).setImage(new Image(getURLOfImage("/images/" + playersInfo.findFolder(killScoreBoardInfo.get(0).get(shownDeaths)) + "/damage.png")));
                currentPane.getChildren().get(0).setVisible(false);
                currentPane.getChildren().get(1).setVisible(true);
                currentPane.getChildren().get(2).setVisible(Boolean.valueOf(killScoreBoardInfo.get(0).get(shownDeaths+1)));
                shownDeaths+=2;
            }else{
                currentPane.getChildren().get(0).setVisible(true);
                currentPane.getChildren().get(1).setVisible(false);
                currentPane.getChildren().get(2).setVisible(false);
            }
        }

        if(shownDeaths < killScoreBoardInfo.get(0).size()-1){
            for(int i = 0; i < killScoreBoardInfo.get(0).size(); i++){
                if(i+shownDeaths < killScoreBoardInfo.get(0).size()){
                    ((ImageView)((Pane)(killsSegs.get(killsSegs.size()-1))).getChildren().get(i)).setImage(new Image(getURLOfImage("/images/" + playersInfo.findFolder(killScoreBoardInfo.get(0).get(shownDeaths+i)) + "/damage.png")));
                    ((Pane)(killsSegs.get(killsSegs.size()-1))).getChildren().get(i).setVisible(true);
                }else
                    ((Pane)(killsSegs.get(killsSegs.size()-1))).getChildren().get(i).setVisible(false);
            }
            (killsSegs.get(killsSegs.size()-1)).setVisible(true);
        }else
            (killsSegs.get(killsSegs.size()-1)).setVisible(false);

        //OVERKILLS

        for(int i = 0; i < doubleKillPane.getChildren().size(); i++){
            if(i < killScoreBoardInfo.get(1).size()){
                ((ImageView)doubleKillPane.getChildren().get(i)).setImage(new Image(getURLOfImage("/images/" + playersInfo.findFolder(killScoreBoardInfo.get(1).get(i)) + "/damage.png")));
                doubleKillPane.getChildren().get(i).setVisible(true);
            }else
                doubleKillPane.getChildren().get(i).setVisible(false);
        }

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
