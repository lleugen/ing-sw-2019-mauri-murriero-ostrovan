package it.polimi.se2019.utils.model;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class ModelUpdateModel extends AbstractModel {
  private String ns;
  private ByteBuffer model;

  public ModelUpdateModel(String ns, ByteBuffer model) {
    this.ns = ns;
    this.model = model;
  }

  @Override
  public ByteBuffer encode() {
    List<ByteBuffer> toReturn = new LinkedList<>();

    toReturn.add(encodeString(this.ns));
    toReturn.add(encodeRaw(this.model));

    return mergeBuffer(toReturn);
  }

  public static ModelUpdateModel decode(ByteBuffer inputData)
          throws InvalidDataException {
    inputData.clear();

    String ns = decodeString(inputData);
    ByteBuffer model = decodeRaw(inputData);

    return new ModelUpdateModel(ns, model);
  }

  public String getNs() {
    return ns;
  }

  public ByteBuffer getModel() {
    return model;
  }

}
