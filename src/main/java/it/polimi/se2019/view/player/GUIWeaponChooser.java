package it.polimi.se2019.view.player;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GUIWeaponChooser extends GUIGenericWindow {

    private List<JButton> actionButtons;
    String result = "";

    public GUIWeaponChooser(){
        super();
    }

    @Override
    public void populate(List<String> itemsToShow) {
        actionButtons = new ArrayList<>();
        //show to the client each weapon he can choose
        for(int i = 0; i < itemsToShow.size(); i++){
            //TO DO: CREATE VIEW WITH BUTTONS FOR EACH WEAPON
            //itemsToShow(i)
        }
        setVisible(true);
    }

    @Override
    public synchronized List<String> askAndRequest(List<String> choices) {
        populate(choices);
        waitForButtonPress(actionButtons);
        List<String> temp = new ArrayList<>();
        temp.add(result);
        return temp;
    }

    /*@Override
    protected void waitForButtonPress(List<JButton> confirmButtons) {
        lockMutex();
        Integer i;
        for(i = 0; i < confirmButtons.size(); i++){
            //Associate button to his action by name, when clicked it returns his name on result global variable
            JButton currentBtn = confirmButtons.get(i);
            //currentBtn.setName(i.toString()); //unneeded, name is assigned in the populate function
            currentBtn.addActionListener(e -> {
                result = currentBtn.getName();
                unlockMutex();
            });
        }

        waitMutex();

        return;
    }*/
}
