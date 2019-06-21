package it.polimi.se2019.utils.model;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

public abstract class AbstractModel {
  /**
   * Encode the current object.
   *
   * @return The encoded object
   */
  public abstract ByteBuffer encode();

  /**
   * Decode an encoded object passed as parameter.
   * If the decoded fails, InvalidDataException should be raised
   *
   * __WARN__ This implementation always raises InvalidDataException.
   *          Implementation should replace this method with their valid
   *          decoding method
   */
  public static AbstractModel decode(ByteBuffer data) throws InvalidDataException{
    throw new InvalidDataException(
            "Subclasses MUST override this method"
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
  protected static ByteBuffer clearBuffer(ByteBuffer buf){
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
  protected static ByteBuffer mergeBuffer(List<ByteBuffer> bufList){
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
   * Encodes a Long Int
   *
   * @param data The long to encode
   *
   * @return the encoded representation of the long
   */
  protected static ByteBuffer encodeLong(Long data){
    List<ByteBuffer> toEncodeList = new LinkedList<>();

    toEncodeList.add(
            ByteBuffer.allocate(Long.BYTES).putLong(data)
    );

    return mergeBuffer(toEncodeList);
  }

  /**
   * Decodes a Long from an encoded ByteBuffer
   *
   * __WARN__ Encoded data are removed from the ByteBuffer
   *
   * @param encoded ByteBuffer containing encoded data. The first byte of the
   *                buffer MUST be the first byte of the encoded data to decode
   *
   * @return the decoded data
   */
  protected static Long decodeLong(ByteBuffer encoded)
          throws InvalidDataException {
    try {
      return encoded.getLong();
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
  protected static ByteBuffer encodeIterable(Iterable<ByteBuffer> data) {
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
  protected static Iterable<ByteBuffer> decodeIterable(ByteBuffer encoded)
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
  protected static ByteBuffer encodeRaw(ByteBuffer data){
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
  protected static ByteBuffer decodeRaw(ByteBuffer encoded)
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
    private static final String MSG = "Data you passed are not valid";

    public InvalidDataException(Throwable e){
      super(MSG, e);
    }

    public InvalidDataException(){
      super(MSG);
    }

    public InvalidDataException(String msg){
      super(MSG + " (" + msg + ")");
    }
  }
}
