package it.polimi.se2019.view.player;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GUIPlayersNumber extends GUIGenericWindow {

    JComboBox combo;
    JButton submitButton;
    String result;

    public GUIPlayersNumber(){
        super();
        result = "";
    }

    @Override
    public void populate(List<String> itemsToShow) {
        submitButton = new JButton();
        combo = new JComboBox(itemsToShow.toArray());
        //show to the client combobox which contains the possible number of players
        for(int i = 0; i < itemsToShow.size(); i++){
            //TO DO: CREATE VIEW WITH COMBOBOX
            //no need to rename
        }
        //TO DO: CREATE SUBMIT BUTTON
        setVisible(true);
    }

    @Override
    public synchronized List<String> askAndRequest(List<String> choices) {
        List<String> temp = new ArrayList<>();
        List<JButton> temp1 = new ArrayList<>();

        populate(choices);

        temp1.add(submitButton);
        waitForButtonPress(temp1);

        result = combo.getSelectedItem().toString();
        temp.add(result);
        return temp;
    }

    /*@Override
    protected void waitForButtonPress(List<JButton> confirmButtons) {
        lockMutex();
        Integer i;
        for(i = 0; i < confirmButtons.size(); i++){
            //Associate button to his action by name, when clicked it unlocks the mutex
            JButton currentBtn = confirmButtons.get(i);
            currentBtn.setName(i.toString());
            currentBtn.addActionListener(e -> {
                unlockMutex();
            });
        }

        waitMutex();

        return;
    }*/
}
