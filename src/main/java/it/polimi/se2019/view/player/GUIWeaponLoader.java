//package it.polimi.se2019.view.player;
//
//import javax.swing.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class GUIWeaponLoader extends GUIGenericWindow {
//
//    private List<JButton> actionButtons;
//    private List<String> possibleAnswers;
//    private String result;
//    /**
//     * used to show to the player witch unloaded weapons he has and let him choose witch one he wants to reload
//     */
//    public GUIWeaponLoader(){
//        super();
//        result = "";
//    }
//
//    @Override
//    public void populate(List<String> itemsToShow) {
//        actionButtons = new ArrayList<>();
//        possibleAnswers = itemsToShow;
//        possibleAnswers.add("");
//        //show to the view each weapon he can load and a button named "nothing" which means he does not want to reload anything
//        for(int i = 0; i < possibleAnswers.size() - 1; i++){
//            //TO DO: CREATE VIEW WITH BUTTONS FROM WEAPONS
//            //itemsToShow(i)
//        }
//        //TO DO: CREATE BUTTON FOR "NOTHING" WEAPON
//        setVisible(true);
//    }
//
//    @Override
//    public synchronized List<String> askAndRequest(List<String> choices) {
//        populate(choices);
//        //do{
//        waitForButtonPress(actionButtons);
//        //}while(result.equals(""));
//        List<String> temp = new ArrayList<>();
//        temp.add(result);
//        return temp;
//    }
//
//    /*@Override
//    protected void waitForButtonPress(List<JButton> confirmButtons) {
//        lockMutex();
//        Integer i;
//        for(i = 0; i < confirmButtons.size(); i++){
//            //Associate button to his action by name, when clicked it returns his name on result global variable
//            JButton currentBtn = confirmButtons.get(i);
//            currentBtn.setName(i.toString());
//            currentBtn.addActionListener(e -> {
//                result = possibleAnswers.get(Integer.getInteger(currentBtn.getName()));
//                unlockMutex();
//            });
//        }
//
//        waitMutex();
//
//        return;
//    }*/
//}
