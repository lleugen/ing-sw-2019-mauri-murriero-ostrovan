package it.polimi.se2019.utils.model;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

public class ModelUpdateModel extends AbstractModel {
  /**
   * UUID for this model
   */
  private static final long UUID = 316_713_123L;
  private String ns;
  private byte[] model;

  public ModelUpdateModel(String ns, byte[] model) {
    this.ns = ns;
    this.model = model.clone();
  }

  @Override
  public ByteBuffer encode() {
    List<ByteBuffer> toReturn = new LinkedList<>();

    toReturn.add(encodeLong(UUID));
    toReturn.add(encodeString(this.ns));
    toReturn.add(encodeRaw(this.model));

    return mergeBuffer(toReturn);
  }

  public static ModelUpdateModel decode(ByteBuffer inputData)
          throws InvalidDataException {
    inputData.clear();

    if (UUID != decodeLong(inputData)){
      throw new InvalidDataException();
    }

    String ns = decodeString(inputData);
    byte[] model = decodeRaw(inputData);

    if (inputData.hasRemaining()) {
      throw new InvalidDataException();
    }
    else {
      return new ModelUpdateModel(ns, model);
    }
  }

  public String getNs() {
    return this.ns;
  }

  public byte[] getModel() {
    return this.model.clone();
  }

}
