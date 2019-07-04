package it.polimi.se2019.view.GUIcontrollers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;

/**
 * Window controller for the chooser of a particular map square from the given ones
 *
 * @author Riccardo Murriero
 */
public class GUISquareChooser extends GUIGenericWindow {

    @FXML
    private ImageView mapImage;

    @FXML
    private GridPane squaresGrid;

    private Boolean[][] squaresMat;
    private String mapName;

    public GUISquareChooser(String mapName, List<List<Integer>> targetsSquares){
        this.mapName = mapName;
        squaresMat = new Boolean[3][4];
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 4; j++)
                squaresMat[i][j] = targetsSquares.get(i).contains(j);
    }

    @Override
    void initialize(){
        mapImage.setImage(new Image(getURLOfImage("images/maps/" + mapName + "/squaredmap.png")));
        //may need viewport, verify.
        ObservableList<Node> nodes = squaresGrid.getChildren();
        for(int i = 0; i < nodes.size(); i++){
            Pane currentPane = (Pane)nodes.get(i);
            Integer x = GridPane.getRowIndex(currentPane);
            Integer y = GridPane.getColumnIndex(currentPane);
            if(squaresMat[x][y]){
                Button btn = (Button)currentPane.getChildren().get(1);
                setOnActionEffect(btn, x.toString() + "_" + y.toString());
            }
            (currentPane.getChildren().get(0)).setVisible(!squaresMat[x][y]);
            (currentPane.getChildren().get(1)).setVisible(squaresMat[x][y]);
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
