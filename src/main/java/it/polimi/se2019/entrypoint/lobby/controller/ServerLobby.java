package it.polimi.se2019.entrypoint.lobby.controller;

import it.polimi.se2019.communication.encodable.AbstractEncodable;
import it.polimi.se2019.communication.encodable.ModelViewUpdateEncodable;
import it.polimi.se2019.communication.virtual_view.AssociatedFunctionCall;
import it.polimi.se2019.communication.virtual_view.NoResponseError;
import it.polimi.se2019.communication.virtual_view.VirtualView;
import it.polimi.se2019.entrypoint.lobby.model.ChatMessageEncodable;
import it.polimi.se2019.entrypoint.lobby.model.LobbyStateEncodable;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerLobby implements Runnable {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "ServerLobby";

  /**
   * Minimum number of player for starting a game
   * Is up to you to ensure it is always less or equals to
   * ServerLobby.MAX_PLAYERS (no checks are made by the program about this
   * condition).
   *
   * Default: 2
   */
  private static final int MIN_PLAYERS_FOR_GAME = 2;

  /**
   * Maximum number of chat messages saved in the controller
   *
   * Default: 50
   */
  private static final int MAX_CHAT_MESSAGES = 5;

  /**
   * True if the server should be running, false otherwise
   */
  private AtomicBoolean running = new AtomicBoolean(true);

  /**
   * Reference to the VirtualView for this controller
   */
  private VirtualView virtualView;

  /**
   * Lobby State associated with this lobby
   */
  private LobbyStateEncodable lobbyStateEncodable;

  /**
   * Start a new ServerLobby.
   * In a ServerLobby users can join new games, and chat each others
   *
   * Remember to call start for starting the lobby
   *
   * @param vv VirtualView associated with this ServerLobby
   */
  public ServerLobby(VirtualView vv) {
    this.virtualView = vv;
    this.lobbyStateEncodable = new LobbyStateEncodable();
  }

  /**
   * Add a new chat message to the lobby, only if it is sent by the user passed
   * as parameter
   * If more than MAX_CHAT_MESSAGES are saved in the lobby, only the
   * newest MAX_CHAT_MESSAGES are preserved
   *
   * @param user  Id of the user who posted the message
   * @param msg   Message to add to the list
   */
  private void addChatMessage(String user, ChatMessageEncodable msg){
    if (
            user.equals(msg.getUser()) &&
            System.currentTimeMillis() >= msg.getDate()
    ) {

      List<ChatMessageEncodable> list = this.lobbyStateEncodable.getChatMsgs();
      list.add(
              new ChatMessageEncodable(
                      msg.getUser(),
                      msg.getMessage().replace("\n", ""),
                      msg.getDate()
              )
      );

      while (list.size() > MAX_CHAT_MESSAGES) {
        list.remove(0);
      }

      this.lobbyStateEncodable.setChatMsgs(list);

      this.sendUpdatedModelToAllClients();
    }
    else {
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.INFO,
              "Discarded chat message, wrong data"
      );
    }
  }

  /**
   * Send the updated model to each connected view
   */
  private void sendUpdatedModelToAllClients(){
    ModelViewUpdateEncodable update = new ModelViewUpdateEncodable(
            ".dashboard",
            "dashboard",
            this.lobbyStateEncodable.encode()
    );

    this.virtualView.getConnectedPlayer().forEach(
            (String p) -> this.virtualView.send(p, update)
    );
  }

  /**
   * Accepts function calls from clients and sends down responses
   */
  public void run(){
    List<String> validNs = new ArrayList<>();
    validNs.add(".dashboard");

    Map<String, Consumer<AssociatedFunctionCall>> callAssoc = new HashMap<>();

    callAssoc.put(
            "postChatMessage",
            new FunctionCallCb<>(
                    this::addChatMessage,
                    ChatMessageEncodable::new
            )
    );

    AssociatedFunctionCall fc = null;

    List<String> validF = new LinkedList<>(callAssoc.keySet());

    while (this.running.get()){
      try {
        fc = this.virtualView.receive(
                Integer.MAX_VALUE,
                null,
                validNs,
                validF
        );
        if (callAssoc.containsKey(fc.getFc().getF())) {
          callAssoc.get(fc.getFc().getF()).accept(fc);
        }

        this.sendUpdatedModelToAllClients();
      }
      catch (NoResponseError e){
        Logger.getLogger(LOG_NAMESPACE).log(
                Level.INFO,
                "No Response received",
                e
        );
      }
    }
  }

  /**
   * Stop the server.
   * After stopping, you cannot restart the server again without recreating it
   */
  public void stop(){
    this.running.set(false);
  }

  /**
   * Contains a FunctionCallCallback, that will parse new calls, and will
   * call the callback only if they are valid
   *
   * @param <T> Type of the model the callback accepts
   *
   * __WARN__ If data is invalid, the call is discarded silently (it is only
   *          logged to the logger)
   */
  private static class FunctionCallCb<T extends AbstractEncodable>
          implements Consumer<AssociatedFunctionCall> {
    /**
     * Namespace this class logs to
     */
    private static final String LOG_NAMESPACE = "ServerLobby";

    /**
     * Callback if accepted data is valid
     */
    private BiConsumer<String, T> cb;

    /**
     * Supplier for the model to use for decode accepted data
     */
    private Supplier<T> mdSupplier;

    /**
     * Create a new Callback for a function call
     *
     * @param cb Function to call if accepted data is valid. First param is
     *           the id of the user who made the call, second param is the
     *           call data
     * @param md Function to create a new model to decode data
     */
    public FunctionCallCb(BiConsumer<String, T> cb, Supplier<T> md){
      this.cb = cb;
      this.mdSupplier = md;
    }

    /**
     * Decodes function call data passed as parameter, using the model created
     * when instantiating the class.
     * If the call is valid, it is propagated using the callback set when
     * instantiating the class.
     * If the call is not valid, it is silently discarded
     * (it is logged, thought)
     *
     * @param t Call to decode
     */
    @Override
    public void accept(AssociatedFunctionCall t) {
      try {
        T md = this.mdSupplier.get();
        md.decode(t.getFc().getData());
        this.cb.accept(t.getUser(), md);
      }
      catch (AbstractEncodable.InvalidDataException e){
        Logger.getLogger(LOG_NAMESPACE).log(
                Level.WARNING,
                "Unable to decode function call data",
                e
        );
      }
    }
  }
//  /**
//   * Update the match settings for a player (AKA the type of match he is
//   * waiting for).
//   *
//   * @param playerId The id of the player
//   * @param settings Settings of the match searched by the player.
//   *                 Null disable matchmaking for the player
//   */
//  public void updateWL(String playerId, ServerMatchSettings settings){
//    players.putIfAbsent(
//            playerId,
//            new ServerPlayer(playerId)
//    );
//
//    availableSettings.putIfAbsent(
//            settings.toString(),
//            new ServerMatchSettings(settings)
//    );
//
//    players.get(playerId).setSettings(new ServerMatchSettings(settings));
//  }
//
//  /**
//   * Process the list of currently connected players to find start possible
//   * valid matches.
//   */
//  private void makeMatchesEverySetting(){
//    System.out.println("Ciao");
////    this.tmpServer.sendUpdate("namespace.prova", "view da provare", "Aggiornamento Modello");
//    this.availableSettings.values().stream()
//            .filter(Objects::nonNull)
//            .forEach(this::makeMatchesForSetting);
//  }
//
//  /**
//   * Starts valid matches related to a given setting
//   *
//   * @param settings Setting to check the list of pending matches against
//   */
//  private void makeMatchesForSetting(ServerMatchSettings settings){
//    List<ServerPlayer> validPlayers = this.players.values().stream()
//            .filter(item ->
//                    settings.equals(item.getSettings())
//            )
//            .collect(Collectors.toList());
//
//    List<ServerPlayer> batch;
//
//    while (validPlayers.size() >= ServerLobby.MAX_PLAYERS){
//      batch = validPlayers.subList(0, (ServerLobby.MAX_PLAYERS - 1));
//      validPlayers.removeAll(batch);
//      this.createServerLobby(
//              batch,
//              new ServerMatchSettings(settings)
//      );
//    }
//
//    if (validPlayers.size() >= MIN_PLAYERS_FOR_GAME){
//      this.createServerLobby(
//              validPlayers,
//              new ServerMatchSettings(settings)
//      );
//    }
//  }
//
//  /**
//   * Creates a new old_server lobby (effectively starting a new match)
//   *
//   * @param p List of players who belongs to the match
//   * @param s Settings to use for the match
//   */
//  private void createServerLobby(List<ServerPlayer> p, ServerMatchSettings s){
//    List<String> allowedPlayers;
//    String tmp;
//    String lobbyId;
//
//    allowedPlayers = p.stream()
//            .map(ServerPlayer::getId)
//            .collect(Collectors.toList());
//
//    ServerLobby lobby = new ServerLobby(
//            allowedPlayers,
//            s.getMapType(),
//            s.getSkulls()
//    );
//
//    do {
//      tmp = System.currentTimeMillis() + "/" + Math.random();
//    } while (this.lobbies.containsKey(tmp));
//    lobbyId = tmp;
//
//    this.lobbies.put(lobbyId, lobby);
//
//    allowedPlayers.forEach(
//            id -> this.players.get(id).setLobbyId(lobbyId)
//    );
//  }
}
