package it.polimi.se2019.view.GUIcontrollers;

import javafx.stage.Stage;

public class MyStage extends Stage {
    public Object showAndGetResult(GUIGenericWindow controller){
        super.showAndWait();
        return controller.getResult();
    }
}
