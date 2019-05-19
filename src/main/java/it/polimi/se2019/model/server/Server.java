package it.polimi.se2019.model.server;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Remote {
  /**
   * Creates a new Server
   */
  public Server(){
    if (System.getSecurityManager() == null) {
      System.setSecurityManager(new SecurityManager());
    }
  }

  /**
   * Publish the object to the RMI registry, to the given key, at the given port
   *
   * @param key The key to publish the server to
   * @param port The port to publish the server to
   *
   * @throws RMIInitializationError if errors occur when initialization the
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
      throw new RMIInitializationError(e);
    }
  }

  /**
   * Creates a new lobby
   *
   * @return the created lobby
   */
  public ServerLobby createLobby(){
    return new ServerLobby(this);
  }

  /**
   * Thrown when there are erros while connecting to the RMI registry
   */
  public static class RMIInitializationError extends RuntimeException{
    RMIInitializationError(Exception e){
      super(e);
    }

    @Override
    public String toString() {
      return "Unable to start RMI registry";
    }
  }
}
