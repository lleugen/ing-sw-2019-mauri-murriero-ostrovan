/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.engine.model;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
   * Return an array of bytes from a ByteBuffer
   *
   * @param input ByteBuffer to convert to array
   *
   * @return the converted array
   */
  public static byte[] bufToArray(ByteBuffer input){
    byte[] toReturn = new byte[input.capacity()];
    input.clear();
    input.get(toReturn);
    return toReturn;
  }

  /**
   * Return a ByteBuffer from a byte array.
   * The returned ByteBuffer is already cleared (ready for reading)
   *
   * @param input Array to converted
   *
   * @return the converted ByteBuffer
   */
  public static ByteBuffer arrayToBuf(byte[] input){
    ByteBuffer toReturn = ByteBuffer.allocate(input.length).put(input);
    toReturn.clear();
    return toReturn;
  }

  /**
   * Makes a copy of the byte array passed as param and return the new copy
   *
   * @param input Array to copy
   *
   * @return a copy of the array
   */
  public static byte[] copyArr(byte[] input){
    byte[] toReturn = new byte[input.length];
    System.arraycopy(input, 0, toReturn, 0, toReturn.length);
    return toReturn;
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
  protected static ByteBuffer encodeIterable(Iterable<byte[]> data) {
    List<ByteBuffer> toEncode = new LinkedList<>();
    Iterator<byte[]> it = data.iterator();

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
  protected static Iterable<byte[]> decodeIterable(ByteBuffer encoded)
          throws InvalidDataException {

    List<byte[]> toReturn = new LinkedList<>();
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
  protected static ByteBuffer encodeRaw(byte[] data){
    List<ByteBuffer> toEncodeList = new LinkedList<>();

    toEncodeList.add(
            encodeInt(data.length)
    );
    toEncodeList.add(
            ByteBuffer.allocate(data.length).put(data)
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
  protected static byte[] decodeRaw(ByteBuffer encoded)
          throws InvalidDataException {

    byte[] toReturn = new byte[decodeInt(encoded)];

    encoded.get(toReturn);

    return toReturn;
  }

  /**
   * Encode a Map of Raw Data
   *
   * @param data Map to encode
   *
   * @return the encoded representation of the map
   */
  protected static ByteBuffer encodeMap(Map<String, byte[]> data){
    List<ByteBuffer> toEncodeList = new LinkedList<>();

    data.forEach((String key, byte[] el) -> {
      List<byte[]> tmp = new LinkedList<>();

      tmp.add(bufToArray(encodeString(key)));
      tmp.add(el);

      toEncodeList.add(encodeIterable(tmp));
    });

    return encodeIterable(
            toEncodeList.stream()
                    .map(AbstractModel::bufToArray)
                    .collect(Collectors.toList())
            );
  }

  /**
   * Decode a map of Raw Data
   *
   * __WARN__ Encoded data are removed from the ByteBuffer
   *
   * @param encoded ByteBuffer containing encoded data. The first byte of the
   *                buffer MUST be the first byte of the encoded data to decode
   *
   *  @return the decoded data
   */
  protected static Map<String, byte[]> decodeMap(ByteBuffer encoded)
          throws InvalidDataException {

    Map<String, byte[]> toReturn = new HashMap<>();
    Iterator<byte[]> decodedIterator = decodeIterable(encoded).iterator();

    while (decodedIterator.hasNext()){
      Iterator<byte[]> dataIterator = decodeIterable(
              AbstractModel.arrayToBuf(
                      decodedIterator.next()
              )
      ).iterator();

      try {
        toReturn.put(
                decodeString(
                        arrayToBuf(
                                dataIterator.next()
                        )
                ),
                dataIterator.next()
        );
      }
      catch (NoSuchElementException e){
        throw new InvalidDataException(e);
      }
    }

    return toReturn;
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
