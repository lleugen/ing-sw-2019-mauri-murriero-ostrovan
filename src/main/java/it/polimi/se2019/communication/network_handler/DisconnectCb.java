package it.polimi.se2019.communication.network_handler;

/**
 * Disconnect Callback.
 * This callback is called when the underlying network interfaces signals a
 * disconnection from the server.
 *
 * When this function is called, the whole NetworkHandler should be considered
 * dead, and therefore dereferenced
 */
@FunctionalInterface
public interface DisconnectCb {
  void ping();
}
