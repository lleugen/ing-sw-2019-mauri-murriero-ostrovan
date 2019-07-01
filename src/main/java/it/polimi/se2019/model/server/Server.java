package it.polimi.se2019.model.server;

import it.polimi.se2019.RMI.ServerLobbyInterface;
import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.model.map.UnknownMapTypeException;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements ServerLobbyInterface, Serializable {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "Server";

  /**
   * Contains the list of all lobbies active
   */
  private transient List<ServerLobby> lobbyes = new LinkedList<>();

  /**
   * Hostname the registry is located to
   */
  private final String hostname;

  /**
   * Creates a new Server
   *
   * @param host Hostname the registry is located to
   */
  public Server(String host) throws RemoteException {
    Registry registry = LocateRegistry.getRegistry(host);
    registry.rebind("//" + host + "/server",
            UnicastRemoteObject.exportObject(this, 0)
    );

    this.hostname = host;

    if (System.getSecurityManager() == null) {
      System.setSecurityManager(new SecurityManager());
    }
  }

  /**
   * Handle connection of an user to the server
   *
   * @param user User id of the connected player
   */
  @Override
  public synchronized void connect(String user){
    try {
      PlayerViewOnServer p = new PlayerViewOnServer(user, this.hostname);
      p.setName(user);

      if (this.lobbyes.isEmpty() || this.lobbyes.get(0).checkRoomFull()) {
        while (!this.addLobby(p)) {
          Logger.getLogger(LOG_NAMESPACE).log(
                  Level.INFO,
                  "Recreating lobby"
          );
        }
      }

      if (!this.lobbyes.isEmpty()) {
        this.lobbyes.get(0).connect(
                p,
                p.getName(),
                p.getCharacter()
        );
      }
    }
    catch (PlayerViewOnServer.InitializationError | UserTimeoutException e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.WARNING,
              "Unable to initialize user",
              e
      );
    }
  }

  /**
   * Add a new lobby to the server
   *
   * @param p User that should initialize the map
   *
   * @return true on success (the lobby will be add at place 0), false on error
   *
   * @throws UserTimeoutException If the user does not respond on time
   */
  private boolean addLobby(PlayerViewOnServer p) throws UserTimeoutException {
    try {
      this.lobbyes.add(
              0,
              new ServerLobby(
                      p.chooseNumberOfPlayers(),
                      p.chooseMap()
              )
      );
      return true;
    }
    catch (UnknownMapTypeException e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.WARNING,
              "Unknown Map Type",
              e
      );
      return false;
    }
  }
}
