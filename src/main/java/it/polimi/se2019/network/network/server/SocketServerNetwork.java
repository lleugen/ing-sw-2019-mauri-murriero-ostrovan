package it.polimi.se2019.network.network.server;
import java.io.*;
import java.net.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SocketServerNetwork {
  /**
   * Contains the reference to the currently open main socket
   */
  ServerSocket ss;

  /**
   * Contains the map of all connected clients, identified by their user id
   */
  Map<String, ConnectedSocket> connections;

  public SocketServerNetwork() throws java.io.IOException{
    this.ss = new ServerSocket(5432);
    this.connections = Collections.synchronizedMap(new HashMap<>());
    System.out.println(ss.toString());
    while (true){
      Socket s = ss.accept();
      System.out.println("Got Connection");
      System.out.println(s.toString());
    }
  }

  private class ConnectedSocket {
    private String user;

    public ConnectedSocket(Socket s) throws java.io.IOException {
      DataInputStream in = new DataInputStream(s.getInputStream());
      DataOutputStream out = new DataOutputStream(s.getOutputStream());

      StringBuilder user = new StringBuilder();
      char c;

      while ((c = in.readChar()) != '\n'){
        System.out.println("Receiving " + c + "\n");
        user.append(c);
      }

      this.user = user.toString();

      System.out.println("Got Nick " + this.user);
    }

    public String getUser(){
      return this.user;
    }
  }
}
