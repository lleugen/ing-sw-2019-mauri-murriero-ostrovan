//package it.polimi.se2019.view.player;
//
//import javax.swing.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ActionSetView extends GUIGenericWindow{
//
//  private List<JButton> actionButtons;
//  private String result;
//  /**
//   * Shows the player the current actions that he can select and wait for a selection
//   */
//  public ActionSetView() {
//    super();
//    result = "";
//  }
//
//  @Override
//  public void populate(List<String> itemsToShow) {
//    String[] btnsNames;
//    if(itemsToShow.get(0).equals("NormalState")){
//      btnsNames = new String[] {"shoot1", "grab1", "run1"};
//    }else if(itemsToShow.get(0).equals("Adrenaline1State")){
//      btnsNames = new String[] {"shoot1", "grab2", "run1"};
//    }else if(itemsToShow.get(0).equals("Adrenaline2State")){
//      btnsNames = new String[] {"shoot3", "grab2", "run1"};
//    }else if(itemsToShow.get(0).equals("FirstFreneticState")){
//      btnsNames = new String[] {"shoot4", "grab4", "run4"};
//    }else{  /* if(itemsToShow.get(0).equals("SecondFreneticState"))*/
//      btnsNames = new String[] {"shoot5", "grab5"};
//    }
//
//    actionButtons = new ArrayList<>();
//    for(int i = 0; i < btnsNames.length; i++){
//      //TO DO: CREATE VIEW WITH BUTTONS
//    }
//    setVisible(true);
//  }
//
//  @Override
//  public synchronized List<String> askAndRequest(List<String> choices) {
//    populate(choices);
//    //do{
//    waitForButtonPress(actionButtons);
//    //}while(result.equals(""));
//    List<String> temp = new ArrayList<>();
//    temp.add(result);
//    return temp;
//  }
//
//  @Override
//  protected synchronized void waitForButtonPress(List<JButton> confirmButtons) {
//    String[] answers = new String[] {"shoot", "grab", "run"};
//    lockMutex();
//    for(int i = 0; i < confirmButtons.size(); i++){
//      //Associate button to his action by name, when clicked it returns his name on result global variable
//      JButton currentBtn = confirmButtons.get(i);
//      currentBtn.setName(answers[i]);
//      currentBtn.addActionListener(e -> {
//        result = currentBtn.getName();
//        unlockMutex();
//      });
//    }
//
//    waitMutex();
//
//    return;
//  }
//
//}