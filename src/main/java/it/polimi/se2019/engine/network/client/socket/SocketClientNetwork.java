/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.engine.network.client.socket;

import it.polimi.se2019.engine.network.client.ClientNetwork;
import it.polimi.se2019.engine.network.Connection;
import it.polimi.se2019.engine.InitializationError;

import java.io.IOException;
import java.net.Socket;

/**
 * Implementation of the Socket Network for a view
 */
public final class SocketClientNetwork extends ClientNetwork {
  /**
   * Init a new socket view network
   *
   * @param host  Hostname to connect to
   * @param port  Port to connect to
   *
   * @throws InitializationError if there is an error initializing the socket
   */
  public SocketClientNetwork(String host, int port)
          throws InitializationError {
    super();
    Socket s = initSocket(host, port);
    try {
      this.setConnection(
              new Connection(
                      s.getInputStream(),
                      s.getOutputStream()
              )
      );
    }
    catch (IOException e){
      throw new InitializationError(e);
    }
  }

  /**
   * Init the socket connection
   *
   * @param host Hostname to connect to
   * @param port Port to connect to
   *
   * @return The opened socket
   *
   * @throws InitializationError if errors occurs while initializing the socket
   */
  /*
   *  The concept of this method is the same of ServerSocket.accept:
   *  the function does something, then returns a new inititialized socket.
   *  Obviously, the returned socket is alive, not dead, and it is up to the
   *  programmer to remember to properly close the socket to prevent resources
   *  from leaking out.
   *
   *  SonarQube, to prevent resources from leaking out, strongly suggest the
   *  user of the try-with-resource pattern, which, on the end of the method
   *  (as it is supposed to do) closes the opened socket.
   *  This action clashes with the requirements of this method (it returns an
   *  alive socket), and with the java socket pattern in general (
   *  e.g. In Java we read data from a socket using an InputStream. The read
   *  operation of an InputStream is blocking, and there is no clean method to
   *  force it to act as a non-blocking operation.
   *
   *  If we need to put all of our logic in the try block, how can we deal, for
   *  example, with write operations, if we are indefinitely blocked waiting for
   *  a read?
   *
   *  This is why the "squid:S2095 - Resources should be closed" rule has been
   *  suppressed in this block of code
   */
  @SuppressWarnings("squid:S2095")
  private static Socket initSocket(String host, int port)
          throws InitializationError {
    try {

      Socket socket = new Socket(host, port);
      socket.setKeepAlive(true);
      return socket;
    }
    catch (IOException e) {
      throw new InitializationError(e);
    }
  }
}
