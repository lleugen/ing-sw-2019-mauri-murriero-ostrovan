/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.engine.model;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

public class FunctionCallModel extends AbstractModel {
  /**
   * UUID for this model
   */
  private static final long UUID = 882_417_897L;
  private String ns;
  private String f;
  private String data;

  public FunctionCallModel(String ns, String f, String data) {
    this.ns = ns;
    this.f = f;
    this.data = data;
  }

  @Override
  public ByteBuffer encode() {
    List<ByteBuffer> toReturn = new LinkedList<>();

    toReturn.add(encodeLong(UUID));
    toReturn.add(encodeString(this.ns));
    toReturn.add(encodeString(this.f));
    toReturn.add(encodeString(this.data));

    return mergeBuffer(toReturn);
  }

  public static FunctionCallModel decode(ByteBuffer inputData)
          throws InvalidDataException {
    inputData.clear();

    if (UUID != decodeLong(inputData)){
      throw new InvalidDataException();
    }

    String ns = decodeString(inputData);
    String f = decodeString(inputData);
    String data = decodeString(inputData);

    if (inputData.hasRemaining()) {
      throw new InvalidDataException();
    }
    else {
      return new FunctionCallModel(ns, f, data);
    }
  }

  public String getNs() {
    return this.ns;
  }

  public String getF() {
    return this.f;
  }

  public String getData() {
    return this.data;
  }

}
