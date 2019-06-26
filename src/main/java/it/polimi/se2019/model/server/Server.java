package it.polimi.se2019.model.server;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Server implements Remote {
  /**
   * Contains a reference to every lobby currently active
   */
  private Map<String, ServerLobby> lobbyMap;

  /**
   * Creates a new Server
   */
  public Server(){
    if (System.getSecurityManager() == null) {
      System.setSecurityManager(new SecurityManager());
    }

    this.lobbyMap = Collections.synchronizedMap(new HashMap<>());
  }

  /**
   * Gets a lobby from the server
   *
   * @param id Id of the lobby to get
   *
   * @return The searched lobby
   *
   * @throws LobbyNotFoundException if the id is not found
   */
  public ServerLobby getLobby(String id){
    if (this.lobbyMap.containsKey(id)) {
      return this.lobbyMap.get(id);
    }
    else {
      throw new LobbyNotFoundException();
    }
  }

  /**
   * Publish the object to the RMI registry, to the given key, at the given port
   *
   * @param key The key to publish the server to
   * @param port The port to publish the server to
   *
   * @throws RMIInitializationException if errors occur when initialization the
   *                                RMI registry
   */
  public void publishToRMI(String key, Integer port){
    try {
      LocateRegistry.getRegistry().rebind(
              key,
              UnicastRemoteObject.exportObject(
                      this, port
              )
      );
    }
    catch (java.rmi.RemoteException e){
      throw new RMIInitializationException(e);
    }
  }

  /**
   * Creates a new lobby
   *
   * @param id The id to assign the new lobby to
   *
   * @throws LobbyConflictException if the id is already registered
   */
  public void createLobby(String id){
    ServerLobby result;
    result = this.lobbyMap.putIfAbsent(
            id,
            new ServerLobby(
                    5,
                    1
            )
    );

    if (result != null){
      throw new LobbyConflictException();
    }
  }

  /**
   * Thrown when there are errors while connecting to the RMI registry
   */
  public static class RMIInitializationException extends RuntimeException{
    RMIInitializationException(Exception e){
      super(e);
    }

    @Override
    public String toString() {
      return "Unable to start RMI registry";
    }
  }

  /**
   * Thrown when a searched lobby is not found
   */
  public static class LobbyNotFoundException extends RuntimeException{
    @Override
    public String toString() {
      return "The searched lobby doesn't exists";
    }
  }

  /**
   * Thrown when trying to create a lobby with an already used id
   */
  public static class LobbyConflictException extends RuntimeException{
    @Override
    public String toString() {
      return "A lobby with this id already exists";
    }
  }
}
