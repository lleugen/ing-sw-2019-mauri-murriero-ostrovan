package it.polimi.se2019.utils.encoder;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeBsonEncoder implements EncoderInterface {
  private static final int INT_SIZE = 2;
  private static final int CHAR_SIZE = 65536;

  @Override
  public char[] encodeString(String data) {
    return data.toCharArray();
  }

  @Override
  public String decodeString(char[] data) throws InvalidDataException {
    try {
      return new String(data);
    }
    catch (Throwable e){
      throw new InvalidDataException(e);
    }
  }

  @Override
  public char[] encodeList(List<char[]> data) {
    StringBuilder sb = new StringBuilder();
    char[] toReturn;

    sb.append(convertIntToCharList(data.size()));
    data.forEach(item -> {
      sb.append(convertIntToCharList(item.length));
      sb.append(item);
    });

    toReturn = new char[sb.length()];
    sb.getChars(0, sb.length(), toReturn, 0);
    return toReturn;
  }

  @Override
  public List<char[]> decodeList(char[] data) throws InvalidDataException {
    List<char[]> toReturn = new ArrayList<>();
    int count;
    int offset = 0;
    int length;

    try {
      count = parseIntFromCharList(
              Arrays.copyOfRange(data, 0, (INT_SIZE - 1))
      );

      offset += INT_SIZE;

      for (int i = 0; i < count; i++) {
        length = parseIntFromCharList(
                Arrays.copyOfRange(
                        data,
                        offset,
                        (INT_SIZE - 1)
                )
        );
        offset += INT_SIZE;
        toReturn.add(
                Arrays.copyOfRange(
                        data,
                        offset,
                        (length - 1)
                )
        );
        offset += length;
      }

      return toReturn;
    }
    catch (Throwable e){
      throw new InvalidDataException(e);
    }
  }

  @Override
  public List<char[]> decodeList(char[] d, int l) throws InvalidDataException {
    List<char[]> toReturn = this.decodeList(d);

    if (toReturn.size() != l){
      throw new InvalidDataException();
    }
    else {
      return toReturn;
    }
  }

  private int parseIntFromCharList(char[] list) throws InvalidDataException{
    int toReturn = 0;

    try {
      for (int i = 0; i < list.length; i++) {
        toReturn += Math.pow(CHAR_SIZE, (list.length - i - 1));
      }

      return toReturn;
    }
    catch (Throwable e){
      throw new InvalidDataException(e);
    }
  }

  private char[] convertIntToCharList(int data){
    char[] toReturn = new char[INT_SIZE];

    for (int i = (INT_SIZE - 1); i >= 0; i--){
      toReturn[i] = (char) (data % CHAR_SIZE);
      data -= toReturn[i];
      data /= CHAR_SIZE;
    }

    return toReturn;
  }
}
