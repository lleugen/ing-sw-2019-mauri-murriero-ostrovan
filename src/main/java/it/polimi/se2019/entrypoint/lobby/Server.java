package it.polimi.se2019.entrypoint.lobby;

import it.polimi.se2019.utils.InitializationError;
import it.polimi.se2019.utils.network.server.ServerNetwork;
import it.polimi.se2019.utils.network.server.socket.SocketServerNetwork;
import it.polimi.se2019.utils.network.virtual_view.VirtualView;
import it.polimi.se2019.entrypoint.lobby.controller.RootController;

import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Server {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "App";

  /**
   * Start a new ServerLobby
   *
   * @param args See table below. args accepted in form -<name>=<value>
   *
   * name       value         note
   * network    (socket|rmi)  Type of network to istantiate
   * port       port to bind  Only for network=socket
   */
  public static void main (String[] args) {
    Map<String, String> argMap = Arrays.stream(args)
            .map(item -> item.replace("-", ""))
            .map(item -> item.split("="))
            .collect(
                    Collectors.toMap(
                            item -> item[0],
                            item -> item[1]
                    )
            );
    ServerNetwork ni;

    try {
      if (argMap.containsKey("network")) {
        switch (argMap.get("network")) {
          case "socket":
            ni = startSocketNetwork(argMap);
            break;
          case "rmi":
            ni = startRmiNetwork(argMap);
            break;
          default:
            throw new InitializationError(
                    "Param <network> must be either `socket` or `rmi`"
            );
        }
      }
      else {
        throw new InitializationError(
                "Param <network> is required"
        );
      }
    }
    catch (InitializationError e) {
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.SEVERE,
              "Error while starting the ServerLobby",
              e
      );
      ni = null;
    }
    VirtualView.init(ni);
    new RootController();
  }

  private static ServerNetwork startSocketNetwork(Map<String, String> argMap)
          throws InitializationError {

    if (argMap.containsKey("port")) {
      try {
        SocketServerNetwork ni = new SocketServerNetwork(
                Integer.parseInt(
                        argMap.get("port")
                )
        );

        new Thread(
                ni
        ).start();

        return ni;
      }
      catch (NumberFormatException e) {
        throw new InitializationError(
                "Invalid format for port",
                e
        );
      }
    }
    else {
      throw new InitializationError(
              "Param <port> is required when using socket"
      );
    }
  }

  private static ServerNetwork startRmiNetwork(Map<String, String> argMap)
          throws InitializationError {
    throw new InitializationError(
            "This method is not supported yet"
    );
  }
}
