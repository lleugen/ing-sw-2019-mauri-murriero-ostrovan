package it.polimi.se2019.model.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A map is a virtual collection of squares, linked together.
 * In reality, only one square is inserted in this class, cause all square are
 * linked together, and therefore the list of squares can easily be retrived
 */
public class Map {
  /**
   *
   */
  private Square root;

  /**
   *
   */
  private Square[][] mapSquares;

  /**
   * Init a new map
   *
   * @param mapType The type of the map to generate
   *
   * @throws UnknownMapTypeException if the type is not valid
   */
  public Map(int mapType) throws UnknownMapTypeException{
    if(mapType == 0){
      mapSquares[0][0] = new AmmoSquare("blue", null);
      mapSquares[0][1] = new AmmoSquare("blue", null);
      mapSquares[0][2] = new SpawnSquare("blue", null);
      mapSquares[0][3] = null;

      mapSquares[1][0] = new SpawnSquare("red", null);
      mapSquares[1][1] = new AmmoSquare("red", null);
      mapSquares[1][2] = new AmmoSquare("red", null);
      mapSquares[1][3] = new AmmoSquare("yellow", null);

      mapSquares[2][0] = null;
      mapSquares[2][1] = new AmmoSquare("white", null);
      mapSquares[2][2] = new AmmoSquare("white", null);
      mapSquares[2][3] = new SpawnSquare("yellow", null);
    }
    else if(mapType == 1){
      mapSquares[0][0] = new AmmoSquare("blue", null);
      mapSquares[0][1] = new AmmoSquare("blue", null);
      mapSquares[0][2] = new AmmoSquare("blue", null);
      mapSquares[0][3] = new AmmoSquare("gray", null);

      mapSquares[1][0] = new SpawnSquare("red", null);
      mapSquares[1][1] = new AmmoSquare("red", null);
      mapSquares[1][2] = new AmmoSquare("yellow", null);
      mapSquares[1][3] = new AmmoSquare("yello", null);

      mapSquares[2][0] = null;
      mapSquares[2][1] = new AmmoSquare("white", null);
      mapSquares[2][2] = new AmmoSquare("yellow", null);
      mapSquares[2][3] = new SpawnSquare("yellow", null);
    }
    else if(mapType == 2){
      mapSquares[0][0] = new AmmoSquare("red", null);
      mapSquares[0][1] = new AmmoSquare("blue", null);
      mapSquares[0][2] = new SpawnSquare("blue", null);
      mapSquares[0][3] = new AmmoSquare("gray", null);

      mapSquares[1][0] = new SpawnSquare("red", null);
      mapSquares[1][1] = new AmmoSquare("purple", null);
      mapSquares[1][2] = new AmmoSquare("yellow", null);
      mapSquares[1][3] = new AmmoSquare("yellow", null);

      mapSquares[2][0] = new AmmoSquare("white", null);
      mapSquares[2][1] = new AmmoSquare("white", null);
      mapSquares[2][2] = new AmmoSquare("yellow", null);
      mapSquares[2][3] = new SpawnSquare("yellow", null);
    }
    else{
      mapSquares[0][0] = new AmmoSquare("red", null);
      mapSquares[0][1] = new AmmoSquare("blue", null);
      mapSquares[0][2] = new SpawnSquare("blue", null);
      mapSquares[0][3] = null;

      mapSquares[1][0] = new SpawnSquare("red", null);
      mapSquares[1][1] = new AmmoSquare("purple", null);
      mapSquares[1][2] = new AmmoSquare("purple", null);
      mapSquares[1][3] = new AmmoSquare("yellow", null);

      mapSquares[2][0] = new AmmoSquare("white", null);
      mapSquares[2][1] = new AmmoSquare("white", null);
      mapSquares[2][2] = new AmmoSquare("white", null);
      mapSquares[2][3] = new SpawnSquare("yellow", null);
    }
    root = mapSquares[0][0];
    //add square adjacencies
    for(int i = 0; i<4; i++){
      for(int j = 0; j<3; j++){
        List<Direction> currentAdjacencies = new ArrayList<Direction>();
        if(j!=0){
          currentAdjacencies.add(new Direction(mapSquares[i][j - 1], false));
        }
        else{
          currentAdjacencies.add(new Direction(null, true));
        }
        if(i!=3){
          currentAdjacencies.add(new Direction(mapSquares[i + 1][j], false));
        }
        else{
          currentAdjacencies.add(new Direction(null, true));
        }
        if(j!=2){
          currentAdjacencies.add(new Direction(mapSquares[i][j + 1], false));
        }
        else{
          currentAdjacencies.add(new Direction(null, true));
        }
        if(i!=0){
          currentAdjacencies.add(new Direction(mapSquares[i - 1][j], false));
        }
        else{
          currentAdjacencies.add(new Direction(null, true));
        }
        mapSquares[i][j].setAdjacencies(currentAdjacencies);
      }
    }

    //add walls
    if(mapType == 0){
      mapSquares[0][1].setBlocked(true, false, true,false);
      mapSquares[1][1].setBlocked(true, false,false,false);
      mapSquares[1][2].setBlocked(false, false, true, false);
      mapSquares[2][2].setBlocked(true, true, true, false);
      mapSquares[2][3].setBlocked(false, true, true, true);
    }
    else if(mapType == 1){
      mapSquares[0][1].setBlocked(true, false, true, false);
      mapSquares[1][1]. setBlocked(true, true, false, false);
      mapSquares[1][2].setBlocked(false, false, false, true);
    }
    else if(mapType == 2){
      mapSquares[1][0].setBlocked(false, true, false, true);
      mapSquares[1][1].setBlocked(false, true, false, true);
      mapSquares[1][2].setBlocked(false, false, false, true);
    }
    else{
      mapSquares[1][0].setBlocked(false, true, false, true);
      mapSquares[1][1].setBlocked(false, false, false, true);
      mapSquares[1][2].setBlocked(false, false, true, false);
      mapSquares[2][2].setBlocked(true, false, true, false);
      mapSquares[0][2].setBlocked(true, true, false, false);
      mapSquares[1][3].setBlocked(true, true, false, false);
    }
  }

  public Square[][]getMapSquares(){
    return mapSquares;
  }

  /**
   * Gets the root square
   */
  public Square getRoot() {
    return this.root;
  }
}
