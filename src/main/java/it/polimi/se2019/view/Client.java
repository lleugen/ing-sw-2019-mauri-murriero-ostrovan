package it.polimi.se2019.view;

import it.polimi.se2019.RMI.ServerLobbyInterface;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "App";

  public Client(String host){
    System.out.println("Hi");
    try {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Choose name.");
      String name = scanner.nextLine();
      new PlayerOnClient(name, host);
      this.findLobby(host, name);
    }
    catch (RemoteException | MalformedURLException e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.SEVERE,
              "Error while connecting to server",
              e
      );
    }
  }

  private void findLobby(String host, String client){
    if (System.getSecurityManager() == null) {
      System.setSecurityManager(new SecurityManager());
    }
    try {
      Registry registry = LocateRegistry.getRegistry(host);
      ServerLobbyInterface lobby = (ServerLobbyInterface) registry.lookup(
              "//" + host + "/server"
      );
      lobby.connect(client);
    }
    catch (Exception e) {
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.SEVERE,
              "Error while starting RMI server",
              e
      );
    }
  }
}
