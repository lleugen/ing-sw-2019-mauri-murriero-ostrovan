package it.polimi.se2019.entrypoint.lobby.view.cli.lobby.piazza;

import it.polimi.se2019.entrypoint.lobby.model.lobby.piazza.PiazzaLobbyModel;
import it.polimi.se2019.utils.view.AbstractView;
import it.polimi.se2019.utils.io.CliIO;

import java.util.List;

public class PiazzaLobbyCliView extends AbstractView<PiazzaLobbyModel, CliIO> {
  public PiazzaLobbyCliView(){
    super(PiazzaLobbyModel::decode);
  }

  @Override
  protected void render(PiazzaLobbyModel data , CliIO io) {
    io.addCommand(
            "lobby.match",
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
