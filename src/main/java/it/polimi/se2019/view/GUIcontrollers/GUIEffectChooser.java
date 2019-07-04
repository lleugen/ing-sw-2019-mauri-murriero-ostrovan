package it.polimi.se2019.view.GUIcontrollers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

/**
 * Window controller for the chooser of a weapon effect to use
 *
 * @author Riccardo Murriero
 */
public class GUIEffectChooser extends GUIGenericWindow {

    @FXML
    private ImageView weaponImage;

    @FXML
    private GridPane effectsGrid;

    private String[] btnsNames;
    private String weaponName;
    /**
     *  Make the player choose witch of the given effects he wants to be used
     *
     * @param weaponName    name of the selected weapon
     * @param effectsList   list of the available effects of the weapon
     *
     */
    public GUIEffectChooser(String weaponName, List<String> effectsList) {
        super();
        btnsNames = effectsList.toArray(new String[0]);
        this.weaponName = weaponName;
        result = 0;
    }

    @Override
    void initialize(){
        weaponImage.setImage(new Image(getURLOfImage("images/cards/" + weaponName + ".png")));
        ObservableList<Node> gridChildren = effectsGrid.getChildren();

        for(Node n : gridChildren){
            int index = (GridPane.getRowIndex(n)*2 + GridPane.getColumnIndex(n));
            if(index < btnsNames.length){
                Button currentBtn = (Button)n;
                currentBtn.setText(btnsNames[index]);
                setOnActionEffect(currentBtn, String.valueOf(index));
                n.setVisible(true);
            }else
                n.setVisible(false);
        }
    }

    @Override
    public Object getResult(){
        return result;
    }

    public void closeWindow(){
        Stage stage = (Stage) weaponImage.getScene().getWindow();
        stage.close();
    }

}
