package it.polimi.se2019.communication.network;

/**
 * Exception for connection initialization
 */
public class InitializationError extends Exception {
  private String msg = "An error occurred during connection initialization";

  public InitializationError(Throwable e){
    super(e);
  }

  public InitializationError(){
    super();
  }

  public InitializationError(String message){
    super();
    this.msg = message;
  }

  @Override
  public String toString() {
    return this.msg;
  }
}
