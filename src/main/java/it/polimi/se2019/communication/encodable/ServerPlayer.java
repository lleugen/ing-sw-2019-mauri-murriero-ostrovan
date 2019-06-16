package it.polimi.se2019.communication.encodable;

import java.nio.ByteBuffer;
import java.util.stream.Collectors;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ServerPlayer extends AbstractEncodable {
  private String id;
  private List<String> cps;

  private static final byte[] UUID = new byte[]{
          -8,125,-20,-68,93,60,37,-51,28,127,104,-117,70,126,43,-73
  };

  /**
   * @param id    Id of the user
   * @param cps   List of id of other connected players to the server
   */
  public ServerPlayer(String id, List<String> cps) {
    super(UUID);

    this.id = id;
    this.cps = new LinkedList<>(cps);
  }

  public ServerPlayer(){
    super(UUID);

    this.id = "";
    this.cps = new LinkedList<>();
  }

  protected Iterable<ByteBuffer> implementedEncode() {
    List<ByteBuffer> toReturn = new LinkedList<>();

    toReturn.add(encodeString(this.id));
    toReturn.add(encodeIterable(
            this.cps.stream()
                    .map(AbstractEncodable::encodeString)
                    .collect(Collectors.toList())
            )
    );

    return toReturn;
  }

  protected void implementedDecode(Iterable<ByteBuffer> encoded)
          throws InvalidDataException {
    Iterator<ByteBuffer> it = encoded.iterator();

    try {
      this.id = decodeString(it.next());

      this.cps = new LinkedList<>();
      for (ByteBuffer item : decodeIterable(it.next())) {
        this.cps.add(decodeString(item));
      }
    }
    catch (NoSuchElementException e){
      throw new InvalidDataException(e);
    }

    if (it.hasNext()){
      throw new InvalidDataException();
    }
  }

  public String getId() {
    return this.id;
  }

  public List<String> getCps() {
    return new LinkedList<>(this.cps);
  }
}

