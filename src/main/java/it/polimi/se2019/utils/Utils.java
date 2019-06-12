package it.polimi.se2019.utils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Collectors;

public class Utils {
//  /**
//   * Encodes a string into a base64 format
//   *
//   * @param data data to encode
//   *
//   * @return encoded string
//   */
//  public static String base64Encode(String data){
//    return Base64.getEncoder().encodeToString(
//              data.getBytes(StandardCharsets.UTF_16)
//    );
//  }
//
//  /**
//   * Utils for decoding strings in base64 encoded with base64Encode.
//   *
//   * __WARN__ Only strings encoded with his counterpart base64Encode are
//   *          guaranteed to be decoded correctly
//   *
//   * @param encoded The string to decode (encoded with base64Encode)
//   *
//   * @return The decoded string
//   *
//   * @throws IllegalArgumentException if not a valid base64 string
//   */
//  public static String base64Decode(String encoded){
//    return new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_16);
//  }
//
//  /**
//   * Helper function for encoding an array of strings.
//   * Encoded data doesn't contains newline characters (you can so use newline
//   * characters as separator for storing multiple encoded arrays)
//   *
//   * @param data Array of string to encode
//   *
//   * @return the encoded representation of the array
//   */
//  public static String arrayEncode(String[] data){
//    return Arrays.stream(data)
//            .map(Utils::base64Encode)
//            .collect(Collectors.joining("|"));
//  }
//
//  /**
//   * Counterpart of arrayEncode.
//   * Decodes a list of string encoded with arrayEncode.
//   *
//   * __WARN__ Only data encoded with arrayEncode is guaranteed to be decoded
//   *          correctly
//   *
//   * @param encoded Data to decode
//   *
//   * @return decoded data
//   *
//   * @throws IllegalArgumentException if not a valid encoded string
//   */
//  public static String[] arrayDecode(String encoded){
//    return Arrays.stream(encoded.split("\\|"))
//            .filter(item -> !(item.isEmpty()))
//            .map(Utils::base64Decode)
//            .toArray(String[]::new);
//  }
}
