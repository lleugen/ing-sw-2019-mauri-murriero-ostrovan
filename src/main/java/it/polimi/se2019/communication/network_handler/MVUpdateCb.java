package it.polimi.se2019.communication.network_handler;

import it.polimi.se2019.communication.encodable.ModelViewUpdateEncodable;

import java.util.function.Consumer;

/**
 * Callback for ModelView Updates.
 * This function is called each time a new update for a given namespace is
 * received.
 *
 * __WARN__: This callback must be stateless and idempotent
 */
@FunctionalInterface
public interface MVUpdateCb extends Consumer<ModelViewUpdateEncodable> { }
