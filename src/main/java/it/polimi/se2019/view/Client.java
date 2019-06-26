package it.polimi.se2019.view;

import it.polimi.se2019.RMI.ServerLobbyInterface;
import it.polimi.se2019.RMI.ViewFacadeInterfaceRMIClient;
import it.polimi.se2019.model.server.ServerLobby;
import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

  public Client(String host){
    System.out.println("Hi");
    try {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Choose name.");
      String name = scanner.nextLine();
      PlayerOnClient client = new PlayerOnClient(name, host);
      this.findLobby(host, name);
    }
    catch (RemoteException | MalformedURLException e){
      e.printStackTrace();
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
      System.err.println("client exception:");
      e.printStackTrace();
    }
  }
}
