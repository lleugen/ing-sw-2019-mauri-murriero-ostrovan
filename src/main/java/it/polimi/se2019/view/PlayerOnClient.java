package it.polimi.se2019.view;

import it.polimi.se2019.rmi.ViewFacadeInterfaceRMIClient;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class PlayerOnClient  {
  /**
   * Create a new Client
   *
   * @param user User Id this client should bind to on the RMI
   * @param host Hostname the registry is located
   *
   * @throws RemoteException        if an error occurs while initialization
   * @throws MalformedURLException  if we cannot bind to the RMI Registry
   * @param ui the type of user interface, can be cli of gui
   */
  public PlayerOnClient(String user, String host, ViewFacadeInterfaceRMIClient ui)
          throws MalformedURLException, RemoteException {
    LocateRegistry.getRegistry(host).rebind("players/" + user, ui);
  }
}
