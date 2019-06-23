package it.polimi.se2019.entrypoint.lobby.model.lobby.piazza;

import it.polimi.se2019.utils.model.AbstractModel;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Collectors;

public class PiazzaLobbyModel extends AbstractModel {
  /**
   * UUID for this model
   */
  private static final long UUID = 456_205_995L;

  /**
   * Number of player required to close a room
   *
   * Default: 2
   */
  private static final int PLAYERS_IN_ROOM = 2;

  /**
   * Association between a match and players registered to it.
   * Matches are identified by <mapId>-<skulls>
   */
  private Map<String, List<String>> matches;

  public PiazzaLobbyModel(Map<String, List<String>> matches){
    this.matches = new HashMap<>();

    matches.forEach((String key, List<String> item) ->
            this.matches.put(key, new LinkedList<>(item))
    );
  }

  public PiazzaLobbyModel(){
    this.matches = new HashMap<>();
  }

  @Override
  public ByteBuffer encode() {
    List<ByteBuffer> toReturn = new LinkedList<>();
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

    toReturn.add(encodeLong(UUID));
    toReturn.add(encodeMap(encodedMatches));

    return mergeBuffer(toReturn);
  }

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
