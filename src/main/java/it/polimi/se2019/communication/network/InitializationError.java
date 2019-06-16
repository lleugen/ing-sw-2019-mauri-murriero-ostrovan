package it.polimi.se2019.communication.network;

/**
 * Exception for connection initialization
 */
public class InitializationError extends Exception {
  public InitializationError(){
    super(
            "An error occurred during connection initialization"
    );
  }

  public InitializationError(Throwable e){
    super(
            "An error occurred during connection initialization",
            e
    );
  }
}
