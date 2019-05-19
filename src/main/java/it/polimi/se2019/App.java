package it.polimi.se2019;
import it.polimi.se2019.model.server.Server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class App {
  private static Map<String, Consumer<Map<String, String>>> typeMapping;
  private static Map<String, String> params;

  private static void initMapping(){
    typeMapping = new HashMap<>();
    typeMapping.put("server", App::spawnServer);
  }

  /**
   *
   * @param args
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
  }

  private static void spawnServer(Map<String, String> args){
    Server server = new Server();
    server.publishToRMI("server", 0);
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

    if (params.containsKey(params.get("type"))){
      typeMapping.get(params.get("type")).accept(params);
    }
    else {
      throw new WrongArguments("Type is either missed or wrong");
    }
  }

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
