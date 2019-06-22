package it.polimi.se2019.utils.view;

import it.polimi.se2019.utils.io.IOInterface;
import it.polimi.se2019.utils.model.AbstractModel;
import it.polimi.se2019.utils.model.ModelDecodingFunction;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractView<T extends AbstractModel, I extends IOInterface> {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "NetworkHandler";

  /**
   * Decoding function for received data
   */
  private ModelDecodingFunction<T> df;

  /**
   * Current state of the view
   */
  private T state;

  /**
   * Init a new View
   *
   * @param decodingFunction Function to use for decoding model data
   */
  protected AbstractView(ModelDecodingFunction<T> decodingFunction){
    this.df = decodingFunction;
  }

  /**
   * Update the current state of the view.
   * If the update cannot be decoded, it is automatically silently discarded
   *
   * @param data Encoded data to render
   */
  public void updateState(byte[] data){
    try {
      this.state = this.df.decode(
              AbstractModel.arrayToBuf(
                      data
              )
      );
    }
    catch (AbstractModel.InvalidDataException e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.WARNING,
              "Unable to decode update",
              e
      );
    }
  }

  /**
   * Refresh the view (tries to rerender if the model is valid)
   *
   * @param io IOInterface to use for rendering data
   */
  public void refresh(I io){
    if (this.state != null){
      this.render(this.state, io);
    }
  }

  /**
   * Render the update passed as parameter.
   *
   * @param data Update to render
   */
  protected abstract void render(T data, I io);
}
