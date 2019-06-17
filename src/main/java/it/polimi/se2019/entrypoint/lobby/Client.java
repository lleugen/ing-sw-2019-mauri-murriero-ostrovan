package it.polimi.se2019.entrypoint.lobby;

import it.polimi.se2019.communication.encodable.AbstractEncodable;
import it.polimi.se2019.communication.encodable.LoginDataEncodable;
import it.polimi.se2019.communication.network.Connection;
import it.polimi.se2019.communication.network.InitializationError;
import it.polimi.se2019.communication.network.client.ClientNetwork;
import it.polimi.se2019.communication.network.client.socket.SocketClientNetwork;
import it.polimi.se2019.communication.network_handler.NetworkHandler;
import it.polimi.se2019.entrypoint.lobby.view.ClientLobby;

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
              "Error while starting the ServerLobby",
              e
      );
      ni = null;
    }

    if (ni != null){
      try {
        doLogin(ni.getConnection(), argMap.get("login"));

        NetworkHandler nh = new NetworkHandler(
                ni.getConnection(),
                () -> {
                  System.out.println("DISCONNECTED!");
                }
        );
        nh.start();

        System.out.println("Type a message, then press enter");
        new ClientLobby(nh, argMap.get("login"));
      }
      catch (InitializationError e){
        Logger.getLogger(LOG_NAMESPACE).log(
                Level.SEVERE,
                "Login Failed",
                e
        );
      }
    }
  }

  private static ClientNetwork startSocketNetwork(Map<String, String> argMap)
          throws InitializationError {

    if (argMap.containsKey("port")) {
      if (argMap.containsKey("host")) {
        try {
          SocketClientNetwork ni = new SocketClientNetwork(
                  argMap.get("host"),
                  Integer.parseInt(
                          argMap.get("port")
                  )
          );

          ni.start();

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

  /**
   * Does login on the controller on the connection passed as parameter
   *
   * @param conn  Connection to login on
   * @param login Data to user for login
   *
   * @throws InitializationError is an error occurs while logging in
   */
  private static void doLogin(Connection conn, String login)
          throws InitializationError {
    try {
      LoginDataEncodable loginData = new LoginDataEncodable(
              login
      );

      conn.send(loginData.encode());
      loginData.decode(conn.receive());
      if (!login.equals(loginData.getUser())){
        throw new InitializationError(
                "Login Failed: Wrong login or already logged in"
        );
      }
      else {
        System.out.println("Login Done: "+ loginData.getUser());
      }
    }
    catch (
            Connection.DisconnectedError |
                    AbstractEncodable.InvalidDataException e
    ){
      throw new InitializationError(e);
    }
  }
}
