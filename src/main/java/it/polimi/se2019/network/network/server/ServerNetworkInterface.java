package it.polimi.se2019.network.network.server;

import it.polimi.se2019.network.encodable.FunctionCall;
import it.polimi.se2019.network.encodable.ModelViewUpdate;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface ServerNetworkInterface {
  void setOnConnectHandler(ConnectionHandler h);
  void setOnRecvHandler(RecvHandler h);

  @FunctionalInterface
  interface UpdateSupplier extends Supplier<ModelViewUpdate> { }

  @FunctionalInterface
  interface RecvHandler extends BiConsumer<String, FunctionCall> { }

  @FunctionalInterface
  interface ConnectionHandler extends Consumer<String> { }
}
