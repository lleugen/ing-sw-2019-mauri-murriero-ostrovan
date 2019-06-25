/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.engine;

/**
 * Exception for connection initialization
 */
public class InitializationError extends Exception {
  public InitializationError(){
    super(
            "An error occurred during initialization"
    );
  }

  public InitializationError(Throwable e){
    super(
            "An error occurred during initialization",
            e
    );
  }

  public InitializationError(String msg){
    super(
            "An error occurred during initialization: " + msg
    );
  }

  public InitializationError(String msg, Throwable e){
    super(
            "An error occurred during initialization: " + msg,
            e
    );
  }
}
