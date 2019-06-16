package it.polimi.se2019.view;

import it.polimi.se2019.communication.encodable.AbstractEncodable;
import it.polimi.se2019.communication.encodable.LoginDataEncodable;
import it.polimi.se2019.communication.encodable.ModelViewUpdateEncodable;
import it.polimi.se2019.communication.encodable.ServerPlayer;
import it.polimi.se2019.communication.network.Connection;
import it.polimi.se2019.communication.network.InitializationError;
import it.polimi.se2019.communication.network.client.socket.SocketClientNetwork;
import it.polimi.se2019.communication.network_handler.NetworkHandler;
import it.polimi.se2019.model.server.ServerLobby;
import it.polimi.se2019.view.player.PlayerView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;

public class Client {

    private PlayerView client;
    private static JFrame loginFrame;
    private String name, character;

//    public static void main(String args[]){
//        Client client = new Client();
//        //client = new PlayerView();
//        //client.generateLoginInfo(this);
//        client.generateLoginInfo();
//    }

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
//            lobby.connect(client, client.getName(), client.getCharacter());
        } catch (Exception e) {
            System.err.println("client exception:");
            e.printStackTrace();
        }
    }

  /**
   *
   * @param args Map of parameters from the command line:
   *             login -> Id of the user to log in with
   *             host -> Host to connect to
   *             port -> Port on host to connect to
   */
  public Client(Map<String, String> args){
    try {
      if (
              !args.containsKey("login") ||
              !args.containsKey("host") ||
              !args.containsKey("port")
      ){
        throw new InitializationError();
      }

      SocketClientNetwork tmp;

      try {
        tmp = new SocketClientNetwork(
                args.get("host"),
                Integer.parseInt(args.get("port"))
        );
      }
      catch (NumberFormatException e){
        throw new InitializationError(e);
      }

      try {
        tmp.getConnection().send(
          new LoginDataEncodable(
                  args.get("login")
          ).encode()
        );
        tmp.getConnection().receive();
      }
      catch (Connection.DisconnectedError e){
        System.out.println(e.toString());
      }

      NetworkHandler networkHandler = new NetworkHandler(
             tmp.getConnection(),
             () -> {System.out.println("Server Disconnected");}
      );

      networkHandler.start();
      networkHandler.subscribe(
              ".dashboard",
              (ModelViewUpdateEncodable update) -> {
                      ServerPlayer p = new ServerPlayer();
                      try {
                        p.decode(update.getModel());
                      }
                      catch (AbstractEncodable.InvalidDataException e){
                        System.out.println(e.toString());
                      }
                      System.out.print("\033\143");
                      System.out.println("Logged in as: " + p.getId());
                      System.out.println("Players online:");
                      p.getCps().forEach(
                              pl -> System.out.println("> " + pl)
                      );
              }
      );
    }
    catch (InitializationError e){
       System.out.println(e);
    }
  }
}
