package it.polimi.se2019;

import it.polimi.se2019.model.server.Server;
import it.polimi.se2019.view.Client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class App {
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
   * Each param is in the form of <key>=<value>, the form -<key>=<value> is
   * accepted, too
   *
   * @param args Array received from the command line, my the main method
   *
   * @throws WrongArguments if cmd arguments are wrong
   */
  private static void initParams(String[] args){
    params = Arrays.stream(args)
            .map((String param) -> param.split("="))
            .collect(Collectors.toMap(
                    (String[] item) -> item[0].replace("-", ""),
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
    new Server();
  }

  /**
   * Spawn a new old_client
   *
   * @param args Args array received from the command line
   */
  private static void spawnClient(Map<String, String> args){
    new Client(args);
  }

  /**
   * Type: server
   *
   * @param args Params to pass to the bootstrapping process
   *
   * @throws WrongArguments When there is an error with the params received,
   *                        the message of the exception contains additional
   *                        info about the error
   */
  public static void main(String[] args) {
    params = new HashMap<>();

    initMapping();
    initParams(args);

    if (typeMapping.containsKey(params.get("type"))){
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
   * The toString method may contain additional information about the error
   */
  public static class WrongArguments extends RuntimeException {
    private final String message;

    WrongArguments(String message){
      this.message = message;
    }

    @Override
    public String toString(){
      return message;
    }
  }
}
