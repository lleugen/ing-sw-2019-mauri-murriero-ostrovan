package it.polimi.se2019.entrypoint.lobby.model.lobby;

import it.polimi.se2019.utils.model.AbstractModel;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

public class LobbyModel extends AbstractModel {
  /**
   * UUID for this model
   */
  private static final long UUID = 328_600_283L;

  @Override
  public ByteBuffer encode() {
    List<ByteBuffer> toReturn = new LinkedList<>();

    toReturn.add(encodeLong(UUID));

    return mergeBuffer(toReturn);
  }

  public static LobbyModel decode(ByteBuffer inputData)
          throws InvalidDataException {

    inputData.clear();

    if (UUID != decodeLong(inputData)){
      throw new InvalidDataException();
    }

    return new LobbyModel();
  }
}
