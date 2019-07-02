package it.polimi.se2019.view;

import com.sun.deploy.util.FXLoader;
import it.polimi.se2019.RMI.ServerLobbyInterface;
import it.polimi.se2019.RMI.ViewFacadeInterfaceRMIClient;
import it.polimi.se2019.view.GUIcontrollers.GUILogin;
import it.polimi.se2019.view.GUIcontrollers.MyStage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
  private String nickname, character;

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
          launch();
          generateLoginWindow(name);
          generatedUi = new GUI(nickname, character);
          name = nickname;
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

  private void generateLoginWindow(String firstName){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/login.fxml"));
    GUILogin loginController = new GUILogin(firstName);
    MyStage loginStage = new MyStage();

    try {
      loader.setController(loginController);
      Parent log = loader.load();
      loginStage.setScene(new Scene(log));
    } catch (Exception e){
      e.printStackTrace();
    }
    String info = (String)loginStage.showAndGetResult(loginController);
    nickname = info.split("%")[0];
    character = info.split("%")[1];
  }

  public void start(Stage primaryStage) throws Exception{
    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/mainwindow.fxml"));
    Parent root = loader.load();
    primaryStage.setScene(new Scene(root));

    primaryStage.setFullScreen(true);
    primaryStage.setResizable(false);
    primaryStage.show();
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
