package it.polimi.se2019.utils.view;

import it.polimi.se2019.utils.model.AbstractModel;
import it.polimi.se2019.utils.model.ModelDecodingFunction;

import java.nio.ByteBuffer;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractView<T extends AbstractModel>
        implements Consumer<ByteBuffer> {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "NetworkHandler";

  /**
   * Decoding function for received data
   */
  private ModelDecodingFunction<T> df;

  /**
   * Init a new View
   *
   * @param decodingFunction Function to use for decoding model data
   */
  protected AbstractView(ModelDecodingFunction<T> decodingFunction){
    this.df = decodingFunction;
  }

  /**
   * Tries the rendering of the update passed as parameter.
   * The update is decoded and, if valid, transferred to the render function,
   * which physically render the update on the view
   *
   * @param data Encoded data to render
   */
  public void accept(ByteBuffer data){
    try {
      this.render(this.df.decode(data));
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
   * Render the update passed as parameter.
   *
   * @param data Update to render
   */
  protected abstract void render(T data);
}
