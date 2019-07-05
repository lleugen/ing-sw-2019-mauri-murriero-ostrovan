package it.polimi.se2019.rmi;

/**
 * Thrown when we can not obtain a response from the user in time
 */
public class UserTimeoutException extends Exception {
  public UserTimeoutException(){
    super();
  }
  public UserTimeoutException(Throwable e){
    super(e);
  }
}
