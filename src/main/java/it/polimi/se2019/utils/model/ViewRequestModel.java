package it.polimi.se2019.utils.model;

import java.nio.ByteBuffer;
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

  /**
   * Return ONLY the views that should be rendered.
   * A view is considered to be rendered if it doesn't collide with views
   * already rendered.
   *
   * A view is considered to render only if it is a child or a parent of the
   * index. The index is the most nested selected view
   *
   * e.g.: We select (in order):
   *       1a.2b, 1a.2c, 1a.2b.3b, 1a.2b.3a, 1a
   *
   *       To decide what views to render we reverses the list (last selected
   *       view is the most important), and we iterate the list selecting only
   *       views children of the last selected view.
   *       So we will have:
   *       1a         OK (first view). Index now is 1a.
   *       1a.2b.3a   OK (is a child view of 1a). Index now is 1a.2b.3b
   *       1a.2b.3b   KO (is not a child view of 1a.2b.3a). Index not changed
   *       1a.2c      KO (is not a parent view of 1a.2b.3a). Index not changed
   *       1a.2b      OK (is a parent view of 1a.2b.3a). Index remains 1a.2b.3b
   *
   *       At the end of the algorithm we will render 1a, 2a.2b.3a, 1a.2b
   *
   * @return ONLY the views that should be rendered.
   */
  public List<String> getViewsToRender() {
    List<String> toReturn = new LinkedList<>();
    String index = "";

    for (String view : this.views) {
      if (view.startsWith(index) || index.startsWith(view)) {
        toReturn.add(view);

        if (view.startsWith(index)) {
          index = view;
        }
      }
    }

    return toReturn;
  }

  /**
   * Add a new view request.
   *
   * @param view view to add
   */
  public void addView(String view){
    // Keeping only newest request
    this.views.removeIf(view::equals);

    // Keeping the list chronologically sorted
    this.views.add(0, view);
  }
}
