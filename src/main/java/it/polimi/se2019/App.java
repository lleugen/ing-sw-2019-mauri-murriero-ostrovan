package it.polimi.se2019;

import it.polimi.se2019.model.server.Server;

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
            .collect(Collectors.toMap(
                    (String[] item) -> item[0],
                    (String[] item) -> item[1]
                    )
            );

    params.keySet().forEach(System.out::println);
  }

  /**
   * Spawn a new server
   *
   * @param args Args array received from the command line
   */
  private static void spawnServer(Map<String, String> args){
    Server server = new Server();
    server.publishToRMI("server", 5432);
  }

  /**
   * Type: server
   * @param args
   */
  public static void main(String[] args) {
    params = new HashMap<>();
    initMapping();

    try {
      initParams(args);
    }
    catch (WrongArguments e){
      // TODO: if necessary, implement additional logic here
      throw e;
    }

    if (params.containsKey("type")){
      System.out.println(params.get("type"));
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
