package it.polimi.se2019.entrypoint.lobby.controller.dashboard;

import it.polimi.se2019.entrypoint.lobby.controller.dashboard.chat.ChatDashboardController;
import it.polimi.se2019.entrypoint.lobby.controller.dashboard.match.MatchDashboardController;
import it.polimi.se2019.entrypoint.lobby.model.RootModel;
import it.polimi.se2019.entrypoint.lobby.model.dashboard.DashboardModel;
import it.polimi.se2019.utils.controller.Controller;

public final class DashboardController extends Controller<DashboardModel> {
  /**
   * Init the Dashboard controller
   */
  public DashboardController(){
    super(".dashboard", RootModel::getDashboard);
    this.init(null);

    new ChatDashboardController();
    new MatchDashboardController();

    this.notifyModelUpdate(null);
  }
}
