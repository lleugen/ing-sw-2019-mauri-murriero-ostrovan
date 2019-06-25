//package it.polimi.se2019.view.player;
//
////import sun.awt.Mutex;
////import java.util.concurrent.*;
//import javax.swing.*;
//import java.util.List;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;
//
//public abstract class GUIGenericWindow {
//
//    /**
//     * frame relative to this interactive window
//     */
//    protected JFrame frame;
//
//    /**
//     * mutex relative to interactions
//     * every time the user asks for a choice, the askAndRequest function display the frame and wait for the "Confirm" button to get
//     * pressed, who will unlock the function and let return what the user has request
//     */
//    //private Mutex mutex;
//    private Lock lock;
//
//    /**
//     * useful variable containing the answer from this window
//     */
//    protected String result;
//
//    public GUIGenericWindow(){
//        frame = new JFrame();
//        lock = new ReentrantLock();
//        lock.unlock();
//        //mutex = new Mutex();
//        //mutex.unlock();
//    }
//
//    /**
//     * Populate the frame with his own JComponents and shows
//     */
//    public abstract void populate(List<String> itemsToShow);
//
//    /**
//     * shows the frame to the view and waits until the view has selected what he has to select
//     *
//     * @param choices list of possible selection asked by the controller
//     * @return sublist of choices
//     */
//    public abstract List<String> askAndRequest(List<String> choices);
//
//    /**
//     * puts an ActionHandler to the selected button and waits to that button to be clicked to unlock
//     * saves the name of the clicked button in the "result" variable
//     *
//     * @param confirmButtons buttons who has to be clicked to unlock the function
//     */
//    protected void waitForButtonPress(List<JButton> confirmButtons){
//        lockMutex();
//        Integer i;
//        for(i = 0; i < confirmButtons.size(); i++){
//            JButton currentBtn = confirmButtons.get(i);
//            currentBtn.addActionListener(e -> {
//                result = currentBtn.getName();
//                unlockMutex();
//            });
//        }
//
//        waitMutex();
//
//        return;
//    }
//
//    public boolean isVisible(){
//        return frame.isVisible();
//    }
//
//    public void setVisible(boolean state){
//        frame.setVisible(state);
//    }
//
//    protected void lockMutex(){
//        //mutex.lock();
//        lock.lock();
//    }
//
//    protected void unlockMutex(){
//        lock.unlock();
//    }
//
//    protected void waitMutex(){
//        try {
//            //mutex.wait();
//            lock.wait();
//        }catch(InterruptedException e){
//            System.out.print("Mutex interrupt: " + e.getMessage());
//            Thread.currentThread().interrupt();
//        }
//    }
//
//    public void dispose(){
//        frame.dispose();
//    }
//}
