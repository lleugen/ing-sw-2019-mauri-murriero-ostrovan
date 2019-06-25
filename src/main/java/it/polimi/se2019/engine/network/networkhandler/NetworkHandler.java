/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.engine.network.networkhandler;

import it.polimi.se2019.engine.io.AbstractIO;
import it.polimi.se2019.engine.model.AbstractModel;
import it.polimi.se2019.engine.model.FunctionCallModel;
import it.polimi.se2019.engine.model.ViewUpdateModel;
import it.polimi.se2019.engine.network.Connection;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class NetworkHandler {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "NetworkHandler";

  /**
   * Reference to the network connection is bound to
   */
  private Connection connection;

  /**
   * IO Interface to use for rendering views
   */
  private AbstractIO ioInterface;

  /**
   * Creates a new Network Handler
   *
   * @param conn  Connection to the server (already opened).
   *              It is up to you to ensure that this connection is used only
   *              by this network handler
   * @param io    Io Interface to use for rendering views
   */
  public NetworkHandler(Connection conn, AbstractIO io) {
    this.ioInterface = io;

    this.connection = conn;
    this.connection.setDisconnectionCb(this::disconnectCallback);
    this.connection.setReceiveCallback(this::receiveCallback);

    this.ioInterface.registerSendDataHandler((FunctionCallModel c) ->
      this.connection.send(
              AbstractModel.bufToArray(
                      c.encode()
              )
      )
    );

  }

  /**
   * Receive an update about model or views to render, decodes it then dispatch
   * it to the proper views.
   * All opened views are rerendered
   *
   * @param data  Update to process
   */
  private synchronized void receiveCallback(byte[] data){
    ViewUpdateModel update;
    try {
      update = ViewUpdateModel.decode(
              AbstractModel.arrayToBuf(data)
      );

      if (!update.getNs().isEmpty()){
        this.ioInterface.updateModel(update.getNs(), update.getModel());
      }

      this.ioInterface.render(update.getViews());
    }
    catch (AbstractModel.InvalidDataException e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.WARNING,
              "Unable to decode ViewUpdate",
              e
      );
    }
  }

  /**
   * Disconnection Callback.
   * Close the ioInterface registered to this network handler
   *
   * @param c Is ignored
   */
  private void disconnectCallback(Connection c){
    this.ioInterface.close();
  }
}
