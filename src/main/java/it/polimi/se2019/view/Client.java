package it.polimi.se2019.view;

import it.polimi.se2019.App;
import it.polimi.se2019.rmi.ServerLobbyInterface;
import it.polimi.se2019.rmi.ViewFacadeInterfaceRMIClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

public class Client {
  /**
   * Reference to the server
   */
  private ServerLobbyInterface server;

  /**
   * Init a new client
   *
   * @param host Hostname of the RMI registry
   * @param ui   cli
   *
   * @throws RemoteException if an error is found while using RMI
   * @throws NotBoundException If the server is not bound to the RMI registry
   */
  public Client(String host, String ui)
          throws RemoteException, NotBoundException {
    this.initNetwork(host);
    this.findLobby(this.genUi(ui));
  }

  /**
   * Generate the proper UI for the game
   *
   * @param ui Id of the UI to generate
   *
   * @throws RemoteException If something goes wrong with RMI
   */
  private ViewFacadeInterfaceRMIClient genUi(String ui) throws RemoteException {
    ViewFacadeInterfaceRMIClient generatedUi;
    if ("cli".equals(ui)) {
      generatedUi = new CLI();
    }
    else {
      throw new App.WrongArguments("Wrong ui param. Valid are <cli>");
    }
    return generatedUi;
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
    int port = ((new Random().nextInt(16383)) + 49152);
    Registry exportedRegistry = LocateRegistry.createRegistry(port);
    exportedRegistry.rebind("client", viewClient);
    this.server.connect("client", port);
  }
}
