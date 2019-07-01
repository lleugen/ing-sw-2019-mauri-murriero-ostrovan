package it.polimi.se2019.view.GUIcontrollers;

//import sun.awt.Mutex;
//import java.util.concurrent.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
        return getClass().getResource(linkAfterResourceFolder).toString();
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
