package it.polimi.se2019.entrypoint.lobby.view.cli;

import it.polimi.se2019.entrypoint.lobby.model.RootModel;
import it.polimi.se2019.utils.view.AbstractView;

public class RootCliView extends AbstractView<RootModel> {
  @Override
  protected void render(RootModel data) { }

  public RootCliView(){
    super(RootModel::decode);
  }
}
