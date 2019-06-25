/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.engine.model;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

public class LoginDataModel extends AbstractModel {
  /**
   * UUID for this model
   */
  private static final long UUID = 548_949_605L;
  private String user;

  public LoginDataModel(String user) {
    this.user = user;
  }

  @Override
  public ByteBuffer encode() {
    List<ByteBuffer> toReturn = new LinkedList<>();

    toReturn.add(encodeLong(UUID));
    toReturn.add(encodeString(this.user));

    return mergeBuffer(toReturn);
  }

  public static LoginDataModel decode(ByteBuffer inputData)
          throws InvalidDataException {
    inputData.clear();

    if (UUID != decodeLong(inputData)){
      throw new InvalidDataException();
    }

    String user = decodeString(inputData);
    if (inputData.hasRemaining()) {
      throw new InvalidDataException();
    }
    else {
      return new LoginDataModel(user);
    }
  }

  public String getUser() {
    return this.user;
  }
}
