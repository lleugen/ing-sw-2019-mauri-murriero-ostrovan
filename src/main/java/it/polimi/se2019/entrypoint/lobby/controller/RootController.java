package it.polimi.se2019.entrypoint.lobby.controller;

import it.polimi.se2019.entrypoint.lobby.controller.dashboard.DashboardController;
import it.polimi.se2019.entrypoint.lobby.model.RootModel;
import it.polimi.se2019.utils.controller.Controller;

public final class RootController extends Controller<RootModel> {
  /**
   * Init the root controller
   */
  public RootController(){
    super(".", RootModel::getMe);
    this.init(null);

    new DashboardController();

    this.notifyModelUpdate(null);
  }
}
