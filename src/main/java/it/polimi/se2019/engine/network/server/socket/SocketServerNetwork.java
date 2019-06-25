/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.engine.network.server.socket;

import it.polimi.se2019.engine.network.Connection;
import it.polimi.se2019.engine.InitializationError;
import it.polimi.se2019.engine.network.server.ServerNetwork;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the Socket Network for a controller
 */
public final class SocketServerNetwork extends ServerNetwork implements Runnable {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "ServerSocketNetwork";

  /**
   * Detects if the thread should be running or not
   */
  private final AtomicBoolean running = new AtomicBoolean(true);

  /**
   * Main socket to listen on
   */
  private final ServerSocket ss;

  /**
   * Inits a new socket controller.
   *
   * @param port  Port the controller should bind to
   *
   * @throws InitializationError if there is an error creating the main socket
   */
  public SocketServerNetwork(int port) throws InitializationError {
    super();

    try {
      this.ss = new ServerSocket(port);
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.INFO,
              "Listening Socket created successfully"
      );
    }
    catch (IOException e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.WARNING,
              "Unable to create main listening socket",
              e
      );
      throw new InitializationError(e);
    }

    new Thread(
            this
    ).start();
  }

  /**
   * Runs the thread
   */
  public void run(){
    while (this.running.get()){
      try {
        Socket s = this.ss.accept();
        s.setKeepAlive(true);
        this.connectionHandler.accept(
                new Connection(
                        s.getInputStream(),
                        s.getOutputStream()
                )
        );
      }
      catch (IOException e){
        Logger.getLogger(LOG_NAMESPACE).log(
                Level.WARNING,
                "Unable to accept connection",
                e
        );
      }
    }

    try {
      this.ss.close();
    }
    catch (IOException e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.WARNING,
              "Unable to properly close listening socket",
              e
      );
    }
  }

  /**
   * See {@link #close}
   */
  public void stop(){
    this.close();
  }

  /**
   * Stops the controller from listening to new connections.
   * __WARN__: After stopping the thread in any way, you must create a new
   * ServerSocketNetwork. The old one cannot start listening to connection again
   */
  public void close(){
    this.running.set(false);
  }
}
