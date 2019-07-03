package it.polimi.se2019.view;

import it.polimi.se2019.App;
import it.polimi.se2019.RMI.ServerLobbyInterface;
import it.polimi.se2019.RMI.ViewFacadeInterfaceRMIClient;
import javafx.application.Application;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "Client";

  /**
   * Init a new client
   *
   * @param host Hostname of the RMI registry
   * @param ui   cli|gui
   *
   * @throws RemoteException if an error is found while using RMI
   * @throws NotBoundException If the server is not bound to the RMI registry
   */
  public Client(String host, String ui)
          throws RemoteException, NotBoundException {
    ViewFacadeInterfaceRMIClient generatedUi;
    switch (ui) {
      case "gui":
        Application.launch(GUILoader.class);
        if (GUILoader.alreadyInitialized()) {
          generatedUi = new GUI(GUILoader.getName(), GUILoader.getCharacter());
        }
        else {
          throw new App.WrongArguments("Launcher was closed");
        }
        break;
      case "cli":
        generatedUi = new CLI();
        break;
      default:
        throw new App.WrongArguments("Wrong ui param. Valid are <gui> and <cli>");
    }

    this.findLobby(host, generatedUi);
  }

  /**
   * Connect to the server and finds an available lobby
   *
   * @param host        Host to connect to
   * @param viewClient  PlayerView to bind
   *
   * @throws RemoteException   If an error is found while using RMI
   * @throws NotBoundException If the server is not bound to the RMI registry
   */
  private void findLobby(String host, ViewFacadeInterfaceRMIClient viewClient)
          throws RemoteException, NotBoundException {
    System.out.println("Finding Lobby");
    Registry registry = LocateRegistry.getRegistry(host);
    ServerLobbyInterface lobby = (ServerLobbyInterface) registry.lookup("server");
    lobby.connect(viewClient);
  }
}
