/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.game.model;

import it.polimi.se2019.engine.model.AbstractModel;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

public final class RootModel extends AbstractModel {
  /**
   * UUID for this model.
   * When decoding, this UUID will be checked agains decoded UUID.
   * If the UUID doesn't match, decoding will be rejected
   */
  private static final long UUID = 953_734_576_878_927_865L;

  /**
   * Encode data.
   * See description for this method in AbstractModel
   */
  @Override
  public ByteBuffer encode(){
    List<ByteBuffer> toReturn = new LinkedList<>();

    toReturn.add(encodeLong(UUID));

    return mergeBuffer(toReturn);
  }

  /**
   * Decodes encoded data
   * See description for this method in AbstractModel
   *
   * @param inputData Input Data to decode
   *
   * @return A new istance of MatchModel, initialized using inputData
   *
   * @throws InvalidDataException if an error occurs while decoding data,
   *                              or if encoded UUID doesn't match
   *                              MatchModel's UUID
   */
  public static RootModel decode(ByteBuffer inputData)
          throws InvalidDataException {

    inputData.clear();

    if (UUID != decodeLong(inputData)){
      throw new InvalidDataException();
    }

    return new RootModel();
  }
}

