/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019;

import it.polimi.se2019.game.Client;
import it.polimi.se2019.game.Server;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "App";

  /**
   * @param args Params to pass to the bootstrapping process
   *             First param is the name of the module to spawn, then comes
   *             args specific of the module
   *             e.g. java -jar ./adrenalina.jar ServerLobby -network=socket
   */
  public static void main(String[] args) {
    String module = args[0];
    String[] argv = Arrays.copyOfRange(args, 1, args.length);

    switch (module) {
      case "ServerLobby":
        Server.main(argv);
        break;
      case "ClientLobby":
        Client.main(argv);
        break;
      default:
        Logger.getLogger(LOG_NAMESPACE).log(
                Level.SEVERE,
                "Wrong module name: Supported are:\n" +
                        "ServerLobby | ClientLobby"
        );
    }
  }
}
