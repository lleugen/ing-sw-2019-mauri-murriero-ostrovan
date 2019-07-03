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
import java.util.List;
import java.util.LinkedList;
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
   * Timeout (in seconds) before starting games in ServerLobby
   */
  private int lobbyTimeout;

  /**
   * Creates a new Server
   *
   * @param host          Hostname the registry is located to
   * @param lobbyTimeout  Timeout (in seconds) before starting a game
   */
  public Server(String host, int lobbyTimeout) throws RemoteException {
    Registry registry = LocateRegistry.getRegistry(host);
    registry.rebind("//" + host + "/server",
            UnicastRemoteObject.exportObject(this, 0)
    );

    this.hostname = host;
    this.lobbyTimeout = lobbyTimeout;
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

      this.registerPlayer(p);
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
   * Register a new player to the server.
   *
   * If an open lobby is available, it is added to that lobby.
   * If there are no lobbies availables, a new lobby is created and the player
   * is added to the newly created lobby
   *
   * @param p VirtualView of the player to add to the lobby
   *
   * @throws UserTimeoutException if the user doesn't answer on time to a server
   *                              request.
   *                              If this exception is raised, the player is
   *                              not added to the lobby
   */
  private synchronized void registerPlayer(PlayerViewOnServer p)
          throws UserTimeoutException {
    while (
            (this.lobbyes.isEmpty()) ||
            !(this.lobbyes.get(0).addPlayer(p, p.getName(), p.getCharacter()))
    ) {
      Integer selectedMap = 0;
      try {
        selectedMap = p.chooseMap();
        this.lobbyes.add(new ServerLobby(selectedMap, this.lobbyTimeout));
      }
      catch (UnknownMapTypeException e){
        Logger.getLogger(LOG_NAMESPACE).log(
                Level.INFO,
                "User choose an unknown map type <{0}>",
                selectedMap
        );
      }
    }
  }
}
