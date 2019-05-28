package it.polimi.se2019.view.player;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_COLOR_BURNPeer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GUIPowerUpsChooser extends GUIGenericWindow {

    JCheckBox checkBox;
    JButton submitButton;

    public GUIPowerUpsChooser(){
        super();
        result = "";
    }

    @Override
    public void populate(List<String> itemsToShow) {
        submitButton = new JButton();
        //show to the client each powerUp he held
        for(int i = 0; i < itemsToShow.size(); i++){
            //TO DO: CREATE VIEW WITH CHECKBOX
        }
        setVisible(true);
    }

    @Override
    public synchronized List<String> askAndRequest(List<String> choices) {
        List<JButton> actionButtons = new ArrayList<>();

        populate(choices);

        actionButtons.add(submitButton);
        waitForButtonPress(actionButtons);

        List<String> temp = new ArrayList<>();
        for(Integer i = 0; i < checkBox.getComponentCount(); i++){
            if(checkBox.getComponent(i).isCursorSet())
                temp.add(i.toString());
        }
        return temp;
    }

}
