//package it.polimi.se2019.view;
//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//
//public class GUIGame  extends Application {
//  @Override
//  public void start(Stage primaryStage) throws IOException {
//    // Setting Background
//    primaryStage.setScene(
//            new Scene(
//                    new FXMLLoader(
//                            getClass().getResource("/gui/MainWindow.fxml")
//                    ).load()
//            )
//    );
//    primaryStage.setResizable(false);
//    primaryStage.show();
////
////    // Showing Login
////    FXMLLoader loader = new FXMLLoader(
////            getClass().getResource("/gui/LogIn.fxml")
////    );
////    GUILogin loginController = new GUILogin("nickname123");
////    MyStage loginStage = new MyStage();
////
////    loader.setController(loginController);
////    loginStage.setScene(new Scene(loader.load()));
////
////    String info = (String) (loginStage).showAndGetResult(loginController);
////    if (info != null) {
////      String[] parsedInfo = info.split("%");
////      updateUserCharacter(parsedInfo[0], parsedInfo[1]);
////    }
////
////    loginStage.close();
////    primaryStage.close();
//  }
//}
