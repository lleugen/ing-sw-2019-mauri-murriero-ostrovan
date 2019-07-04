package it.polimi.se2019.view.GUIcontrollers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class GUIGenericWindow implements Initializable{

    /**
     * useful variable containing the answer from this window
     */
    protected Object result;

    public GUIGenericWindow(){

    }

    public abstract Object getResult();

    public abstract void closeWindow();

    protected String getURLOfImage(String linkAfterResourceFolder){
        return getClass().getResource("/" + linkAfterResourceFolder).toString();
    }

    protected void setOnActionEffect(Button button, String newResultValue){
        button.setOnAction(e -> {
            this.result = newResultValue;
            closeWindow();
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        initialize();
    }

    abstract void initialize();
}
