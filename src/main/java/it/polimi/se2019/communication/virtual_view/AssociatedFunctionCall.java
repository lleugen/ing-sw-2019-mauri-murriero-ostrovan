package it.polimi.se2019.communication.virtual_view;

import it.polimi.se2019.communication.encodable.FunctionCallEncodable;

/**
 * Function call with user id associated to it
 */
public class AssociatedFunctionCall {
  /**
   * Reference to the function call
   */
  private FunctionCallEncodable fc;

  /**
   * User this function call is associated to
   */
  private String user;

  /**
   * Create a new AssociatedFunctionCall
   *
   * @param fc    FunctionCall
   * @param user  User the FunctionCall is associated to
   */
  public AssociatedFunctionCall(FunctionCallEncodable fc, String user){
    this.fc = fc;
    this.user = user;
  }

  /**
   * Get the associated FunctionCall
   *
   * @return the associated FunctionCall
   */
  public FunctionCallEncodable getFc() {
    return this.fc;
  }

  /**
   * Get the user the FunctionCall is associated to
   *
   * @return the user the FunctionCall is associated to
   */
  public String getUser() {
    return this.user;
  }
}
