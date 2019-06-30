package it.polimi.se2019.view;

import it.polimi.se2019.RMI.ViewFacadeInterfaceRMIClient;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerOnClient  {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "PlayerOnClient";

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

  @Override
  public void sendCharacterInfo(List<String> characterInfo) throws RemoteException {
    // TODO ricky
    System.out.println("sendCharacterInfo");
  }
}
