package it.polimi.se2019.network.encodable;

import it.polimi.se2019.utils.encoder.EncodableInterface;
import it.polimi.se2019.utils.encoder.EncoderInterface;
import it.polimi.se2019.utils.encoder.FakeBsonEncoder;

import java.util.ArrayList;
import java.util.List;

public class FunctionCall implements EncodableInterface {
  private static final int NS_POSITION = 0;
  private static final int FUNCTION_POSITION = 1;
  private static final int DATA_POSITION = 2;
  private static final int ENCODED_SIZE = 3;

  private String ns;
  private String f;
  private EncodableInterface data;
  private EncoderInterface encoder;

  public FunctionCall(String ns, String f, EncodableInterface data){
    this.ns = ns;
    this.f = f;
    this.data = data;
    this.encoder = new FakeBsonEncoder();
  }

  public char[] encode(){
    List<char[]> toEncode = new ArrayList<>(ENCODED_SIZE);
    for (int i = 0; i < ENCODED_SIZE; i++){
      toEncode.add(new char[]{});
    }
    toEncode.set(
            NS_POSITION,
            this.encoder.encodeString(this.ns)
    );


    toEncode.set(
            FUNCTION_POSITION,
            this.encoder.encodeString(this.f)
    );

    toEncode.set(
            DATA_POSITION,
            this.data.encode()
    );

    return this.encoder.encodeList(toEncode);
  }

  public void decode(char[] encoded)
          throws EncoderInterface.InvalidDataException {
    List<char[]> decodedList;

    decodedList = this.encoder.decodeList(encoded);

    if (decodedList.size() != ENCODED_SIZE){
      throw new EncoderInterface.InvalidDataException();
    }
    else {
      this.ns = encoder.decodeString(
              decodedList.get(NS_POSITION)
      );

      this.f = encoder.decodeString(
              decodedList.get(FUNCTION_POSITION)
      );

      this.data.decode(decodedList.get(DATA_POSITION));
    }
  }
}
