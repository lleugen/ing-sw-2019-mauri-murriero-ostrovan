/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.game.model.lobby.piazza;

import it.polimi.se2019.engine.model.AbstractModel;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 * Representation of a Piazza, places where players find matches to join 
 */
public class PiazzaLobbyModel 
        extends AbstractModel {
  /**
   * Number of player required to close a room
   *
   * Default: 2
   */
  private static final int PLAYERS_IN_ROOM = 2;

  /**
   * UUID for this model.
   * When decoding, this UUID will be checked agains decoded UUID.
   * If the UUID doesn't match, decoding will be rejected
   */
  private static final long UUID = 721_829_730_884_498_478L;

  /**
   * Association between a match and players registered to it.
   * Matches are identified by <mapId>-<skulls>
   */
  private Map<String, List<String>> matches;

  /**
   * Create a new PiazzaLobbyModel
   *
   * @param matches Map of matches to initialize the piazza with
   */
  public PiazzaLobbyModel(Map<String, List<String>> matches) {
    this.matches = new HashMap<>();

    matches.forEach((String key, List<String> item) ->
            this.matches.put(key, new LinkedList<>(item))
    );
  }

  /**
   * Create a new empty PiazzaLobbyModel
   */
  public PiazzaLobbyModel(){
    this.matches = new HashMap<>();
  }

  /**
   * Encode data.
   * See description for this method in AbstractModel
   */
  @Override
  public ByteBuffer encode() {
    List<ByteBuffer> toReturn = new LinkedList<>();

    toReturn.add(encodeLong(UUID));

    Map<String, byte[]> encodedMatches = new HashMap<>();

    matches.forEach((String key, List<String> data) ->
            encodedMatches.put(
                    key,
                    bufToArray(
                            encodeIterable(
                                    data.stream()
                                            .map(AbstractModel::encodeString)
                                            .map(AbstractModel::bufToArray)
                                            .collect(Collectors.toList())
                            )
                    )
            )
    );
    toReturn.add(encodeMap(encodedMatches));

    return mergeBuffer(toReturn);
  }

  /**
   * Decodes encoded data
   * See description for this method in AbstractModel
   * 
   * @param inputData Input Data to decode
   * 
   * @return A new istance of PiazzaLobbyModel, initialized using inputData
   * 
   * @throws InvalidDataException if an error occurs while decoding data, 
   *                              or if encoded UUID doesn't match 
   *                              PiazzaLobbyModel's UUID
   */
  public static PiazzaLobbyModel decode(ByteBuffer inputData)
          throws InvalidDataException {

    inputData.clear();

    if (UUID != decodeLong(inputData)){
      throw new InvalidDataException();
    }

    Map<String, byte[]> parsedMap = decodeMap(inputData);
    Map<String, List<String>> toReturn = new HashMap<>();

    for (Map.Entry<String, byte[]> entry : parsedMap.entrySet()) {
      toReturn.put(
              entry.getKey(),
              new LinkedList<>()
      );

      Iterator<byte []> listIterator = decodeIterable(
              arrayToBuf(entry.getValue())
      ).iterator();

      while (listIterator.hasNext()){
        toReturn.get(entry.getKey()).add(
                decodeString(
                        arrayToBuf(
                                listIterator.next()
                        )
                )
        );
      }
    }

    return new PiazzaLobbyModel(toReturn);
  }

  /**
   * Get the map of available matches (not yet full)
   * @return The map of available matches (not yet full)
   */
  public Map<String, List<String>> getMatches() {
    Map<String, List<String>> toReturn = new HashMap<>();

    matches.forEach((String key, List<String> item) ->
            toReturn.put(key, new LinkedList<>(item))
    );

    return toReturn;
  }

  /**
   * Add a new player to the selected room.
   * If the player was registered to another room, it is deregistered from the
   * old room
   *
   * @param user User to add to the room
   * @param room Id of the room to add the player to (<mapId>-<skulls>)
   *
   * @return a list of players id removed from the room. An empty list if the
   *         room is not full yet
   */
  public synchronized List<String> addPlayer(String user, String room){
    // See ConcurrentModificationException
    List<String> toRemove = new LinkedList<>();

    for (Map.Entry<String, List<String>> entry : this.matches.entrySet()) {
      entry.getValue().remove(user);
      if (entry.getValue().isEmpty()){
        toRemove.add(entry.getKey());
      }
    }

    toRemove.forEach(this.matches::remove);

    this.matches.putIfAbsent(room, new LinkedList<>());
    this.matches.get(room).add(user);

    if (this.matches.get(room).size() == PLAYERS_IN_ROOM){
      List<String> toReturn = this.matches.get(room);
      this.matches.remove(room);
      return toReturn;
    }
    else {
      return new LinkedList<>();
    }
  }
}
