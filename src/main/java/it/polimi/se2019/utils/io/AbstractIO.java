package it.polimi.se2019.utils.io;

public abstract class AbstractIO {
  /**
   * Disconnection Callback
   */
  private DisconnectCb dcb;

  protected AbstractIO(DisconnectCb dcb){
    this.dcb = dcb;
  }

  /**
   * Function to call when a disconnection on the network handler is detected
   */
  protected interface DisconnectCb {
    void ping();
  }
}
