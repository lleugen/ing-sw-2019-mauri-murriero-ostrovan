package it.polimi.se2019.view;

import it.polimi.se2019.view.GUIcontrollers.GUILogin;
import it.polimi.se2019.view.GUIcontrollers.MyStage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * __WARN__ No more than ONE istance of this class must exist at a given time.
 *          This because getUser and getChar methods are static, cause JavaFX
 *          doesn't allows data return from a started application.
 *
 *          If multiple istances of this class are launched at the same time,
 *          the two methods above may return wrong data
 */
public class GUILoader extends Application {
  /**
   * Nickname of the player
   *
   * __WARN__ It may be null if no data has been inserted by the player
   */
  private static String name;

  /**
   * Character of the player
   *
   * __WARN__ It may be null if no data has been inserted by the player
   */
  private static String character;

  /**
   * Update User and Character static fields, only if they are not already
   * initialized
   *
   * @param u Username to save
   * @param c Character to save
   */
  private static synchronized void updateUserCharacter(String u, String c){
    if (!alreadyInitialized()){
      name = u;
      character = c;
    }
  }

  /**
   * @return true if the loader is already initialized (an username has already
   *         been inserted), false otherwise
   */
  public static synchronized boolean alreadyInitialized(){
    return ((name != null) || (character != null));
  }

  /**
   * @return The name of the player.
   *         Null if not initialized {see alreadyInitialized}
   */
  public static synchronized String getName() {
    return name;
  }

  /**
   * @return The character of the player.
   *         Null if not initialized {see alreadyInitialized}
   */
  public static synchronized String getCharacter() {
    return character;
  }

  //  public static synchronized String getUser(){
//
//  }
//
//  public static synchronized String getCharacter(){
//
//  }
  @Override
  public void start(Stage primaryStage) throws IOException {
    if (!alreadyInitialized()) {
      // Setting Background
      primaryStage.setScene(
              new Scene(
                      new FXMLLoader(
                              getClass().getResource("/gui/MainWindow.fxml")
                      ).load()
              )
      );
      primaryStage.setResizable(false);
      primaryStage.show();

      // Showing Login
      FXMLLoader loader = new FXMLLoader(
              getClass().getResource("/gui/LogIn.fxml")
      );
      GUILogin loginController = new GUILogin("nickname123");
      MyStage loginStage = new MyStage();

      loader.setController(loginController);
      loginStage.setScene(new Scene(loader.load()));

      String info = (String) (loginStage).showAndGetResult(loginController);
      if (info != null) {
        String[] parsedInfo = info.split("%");
        updateUserCharacter(parsedInfo[0], parsedInfo[1]);
      }

      //it closes itself on showAndGetResult
      //loginStage.close();
      //primaryStage.close();
    }
  }
}
