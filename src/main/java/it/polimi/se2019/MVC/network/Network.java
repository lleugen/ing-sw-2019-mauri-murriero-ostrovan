package it.polimi.se2019.MVC.network;

public interface NetworkInterface {
  public void register();
  public void unregister();
  public void sendModelUpdate();
  public void receiveModelUpdate();

}
