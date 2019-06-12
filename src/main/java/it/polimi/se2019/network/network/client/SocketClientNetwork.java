package it.polimi.se2019.network.network.client;

import java.io.DataOutputStream;
import java.net.Socket;

public class SocketClientNetwork {
  public SocketClientNetwork() {
    try{
      Socket s = new Socket("localhost",5432);
      System.out.println(s.toString());
      DataOutputStream dout = new DataOutputStream(s.getOutputStream());
      dout.writeUTF("This Is My Nickname");
      dout.flush();
      dout.close();
      s.close();
      System.out.println("Done");
    }catch(Exception e){System.out.println(e);}
  }
}
