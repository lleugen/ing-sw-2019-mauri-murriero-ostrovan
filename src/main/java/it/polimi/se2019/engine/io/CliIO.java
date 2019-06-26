/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.engine.io;

import it.polimi.se2019.game.view.cli.RootCliView;
import it.polimi.se2019.game.view.cli.lobby.LobbyCliView;
import it.polimi.se2019.game.view.cli.lobby.chat.ChatLobbyCliView;
import it.polimi.se2019.game.view.cli.lobby.piazza.PiazzaLobbyCliView;
import it.polimi.se2019.game.view.cli.match.MatchCliView;
import it.polimi.se2019.engine.view.AbstractView;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class CliIO extends AbstractIO {
  /**
   * Namespace this class logs to
   */
  private static final String LOG_NAMESPACE = "CliIO";

  /**
   * Length in elements of a command parsed from the command line.
   * Commands are in the form <namespace> <function> <data>. so this costant
   * assumes the value of 3
   */
  private static final int PARSED_COMMAND_LENGTH = 3;

  /**
   * Position of the namespace in the parsed command
   *
   * Default: 0
   */
  private static final int NS_POSITION_IN_PARSED_COMMAND = 0;

  /**
   * Position of the call in the parsed command
   *
   * Default: 1
   */
  private static final int CALL_POSITION_IN_PARSED_COMMAND = 1;

  /**
   * Position of the data in the parsed command
   *
   * Default: 2
   */
  private static final int DATA_POSITION_IN_PARSED_COMMAND = 2;

  /**
   * True if we should accept input from the user, false otherwise
   */
  private AtomicBoolean running = new AtomicBoolean(true);

  /**
   * Contains data to print to screen on render
   */
  private StringBuilder pendingUpdate = new StringBuilder();

  /**
   * Contains commands to print to screen on render
   */
  private StringBuilder pendingCommands = new StringBuilder();

  /**
   * Create a new CliIO
   */
  public CliIO(){
    super(getAvailableViews());

    new Thread(
            this::readingThread
    ).start();
  }

  /**
   * @return The list of renderizable views for the CliIO
   */
  private static Map<String, AbstractView> getAvailableViews(){
    Map<String, AbstractView> toReturn = new HashMap<>();

    toReturn.put(".", new RootCliView());
    toReturn.put(".lobby", new LobbyCliView());
    toReturn.put(".lobby.chat", new ChatLobbyCliView());
    toReturn.put(".lobby.piazza", new PiazzaLobbyCliView());
    toReturn.put(".match", new MatchCliView());

    return toReturn;
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

  @Override
  protected void displayRender() {
    System.console().writer().println("\033[H\033[2J");

    System.console().writer().write(this.pendingUpdate.toString());
    System.console().writer().write("\n");
    System.console().writer().write(this.pendingCommands.toString());
    System.console().writer().write("> ");

    this.pendingCommands.delete(0, this.pendingCommands.length());
    this.pendingUpdate.delete(0, this.pendingUpdate.length());

    System.console().writer().flush();
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
   * Close the handler, stopping listening for input from the keyboard
   */
  @Override
  public void close() {
    running.set(false);
    System.console().writer().println(
            "\033[H\033[2J[!!!] Disconnected from server\n" +
            "Press ENTER to close this app"
    );
  }

  /**
   * Mark a section in the buffer (format data as a title)
   *
   * @param title Title of the section
   */
  public void markSection(String title) {
    this.pendingUpdate.append("_");
    for (int i = 0; i < (title.length()); i++){
      this.pendingUpdate.append("_");
    }
    this.pendingUpdate.append("_");

    this.pendingUpdate.append("\n");

    this.pendingUpdate.append("|");
    this.pendingUpdate.append(title);
    this.pendingUpdate.append("|");

    this.pendingUpdate.append("\n");

    this.pendingUpdate.append("|");
    for (int i = 0; i < (title.length()); i++){
      this.pendingUpdate.append("_");
    }
    this.pendingUpdate.append("|");

    this.pendingUpdate.append("\n");
  }

  private void readingThread() {
    try {
      while (this.running.get()) {
        this.processCommand(readLine());
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
    StringBuilder sb = new StringBuilder();
    int c;

    while ((c = System.in.read()) != '\n'){
      sb.append((char) c);
    }

    return sb.toString();
  }

  private void processCommand(String input){
    if (running.get()){
      String[] parsedInput;

      parsedInput = input.split(" ");

      if (parsedInput.length >= PARSED_COMMAND_LENGTH) {
        String ns = parsedInput[NS_POSITION_IN_PARSED_COMMAND];
        String f = parsedInput[CALL_POSITION_IN_PARSED_COMMAND];
        String[] data = Arrays.copyOfRange(
                parsedInput,
                DATA_POSITION_IN_PARSED_COMMAND,
                parsedInput.length
        );

        if (!ns.startsWith(".")) {
          ns = "." + ns;
        }

        System.console().writer().write("" +
                "Sending...\n" +
                "Nothing is happening? Double-check your command ;)\n" +
                "\n" +
                "> "
        );
        System.console().flush();

        this.call(ns, f , String.join(" ", data));
      }
      else {
        System.console().writer().println(
                "[FAIL] Invalid command: accepted in form <namespace> <call> <data>"
        );
      }
    }
  }
}
