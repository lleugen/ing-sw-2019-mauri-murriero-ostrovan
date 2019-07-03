package it.polimi.se2019.view;

import it.polimi.se2019.RMI.ServerLobbyInterface;
import it.polimi.se2019.RMI.ViewFacadeInterfaceRMIClient;
import it.polimi.se2019.view.GUIcontrollers.GUILogin;
import it.polimi.se2019.view.GUIcontrollers.MyStage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends Application {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "App";
  private String nickname, character, host;

  /**
   * Init a new client
   *
   * @param host Hostname of the RMI registry
   * @param ui   cli|gui
   */
  public Client(String host, String ui){
    this.host = host;
    Scanner scanner = new Scanner(System.in);
    System.console().writer().println("Choose name.");
    String name = scanner.nextLine();

    try {
      ViewFacadeInterfaceRMIClient generatedUi = new CLI();
      new PlayerOnClient(name, host, generatedUi);
      this.findLobby(host, name);
    }
    catch (RemoteException | MalformedURLException e) {
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.SEVERE,
              "Error while connecting to server",
              e
      );
    }
  }

  public Client(){

  }

  private void generateLoginWindow(String firstName){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LogIn.fxml"));
    GUILogin loginController = new GUILogin(firstName);
    MyStage loginStage = new MyStage();

    try {
      loader.setController(loginController);
      Parent log = loader.load();
      loginStage.setScene(new Scene(log));

      String info = (String)(loginStage).showAndGetResult(loginController);
      nickname = info.split("%")[0];
      character = info.split("%")[1];
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    host = getParameters().getRaw().get(0);

    //FXMLLoader loader =
    Parent root = new FXMLLoader(getClass().getResource("/gui/MainWindow.fxml")).load();
    primaryStage.setScene(new Scene(root));

    //primaryStage.setFullScreen(true);
    primaryStage.setResizable(false);
    primaryStage.show();

    try{
      ViewFacadeInterfaceRMIClient generatedUI = new GUI();
      generateLoginWindow("nickname123");
      ((GUI) generatedUI).setLocalPlayerName(nickname, character);
      new PlayerOnClient(nickname, host, generatedUI);
      this.findLobby(host, nickname);
    }catch (RemoteException | MalformedURLException e) {
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
