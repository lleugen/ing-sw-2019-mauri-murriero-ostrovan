package it.polimi.se2019.communication.encodable;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Encodable {
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
  private byte[] encodableUUID;

  /**
   * Contains the encoding logic for classes that extends this base class
   */
  private EncodingLogic encodingLogic;

  /**
   * Contains the decoding logic for classes that extendes this base class
   */
  private DecodingLogic decodingLogic;

  /**
   * Creates a new encodable.
   * Remember to call setEncodingLogic and setDecodingLogic after creating
   * this class
   *
   * @param uuid UUID for the encodable {@link #encodableUUID}
   */
  protected Encodable(byte[] uuid){
    this.encodableUUID = uuid.clone();
    this.encodingLogic = LinkedList::new;
    this.decodingLogic = (List<ByteBuffer> data) -> {};
  }

  /**
   * Sets the encoding logic.
   * This method should be called right after the constructor
   *
   * @param logic Encoding logic
   */
  protected void setEncodingLogic(EncodingLogic logic){
    this.encodingLogic = logic;
  }

  /**
   * Sets the encoding logic.
   * This method should be called right after the constructor
   *
   * @param logic Decoding logic
   */
  protected void setDecodingLogic(DecodingLogic logic){
    this.decodingLogic = logic;
  }

  /**
   * Encode the current object.
   *
   * Underlying implementation should not override this function, cause it
   * contains controls about the UUID.
   * Instead, implementedDecode and implementedEncode should be implemented
   * with the customized decoding and encoding logic.
   */
  public ByteBuffer encode() {
    ByteBuffer uuid = ByteBuffer
            .allocate(encodableUUID.length)
            .put(encodableUUID);

    ByteBuffer encodedData = this.encodeList(this.encodingLogic.get());

    return ByteBuffer
            .allocate(
                    uuid.capacity() +
                    encodedData.capacity()
            )
            .put(uuid)
            .put(encodedData);
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
    byte[] uuid = new byte[encodableUUID.length];

    try {
      encoded.get(uuid, 0, encodableUUID.length);
    }
    catch (BufferUnderflowException e){
      throw new InvalidDataException(e);
    }

    for (int i = 0; i < encodableUUID.length; i++){
      if (encodableUUID[i] != uuid[i]){
        throw new InvalidDataException();
      }
    }

    this.decodingLogic.accept(
            this.decodeList(encoded)
    );
  }

  /**
   * Encodes an Integer
   *
   * @param data The integer to encode
   *
   * @return the encoded representation of the int
   */
  protected ByteBuffer encodeInt(Integer data){
    return ByteBuffer.allocate(Integer.BYTES).putInt(data);
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
  protected Integer decodeInt(ByteBuffer encoded) throws InvalidDataException {
    try {
      return encoded.getInt();
    }
    catch (BufferUnderflowException e){
      throw new InvalidDataException(e);
    }
  }

  /**
   * Encodes a list of raw data
   *
   * @param data Data to encode
   */
  public ByteBuffer encodeList(List<ByteBuffer> data) {
    ByteBuffer toReturn;
    ByteBuffer encodedLength;
    int encodedSize;
    List<ByteBuffer> encodedDataList;

    encodedDataList = data.stream()
            .map(this::encodeRaw)
            .collect(Collectors.toList());
    encodedLength = this.encodeInt(encodedDataList.size());
    encodedSize = encodedDataList.stream()
            .mapToInt(ByteBuffer::capacity)
            .sum();

    toReturn = ByteBuffer
            .allocate(
                    encodedLength.capacity() +
                            encodedSize
            )
            .put(encodedLength);

    for (ByteBuffer item : encodedDataList) {
      toReturn.put(item);
    }

    return toReturn;
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
  public List<ByteBuffer> decodeList(ByteBuffer encoded)
          throws InvalidDataException {

    List<ByteBuffer> toReturn = new LinkedList<>();
    Integer length = this.decodeInt(encoded);

    for (int i = 0; i < length; i++){
      toReturn.add(
              this.decodeRaw(encoded)
      );
    }

    return toReturn;
  }
  /**
   * Encode raw data
   *
   * @param data Data to encode
   */
  ByteBuffer encodeRaw(ByteBuffer data){
    int size = data.capacity();
    ByteBuffer encodedSize = this.encodeInt(size);

    return ByteBuffer
            .allocate(
                    size +
                    encodedSize.capacity()
            )
            .put(encodedSize)
            .put(data);
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
  public ByteBuffer decodeRaw(ByteBuffer encoded)
          throws InvalidDataException {

    Integer size = this.decodeInt(encoded);

    ByteBuffer toReturn = ByteBuffer.allocate(size);
    byte[] readBytes = new byte[size];

    encoded.get(readBytes);
    toReturn.put(readBytes);

    return toReturn;
  }
  /**
   * Encodes a string
   *
   * @param data String to encode
   *
   * @return the encoded representation of the string
   */
  protected ByteBuffer encodeString(String data){
    byte[] byteEncodedString = data.getBytes(StandardCharsets.UTF_8);

    ByteBuffer encodedString = ByteBuffer
              .allocate(byteEncodedString.length)
              .put(byteEncodedString);

    ByteBuffer encodedLength = this.encodeInt(byteEncodedString.length);

    return ByteBuffer
            .allocate(
                    encodedLength.capacity() +
                    encodedString.capacity()
            )
            .put(encodedLength)
            .put(encodedString);
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
  protected String decodeString(ByteBuffer encoded)
          throws InvalidDataException {
    Integer length = this.decodeInt(encoded);
    byte[] readString = new byte[length];

    try {
      encoded.get(readString, 0, length);
    }
    catch (BufferUnderflowException e){
      throw new InvalidDataException(e);
    }

    return new String(readString, StandardCharsets.UTF_8);
  }

  /**
   * Definition for the encoding logic.
   *
   * The encoding logic should encode the object it is contained in (possibly
   * using helper functions such as encodeString defined in this class) and
   * return a list of ByteBuffers containing the encoded version of each
   * properties to encode.
   */
  @FunctionalInterface
  public interface EncodingLogic extends Supplier<List<ByteBuffer>> { }

  /**
   * Definition of a decoding logic.
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
  @FunctionalInterface
  public interface DecodingLogic {
    void accept(List<ByteBuffer> data) throws InvalidDataException;
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
