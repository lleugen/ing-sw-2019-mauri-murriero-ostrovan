package it.polimi.se2019.view;

import it.polimi.se2019.RMI.ViewFacadeInterfaceRMIClient;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class PlayerOnClient  {
  /**
   * Create a new Client
   *
   * @param user User Id this client should bind to on the RMI
   * @param host Hostname the registry is located
   *
   * @throws RemoteException        if an error occurs while initialization
   * @throws MalformedURLException  if we cannot bind to the RMI Registry
   */
  public PlayerOnClient(String user, String host, ViewFacadeInterfaceRMIClient ui)
          throws MalformedURLException, RemoteException {
    Naming.rebind("//" + host + "/players/" + user, ui);
  }
}
