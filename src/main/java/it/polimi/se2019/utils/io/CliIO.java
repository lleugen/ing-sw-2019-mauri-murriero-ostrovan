package it.polimi.se2019.utils.io;

import it.polimi.se2019.utils.model.FunctionCallModel;
import it.polimi.se2019.utils.network.network_handler.NetworkHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class CliIO implements IOInterface {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "CliIO";

  /**
   * Reference to the CliIO (it is a singleton)
   */
  private static CliIO me;

  /**
   * True if we should accept input from the user, false otherwise
   */
  private static AtomicBoolean running;

  /**
   * Contains data to print to screen on render
   */
  private StringBuilder pendingUpdate = new StringBuilder();

  /**
   * Contains commands to print to screen on render
   */
  private StringBuilder pendingCommands = new StringBuilder();

  /**
   * Singleton. Hides public constructor
   */
  private CliIO(){
    super();
    // Hides public constructor
  }

  /**
   * Get a reference to the CliIO.
   *
   * @return a reference to the CliIO
   */
  public static synchronized IOInterface getIO(){
    if (me == null){
      me = new CliIO();

      running = new AtomicBoolean(true);

      new Thread(
              CliIO::readingThread
      ).start();
    }

    return me;
  }

  /**
   * Add a new function that can be called
   *
   * @param ns     Namespace the call belongs to
   * @param f      Name of the function to call
   * @param params Params of the call
   * @param desc   Description of the function
   */
  @Override
  public void addCommand(String ns, String f, String params, String desc) {
    this.pendingCommands.append("?) ");
    this.pendingCommands.append(ns);
    this.pendingCommands.append(" ");
    this.pendingCommands.append(f);
    this.pendingCommands.append(" ");
    this.pendingCommands.append(params);
    this.pendingCommands.append(" -> ");
    this.pendingCommands.append(desc);
    this.pendingCommands.append("\n");
  }

  /**
   * Buffers data to print to screen.
   * Data will be printed on render
   *
   * @param data Data to buffer
   */
  public synchronized void writeLn(String data){
    this.pendingUpdate.append(data);
    this.pendingUpdate.append("\n");
  }

  /**
   * Render to screen buffered data
   */
  @Override
  public synchronized void render(){
    if (
            (this.pendingUpdate.length() > 0) ||
            (this.pendingCommands.length() > 0)
    ) {
      System.console().writer().print(this.pendingUpdate.toString());
      System.console().writer().print("\n");
      System.console().writer().print(this.pendingCommands.toString());
      System.console().writer().print("> ");
      this.pendingUpdate.delete(0, this.pendingUpdate.length());
      this.pendingCommands.delete(0, this.pendingCommands.length());

      this.pendingUpdate.append("\033[H\033[2J");
      System.console().writer().flush();
    }
  }

  /**
   * Close the handler, stopping listening for input from the keyboard
   */
  public void close() {
    running.set(false);
    System.console().writer().println(
            "[!!!] Disconnected from server\n" +
            "Press ENTER to close this app"
    );
  }

  /**
   * Mark a section in the buffer (format data as a title)
   *
   * @param title Title of the section
   */
  public void markSection(String title) {
    pendingUpdate.append("_");
    for (int i = 0; i < (title.length()); i++){
      pendingUpdate.append("_");
    }
    pendingUpdate.append("_");

    pendingUpdate.append("\n");

    pendingUpdate.append("|");
    pendingUpdate.append(title);
    pendingUpdate.append("|");

    pendingUpdate.append("\n");

    pendingUpdate.append("|");
    for (int i = 0; i < (title.length()); i++){
      pendingUpdate.append("_");
    }
    pendingUpdate.append("|");

    pendingUpdate.append("\n");
  }

  private static void readingThread() {
    try {
      while (running.get()) {
        processCommand(readLine());
      }
    }
    catch (IOException e){
      Logger.getLogger(LOG_NAMESPACE).log(
              Level.SEVERE,
              "Lost connection with console. You can safely kill this app",
              e
      );
    }
  }

  private static String readLine() throws IOException {
    StringBuilder toReturn = new StringBuilder();
    int c;

    while ((c = System.in.read()) != '\n'){
      toReturn.append((char) c);
    }

    return toReturn.toString();
  }

  private static void processCommand(String input){
    if (running.get()){
      String[] parsedInput;

      parsedInput = input.split(" ");

      if (parsedInput.length >= 3) {
        String ns = parsedInput[0];
        String f = parsedInput[1];
        String[] data = Arrays.copyOfRange(parsedInput, 2, parsedInput.length);

        if (!ns.startsWith(".")) {
          ns = "." + ns;
        }

        NetworkHandler.send(
                new FunctionCallModel(
                        ns,
                        f,
                        String.join(" ", data)
                )
        );
      }
      else {
        System.console().writer().println(
                "[FAIL] Invalid command: accepted in form <namespace> <call> <data>"
        );
      }
    }
  }
}
