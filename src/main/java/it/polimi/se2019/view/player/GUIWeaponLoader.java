package it.polimi.se2019.view.player;

import javax.swing.*;
import java.util.List;

public class GUIWeaponLoader extends GUIGenericWindow {

    /**
     * used to show to the player witch unloaded weapons he has and let him choose witch one he wants to reload
     */
    public GUIWeaponLoader(){
        super();
    }

    @Override
    public void populate(List<String> itemsToShow) {
        //TO DO
        setVisible(true);
    }

    @Override
    public synchronized List<String> askAndRequest(List<String> choices) {
        populate(choices);
        //TO DO
        return null;
    }

    @Override
    protected void waitForButtonPress(List<JButton> confirmButtons) {
        //TO DO
    }
}
