package it.polimi.se2019.communication.virtual_view;

public class NoResponseError extends Exception {
  public NoResponseError(Exception e){
    super(
            "User did not responded in time",
            e
    );
  }

  public NoResponseError(){
    super(
            "User did not responded in time"
    );
  }
}
