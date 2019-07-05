package it.polimi.se2019.view;

import it.polimi.se2019.App;
import it.polimi.se2019.rmi.ServerLobbyInterface;
import it.polimi.se2019.rmi.ViewFacadeInterfaceRMIClient;
import javafx.application.Application;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Client {
  /**
   * Reference to the server
   */
  private ServerLobbyInterface server;


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
    this.initNetwork(host);
    ViewFacadeInterfaceRMIClient generatedUi;
    switch (ui) {
      case "gui":
        new Thread(
                () -> Application.launch(GUILoader.class)
        ).start();
        generatedUi = GUILoader.getRmi();
        break;
      case "cli":
        generatedUi = new CLI();
        break;
      default:
        throw new App.WrongArguments("Wrong ui param. Valid are <gui> and <cli>");
    }

    this.findLobby(generatedUi);
  }

  /**
   * Init the server
   *
   * @param host Hostname of the server
   */
  private void initNetwork(String host) throws RemoteException, NotBoundException {
    this.server = (ServerLobbyInterface) LocateRegistry
            .getRegistry(host)
            .lookup("server");

    String localAddress = server.getIp();

    System.setProperty(
            "java.rmi.server.hostname",
            localAddress
    );
  }

  /**
   * Connect to the server and finds an available lobby
   * The network must have been already initialized with {@link #initNetwork(String)}
   *
   * @param viewClient  PlayerView to bind
   *
   * @throws RemoteException   If an error is found while using RMI
   */
  private void findLobby(ViewFacadeInterfaceRMIClient viewClient)
          throws RemoteException {
    LocateRegistry.createRegistry(1099).rebind("client", viewClient);

    this.server.connect("client", 1099);
  }
}
