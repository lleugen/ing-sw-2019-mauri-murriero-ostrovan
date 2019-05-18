package it.polimi.se2019.model.map;

import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.GameBoard;

import java.util.ArrayList;
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
   * the game board this map belongs to
   */
  private GameBoard gameBoard;

  /**
   *
   */
  private Square[][] mapSquares;

  /**
   *
   */
  private int mapType;

  /**
   *
   */
  private Square redSpawnPoint;
  private Square blueSpawnPoint;
  private Square yellowSpawnPoint;
  public Square getRedSpawnPoint(){
    return redSpawnPoint;
  }
  public Square getBlueSpawnPoint(){
    return blueSpawnPoint;
  }
  public Square getYellowSpawnPoint(){
    return yellowSpawnPoint;
  }

  /**
   * Init a new map
   *
   * @param mapType The type of the map to generate
   *
   * @throws UnknownMapTypeException if the type is not valid
   */
  public Map(int mapType, GameBoard g) throws UnknownMapTypeException{
    gameBoard = g;
    this.mapType = mapType;
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
      mapSquares[0][2] = new SpawnSquare("blue", null);
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
    redSpawnPoint = mapSquares[0][2];
    blueSpawnPoint = mapSquares[1][0];
    yellowSpawnPoint = mapSquares[2][3];
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

  /**
   * Get a list of visible squares from one square
   */
  public List<Square> getVisibleSquares(Square position){
    List<Square> visibleSquares = new ArrayList<>();
    List<String> visibleRooms = new ArrayList<>();
    for(Direction d : position.getAdjacencies()){
      if(!d.isBlocked()){
        visibleRooms.add(d.getSquare().getIdRoom());
      }
    }
    for(int i = 0; i<3; i++){
      for(int j = 0; j<2; j++){
        for(String s : visibleRooms){
          if(mapSquares[i][j].getIdRoom().equals(s)){
            visibleSquares.add(mapSquares[i][j]);
          }
        }
      }
    }
    return visibleSquares;
  }

  /**
   *
   */
  public int getMapType(){
    return this.mapType;
  }

  /**
   * Get a list of all visible players from a square
   */
  public List<Player> getVisiblePlayers(Square position){
    List<Player> visiblePlayers = new ArrayList<>();
    List<Square> visibleSquares = getVisibleSquares(position);
    for(Player p : gameBoard.getPlayers()){
      if(visibleSquares.contains(p.getPosition())){
        visiblePlayers.add(p);
      }
    }
    return visiblePlayers;
  }

  /**
   * get the coordinates of a square
   */
  public List<Integer> getSquareCoordinates(Square position){
    int xCoordinate = 0;
    int yCoordinate = 0;
    for(int i = 0; i<3; i++){
      for(int j = 0; j<2; j++){
        if(mapSquares[i][j].equals(position)){
          xCoordinate = i;
          yCoordinate = j;
        }
      }
    }
    List<Integer> positionCoordinates = new ArrayList<>();
    positionCoordinates.add(xCoordinate);
    positionCoordinates.add(yCoordinate);
    return positionCoordinates;
  }

  /**
   * Calculate the Manhattan distance between two squares
   */
  public Integer calculateDistance(Square a, Square b){
    List<Integer> aCoordinates = getSquareCoordinates(a);
    List<Integer> bCoordinates = getSquareCoordinates(b);
    Integer xDifference = aCoordinates.get(0) - bCoordinates.get(0);
    if(xDifference < 0){
      xDifference = -xDifference;
    }
    Integer yDifference = aCoordinates.get(1) - bCoordinates.get(1);
    if(yDifference < 0){
      yDifference = -yDifference;
    }
    return(xDifference + yDifference);
  }
}
