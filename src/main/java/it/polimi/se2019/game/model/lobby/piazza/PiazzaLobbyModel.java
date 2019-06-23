package it.polimi.se2019.game.model.lobby.piazza;

import it.polimi.se2019.utils.model.AbstractModel;

import java.nio.ByteBuffer;
import java.util.*;
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
}
