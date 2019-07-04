package it.polimi.se2019.view.GUIcontrollers;

import javafx.stage.Stage;

/**
 * Application.Stage with a blocking method that return a particular answer from the window when that window is closed
 *
 * @author Riccardo Murriero
 */
public class MyStage extends Stage {
    public MyStage(){
        super();
    }

    public Object showAndGetResult(GUIGenericWindow controller){
        super.showAndWait();
        return controller.getResult();
    }
}
