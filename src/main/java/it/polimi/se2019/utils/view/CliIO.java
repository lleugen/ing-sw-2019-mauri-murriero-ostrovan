package it.polimi.se2019.utils.view;

import it.polimi.se2019.utils.io.AbstractIO;
import it.polimi.se2019.utils.model.FunctionCallModel;
import it.polimi.se2019.utils.network.network_handler.NetworkHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class CliIO extends AbstractIO {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "AbstractIO";

  /**
   * True if we should accept input from the user, false otherwise
   */
  private static AtomicBoolean running;

  private static StringBuilder pendingUpdate = new StringBuilder();

  public static synchronized void update(String data){
    pendingUpdate.append(data);
    pendingUpdate.append("\n");
  }

  public static synchronized void publish(){
    System.out.println(pendingUpdate.toString());
    pendingUpdate.delete(0, pendingUpdate.length());
    pendingUpdate.append("\n\n");
  }

  /**
   * Start the IO handler.
   * This is a singleton. Multiple starts will be ignored
   */
  public static synchronized void init(){
    if (running == null) {
      new CliIO();
      running = new AtomicBoolean(true);

      new Thread(
              CliIO::ReadingThread
      ).start();
    }
  }

  /**
   * Singleton
   */
  private CliIO(){
    super(() -> running.set(false));
  }

  private static void ReadingThread() {
    while (running.get()) {
      processCommand(System.console().readLine());
    }
  }

  private static void processCommand(String input){
    String[] parsedInput;
    parsedInput = input.split(" ");

    if (parsedInput.length >= 3) {
      String ns = parsedInput[0];
      String f = parsedInput[1];
      String[] data = Arrays.copyOfRange(parsedInput, 2, parsedInput.length);

      if (!ns.startsWith(".")) {
        ns = "." + ns;
      }

      System.console().writer().println(
              "Sent " + ns + " " + f
      );
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
              "Invalid command: accepted in form <namespace> <call> <data>"
      );
    }
  }
}
