package it.polimi.se2019.utils.network.virtual_view;

import java.util.function.BiConsumer;

/**
 * Definition of a callback for a function call
 *
 * First parameter is the id of the user who made the call
 * Second param is the data of the call
 */
@FunctionalInterface
public interface FunctionCallCallback extends BiConsumer<String, String> { }
