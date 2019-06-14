package it.polimi.se2019.communication.encodable;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ModelViewUpdate extends Encodable {
  private static final byte[] UUID = new byte[]{
          115,87,74,-19,-57,27,84,15,114,95,23,-34,75,1,57,7
  };

  private String ns;
  private String view;
  private ByteBuffer model;

  public ModelViewUpdate(String ns, String view, ByteBuffer model) {
    super(UUID);
    this.setEncodingLogic(this::implementedEncode);
    this.setDecodingLogic(this::implementedDecode);

    this.ns = ns;
    this.view = view;
    this.model = model;
  }

  private List<ByteBuffer> implementedEncode() {
    List<ByteBuffer> toReturn = new ArrayList<>();

    toReturn.add(this.encodeString(this.ns));
    toReturn.add(this.encodeString(this.view));
    toReturn.add(this.encodeRaw(this.model));

    return toReturn;
  }

  private void implementedDecode(List<ByteBuffer> encoded)
          throws InvalidDataException {
    this.ns = this.decodeString(encoded.get(0));
    encoded.remove(0);

    this.view = this.decodeString(encoded.get(0));
    encoded.remove(0);

    this.model = this.decodeRaw(encoded.get(0));
    encoded.remove(0);

    if (!encoded.isEmpty()){
      throw new InvalidDataException();
    }
  }
}
