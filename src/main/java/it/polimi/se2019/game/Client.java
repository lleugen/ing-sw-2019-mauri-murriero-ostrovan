/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.game;

import it.polimi.se2019.engine.InitializationError;
import it.polimi.se2019.engine.io.CliIO;
import it.polimi.se2019.engine.model.AbstractModel;
import it.polimi.se2019.engine.model.LoginDataModel;
import it.polimi.se2019.engine.network.Connection;
import it.polimi.se2019.engine.network.client.ClientNetwork;
import it.polimi.se2019.engine.network.client.socket.SocketClientNetwork;
import it.polimi.se2019.engine.network.networkhandler.NetworkHandler;

import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Client {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "App";

  /**
   * Start a new ClientLobby
   *
   * @param args See table below. args accepted in form -<name>=<value>
   *
   * name       value                 note
   * network    (socket|rmi)          Type of network to istantiate
   * port       port to bind          Only for network=socket
   * host       host to connect to    Only for network=socket
   * login      UserId to login with  -
   */
  public static void main(String[] args){
    Map<String, String> argMap = Arrays.stream(args)
            .map(item -> item.replace("-", ""))
            .map(item -> item.split("="))
            .collect(
                    Collectors.toMap(
                            item -> item[0],
                            item -> item[1]
                    )
            );
    ClientNetwork ni;

    try {
      if (argMap.containsKey("network") && argMap.containsKey("login")) {
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
                "Params <network> and <login> are required"
        );
      }
    }
    catch (InitializationError e) {
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.SEVERE,
              "Error while starting the Client",
              e
      );
      ni = null;
    }

    if (ni != null){
      Connection connection = ni.getConnection();
      connection.send(
              AbstractModel.bufToArray(
                      new LoginDataModel(
                              argMap.get("login")
                      ).encode()
              )
      );

      new NetworkHandler(connection, new CliIO());
    }
  }

  private static ClientNetwork startSocketNetwork(Map<String, String> argMap)
          throws InitializationError {

    if (argMap.containsKey("port")) {
      if (argMap.containsKey("host")) {
        try {
          return new SocketClientNetwork(
                  argMap.get("host"),
                  Integer.parseInt(
                          argMap.get("port")
                  )
          );
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
                "Param <host> is required when using socket"
        );
      }
    }
    else {
      throw new InitializationError(
              "Param <port> is required when using socket"
      );
    }
  }

  private static ClientNetwork startRmiNetwork(Map<String, String> argMap)
          throws InitializationError {
    throw new InitializationError(
            "This method is not supported yet"
    );
  }
}
