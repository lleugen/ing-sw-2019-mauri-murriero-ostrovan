package it.polimi.se2019.entrypoint.lobby.view.cli.dashboard;

import it.polimi.se2019.entrypoint.lobby.model.dashboard.DashboardModel;
import it.polimi.se2019.utils.io.CliIO;
import it.polimi.se2019.utils.view.AbstractView;

public class DashboardCliView extends AbstractView<DashboardModel, CliIO> {
  public DashboardCliView(){
    super(DashboardModel::decode);
  }

  @Override
  protected void render(DashboardModel data, CliIO io) {
    // Dashboard doesn't render enything in this view
  }
}
