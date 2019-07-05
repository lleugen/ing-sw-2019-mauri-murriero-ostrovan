package it.polimi.se2019.view;

import it.polimi.se2019.rmi.ViewFacadeInterfaceRMIClient;
import it.polimi.se2019.view.GUIcontrollers.GUILogin;
import it.polimi.se2019.view.GUIcontrollers.MyStage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * __WARN__ No more than ONE istance of this class must exist at a given time.
 *          This because getRMI is static, cause JavaFX doesn't allows data
 *          return from a started application.
 *
 *          If multiple istances of this class are launched at the same time,
 *          getRMI may return wrong data
 */
public class GUILoader extends Application {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "GUILoader";

  /**
   * ViewFacade initialized with data obtained by the gui
   */
  private static ViewFacadeInterfaceRMIClient rmi;

  /**
   * Object used for handling wait and wakeup call
   */
  private static final Object waitingSyncronizer = new Object();

  /**
   * Update User and Character static fields, only if they are not already
   * initialized
   *
   * @param v View to update
   */
  private static void updateRMI(ViewFacadeInterfaceRMIClient v){
    synchronized (waitingSyncronizer) {
      if (rmi == null) {
        rmi = v;
      }
      waitingSyncronizer.notifyAll();
    }
  }

  /**
   * @return The initialized RMI client.
   *         This method blocks till the user insert valid data for the rmi
   */
  public static ViewFacadeInterfaceRMIClient getRmi() {
    synchronized (waitingSyncronizer) {
      while (rmi == null) {
        try {
          waitingSyncronizer.wait();
        }
        catch (InterruptedException e){
          Logger.getLogger(LOG_NAMESPACE).log(
                  Level.INFO,
                  "Interrupted!",
                  e
          );
          Thread.currentThread().interrupt();
        }
      }
      return rmi;
    }
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
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

    String info;
    do {
      // Showing Login
      FXMLLoader loader = new FXMLLoader(
              getClass().getResource("/gui/LogIn.fxml")
      );
      GUILogin loginController = new GUILogin("nickname123");
      MyStage loginStage = new MyStage();

      loader.setController(loginController);
      loginStage.setScene(new Scene(loader.load()));

      info = (String) (loginStage).showAndGetResult(loginController);
      if (info != null) {
        String[] parsedInfo = info.split("%");
        if ("".equals(parsedInfo[0])){
          info = null;
        }
        else {
          updateRMI(
                  new GUI(
                          parsedInfo[0],
                          parsedInfo[1]
                  )
          );
        }
      }
      loginStage.close();
    } while (info == null);
  }
}
