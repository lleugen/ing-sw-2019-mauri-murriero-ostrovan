package it.polimi.se2019.entrypoint.lobby.view.cli.dashboard;

import it.polimi.se2019.entrypoint.lobby.model.dashboard.DashboardModel;
import it.polimi.se2019.utils.view.AbstractView;

public class DashboardCliView extends AbstractView<DashboardModel> {
  @Override
  protected void render(DashboardModel data) { }

  public DashboardCliView(){
    super(DashboardModel::decode);
  }
}
