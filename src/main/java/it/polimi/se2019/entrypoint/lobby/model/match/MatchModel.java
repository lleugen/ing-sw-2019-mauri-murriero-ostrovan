package it.polimi.se2019.entrypoint.lobby.model.match;

import it.polimi.se2019.utils.model.AbstractModel;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

public class MatchModel extends AbstractModel {
  /**
   * UUID for this model
   */
  private static final long UUID = 979_372_656L;

  /**
   * Create a Match Model
   *
   */
  public MatchModel() {
  }

  @Override
  public ByteBuffer encode() {
    List<ByteBuffer> toReturn = new LinkedList<>();

//    toReturn.add(encodeLong(UUID));
//    toReturn.add(encodeIterable(
//            this.messages.stream()
//                    .map(AbstractModel::encodeString)
//                    .map(AbstractModel::bufToArray)
//                    .collect(Collectors.toList())
//    ));

    return mergeBuffer(toReturn);
  }

  public static MatchModel decode(ByteBuffer inputData)
          throws InvalidDataException {
    inputData.clear();

    if (UUID != decodeLong(inputData)){
      throw new InvalidDataException();
    }

//    List<String> messages = new LinkedList<>();
//    for (byte[] item : decodeIterable(inputData)) {
//      messages.add(
//              decodeString(
//                      AbstractModel.arrayToBuf(
//                              item
//                      )
//              )
//      );
//    }

    return new MatchModel();
  }
}

