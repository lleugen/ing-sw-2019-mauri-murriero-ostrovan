package it.polimi.se2019.communication.encodable;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class FunctionCallEncodable extends AbstractEncodable {
  private String ns;
  private String f;
  private ByteBuffer data;

  private static final String UUID = "FunctionCallEncodable.v1";

  public FunctionCallEncodable(String ns, String f, ByteBuffer data) {
    super(UUID);

    this.ns = ns;
    this.f = f;
    this.data = data;
  }

  public FunctionCallEncodable(){
    super(UUID);

    this.ns = "";
    this.f = "";
    this.data = ByteBuffer.allocate(0);
  }

  protected Iterable<ByteBuffer> implementedEncode() {
    List<ByteBuffer> toReturn = new LinkedList<>();

    toReturn.add(encodeString(this.ns));
    toReturn.add(encodeString(this.f));
    toReturn.add(encodeRaw(this.data));

    return toReturn;
  }

  protected void implementedDecode(Iterable<ByteBuffer> encoded)
          throws InvalidDataException {
    Iterator<ByteBuffer> it = encoded.iterator();

    try {
      this.ns = decodeString(it.next());
      this.f = decodeString(it.next());
      this.data = decodeRaw(it.next());
    }
    catch (NoSuchElementException e){
      throw new InvalidDataException(e);
    }

    if (it.hasNext()){
      throw new InvalidDataException();
    }
  }

  public String getNs() {
    return ns;
  }

  public String getF() {
    return f;
  }

  public ByteBuffer getData() {
    return data;
  }
}
