package it.polimi.se2019.utils.io;

public interface IOInterface {
  /**
   * Get the current interface.
   * The interface must me a singleton
   *
   * Each extension must implement this method
   */
  static IOInterface getIO(){
    throw new UnsupportedOperationException(
            "Extensions must implement this class"
    );
  }

  /**
   * Close the current IO handler.
   */
  void close();

  /**
   * Render to screen buffered data
   */
  void render();

  /**
   * Add a new function that can be called
   *
   * @param ns     Namespace the call belongs to
   * @param f      Name of the function to call
   * @param params Params of the call
   * @param desc   Description of the function
   */
  void addCommand(String ns, String f, String params, String desc);
}
