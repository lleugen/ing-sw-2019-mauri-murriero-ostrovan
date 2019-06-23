package it.polimi.se2019.entrypoint.lobby.view.cli.match;

import it.polimi.se2019.entrypoint.lobby.model.lobby.LobbyModel;
import it.polimi.se2019.entrypoint.lobby.model.match.MatchModel;
import it.polimi.se2019.utils.io.CliIO;
import it.polimi.se2019.utils.view.AbstractView;

public class MatchCliView extends AbstractView<MatchModel, CliIO> {
  public MatchCliView(){
    super(MatchModel::decode);
  }

  @Override
  protected void render(MatchModel data, CliIO io) {
    // Dashboard doesn't render enything in this view
  }
}
