package it.polimi.se2019.view;

import it.polimi.se2019.model.server.ServerLobby;
import it.polimi.se2019.view.player.PlayerView;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

    public static void main(String [] args){
        PlayerView client = new PlayerView();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose name.");
        client.setName(scanner.nextLine());
        System.out.println("Choose character.");
        client.setCharacter(scanner.nextLine());
        //client.generateLoginInfo(this);
        findLobby(client);
    }

    public static void findLobby(PlayerView client){
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
