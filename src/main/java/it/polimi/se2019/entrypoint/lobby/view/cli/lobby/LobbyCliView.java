package it.polimi.se2019.entrypoint.lobby.view.cli.lobby;

import it.polimi.se2019.entrypoint.lobby.model.lobby.LobbyModel;
import it.polimi.se2019.utils.io.CliIO;
import it.polimi.se2019.utils.view.AbstractView;

public class LobbyCliView extends AbstractView<LobbyModel, CliIO> {
  public LobbyCliView(){
    super(LobbyModel::decode);
  }

  @Override
  protected void render(LobbyModel data, CliIO io) {
    // Dashboard doesn't render enything in this view
  }
}
