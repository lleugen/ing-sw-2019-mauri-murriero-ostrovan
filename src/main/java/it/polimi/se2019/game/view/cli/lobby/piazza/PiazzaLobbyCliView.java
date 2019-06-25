/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.game.view.cli.lobby.piazza;

import it.polimi.se2019.engine.io.CliIO;
import it.polimi.se2019.engine.view.AbstractView;
import it.polimi.se2019.game.model.lobby.piazza.PiazzaLobbyModel;

import java.util.List;

public class PiazzaLobbyCliView 
        extends AbstractView<PiazzaLobbyModel, CliIO> {
  /**
   * Initialize a new PiazzaLobbyCliView.
   * See description for this method in AbstractView
   */ 
  public PiazzaLobbyCliView(){
    super(PiazzaLobbyModel::decode);
  }

  /**
   * Render the view.
   * See description for this method in AbstractView
   *
   * @param data Model data to render
   * @param io   IOInterface to use for rendering data
   */
  @Override
  protected void render(PiazzaLobbyModel data, CliIO io) {
    io.addCommand(
            "lobby.piazza",
            "join",
            "<mapId>-<skulls>",
            "Join/Create a match"
    );

    io.markSection("MATCHES");

    if (data.getMatches().isEmpty()){
      io.writeLn("No Open Match Found. Start you ;)");
    }
    else {
      io.writeLn("Available Matches are (Map Id - Skulls):");
      data.getMatches().forEach((String room, List<String> players) ->
              io.writeLn(
                      "*) " + room + ": " + String.join(", ", players)
              )
      );
    }
  }
}
