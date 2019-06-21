package it.polimi.se2019.utils.model;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

public class FunctionCallModel extends AbstractModel {
//  private String user;
  private String ns;
  private String f;
  private String data;

  public FunctionCallModel(String ns, String f, String data) {
//    this.user = user;
    this.ns = ns;
    this.f = f;
    this.data = data;
  }

  @Override
  public ByteBuffer encode() {
    List<ByteBuffer> toReturn = new LinkedList<>();

//    toReturn.add(encodeString(this.user));
    toReturn.add(encodeString(this.ns));
    toReturn.add(encodeString(this.f));
    toReturn.add(encodeString(this.data));

    return mergeBuffer(toReturn);
  }

  public static FunctionCallModel decode(ByteBuffer inputData)
          throws InvalidDataException {
    inputData.clear();
//    String user = decodeString(inputData);
    String ns = decodeString(inputData);
    String f = decodeString(inputData);
    String data = decodeString(inputData);

//    return new FunctionCallModel(user, ns, f, data);
    return new FunctionCallModel(ns, f, data);
  }
//
//  public String getUser() {
//    return this.user;
//  }

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
