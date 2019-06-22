package it.polimi.se2019.utils.model;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ViewRequestModel extends AbstractModel {
  /**
   * UUID for this model
   */
  private static final long UUID = 397_096_959L;
  private List<String> views;

  public ViewRequestModel(List<String> views) {
    this.views = new LinkedList<>(views);
  }

  public ViewRequestModel(ViewRequestModel base) {
    this.views = new LinkedList<>(base.views);
  }

  @Override
  public ByteBuffer encode() {
    List<ByteBuffer> toReturn = new LinkedList<>();

    toReturn.add(encodeLong(UUID));
    toReturn.add(
            encodeIterable(this.views.stream()
                    .map(AbstractModel::encodeString)
                    .map(AbstractModel::bufToArray)
                    .collect(Collectors.toList())
            )
    );

    return mergeBuffer(toReturn);
  }

  public static ViewRequestModel decode(ByteBuffer inputData)
          throws InvalidDataException {
    inputData.clear();

    if (UUID != decodeLong(inputData)){
      throw new InvalidDataException();
    }

    List<String> views = new LinkedList<>();

    for (byte[] item : (decodeIterable(inputData))) {
      views.add(
              AbstractModel.decodeString(
                      AbstractModel.arrayToBuf(
                              item
                      )
              )
      );
    }

    if (inputData.hasRemaining()) {
      throw new InvalidDataException();
    }
    else {
      return new ViewRequestModel(views);
    }
  }

  public List<String> getViews() {
    return new LinkedList<>(this.views);
  }

  /**
   * Add a new view request.
   * If the new view has a sibling already opened on the parent namespace, that
   * sibling and all his children are removed.
   *
   * For example, if we are adding the view a.b.c, and the view a.b.d is already
   * opened, the view a.b.d and all its sibling are removed
   *
   * @param view view to add
   */
  public void addView(String view){
    String[] exploded = view.split("\\.");
    String parent = String.join(
            ".",
            Arrays.copyOfRange(exploded, 0, exploded.length)
    );

    this.views.removeIf(
            (String item) -> item.startsWith(parent)
    );
    this.views.add(view);
  }
}
