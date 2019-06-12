package it.polimi.se2019.utils.encoder;

import java.util.List;

public interface EncoderInterface {
  char[] encodeString(String data);
  String decodeString(char[] data) throws InvalidDataException;
  char[] encodeList(List<char[]> data);
  List<char[]> decodeList(char[] data) throws InvalidDataException;
  List<char[]> decodeList(char[] d, int l) throws InvalidDataException;
  class InvalidDataException extends Exception {
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
