package it.polimi.se2019.network.encodable;

import it.polimi.se2019.network.encodable.model.ModelInterface;
import it.polimi.se2019.utils.encoder.EncodableInterface;
import it.polimi.se2019.utils.encoder.EncoderInterface;
import it.polimi.se2019.utils.encoder.FakeBsonEncoder;

import java.util.ArrayList;
import java.util.List;

public class ModelViewUpdate implements EncodableInterface {
  private static final int NS_POSITION = 0;
  private static final int VIEW_POSITION = 1;
  private static final int MODEL_POSITION = 2;
  private static final int ENCODED_SIZE = 3;
  private String ns;
  private String view;
  private ModelInterface model;
  private EncoderInterface encoder;

  public String getNamespace() {
    return this.ns;
  }

  public void setNamespace(String namespace) {
    this.ns = ns;
  }

  public String getView() {
    return this.view;
  }

  public void setView(String view) {
    this.view = view;
  }

  public ModelInterface getModel() {
    return this.model;
  }

  public void setModel(ModelInterface model) {
    this.model = model;
  }

  public ModelViewUpdate(String ns, String view, ModelInterface model){
    this.ns = ns;
    this.view = view;
    this.model = model;
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
            VIEW_POSITION,
            this.encoder.encodeString(this.view)
    );

    toEncode.set(
            MODEL_POSITION,
            this.model.encode()
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

      this.view = encoder.decodeString(
              decodedList.get(VIEW_POSITION)
      );

      this.model.decode(decodedList.get(MODEL_POSITION));
    }
  }
}
