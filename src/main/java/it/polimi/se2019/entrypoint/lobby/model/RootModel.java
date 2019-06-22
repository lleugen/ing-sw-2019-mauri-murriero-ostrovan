package it.polimi.se2019.entrypoint.lobby.model;

import it.polimi.se2019.utils.model.AbstractModel;
import it.polimi.se2019.entrypoint.lobby.model.dashboard.DashboardModel;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

/**
 * __WARN__ This class is a singleton.
 */
public final class RootModel extends AbstractModel {
  /**
   * UUID for this model
   */
  private static final long UUID = 400_549_384L;
  private static final DashboardModel dashboard = new DashboardModel();

  /**
   * Singleton class, reference to the instance of the class
   */
  private static RootModel me;

  /**
   * Hides public constructor
   */
  private RootModel(){
    // Hides public constructor
  }

  @Override
  public ByteBuffer encode(){
    List<ByteBuffer> toReturn = new LinkedList<>();

    toReturn.add(encodeLong(UUID));

    return mergeBuffer(toReturn);
  }

  public static RootModel decode(ByteBuffer inputData)
          throws InvalidDataException {

    inputData.clear();

    if (UUID != decodeLong(inputData)){
      throw new InvalidDataException();
    }

    return new RootModel();
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

