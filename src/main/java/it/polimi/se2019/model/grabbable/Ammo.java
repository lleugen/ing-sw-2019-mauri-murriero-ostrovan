package it.polimi.se2019.model.grabbable;

/**
 * The ammo class represents a box of ammunition cubes
 */
public class Ammo extends Grabbable {
  /**
   * The amount of red ammunition cubes in the ammo box
   */
  private Integer red;

  /**
   * The amount of blue ammunition cubes in the ammo box
   */
  private Integer blue;

  /**
   * The amount of yellow ammunition cubes in the ammo box
   */
  private Integer yellow;

  /**
   * @param redAmount The amount of red ammunition cubes in the ammo box
   * @param blueAmount The amount of blue ammunition cubes in the ammo box
   * @param yellowAmount The amount of yellow ammunition cubes in the ammo box
   */
  public Ammo(Integer redAmount, Integer blueAmount, Integer yellowAmount) {
    if (redAmount<=3){
      red = redAmount;
    }
    else red = 3;
    if(blueAmount <= 3){
      blue = blueAmount;
    }
    else blue = 3;
    if(yellowAmount<=3){
      yellow = yellowAmount;
    }
    else yellow = 3;
  }

  /**
   * @param set the ammo to be compared
   * @param reference the comparison reference
   * @return 1 if set contains the same amount or more than reference, 0 if set contains less ammo cubes of any colour than reference
   */
  public static int compare(Ammo set, Ammo reference) {
    if((set.getRed() >= reference.getRed())
            &(set.getBlue()>= reference.getBlue())
            &(set.getYellow() >= reference.getYellow())){
      return 1;
    }
    else return 0;
  }

  /**
   * @return the amount of red ammunition cubes in the ammo box
   */
  public Integer getRed() {
    return this.red;
  }

  /**
   * @return the amount of blue ammunition cubes in the ammo box
   */
  public Integer getBlue() {
    return this.blue;
  }

  /**
   * @return the amount of yellow ammunition cubes in the ammo box
   */
  public Integer getYellow() {
    return this.yellow;
  }

  /**
   * @param amount add amount of red ammunition cubes to the ammo box
   */
  public void addRed(int amount) {
    this.red  = this.red + amount;
    if(this.red > 3) {
      this.red = 3;
    }
  }

  /**
   * @param amount add amount of blue ammunition cubes to the ammo box
   */
  public void addBlue(int amount){
    this.blue = this.blue + amount;
    if(this.blue > 3) {
      this.blue = 3;
    }
  }

  /**
   * @param amount add amount of blue ammunition cubes to the ammo box
   */
  public void addYellow(int amount){
    this.yellow = this.yellow + amount;
    if(this.yellow > 3) {
      this.yellow = 3;
    }
  }

  /**
   * @param amount subtract amount red ammunition cubes from the ammo box
   */
  public boolean useRed(int amount){
    if(getRed() >= amount){
      this.red = this.red - amount;
      return true;
    }
    else{
      return false;
    }
  }

  /**
   *
   * @param amount subtract amount of blue ammunition cubes from the ammo box
   */
  public boolean useBlue(int amount){
    if(getBlue() >= amount){
      this.blue = this.blue - amount;
      return true;
    }
    else{
      return false;
    }
  }

  /**
   *
   * @param amount subtract amount of yellow ammunition cubes from the ammo box
   */
  public boolean useYellow(int amount){
    if(getYellow() >= amount){
      this.yellow = this.yellow - amount;
      return true;
    }
    else{
      return false;
    }
  }
}

