package it.polimi.se2019.view.map;

import javax.swing.*;

public class GUIView implements RemoteView {

    private JFrame mapWindow;

    public void main(String[] args){
        //mapWindow = new JFrame();
        JDialog w = new JDialog();
        w.setSize(200, 40);
        w.setName("My first JDialog");
        w.setTitle("Uela come butta?");
        w.setVisible(true);
    }

    @Override
    public void ShowMap(){

    }

    @Override
    public void ShowPlayers(){

    }

    @Override
    public void ShowInventory(){

    }
}
