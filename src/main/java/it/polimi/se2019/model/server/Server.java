package it.polimi.se2019.model.server;

import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.controller.PlayerController;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerView;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Server {
    private List<Player> players;
    private List<PlayerController> playerControllers;
    private List<PlayerView> clients;
    private int mapType;
    private int numberOfPlayers;
    private GameBoardController gameBoard;

    public static void main(){
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "ServerLobby";
            ServerLobby lobby = new ServerLobby();
            ServerLobby stub =
                    (ServerLobby) UnicastRemoteObject.exportObject(lobby, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("lobby bound to name");
        } catch (Exception e) {
            System.err.println("lobby exception:");
            e.printStackTrace();
        }
    }

    /**
     * This method creates a player class (model) for each player who joins the
     * game and adds it to the "players" list in the game board model.
     * A player is created with an inventory containing only one power up
     * card and a player board with empty damage and marks lists,
     * 0 deaths, "8 6 4 2 1 1" death value and board side '0'.
     */
    }
    public void createNewPlayer(PlayerView client){
        if(clients.isEmpty()){
            mapType = client.chooseMap();
            numberOfPlayers = client.chooseNumberOfPlayers();
        }
        clients.add(client);
        Player newPlayer = new Player(client.getName(), client.getCharacter());
        players.add(newPlayer);
        if(players.size() == numberOfPlayers){
            gameBoard = new GameBoardController(players,)
            for(int i = 0; i<numberOfPlayers; i++){
                playerControllers.add(new PlayerController(gameBoard, players.get(i), clients.get(i)))
            }
    }
}
