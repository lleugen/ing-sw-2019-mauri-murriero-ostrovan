package it.polimi.se2019.entrypoint.lobby.view.cli.dashboard.match;

import it.polimi.se2019.entrypoint.lobby.model.dashboard.match.MatchDashboardModel;
import it.polimi.se2019.utils.view.AbstractView;
import it.polimi.se2019.utils.io.CliIO;

import java.util.List;

public class MatchDashboardCliView extends AbstractView<MatchDashboardModel, CliIO> {
  public MatchDashboardCliView(){
    super(MatchDashboardModel::decode);
  }

  @Override
  protected void render(MatchDashboardModel data ,CliIO io) {
    io.addCommand(
            "dashboard.match",
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
