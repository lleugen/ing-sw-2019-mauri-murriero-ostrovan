/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.engine.io;

import it.polimi.se2019.engine.model.FunctionCallModel;
import it.polimi.se2019.engine.model.ViewUpdateModel;
import it.polimi.se2019.engine.view.AbstractView;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractIO {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "AbstractIO";

  /**
   * Map of views renderizable by this IO, indexed by namespace
   */
  private Map<String, AbstractView> availableViews;

  /**
   * Registered callback for sending data to the network handler
   */
  private Consumer<FunctionCallModel> sendHandler;

  /**
   * Create a new AbstractIO
   *
   * @param availableViews Map of view renderizable by this IO
   */
  protected AbstractIO(Map<String, AbstractView> availableViews){
    this.availableViews = new HashMap<>(availableViews);
  }

  /**
   * Close the current IO handler.
   */
  public abstract void close();

  /**
   * Update a model on a view.
   * If the view doesn't exists, or the update cannot be decoded, the update
   * is silently discarded (it is possible the error will be logged, thought)
   *
   * @param view Namespace to send the model update to
   * @param data Model data to send to the namespace
   */
  public synchronized void updateModel(String view, byte[] data){
    if (this.availableViews.containsKey(view)){
      this.availableViews.get(view).updateState(data);
    }
    else {
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.WARNING,
              "Attempting to update a non-existing view {0}",
              view
      );
    }
  }

  /**
   * Render to screen the list of views passed as parameter.
   *
   * __INFO__ Wildcards are supported
   * __WARN__ Views are rendered with the last model passed to them using
   */
  public synchronized void render(Collection<String> views){
    views.stream()
            .map(this::expandWildcard)
            .flatMap(List::stream)
            .forEach(this::renderView);

    this.displayRender();
  }

  /**
   * Physically display to screen views render using renderView
   */
  protected abstract void displayRender();

  /**
   * Helper method.
   *
   * See expand wildcard in ViewUpdateModel
   *
   * @param v Wildcard view to expand
   */
  private List<String> expandWildcard(String v){
    return ViewUpdateModel.expandWildcard(v, this.availableViews.keySet());
  }

  /**
   * Attempt to render a single view.
   *
   * __INFO__ Wildcards are NOT supported
   * __WARN__ Views are rendered with the last model passed to them using
   *
   * @param v Namespace of the view to render
   */
  private void renderView(String v){
    if (this.availableViews.containsKey(v)){
      this.availableViews.get(v).refresh(this);
    }
    else {
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.WARNING,
              "Attempting to refresh a non-existing view {0}",
              v
      );
    }
  }

  /**
   * Add a new function that can be called
   *
   * @param ns Namespace the call belongs to
   * @param f  Name of the function to call
   * @param p  Params of the call
   * @param d  Description of the function
   */
  protected abstract void addCommand(String ns, String f, String p, String d);

  /**
   * Send a function call to the network handler
   *
   * @param ns Namespace to call the function on
   * @param f  Function to call
   * @param d  Data of the call
   */
  protected void call(String ns, String f, String d){
    this.sendHandler.accept(
            new FunctionCallModel(
                    ns,
                    f,
                    d
            )
    );
  }
  /**
   * Register the handler for sending data to the network handler
   */
  public void registerSendDataHandler(Consumer<FunctionCallModel> h) {
    this.sendHandler = h;
  }
}
