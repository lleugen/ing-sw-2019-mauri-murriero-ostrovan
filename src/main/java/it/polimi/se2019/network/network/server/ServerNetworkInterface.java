package it.polimi.se2019.network.network.server;

import it.polimi.se2019.network.encodable.FunctionCall;
import it.polimi.se2019.network.encodable.ModelViewUpdate;

import java.util.function.Supplier;

public interface ServerNetworkInterface {
  void setOnConnectHandler(ConnectionHandler h);
  void setOnRecvHandler(RecvHandler h);
  void setUpdateSupplier(String user, UpdateSupplier s);


  interface UpdateSupplier {
    ModelViewUpdate get();
  }

  interface RecvHandler {
    void accept(String user, FunctionCall data);
  }

  interface ConnectionHandler {
    void accept(String user);
  }
}
