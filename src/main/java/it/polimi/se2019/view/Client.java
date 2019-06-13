package it.polimi.se2019.view;

import it.polimi.se2019.model.server.ServerLobby;
import it.polimi.se2019.view.player.PlayerView;

import javax.swing.*;
import java.awt.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    private PlayerView client;
    private static JFrame loginFrame;
    private String name, character;

    public static void main(String args[]){
        Client client = new Client();
        //client = new PlayerView();
        //client.generateLoginInfo(this);
        client.generateLoginInfo();
    }

    public void generateLoginInfo(){
        JTextField playerNameField = new JTextField("min 6 chars", 20);
        JComboBox<String> characterCombo = new JComboBox<>(new String[] {"Banshee", ":D-STRUTT-OR3", "Dozer", "Sprog", "Violetta"});
        JButton confirmButton = new JButton("Log in");
        confirmButton.addActionListener(e -> {
            if(playerNameField.getText().length() >= 6){
                this.name = playerNameField.getText();
                this.character = (String) characterCombo.getSelectedItem();
                loginFrame.setVisible(false);
                this.findLobby();
            }
        });

        loginFrame = new JFrame("Adrenalina - Log in");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setResizable(false);
        loginFrame.setLayout(new FlowLayout());
        loginFrame.add(new JTextArea("player name:"));
        loginFrame.add(playerNameField);
        loginFrame.add(new JTextArea("character:"));
        loginFrame.add(characterCombo);
        loginFrame.add(confirmButton);
        loginFrame.setVisible(true);
    }

    public void findLobby(){
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "ServerLobby";
            Registry registry = LocateRegistry.getRegistry();
            ServerLobby lobby = (ServerLobby) registry.lookup(name);
            lobby.connect(client, client.getName(), client.getCharacter());
        } catch (Exception e) {
            System.err.println("client exception:");
            e.printStackTrace();
        }
    }
}
