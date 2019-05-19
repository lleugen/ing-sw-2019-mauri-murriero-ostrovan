package it.polimi.se2019.view.player;

import sun.awt.Mutex;

import javax.swing.*;
import java.util.List;

public abstract class GUIGenericWindow {

    /**
     * frame relative to this interactive window
     */
    protected JFrame frame;

    /**
     * mutex relative to interactions
     * every time the user asks for a choice, the askAndRequest function display the frame and wait for the "Confirm" button to get
     * pressed, who will unlock the function and let return what the user has request
     */
    private Mutex mutex;

    public GUIGenericWindow(){
        frame = new JFrame();
        mutex = new Mutex();
        mutex.unlock();
    }

    /**
     * Populate the frame with his own JComponents and shows
     */
    public abstract void populate(List<String> itemsToShow);

    /**
     * shows the frame to the client and waits until the client has selected what he has to select
     *
     * @param choices list of possible selection asked by the server
     * @return sublist of choices
     */
    public abstract List<String> askAndRequest(List<String> choices);

    /**
     * puts an ActionHandler to the selected button and waits to that button to be clicked to unlock
     *
     * @param confirmButtons buttons who has to be clicked to unlock the function
     */
    protected abstract void waitForButtonPress(List<JButton> confirmButtons);

    public boolean isVisible(){
        return frame.isVisible();
    }

    public void setVisible(boolean state){
        frame.setVisible(state);
    }

    protected void lockMutex(){
        mutex.lock();
    }

    protected void unlockMutex(){
        mutex.unlock();
    }

    protected void waitMutex() throws InterruptedException{
        mutex.wait();
    }

    public void dispose(){
        frame.dispose();
    }
}
