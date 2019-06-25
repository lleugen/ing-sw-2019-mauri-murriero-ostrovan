//package it.polimi.se2019.view.player;
//
//import javax.swing.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class GUISpawnLocation extends GUIGenericWindow {
//
//    List<JButton> actionButtons;
//    String result;
//
//    public GUISpawnLocation(){
//        super();
//        result = "";
//    }
//
//    @Override
//    public void populate(List<String> itemsToShow) {
//        actionButtons = new ArrayList<>();
//        //show to the view the powerups he can discard to spawn on the same colour spawn square.
//        for(int i = 0; i < itemsToShow.size(); i++){
//            //TO DO: CREATE VIEW WITH BUTTONS
//            //currentBtn.setName(i.toString());
//        }
//        setVisible(true);
//    }
//
//    @Override
//    public synchronized List<String> askAndRequest(List<String> choices) {
//        List<String> temp = new ArrayList<>();
//        populate(choices);
//
//        waitForButtonPress(actionButtons);
//
//        temp.add(result);
//        return temp;
//    }
//
//    /*@Override
//    protected void waitForButtonPress(List<JButton> confirmButtons) {
//        lockMutex();
//        Integer i;
//        for(i = 0; i < confirmButtons.size(); i++){
//            //Associate button to his action by name, when clicked it unlocks the mutex and submit the answer
//            JButton currentBtn = confirmButtons.get(i);
//            currentBtn.addActionListener(e -> {
//                result = currentBtn.getName();
//                unlockMutex();
//            });
//        }
//        waitMutex();
//
//        return;
//    }*/
//}
