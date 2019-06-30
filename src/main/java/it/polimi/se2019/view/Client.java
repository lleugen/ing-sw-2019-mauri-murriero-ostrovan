package it.polimi.se2019.view;

import it.polimi.se2019.RMI.ServerLobbyInterface;
import it.polimi.se2019.RMI.ViewFacadeInterfaceRMIClient;

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

    try {
      ViewFacadeInterfaceRMIClient generatedUi;

      switch (ui){
        case "gui":
          generatedUi = new GUI();
          break;
        case "cli":
          generatedUi = new CLI();
          break;
        default:
          generatedUi = null;
      }

      if (generatedUi != null){
        new PlayerOnClient(name, host, generatedUi);
        this.findLobby(host, name);
      }
      else {
        Logger.getLogger(LOG_NAMESPACE).log(
                Level.SEVERE,
                "Unknown UI param, supported are <cli> and <gui>"
        );
      }
    }
    catch (RemoteException | MalformedURLException e) {
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
