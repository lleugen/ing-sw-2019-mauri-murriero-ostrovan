package it.polimi.se2019.utils.encoder;

public interface EncodableInterface {
  char[] encode();
  void decode(char[] encoded) throws EncoderInterface.InvalidDataException;
}
