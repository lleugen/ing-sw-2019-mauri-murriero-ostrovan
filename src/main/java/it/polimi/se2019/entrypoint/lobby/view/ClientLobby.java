package it.polimi.se2019.entrypoint.lobby.view;

import it.polimi.se2019.communication.encodable.AbstractEncodable;
import it.polimi.se2019.communication.encodable.FunctionCallEncodable;
import it.polimi.se2019.communication.encodable.ModelViewUpdateEncodable;
import it.polimi.se2019.communication.network_handler.NetworkHandler;
import it.polimi.se2019.entrypoint.lobby.model.ChatMessageEncodable;
import it.polimi.se2019.entrypoint.lobby.model.LobbyStateEncodable;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientLobby {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "ClientLobby";

  /**
   * Reference to the NetworkHandler this Client is bound to
   */
  private NetworkHandler networkHandler;

  /**
   * Id of the user currently logged in
   */
  private String user;

  /**
   * Start the ClientLobby
   *
   * @param networkHandler Connection to the controller
   * @param user           Id of the user currently logged in
   */
  public ClientLobby(NetworkHandler networkHandler, String user){
    this.user = user;
    this.networkHandler = networkHandler;
    this.networkHandler.subscribe(
            ".dashboard",
            (ModelViewUpdateEncodable update) -> {
              LobbyStateEncodable modelData = new LobbyStateEncodable();
              try {
                System.out.print("\033\143");
                System.out.println("Logged in as: " + this.user);
                modelData.decode(update.getModel());
                System.out.println("Chat: ");
                modelData.getChatMsgs().forEach(
                        (ChatMessageEncodable msg) -> System.out.println(
                                "[" + msg.getUser() + "] " + msg.getMessage()
                        )
                );
                modelData.getCps().forEach(
                  System.out::println
                );
              }
              catch (AbstractEncodable.InvalidDataException e){
                Logger.getLogger(LOG_NAMESPACE).log(
                        Level.WARNING,
                        "Unable to decode update",
                        e
                );
              }
            }
    );

    try {
      while (true) {
        StringBuilder command = new StringBuilder();
        int c;
        do {
          c = System.in.read();
          command.append((char) c);
        } while (c != '\n');
        this.networkHandler.send(
                new FunctionCallEncodable(
                        ".dashboard",
                        "postChatMessage",
                        new ChatMessageEncodable(
                                user,
                                command.toString(),
                                System.currentTimeMillis()
                        ).encode()
                )
        );
      }
    }
    catch (IOException e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.SEVERE,
              "DISCONNECTED FROM CONSOLE!",
              e
      );
    }
  }
}
