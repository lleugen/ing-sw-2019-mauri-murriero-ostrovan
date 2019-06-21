package it.polimi.se2019.utils.model;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LoginDataModel extends AbstractModel {
  private String user;

  public LoginDataModel(String user) {
    this.user = user;
  }

  @Override
  public ByteBuffer encode() {
    List<ByteBuffer> toReturn = new LinkedList<>();

    toReturn.add(encodeString(this.user));

    return mergeBuffer(toReturn);
  }

  public static LoginDataModel decode(ByteBuffer inputData)
          throws InvalidDataException {
    inputData.clear();

    String user = decodeString(inputData);

    return new LoginDataModel(user);
  }

  public String getUser() {
    return this.user;
  }
}
