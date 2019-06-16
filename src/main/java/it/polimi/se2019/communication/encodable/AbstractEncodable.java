package it.polimi.se2019.communication.encodable;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

public abstract class AbstractEncodable {
  /**
   * UUID of the encodable object.
   * It will be used while decoding to be sure encoded object matches decoded
   * target.
   * You must change it each time you makes a change to the encoded
   * representation of the object.
   *
   * A good way to generate this UUID may be:
   * https://firebase.googleblog.com/2015/02/the-2120-ways-to-ensure-unique_68.html
   */
  private ByteBuffer encodableUUID;

  /**
   * Creates a new encodable.
   * Remember to call setEncodingLogic and setDecodingLogic after creating
   * this class
   *
   * @param uuid UUID for the encodable {@link #encodableUUID}
   */
  AbstractEncodable(byte[] uuid){
    this.encodableUUID = clearBuffer(
            ByteBuffer
            .allocate(uuid.length)
            .put(uuid)
    );
  }

  /**
   * Clears the buffer passed as parameter (prepares it for reading after
   * adding data to it)
   *
   * @param buf the buffer to clear
   *
   * @return The cleared buffer. It is not required that the same buffer will
   *         be returned. A new buffer with the same data may be returned
   */
  private static ByteBuffer clearBuffer(ByteBuffer buf){
    buf.clear();
    return buf;
  }

  /**
   * Concats an arrays of ByteBuffers in a single ByteBuffer.
   * The merged buffer will have the size of the sum of the buffers passed as
   * parameter, will contains the buffers passed as parameter concatenated, and
   * will be returned already ready for reading data
   *
   * @param bufList Arrays of buffer to concatenate
   *
   * @return The concatenated buffer
   */
  private static ByteBuffer mergeBuffer(List<ByteBuffer> bufList){
    ByteBuffer toReturn;

    int totalSize = bufList.stream()
            .mapToInt(ByteBuffer::capacity)
            .sum();

    toReturn = ByteBuffer.allocate(totalSize);

    bufList.forEach(
            buf -> toReturn.put(
                    clearBuffer(buf)
            )
    );

    return clearBuffer(toReturn);
  }

  /**
   * Implemented encoding logic for each extension
   *
   * The encoding logic should encode the object it is contained in (possibly
   * using helper functions such as encodeString defined in this class) and
   * return a list of ByteBuffers containing the encoded version of each
   * properties to encode.
   */
  protected abstract Iterable<ByteBuffer> implementedEncode();

  /**
   * Implemented decoding logic for each extension
   *
   * A decoding logic will receive encoded data from an EncodingLogic, as
   * passed as parameter to the EncodingLogic, and should restore properties
   * in the object he has in control
   *
   * If encoded data is invalid, she should throw an InvalidDataException.
   * It is not required to clean up object state when throwing the exception.
   * If this exception is catched, object should be considered invalid, and so
   * discarded
   */
  protected abstract void implementedDecode(Iterable<ByteBuffer> data)
          throws InvalidDataException;

  /**
   * Encode the current object.
   *
   * Underlying implementation should not override this function, cause it
   * contains controls about the UUID.
   * Instead, implementedDecode and implementedEncode should be implemented
   * with the customized decoding and encoding logic.
   */
  public ByteBuffer encode() {
    List<ByteBuffer> toEncodeList = new LinkedList<>();

    toEncodeList.add(this.encodableUUID);
    toEncodeList.add(
            encodeIterable(
                    this.implementedEncode()
            )
    );

    return mergeBuffer(toEncodeList);
  }

  /**
   * Decodes data passed as parameter updating the current istance of the class
   *
   * Underlying implementation should not override this function, cause it
   * contains controls about the UUID.
   * Instead, implementedDecode and implementedEncode should be implemented
   * with the customized decoding and encoding logic.
   *
   * @param encoded Encoded data to be decoded
   *
   * @throws InvalidDataException if there is an error while decoding data
   *                              (e.g. you are trying to decode an encoded
   *                              class with another class)
   */
  public void decode(ByteBuffer encoded) throws InvalidDataException {
    byte[] uuid = new byte[encodableUUID.capacity()];

    try {
      encoded.get(uuid);
    }
    catch (BufferUnderflowException e){
      throw new InvalidDataException(e);
    }

    ByteBuffer uuidByteBuffer;
    uuidByteBuffer = ByteBuffer.allocate(uuid.length).put(uuid);
    uuidByteBuffer.clear();
    this.encodableUUID.clear();

    if (!this.encodableUUID.equals(uuidByteBuffer)){
      throw new InvalidDataException();
    }

    this.implementedDecode(
            decodeIterable(encoded)
    );
  }

  /**
   * Encodes an Integer
   *
   * @param data The integer to encode
   *
   * @return the encoded representation of the int
   */
  protected static ByteBuffer encodeInt(Integer data){
    List<ByteBuffer> toEncodeList = new LinkedList<>();

    toEncodeList.add(
            ByteBuffer.allocate(Integer.BYTES).putInt(data)
    );

    return mergeBuffer(toEncodeList);
  }

  /**
   * Decodes an Integer from an encoded ByteBuffer
   *
   * __WARN__ Encoded data are removed from the ByteBuffer
   *
   * @param encoded ByteBuffer containing encoded data. The first byte of the
   *                buffer MUST be the first byte of the encoded data to decode
   *
   * @return the decoded data
   */
  protected static Integer decodeInt(ByteBuffer encoded)
          throws InvalidDataException {
    try {
      return encoded.getInt();
    }
    catch (BufferUnderflowException e){
      throw new InvalidDataException(e);
    }
  }

  /**
   * Encodes a Collection of raw data
   *
   * @param data Data to encode
   */
  public static ByteBuffer encodeIterable(Iterable<ByteBuffer> data) {
    List<ByteBuffer> toEncode = new LinkedList<>();
    Iterator<ByteBuffer> it = data.iterator();

    toEncode.add(ByteBuffer.allocate(0));
    while (it.hasNext()){
      toEncode.add(encodeRaw(it.next()));
    }
    toEncode.set(
            0,
            encodeInt(
                    (toEncode.size() - 1)
            )
    );

    return mergeBuffer(
            toEncode
    );
  }

  /**
   * Decodes a list of raw data
   *
   * __WARN__ Encoded data are removed from the ByteBuffer
   *
   * @param encoded ByteBuffer containing encoded data. The first byte of the
   *                buffer MUST be the first byte of the encoded data to decode
   *
   * @return the decoded data
   *
   */
  public static Iterable<ByteBuffer> decodeIterable(ByteBuffer encoded)
          throws InvalidDataException {

    List<ByteBuffer> toReturn = new LinkedList<>();
    Integer length = decodeInt(encoded);

    for (int i = 0; i < length; i++){
      toReturn.add(
              decodeRaw(encoded)
      );
    }

    return toReturn;
  }

  /**
   * Encode raw data
   *
   * @param data Data to encode
   */
  static ByteBuffer encodeRaw(ByteBuffer data){
    List<ByteBuffer> toEncodeList = new LinkedList<>();

    toEncodeList.add(
            encodeInt(data.capacity())
    );
    toEncodeList.add(
            data
    );

    return mergeBuffer(toEncodeList);
  }

  /**
   * Decodes raw data
   *
   * __WARN__ Encoded data are removed from the ByteBuffer
   *
   * @param encoded ByteBuffer containing encoded data. The first byte of the
   *                buffer MUST be the first byte of the encoded data to decode
   *
   * @return the decoded data
   *
   */
  public static ByteBuffer decodeRaw(ByteBuffer encoded)
          throws InvalidDataException {

    Integer size = decodeInt(encoded);

    ByteBuffer toReturn = ByteBuffer.allocate(size);
    byte[] readBytes = new byte[size];

    encoded.get(readBytes);
    toReturn.put(readBytes);

    return clearBuffer(toReturn);
  }

  /**
   * Encodes a string
   *
   * @param data String to encode
   *
   * @return the encoded representation of the string
   */
  protected static ByteBuffer encodeString(String data){
    byte[] byteEncodedString = data.getBytes(StandardCharsets.UTF_16);

    ByteBuffer encodedString = ByteBuffer
            .allocate(byteEncodedString.length)
            .put(byteEncodedString);

    List<ByteBuffer> toEncodeList = new LinkedList<>();

    toEncodeList.add(
            encodeInt(encodedString.capacity())
    );
    toEncodeList.add(
            encodedString
    );

    return mergeBuffer(toEncodeList);
  }

  /**
   * Decodes a String from an encoded ByteBuffer
   *
   * __WARN__ Encoded data are removed from the ByteBuffer
   *
   * @param encoded ByteBuffer containing encoded data. The first byte of the
   *                buffer MUST be the first byte of the encoded data to decode
   *
   * @return the decoded data
   */
  protected static String decodeString(ByteBuffer encoded)
          throws InvalidDataException {
    Integer length = decodeInt(encoded);
    byte[] readString = new byte[length];

    try {
      encoded.get(readString, 0, length);
    }
    catch (BufferUnderflowException e){
      throw new InvalidDataException(e);
    }

    return new String(readString, StandardCharsets.UTF_16);
  }

  /**
   * Raised when there is an error decoding data.
   * When this exception is raised, the class must be discarded, cause it may
   * have been left in a non consistent state.
   */
  public static class InvalidDataException extends Exception {
    public InvalidDataException(Throwable e){
      super(e);
    }

    public InvalidDataException(){
      super();
    }

    @Override
    public String toString() {
      return "Data you passed are not valid";
    }
  }
}
