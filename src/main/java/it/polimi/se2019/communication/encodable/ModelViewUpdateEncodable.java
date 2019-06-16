package it.polimi.se2019.communication.encodable;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ModelViewUpdateEncodable extends AbstractEncodable {
  private static final byte[] UUID = new byte[]{
          115,87,74,-19,-57,27,84,15,114,95,23,-34,75,1,57,7
  };

  private String ns;
  private String view;
  private ByteBuffer model;

  public ModelViewUpdateEncodable(String ns, String view, ByteBuffer model) {
    super(UUID);

    this.ns = ns;
    this.view = view;
    this.model = model;
  }

  public ModelViewUpdateEncodable(){
    super(UUID);

    this.ns = "";
    this.view = "";
    this.model = ByteBuffer.allocate(0);
  }
  protected Iterable<ByteBuffer> implementedEncode() {
    List<ByteBuffer> toReturn = new LinkedList<>();

    toReturn.add(encodeString(this.ns));
    toReturn.add(encodeString(this.view));
    toReturn.add(encodeRaw(this.model));

    return toReturn;
  }

  protected void implementedDecode(Iterable<ByteBuffer> encoded)
          throws InvalidDataException {
    Iterator<ByteBuffer> it = encoded.iterator();

    try {
      this.ns = decodeString(it.next());
      this.view = decodeString(it.next());
      this.model = decodeRaw(it.next());
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

  public String getView() {
    return view;
  }

  public ByteBuffer getModel() {
    return model;
  }
}
