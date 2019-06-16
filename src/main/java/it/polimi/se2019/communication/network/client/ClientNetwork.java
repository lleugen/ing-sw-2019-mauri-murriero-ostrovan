package it.polimi.se2019.communication.network.client;

import it.polimi.se2019.communication.network.Connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * General definition of a Client Network interface
 */
public class ClientNetwork {
  /**
   * Reference to the currently active connection
   */
  private Connection connection;

  /**
   * Init a new ClientNetwork
   */
  protected ClientNetwork(){
    this.connection = new Connection(
            new InputStream() {
              @Override
              public int read() throws IOException {
                throw new IOException("Connection not initialized");
              }
            },
            new OutputStream() {
              @Override
              public void write(int b) throws IOException {
                throw new IOException("Connection not initialized");
              }
            },
            () -> {}
    );
  }

  /**
   * Gets the currently opened connection
   *
   * @return the currently opened connection
   */
  public Connection getConnection(){
    return this.connection;
  }

  /**
   * Sets the currently opened connection.
   * Underlying implementations should call this function after opening a
   * new connection
   *
   * @param connection the currently opened connection
   */
  protected void setConnection(Connection connection){
    this.connection = connection;
  }
}
