package it.polimi.se2019.network.network;

/**
 * Exception for connection initialization
 */
public class InitializationError extends Exception {
  public InitializationError(Throwable e){
    super(e);
  }

  public InitializationError(){
    super();
  }

  @Override
  public String toString() {
    return "An error occurred during connection initialization";
  }
}
