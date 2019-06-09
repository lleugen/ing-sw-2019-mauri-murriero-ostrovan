package it.polimi.se2019.model.server;

import java.rmi.Remote;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Objects;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Server implements Remote {
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
   * Timeout in seconds for cron job used to start new games
   *
   * Default: 15
   */
  private static final int CRONJOB_TIMEOUT = 1;

  private Map<String, ServerPlayer> players;
  private Map<String, ServerMatchSettings> availableSettings;
  private Map<String, ServerLobby> lobbies;

  public Server(){
    this.players = Collections.synchronizedMap(new HashMap<>());
    this.availableSettings = Collections.synchronizedMap(new HashMap<>());
    this.lobbies = Collections.synchronizedMap(new HashMap<>());

    Executors.newScheduledThreadPool(1)
    .scheduleWithFixedDelay(
            this::makeMatchesEverySetting,
            0,
            CRONJOB_TIMEOUT,
            TimeUnit.SECONDS
    );
  }

  /**
   * Update the match settings for a player (AKA the type of match he is
   * waiting for).
   *
   * @param playerId The id of the player
   * @param settings Settings of the match searched by the player.
   *                 Null disable matchmaking for the player
   */
  public void updateWL(String playerId, ServerMatchSettings settings){
    players.putIfAbsent(
            playerId,
            new ServerPlayer(playerId)
    );

    availableSettings.putIfAbsent(
            settings.toString(),
            new ServerMatchSettings(settings)
    );

    players.get(playerId).setSettings(new ServerMatchSettings(settings));
  }

  /**
   * Process the list of currently connected players to find start possible
   * valid matches.
   */
  private void makeMatchesEverySetting(){
    System.out.println("Ciao");
    this.availableSettings.values().stream()
            .filter(Objects::nonNull)
            .forEach(this::makeMatchesForSetting);
  }

  /**
   * Starts valid matches related to a given setting
   *
   * @param settings Setting to check the list of pending matches against
   */
  private void makeMatchesForSetting(ServerMatchSettings settings){
    List<ServerPlayer> validPlayers = this.players.values().stream()
            .filter(item ->
                    settings.equals(item.getSettings())
            )
            .collect(Collectors.toList());

    List<ServerPlayer> batch;

    while (validPlayers.size() >= ServerLobby.MAX_PLAYERS){
      batch = validPlayers.subList(0, (ServerLobby.MAX_PLAYERS - 1));
      validPlayers.removeAll(batch);
      this.createServerLobby(
              batch,
              new ServerMatchSettings(settings)
      );
    }

    if (validPlayers.size() >= MIN_PLAYERS_FOR_GAME){
      this.createServerLobby(
              validPlayers,
              new ServerMatchSettings(settings)
      );
    }
  }

  /**
   * Creates a new server lobby (effectively starting a new match)
   *
   * @param p List of players who belongs to the match
   * @param s Settings to use for the match
   */
  private void createServerLobby(List<ServerPlayer> p, ServerMatchSettings s){
    List<String> allowedPlayers;
    String tmp;
    String lobbyId;

    allowedPlayers = p.stream()
            .map(ServerPlayer::getId)
            .collect(Collectors.toList());

    ServerLobby lobby = new ServerLobby(
            allowedPlayers,
            s.getMapType(),
            s.getSkulls()
    );

    do {
      tmp = System.currentTimeMillis() + "/" + Math.random();
    } while (this.lobbies.containsKey(tmp));
    lobbyId = tmp;

    this.lobbies.put(lobbyId, lobby);

    allowedPlayers.forEach(
            id -> this.players.get(id).setLobbyId(lobbyId)
    );
  }
}
