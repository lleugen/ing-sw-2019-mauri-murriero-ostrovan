package it.polimi.se2019.view;

import it.polimi.se2019.RMI.ServerLobbyInterface;
import it.polimi.se2019.RMI.ViewFacadeInterfaceRMIClient;

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

  /**
   * Init a new client
   *
   * @param host Hostname of the RMI registry
   * @param ui   cli|gui
   */
  public Client(String host, String ui){
    Scanner scanner = new Scanner(System.in);
    System.console().writer().println("Choose name.");
    String name = scanner.nextLine();
    System.console().writer().println("Choose character.");
    String character = scanner.nextLine();

    try {
      ViewFacadeInterfaceRMIClient generatedUi;

      if ("cli".equals(ui)) {
        generatedUi = new CLI();
      }
      else {
        generatedUi = null;
      }

      if (generatedUi != null){
        this.findLobby(host, name, character, generatedUi);
      }
      else {
        Logger.getLogger(LOG_NAMESPACE).log(
                Level.SEVERE,
                "Unknown UI param, supported are <cli>"
        );
      }
    }
    catch (RemoteException e) {
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.SEVERE,
              "Error while connecting to server",
              e
      );
    }
  }

  /**
   * Connect to the server and finds an available lobby
   *
   * @param host        Host to connect to
   * @param client      Username of the client
   * @param character   Character of the player
   * @param viewClient  PlayerView to bind
   */
  private void findLobby(String host, String client, String character, ViewFacadeInterfaceRMIClient viewClient){
    if (System.getSecurityManager() == null) {
      System.setSecurityManager(new SecurityManager());
    }
    try {
      Registry registry = LocateRegistry.getRegistry(host);
      ServerLobbyInterface lobby = (ServerLobbyInterface) registry.lookup("server");
      lobby.connect(client, character, viewClient);
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
