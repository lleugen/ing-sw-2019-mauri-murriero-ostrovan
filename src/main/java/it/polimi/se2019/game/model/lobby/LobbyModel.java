/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.game.model.lobby;

import it.polimi.se2019.engine.model.AbstractModel;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

/**
 * Representation of a Main places where players are inserted while waiting 
 * for available matches 
 */
public class LobbyModel 
        extends AbstractModel {
  /**
   * UUID for this model.
   * When decoding, this UUID will be checked agains decoded UUID.
   * If the UUID doesn't match, decoding will be rejected
   */
  private static final long UUID = 318_580_125_666_905_273L;

  /**
   * Create a new LobbyModel
   */
  public LobbyModel() {
    // Standard Constructor
  }

  /**
   * Encode data.
   * See description for this method in AbstractModel
   */
  @Override
  public ByteBuffer encode() {
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
   * @return A new istance of LobbyModel, initialized using inputData
   * 
   * @throws InvalidDataException if an error occurs while decoding data, 
   *                              or if encoded UUID doesn't match 
   *                              LobbyModel's UUID
   */
  public static LobbyModel decode(ByteBuffer inputData)
          throws InvalidDataException {

    inputData.clear();

    if (UUID != decodeLong(inputData)){
      throw new InvalidDataException();
    }

    return new LobbyModel(
       
    );
  }
}
