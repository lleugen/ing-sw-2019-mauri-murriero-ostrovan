package it.polimi.se2019.model.server;

import it.polimi.se2019.rmi.ServerLobbyInterface;
import it.polimi.se2019.rmi.UserTimeoutException;
import it.polimi.se2019.rmi.ViewFacadeInterfaceRMIClient;
import it.polimi.se2019.model.map.UnknownMapTypeException;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.rmi.server.RemoteServer.getClientHost;

/**
 * @author Fabio Mauri
 */
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
   * Contains the mapping between an user id and a lobby an user should be
   * registered to
   */
  private ConcurrentHashMap<String, ServerLobby> userLobbyMap = new ConcurrentHashMap<>();

  /**
   * Timeout (in seconds) before starting games in ServerLobby
   */
  private int lobbyTimeout;

  /**
   * Timeout (in seconds) before disconnecting an user
   */
  private int disconnectionTimeout;

  /**
   * Creates a new Server
   *
   * @param host                  Hostname the registry is located to
   * @param lobbyTimeout          Timeout (in seconds) before starting a game
   * @param disconnectionTimeout  Timeout (in seconds) before starting a game
   * @throws RemoteException      if there is an error in the RMI connection
   * @throws UnknownHostException if the hostname cannot be resolved
   */
  public Server(String host, int lobbyTimeout, int disconnectionTimeout) throws RemoteException, UnknownHostException {
    System.setProperty(
            "java.rmi.server.hostname",
            InetAddress.getByName(host).getHostAddress()
    );

    LocateRegistry.createRegistry(1099).rebind("server",
            UnicastRemoteObject.exportObject(this, 0)
    );

    this.lobbyTimeout = lobbyTimeout;
    this.disconnectionTimeout = disconnectionTimeout;
    if (System.getSecurityManager() == null) {
      System.setSecurityManager(new SecurityManager());
    }
  }

  /**
   * Make the connection to the server
   * The server will lookup for an RMI registry on the ip the
   * client used to connect (see {@link #getIp()}), on the port
   * passed as parameter, at the reference passed as parameter.
   *
   * @param ref  The reference the server should use to retrieve a view to
   *             interact with.
   * @param port Port the registry is exposed on
   *
   * @throws RemoteException If something goes wrong with RMI
   */
  @Override
  public void connect(String ref, int port) throws RemoteException {
    System.out.println("connect");
    try {
      ViewFacadeInterfaceRMIClient userView = (ViewFacadeInterfaceRMIClient) LocateRegistry
              .getRegistry(getClientHost(),port)
              .lookup(ref);
      System.out.println("registering out");
      this.registerPlayer(userView);
    }
    catch (ServerNotActiveException | NotBoundException | UserTimeoutException e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.WARNING,
              "Unable to initialize user",
              e
      );
    }
  }

  /**
   * @return The ip (viewed by the server) of the connecting client. This
   *         can then be used to properly create an RMI Registry.
   *         Null if for some reason the ip can not retrieved
   *
   * @throws RemoteException If something goes wrong with RMI
   */
  @Override
  public String getIp() throws RemoteException {
    try {
      return getClientHost();
    }
    catch (ServerNotActiveException e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.WARNING,
              "The server is not active",
              e
      );
      return null;
    }
  }

  /**
   * Register a new player to the server.
   *
   * If an open lobby is available, it is added to that lobby.
   * If there are no lobbies availables, a new lobby is created and the player
   * is added to the newly created lobby
   *
   * @param v VirtualView of the player to add to the lobby
   *
   * @throws UserTimeoutException if the user doesn't answer on time to a server
   *                              request.
   *                              If this exception is raised, the player is
   *                              not added to the lobby
   * @throws RemoteException If something goes wrong with RMI
   */
  private void registerPlayer(ViewFacadeInterfaceRMIClient v)
          throws UserTimeoutException, RemoteException {
    System.out.println("Starting Registering");
    if (
            this.userLobbyMap.containsKey(v.getName()) &&
            this.userLobbyMap.get(v.getName()).isGameRunning()
    ){
      // Reconnecting user
      PlayerViewOnServer.registerPlayer(v.getName(), v);
    }
    else {
      // Creating a new user
      PlayerViewOnServer p = new PlayerViewOnServer(v, this.disconnectionTimeout);

      if (!this.lobbyes.isEmpty()){
        this.userLobbyMap.put(v.getName(), this.lobbyes.get(0));
      }

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
        this.userLobbyMap.put(v.getName(), this.lobbyes.get(0));
      }
    }
  }
}
