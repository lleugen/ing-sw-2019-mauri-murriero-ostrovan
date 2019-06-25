/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.engine.model;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Representation of a View Update.
 * A view update contains the list of currently rendered view and may contain
 * an update for a model.
 * The update for a model is present only if the namespace the update refers to
 * is not an empty string
 */
public class ViewUpdateModel extends AbstractModel {
  /**
   * UUID for this model.
   * When decoding, this UUID will be checked agains decoded UUID.
   * If the UUID doesn't match, decoding will be rejected
   */
  private static final long UUID = 953_734_576_878_927_865L;

  /**
   * Contains the list of views to render.
   * Wildcard are supported
   */
  private List<String> views;

  /**
   * Contain the namespace the model refer to.
   * If this namespace is an empty string, this update doesn't transport any
   * model update
   */
  private String ns;

  /**
   * Contains the encoded model update.
   * If ns is an empty String, this field is undeterminated
   */
  private byte[] model;

  /**
   * Create a new ViewUpdateModel
   */
  public ViewUpdateModel() {
    this.views = new LinkedList<>();
    this.ns = "";
    this.model = new byte[0];
  }

  /**
   * Create a new ViewUpdateModel
   *
   * @param views List of currently rendered views
   * @param ns    Namespace the model refers to
   * @param model Model update transported by this update
   */
  private ViewUpdateModel(List<String> views, String ns, byte[] model){
    this.views = new LinkedList<>(views);
    this.ns = ns;
    this.model = model.clone();
  }

  /**
   * Create a new ViewUpdateModel copying data from an already existing update
   *
   * @param base Update to copy data from
   */
  public ViewUpdateModel(ViewUpdateModel base){
    this.ns = base.ns;
    this.model = base.model.clone();
    this.views = new LinkedList<>(base.views);
  }

  /**
   * @return A list of views to render (with wildcards)
   */
  public List<String> getViews() {
    return new LinkedList<>(this.views);
  }

  /**
   * @return The namespace the model update refers to. If an empty string is
   *         returned, this update doesn't contain any model update
   */
  public String getNs() {
    return this.ns;
  }

  /**
   * @return The model update. The update is valid only if the namespace is not
   *         an empty string
   */
  public byte[] getModel() {
    return this.model.clone();
  }

  /**
   * Set the model for this update
   *
   * @param ns    Namespace the update refers to. Empty string if no update is
   *              transported
   * @param model Model update to transport
   */
  public void setModel(String ns, byte[] model) {
    if (ns.isEmpty()) {
      this.ns = "";
      this.model = new byte[0];
    }
    else {
      this.ns = ns;
      this.model = model.clone();
    }
  }

  /**
   * Add a new view to the list of views to render.
   *
   * @param view Namespace of the view to render
   *
   * __NOTE__ this function doesn't replace any view. If you need to replace a
   *          view with another, use replaceView
   */
  public void addView(String view) {
    this.views.add(view);
  }

  /**
   * Insert a view to the list of views to render, deleting conflicting views.
   * To detect conflicting views we takes the first parent of the views passed
   * as parameter, and we delete every child (at any level) of this parent.
   *
   * e.g.: We have .1a, .1a.2b, .1a.2a.3a, .1a.2a.3a.4a and we pass .1a.2a.3b
   *       The parent of the view passed as parameter is .1a.2a
   *       We then need to remove every view that matches .1a.2a.*
   *       We remove .1a.2a.3a and .1a.2a.3a.4a, leaving .1a and .1a.2b.
   *       We will the insert in the list .1a.2a.3b
   *       The final list results to be .1a .1a.2b .1a.2a.3b
   *
   * @param view View to replace in the list of currently opened view
   */
  public void replaceView(String view) {
    String[] sView = view.split("\\.");

    this.views.removeAll(
            expandWildcard(
                    String.join(
                            ".",
                            Arrays.copyOfRange(
                                    sView,
                                    0,
                                    (sView.length - 1)
                            )
                    ) + ".*",
                    this.views
            )
    );
  }

  /**
   * Expand a wildcard view.
   * A wildcard view can have only one wildcard, at the end of the view,
   * precedeed by a dot.
   * If the view is not a wildcard view, an iterable with only the view passed
   * as parameter is returned
   *
   * @param v     View to expand
   * @param list  List of view to use for expanding the wildcard
   *
   * @return A list of all views that matches that view, found in list param
   */
  public static List<String> expandWildcard(String v, Collection<String> list){
    if (v.endsWith(".*")) {
      String fv = v.replace(".*", "");

      return list.stream()
              .filter((String tv) -> tv.startsWith(fv))
              .collect(Collectors.toList());
    }
    else {
      List<String> toReturn = new LinkedList<>();
      toReturn.add(v);
      return toReturn;
    }
  }

  /**
   * Encode data.
   * See description for this method in AbstractModel
   */
  @Override
  public ByteBuffer encode() {
    List<ByteBuffer> toReturn = new LinkedList<>();

    toReturn.add(encodeLong(UUID));

    toReturn.add(encodeIterable(
            this.views.stream()
                    .map(AbstractModel::encodeString)
                    .map(AbstractModel::bufToArray)
                    .collect(Collectors.toList())
    ));
    toReturn.add(encodeString(this.ns));
    toReturn.add(encodeRaw(this.model));

    return mergeBuffer(toReturn);
  }

  /**
   * Decodes encoded data
   * See description for this method in AbstractModel
   *
   * @param inputData Input Data to decode
   *
   * @return A new istance of ChatLobbyModel, initialized using inputData
   *
   * @throws InvalidDataException if an error occurs while decoding data,
   *                              or if encoded UUID doesn't match
   *                              ChatLobbyModel's UUID
   */
  public static ViewUpdateModel decode(ByteBuffer inputData)
          throws InvalidDataException {

    inputData.clear();

    if (UUID != decodeLong(inputData)){
      throw new InvalidDataException();
    }

    List<String> views = new LinkedList<>();
    for (byte[] item : decodeIterable(inputData)) {
      views.add(
              decodeString(
                      AbstractModel.arrayToBuf(
                              item
                      )
              )
      );
    }
    String ns = decodeString(inputData);
    byte[] model = decodeRaw(inputData);

    return new ViewUpdateModel(views, ns, model);
  }
}
