package it.polimi.se2019.entrypoint.lobby.controller.dashboard;

import it.polimi.se2019.entrypoint.lobby.controller.dashboard.chat.ChatDashboardController;
import it.polimi.se2019.entrypoint.lobby.model.RootModel;
import it.polimi.se2019.entrypoint.lobby.model.dashboard.DashboardModel;
import it.polimi.se2019.utils.controller.Controller;
import it.polimi.se2019.utils.network.virtual_view.FunctionCallCallback;

import java.util.HashMap;
import java.util.Map;

public class DashboardController extends Controller<DashboardModel> {
  /**
   * Chat Controller associated to this Dashboard
   */
  private ChatDashboardController chatController;

  /**
   * Init the Dashboard controller
   */
  public DashboardController(){
    super(".dashboard", RootModel::getDashboard);
    this.init(null);

    this.chatController = new ChatDashboardController();

    this.notifyModelUpdate(null);
  }
}
