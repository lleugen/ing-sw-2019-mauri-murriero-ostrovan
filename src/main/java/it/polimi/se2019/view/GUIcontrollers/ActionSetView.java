package it.polimi.se2019.view.GUIcontrollers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Window controller for the chooser of the Action Set
 *
 * @author Riccardo Murriero
 */
public class ActionSetView extends GUIGenericWindow{

  @FXML
  private Label questionLabel;

  @FXML
  private HBox choicesBox;

  @FXML
  private Button skipButton;

  private String[] btnsNames;
  private String charFolder;
  /**
   * Shows the player the current actions that he can select and wait for a selection
   *
   * @param actionSetName   player current state
   * @param charFolder      character images folder, necessary for showing images
   *
   */
  public ActionSetView(String actionSetName, String charFolder) {
    super();
    this.charFolder = charFolder;
    if(actionSetName.equals("NormalState")){
      btnsNames = new String[] {"shoot1", "grab1", "run1"};
    }else if(actionSetName.equals("Adrenaline1State")){
      btnsNames = new String[] {"shoot1", "grab2", "run1"};
    }else if(actionSetName.equals("Adrenaline2State")){
      btnsNames = new String[] {"shoot3", "grab2", "run1"};
    }else if(actionSetName.equals("FirstFreneticState")){
      btnsNames = new String[] {"shoot4", "grab4", "run4"};
    }else{
      btnsNames = new String[] {"shoot5", "grab5"};
    }
    result = "";
  }

  @Override
  void initialize(){
    for(int i = 0; i < choicesBox.getChildren().size(); i++){
      VBox currentChoiceObj = ((VBox)(choicesBox.getChildren().get(i)));
      if(i < btnsNames.length){
        ((ImageView)currentChoiceObj.getChildren().get(0)).setImage(new Image(getURLOfImage("images/characters/" + charFolder + "/" + btnsNames[i] + ".png")));
        ((Button)currentChoiceObj.getChildren().get(1)).setText(btnsNames[i].substring(0, btnsNames[i].length()-1));
        setOnActionEffect(((Button)currentChoiceObj.getChildren().get(1)), ((Button)currentChoiceObj.getChildren().get(1)).getText());
        currentChoiceObj.setVisible(true);
      }else{
        currentChoiceObj.setVisible(false);
      }
    }
    questionLabel.setText("Quale azione vuoi eseguire?");
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