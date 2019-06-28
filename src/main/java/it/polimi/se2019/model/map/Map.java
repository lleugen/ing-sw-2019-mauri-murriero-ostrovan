package it.polimi.se2019.model.map;

import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.GameBoard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
  public GameBoard getGameBoard(){
    return gameBoard;
  }


  /**
   * Init a new map
   *
   * @param mapType The type of the map to generate
   */
  public Map(int mapType, GameBoard g){
    gameBoard = g;
    mapSquares = new Square[3][4];
    if(mapType == 0){
      mapSquares[0][0] = new AmmoSquare(this,"blue", null);
      mapSquares[0][1] = new AmmoSquare(this,"blue", null);
      mapSquares[0][2] = new SpawnSquare(this,"blue", null);
      mapSquares[0][3] = null;

      mapSquares[1][0] = new SpawnSquare(this,"red", null);
      mapSquares[1][1] = new AmmoSquare(this,"red", null);
      mapSquares[1][2] = new AmmoSquare(this,"red", null);
      mapSquares[1][3] = new AmmoSquare(this,"yellow", null);

      mapSquares[2][0] = null;
      mapSquares[2][1] = new AmmoSquare(this,"white", null);
      mapSquares[2][2] = new AmmoSquare(this,"white", null);
      mapSquares[2][3] = new SpawnSquare(this,"yellow", null);
    }
    else if(mapType == 1){
      mapSquares[0][0] = new AmmoSquare(this,"blue", null);
      mapSquares[0][1] = new AmmoSquare(this,"blue", null);
      mapSquares[0][2] = new SpawnSquare(this,"blue", null);
      mapSquares[0][3] = new AmmoSquare(this,"gray", null);

      mapSquares[1][0] = new SpawnSquare(this,"red", null);
      mapSquares[1][1] = new AmmoSquare(this,"red", null);
      mapSquares[1][2] = new AmmoSquare(this,"yellow", null);
      mapSquares[1][3] = new AmmoSquare(this,"yello", null);

      mapSquares[2][0] = null;
      mapSquares[2][1] = new AmmoSquare(this,"white", null);
      mapSquares[2][2] = new AmmoSquare(this,"yellow", null);
      mapSquares[2][3] = new SpawnSquare(this,"yellow", null);
    }
    else if(mapType == 2){
      mapSquares[0][0] = new AmmoSquare(this,"red", null);
      mapSquares[0][1] = new AmmoSquare(this,"blue", null);
      mapSquares[0][2] = new SpawnSquare(this,"blue", null);
      mapSquares[0][3] = new AmmoSquare(this,"gray", null);

      mapSquares[1][0] = new SpawnSquare(this,"red", null);
      mapSquares[1][1] = new AmmoSquare(this,"purple", null);
      mapSquares[1][2] = new AmmoSquare(this,"yellow", null);
      mapSquares[1][3] = new AmmoSquare(this,"yellow", null);

      mapSquares[2][0] = new AmmoSquare(this,"white", null);
      mapSquares[2][1] = new AmmoSquare(this,"white", null);
      mapSquares[2][2] = new AmmoSquare(this,"yellow", null);
      mapSquares[2][3] = new SpawnSquare(this,"yellow", null);
    }
    else{
      mapSquares[0][0] = new AmmoSquare(this,"red", null);
      mapSquares[0][1] = new AmmoSquare(this,"blue", null);
      mapSquares[0][2] = new SpawnSquare(this,"blue", null);
      mapSquares[0][3] = null;

      mapSquares[1][0] = new SpawnSquare(this,"red", null);
      mapSquares[1][1] = new AmmoSquare(this,"purple", null);
      mapSquares[1][2] = new AmmoSquare(this,"purple", null);
      mapSquares[1][3] = new AmmoSquare(this,"yellow", null);

      mapSquares[2][0] = new AmmoSquare(this,"white", null);
      mapSquares[2][1] = new AmmoSquare(this,"white", null);
      mapSquares[2][2] = new AmmoSquare(this,"white", null);
      mapSquares[2][3] = new SpawnSquare(this,"yellow", null);
    }
    root = mapSquares[0][0];
    redSpawnPoint = mapSquares[0][2];
    blueSpawnPoint = mapSquares[1][0];
    yellowSpawnPoint = mapSquares[2][3];


    //add square adjacencies
    List<Direction> currentAdjacencies = new ArrayList<>();
    for(int i = 0; i<3; i++){
      for(int j = 0; j<4; j++){
        if(mapSquares[i][j] != null){
          currentAdjacencies.clear();
          //north adjacency
          if(i!=0){
            currentAdjacencies.add(0,new Direction(mapSquares[i - 1][j], false));
          }
          else{
            currentAdjacencies.add(0,new Direction(null, true));
          }
          //east adjacency
          if(j!=3){
            currentAdjacencies.add(1,new Direction(mapSquares[i][j + 1], false));
          }
          else{
            currentAdjacencies.add(1,new Direction(null, true));
          }
          //south adjacency
          if(i!=2){
            currentAdjacencies.add(2,new Direction(mapSquares[i + 1][j], false));
          }
          else{
            currentAdjacencies.add(2,new Direction(null, true));
          }
          //west adjacency
          if(j!=0){
            currentAdjacencies.add(3,new Direction(mapSquares[i][j - 1], false));
          }
          else{
            currentAdjacencies.add(3,new Direction(null, true));
          }
          mapSquares[i][j].setAdjacencies(currentAdjacencies);
        }
      }
    }



    //add walls
    if(mapType == 0){
      mapSquares[0][0].setBlocked(true, false, false,true);
      mapSquares[0][1].setBlocked(true, false, true,false);
      mapSquares[0][2].setBlocked(true, true,false,false);
      //mapSquares[0][3].setBlocked(true, true, true, true);
      mapSquares[1][0].setBlocked(false, false, true, true);
      mapSquares[1][1].setBlocked(true, false, false, false);
      mapSquares[1][2].setBlocked(false, false, true, false);
      mapSquares[1][3].setBlocked(true, true, false, false);
      //mapSquares[2][0].setBlocked(true, true, true, true);
      mapSquares[2][1].setBlocked(false, false, true, true);
      mapSquares[2][2].setBlocked(true, false, true, false);
      mapSquares[2][3].setBlocked(false, true, true, false);
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


    //add items to squares
    for(int i = 0; i<3; i++){
      for(int j = 0; j<4; j++){
        if(mapSquares[i][j] != null){
          if(gameBoard.getDecks() != null){
            mapSquares[i][j].refill();
          }
          else{
            System.err.println("decks is null in map constructor");
          }

        }
      }
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
      if(!d.isBlocked() && !visibleRooms.contains(d.getSquare().getIdRoom())){
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
        if(mapSquares[i][j] != null){
          if(mapSquares[i][j].equals(position)){
            xCoordinate = i;
            yCoordinate = j;
          }
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

  /**
   * Get all players one move away
   */
  public List<Player> getOneMoveAway(Square position){
    List<Player> playersOneMoveAway = new ArrayList<>();
    for(int i = 0; i<3; i++){
      if(!position.getAdjacencies().get(i).isBlocked()){
        playersOneMoveAway.addAll(getPlayersOnSquare(position.getAdjacencies().get(i).getSquare()));
      }
    }
    return playersOneMoveAway;
  }

  /**
   * return a list of all the players on a given square
   */
  public List<Player> getPlayersOnSquare(Square position){
    List<Player> playersOnPosition = new ArrayList<>();
    for(Player p : gameBoard.getPlayers()){
      if(p.getPosition().equals(position)){
        playersOnPosition.add(p);
      }
    }
    return playersOnPosition;
  }

  /**
   * get directions that are not blocked
   */
  public List<Integer> getOpenDirections(Square position){
    List<Integer> openDirections = new ArrayList<>();
    for(int i = 0; i<3; i++){
      if(!position.getAdjacencies().get(i).isBlocked()){
        openDirections.add(i);
      }
    }
    return openDirections;
  }

//  /**
//   * get adjacent squares
//   */
//  public List<Square> getAdjacentSquares(Square position){
//    List<Square> adjacentSquares = new ArrayList<>();
//    for(int i = 0; i<3; i++){
//      if(!position.getAdjacencies().get(i).isBlocked()){
//        adjacentSquares.add(position.getAdjacencies().get(i).getSquare());
//      }
//    }
//    return adjacentSquares;
//  }

  /**
   * get all players who are at most two moves away from position
   */
  public List<Player> getTwoMovesAway(Square position){
    List<Square> twoMovesAway = new ArrayList<>();
    List<Square> oneMoveAway = new ArrayList<>();
    List<Player> twoMovesAwayPlayers = new ArrayList<>();
    for(Direction d : position.getAdjacencies()){
      if(!d.isBlocked()){
        oneMoveAway.add(d.getSquare());
      }
    }
    for(Square q : oneMoveAway){
      for(Direction d : q.getAdjacencies()){
        if((!d.isBlocked()) && (!twoMovesAway.contains(d.getSquare()))){
          twoMovesAway.add(d.getSquare());
        }
      }
    }
    for(Player p : gameBoard.getPlayers()){
      if(twoMovesAway.contains(p.getPosition())){
        twoMovesAwayPlayers.add(p);
      }
    }
    return twoMovesAwayPlayers;
  }

//  /**
//   * get all squares that are at most two moves away from position
//   */
//  public List<Square> getTwoMovesAwaySquares(Square position){
//    List<Square> twoMovesAway = new ArrayList<>();
//    List<Square> oneMoveAway = new ArrayList<>();
//    List<Player> twoMovesAwayPlayers = new ArrayList<>();
//    for(Direction d : position.getAdjacencies()){
//      if(!d.isBlocked()){
//        oneMoveAway.add(d.getSquare());
//      }
//    }
//    for(Square q : oneMoveAway){
//      for(Direction d : q.getAdjacencies()){
//        if((!d.isBlocked()) && (!twoMovesAway.contains(d.getSquare()))){
//          twoMovesAway.add(d.getSquare());
//        }
//      }
//    }
//    return twoMovesAway;
//  }

//  /**
//   * get all squares that are at most three moves away from position
//   */
//  public List<Square> getThreeMovesAwaySquares(Square position){
//    List<Square> twoMovesAway = getTwoMovesAwaySquares(position);
//    List<Square> threeMovesAway = new LinkedList<>(twoMovesAway);
//    for(Square q : twoMovesAway){
//      for(Direction d : q.getAdjacencies()){
//        if((!d.isBlocked()) && (!threeMovesAway.contains(d.getSquare()))){
//          threeMovesAway.add(d.getSquare());
//        }
//      }
//    }
//    return threeMovesAway;
//
////    this.getReachableSquaresFromSquare(position, )
//  }

  /**
   * Get a list of reachable squares from the squares passed as parameter
   *
   * @param b   List of squares to use as base
   * @param d   Maximum distance to search
   *
   * @return the list of reachable squares
   *
   * __INFO__ The result is duplicate-free
   */
  public List<Square> getReachableSquares(Square b, int d) {
    List<Square> toReturn = new LinkedList<>();
    toReturn.add(b);

    for (int i = 0; i < d; i++){
      toReturn.addAll(this.getAdjacients(toReturn));
    }

    return toReturn.stream()
            .distinct()
            .collect(Collectors.toList());
  }

  /**
   * Get the list of adjacent squares (squares reachable with one movement)
   * from the squares passed as parameter
   *
   * @param b List of squares to calculate adjacent from
   *
   * @return The list of adjacent squares
   *
   * __WARN__ The returned list may contains duplicates
   */
  private List<Square> getAdjacients(Collection<Square> b){
    return b.stream()
            .map(Square::getAdjacencies)
            .flatMap(List::stream)
            .filter(Direction::isAccessible)
            .map(Direction::getSquare)
            .collect(Collectors.toList());
  }
}
