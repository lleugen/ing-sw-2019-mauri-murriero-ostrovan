// Ciao eu v2

package it.polimi.se2019;

import it.polimi.se2019.model.server.Server;
import it.polimi.se2019.view.Client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class App {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "App";

  /**
   * Contains actions for param type (what type of program should be spawned)
   */
  private static Map<String, Consumer<Map<String, String>>> typeMapping;

  /**
   * Contains params parsed by the command line, on app bootstrapping
   */
  private static Map<String, String> params;

  /**
   * Init the mapping array
   */
  private static void initMapping(){
    typeMapping = new HashMap<>();
    typeMapping.put("server", App::spawnServer);
    typeMapping.put("client", App::spawnClient);
  }

  /**
   * Init the params array. Parses the args array received by the main method,
   * from the command line.
   * Each param is in the form of <key>=<value>
   *
   * @param args Array received from the command line, my the main method
   *
   * @throws WrongArguments if cmd arguments are wrong
   */
  private static void initParams(String[] args){
    params = Arrays.stream(args)
            .map((String param) -> param.split("="))
            .filter((String[] a) -> a.length == 2)
            .collect(Collectors.toMap(
                    (String[] item) -> item[0],
                    (String[] item) -> item[1]
                    )
            );
  }

  /**
   * Spawn a new server
   *
   * @param args Args array received from the command line
   */
  private static void spawnServer(Map<String, String> args){
    if (args.containsKey("host") && args.containsKey("lobbyTimeout")) {
      try {
        new Server(
                args.get("host"),
                Integer.parseInt(
                        args.get("lobbyTimeout")
                )
        );
      }
      catch (RemoteException e){
        Logger.getLogger(LOG_NAMESPACE).log(
                Level.SEVERE,
                "Error while starting RMI server",
                e
        );
        throw new WrongArguments("Unable to start RMI server");
      }
      catch (NumberFormatException e){
        Logger.getLogger(LOG_NAMESPACE).log(
                Level.SEVERE,
                "Error while parsing lobbyTimeout",
                e
        );
        throw new WrongArguments("Unable to parse lobbyTimeout param");
      }
    }
    else {
      throw new WrongArguments("Host and lobbyTimeout params are required");
    }
  }

  /**
   * Spawn a new client
   *
   * @param args Args array received from the command line
   *
   * @throws RemoteException if an error is found while using RMI
   */
  private static void spawnClient(Map<String, String> args) {
    if (args.containsKey("host") && args.containsKey("ui")) {
      try {
        new Client(args.get("host"), args.get("ui"));
      }
      catch (RemoteException  | NotBoundException e){
        Logger.getLogger(LOG_NAMESPACE).log(
                Level.SEVERE,
                "Unable to start network",
                e
        );
      }
    }
    else {
      throw new WrongArguments("Host and UI params are required");
    }
  }

  /**
   * Type: server, client
   * @param args type: client|server
   *             ui: cli
   *
   * @throws WrongArguments  If passed args are invalid
   */
  public static void main(String[] args) {
    if (System.getSecurityManager() == null) {
      System.setSecurityManager(new SecurityManager());
    }

    params = new HashMap<>();
    initMapping();

    initParams(args);

    if (params.containsKey("type")){
      typeMapping.get(params.get("type")).accept(params);
    }
    else {
      throw new WrongArguments("" +
              "Type is either missed or wrong\nSupported types are " +
              String.join(", ", typeMapping.keySet()));
    }
  }

  /**
   * Thrown when there is an error in the args array received by the command
   * line.
   * The toString method may contain additional informations about the error
   */
  public static class WrongArguments extends RuntimeException {
    public WrongArguments(String message){
      super(message);
    }
  }
}
