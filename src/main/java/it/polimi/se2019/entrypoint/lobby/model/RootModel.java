package it.polimi.se2019.entrypoint.lobby.model;

import it.polimi.se2019.utils.model.AbstractModel;
import it.polimi.se2019.entrypoint.lobby.model.dashboard.DashboardModel;

import java.nio.ByteBuffer;
import java.util.LinkedList;

/**
 * __WARN__ This class is a singleton.
 */
public final class RootModel extends AbstractModel {
  private static final DashboardModel dashboard = new DashboardModel();

  /**
   * Singleton class, reference to the instance of the class
   */
  private static RootModel me;

  @Override
  public ByteBuffer encode() {
    return mergeBuffer(new LinkedList<>());
  }

  public static RootModel decode(ByteBuffer inputData){
    return new RootModel();
  }

  /**
   * Hides public constructor
   */
  private RootModel(){
    // Hides public constructor
  }

  public static synchronized RootModel getMe() {
    if (me == null) {
      me = new RootModel();
    }

    return me;
  }

  public static DashboardModel getDashboard() {
    return dashboard;
  }
}

