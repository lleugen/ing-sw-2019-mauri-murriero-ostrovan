package it.polimi.se2019.network.network.client;

import it.polimi.se2019.network.encodable.FunctionCall;

import java.io.DataOutputStream;
import java.net.Socket;

public class SocketClientNetwork implements ClientNetworkInterface {
  /**
   * Id of the currently connected client
   */
  private String id;

  @Override
  public void call(String ns, FunctionCall data) {

  }

  @Override
  public String getUserId() {
    return null;
  }

  @Override
  public void setOnUpdateCallback(UpdateCallback cb) {

  }

  /**
   * Get the id of the currently connected player
   *
   * @return the id of the currently connected player
   */
  public String getId(){
    return this.id;
  }

  /**
   * The callback to call when receiving messages from the server
   */
  private UpdateCallback updateCb;

  public SocketClientNetwork(String hostname, Integer port, String login) {
    try (Socket s = new Socket("localhost",5432)){
      System.out.println(s.toString());
      DataOutputStream dout = new DataOutputStream(s.getOutputStream());
      dout.writeChars("This Is My Nickname\n");
      dout.flush();
      dout.close();
      s.close();
      System.out.println("Done");
    }
    catch(Exception e){System.out.println(e);}
  }
}
