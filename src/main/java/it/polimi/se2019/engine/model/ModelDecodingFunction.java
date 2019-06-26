/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.engine.model;

import java.nio.ByteBuffer;

/**
 * Decoding function for a model.
 * Takes an encoded model and return a decoded instance of the encoded model
 * If the encoded data cannot be decoded throws InvalidDataException
 *
 * @param <T> Type of the encoded and decoded model
 */
@FunctionalInterface
public interface ModelDecodingFunction<T extends AbstractModel> {
  T decode(ByteBuffer data) throws AbstractModel.InvalidDataException;
}
