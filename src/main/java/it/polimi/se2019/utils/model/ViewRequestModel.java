package it.polimi.se2019.utils.model;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class ViewRequestModel extends AbstractModel {
  private String view;

  public ViewRequestModel(String view) {
    this.view = view;
  }

  @Override
  public ByteBuffer encode() {
    List<ByteBuffer> toReturn = new LinkedList<>();

    toReturn.add(encodeString(this.view));

    return mergeBuffer(toReturn);
  }

  public static ViewRequestModel decode(ByteBuffer inputData)
          throws InvalidDataException {
    inputData.clear();

    String view = decodeString(inputData);

    return new ViewRequestModel(view);
  }

  public String getView() {
    return view;
  }
}
