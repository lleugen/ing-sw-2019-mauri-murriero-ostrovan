package it.polimi.se2019.model.map;

import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.GameBoard;

import java.util.*;
import java.util.function.Function;
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
  public Map(Integer mapType, GameBoard g) throws UnknownMapTypeException {
    this.gameBoard = g;

    this.mapSquares = genMap(this, mapType.toString());

    this.root = this.mapSquares[0][0];
    this.redSpawnPoint = this.mapSquares[0][2];
    this.blueSpawnPoint = this.mapSquares[1][0];
    this.yellowSpawnPoint = this.mapSquares[2][3];

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
    this.refillAll();
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
   * get the coordinates of a square
   */
  public List<Integer> getSquareCoordinates(Square position){
    int xCoordinate = 0;
    int yCoordinate = 0;
    for(int i = 0; i<3; i++){
      for(int j = 0; j<2; j++){
        if(mapSquares[i][j] != null && mapSquares[i][j].equals(position)){
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


  // -----

  /**
   * Refill all squares of the map
   */
  private void refillAll(){
    Arrays.stream(this.mapSquares)
            .flatMap(Arrays::stream)
            .filter(Objects::nonNull)
            .forEach(Square::refill);
  }

  /**
   * Generate a new map
   *
   * @param mapReference A reference to the map the generated map belongs to
   * @param type         Type of the map to generate
   *
   * @return The generated map
   */
  private static Square[][] genMap(Map mapReference, String type)
          throws UnknownMapTypeException {
    java.util.Map<String, Function<Map, Square[][]>> generators = new HashMap<>();

    generators.put("0", Map::genMapType0);
    generators.put("1", Map::genMapType1);
    generators.put("2", Map::genMapType2);
    generators.put("3", Map::genMapType3);

    if (generators.containsKey(type)) {
      return generators.get(type).apply(mapReference);
    }
    else {
      throw new UnknownMapTypeException();
    }
  }

  /**
   * Generate a new map of type 0
   *
   * @param mapReference A reference to the map the generated map belongs to
   *
   * @return The generated map
   */
  private static Square[][] genMapType0(Map mapReference){
    Square[][] mapSquares = new Square[3][4];

    // Squares
    mapSquares[0][0] = new AmmoSquare(mapReference,Square.RoomColor.BLUE, null);
    mapSquares[0][1] = new AmmoSquare(mapReference,Square.RoomColor.BLUE, null);
    mapSquares[0][2] = new SpawnSquare(mapReference,Square.RoomColor.BLUE, null);
    mapSquares[0][3] = null;

    mapSquares[1][0] = new SpawnSquare(mapReference,Square.RoomColor.RED, null);
    mapSquares[1][1] = new AmmoSquare(mapReference,Square.RoomColor.RED, null);
    mapSquares[1][2] = new AmmoSquare(mapReference,Square.RoomColor.RED, null);
    mapSquares[1][3] = new AmmoSquare(mapReference,Square.RoomColor.YELLOW, null);

    mapSquares[2][0] = null;
    mapSquares[2][1] = new AmmoSquare(mapReference,Square.RoomColor.WHITE, null);
    mapSquares[2][2] = new AmmoSquare(mapReference,Square.RoomColor.WHITE, null);
    mapSquares[2][3] = new SpawnSquare(mapReference,Square.RoomColor.YELLOW, null);

    // walls
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

    /*
    // adjacencies
    //add square adjacencies
    List<Direction> currentAdjacencies = new ArrayList<>();
    for(int i = 0; i<3; i++){
      for(int j = 0; j<4; j++){
        if(mapSquares[i][j] != null){
          currentAdjacencies.clear();
          //west adjacency
          if(j!=0){
            currentAdjacencies.add(new Direction(mapSquares[i][j - 1], false));
          }
          else{
            currentAdjacencies.add(new Direction(null, true));
          }
          //south adjacency
          if(i!=2){
            currentAdjacencies.add(new Direction(mapSquares[i + 1][j], false));
          }
          else{
            currentAdjacencies.add(new Direction(null, true));
          }
          //east adjacency
          if(j!=3){
            currentAdjacencies.add(new Direction(mapSquares[i][j + 1], false));
          }
          else{
            currentAdjacencies.add(new Direction(null, true));
          }
          //north adjacency
          if(i!=0){
            currentAdjacencies.add(new Direction(mapSquares[i - 1][j], false));
          }
          else{
            currentAdjacencies.add(new Direction(null, true));
          }
          mapSquares[i][j].setAdjacencies(currentAdjacencies);
        }
      }
    }
    */

    return mapSquares;
  }

  /**
   * Generate a new map of type 1
   *
   * @param mapReference A reference to the map the generated map belongs to
   *
   * @return The generated map
   */
  private static Square[][] genMapType1(Map mapReference){
    Square[][] mapSquares = new Square[3][4];

    // Squares
    mapSquares[0][0] = new AmmoSquare(mapReference,Square.RoomColor.BLUE, null);
    mapSquares[0][1] = new AmmoSquare(mapReference,Square.RoomColor.BLUE, null);
    mapSquares[0][2] = new SpawnSquare(mapReference,Square.RoomColor.BLUE, null);
    mapSquares[0][3] = new AmmoSquare(mapReference,Square.RoomColor.GRAY, null);

    mapSquares[1][0] = new SpawnSquare(mapReference,Square.RoomColor.RED, null);
    mapSquares[1][1] = new AmmoSquare(mapReference,Square.RoomColor.RED, null);
    mapSquares[1][2] = new AmmoSquare(mapReference,Square.RoomColor.YELLOW, null);
    mapSquares[1][3] = new AmmoSquare(mapReference,Square.RoomColor.YELLOW, null);

    mapSquares[2][0] = null;
    mapSquares[2][1] = new AmmoSquare(mapReference,Square.RoomColor.WHITE, null);
    mapSquares[2][2] = new AmmoSquare(mapReference,Square.RoomColor.YELLOW, null);
    mapSquares[2][3] = new SpawnSquare(mapReference,Square.RoomColor.YELLOW, null);

    // walls
    mapSquares[0][1].setBlocked(true, false, true, false);
    mapSquares[1][1]. setBlocked(true, true, false, false);
    mapSquares[1][2].setBlocked(false, false, false, true);

    /*
    //add square adjacencies
    List<Direction> currentAdjacencies = new ArrayList<>();
    for(int i = 0; i<3; i++){
      for(int j = 0; j<4; j++){
        if(mapSquares[i][j] != null){
          currentAdjacencies.clear();
          //west adjacency
          if(j!=0){
            currentAdjacencies.add(new Direction(mapSquares[i][j - 1], false));
          }
          else{
            currentAdjacencies.add(new Direction(null, true));
          }
          //south adjacency
          if(i!=2){
            currentAdjacencies.add(new Direction(mapSquares[i + 1][j], false));
          }
          else{
            currentAdjacencies.add(new Direction(null, true));
          }
          //east adjacency
          if(j!=3){
            currentAdjacencies.add(new Direction(mapSquares[i][j + 1], false));
          }
          else{
            currentAdjacencies.add(new Direction(null, true));
          }
          //north adjacency
          if(i!=0){
            currentAdjacencies.add(new Direction(mapSquares[i - 1][j], false));
          }
          else{
            currentAdjacencies.add(new Direction(null, true));
          }
          mapSquares[i][j].setAdjacencies(currentAdjacencies);
        }
      }
    }
    */

    return mapSquares;
  }

  /**
   * Generate a new map of type 2
   *
   * @param mapReference A reference to the map the generated map belongs to
   *
   * @return The generated map
   */
  private static Square[][] genMapType2(Map mapReference){
    Square[][] mapSquares = new Square[3][4];

    // Squares
    mapSquares[0][0] = new AmmoSquare(mapReference,Square.RoomColor.RED, null);
    mapSquares[0][1] = new AmmoSquare(mapReference,Square.RoomColor.BLUE, null);
    mapSquares[0][2] = new SpawnSquare(mapReference,Square.RoomColor.BLUE, null);
    mapSquares[0][3] = new AmmoSquare(mapReference,Square.RoomColor.GRAY, null);

    mapSquares[1][0] = new SpawnSquare(mapReference,Square.RoomColor.RED, null);
    mapSquares[1][1] = new AmmoSquare(mapReference,Square.RoomColor.PURPLE, null);
    mapSquares[1][2] = new AmmoSquare(mapReference,Square.RoomColor.YELLOW, null);
    mapSquares[1][3] = new AmmoSquare(mapReference,Square.RoomColor.YELLOW, null);

    mapSquares[2][0] = new AmmoSquare(mapReference,Square.RoomColor.WHITE, null);
    mapSquares[2][1] = new AmmoSquare(mapReference,Square.RoomColor.WHITE, null);
    mapSquares[2][2] = new AmmoSquare(mapReference,Square.RoomColor.YELLOW, null);
    mapSquares[2][3] = new SpawnSquare(mapReference,Square.RoomColor.YELLOW, null);

    // walls
    mapSquares[1][0].setBlocked(false, true, false, true);
    mapSquares[1][1].setBlocked(false, true, false, true);
    mapSquares[1][2].setBlocked(false, false, false, true);

    /*
    //add square adjacencies
    List<Direction> currentAdjacencies = new ArrayList<>();
    for(int i = 0; i<3; i++){
      for(int j = 0; j<4; j++){
        if(mapSquares[i][j] != null){
          currentAdjacencies.clear();
          //west adjacency
          if(j!=0){
            currentAdjacencies.add(new Direction(mapSquares[i][j - 1], false));
          }
          else{
            currentAdjacencies.add(new Direction(null, true));
          }
          //south adjacency
          if(i!=2){
            currentAdjacencies.add(new Direction(mapSquares[i + 1][j], false));
          }
          else{
            currentAdjacencies.add(new Direction(null, true));
          }
          //east adjacency
          if(j!=3){
            currentAdjacencies.add(new Direction(mapSquares[i][j + 1], false));
          }
          else{
            currentAdjacencies.add(new Direction(null, true));
          }
          //north adjacency
          if(i!=0){
            currentAdjacencies.add(new Direction(mapSquares[i - 1][j], false));
          }
          else{
            currentAdjacencies.add(new Direction(null, true));
          }
          mapSquares[i][j].setAdjacencies(currentAdjacencies);
        }
      }
    }
    */

    return mapSquares;
  }

  /**
   * Generate a new map of type 3
   *
   * @param mapReference A reference to the map the generated map belongs to
   *
   * @return The generated map
   */
  private static Square[][] genMapType3(Map mapReference){
    Square[][] mapSquares = new Square[3][4];

    // Squares
    mapSquares[0][0] = new AmmoSquare(mapReference,Square.RoomColor.RED, null);
    mapSquares[0][1] = new AmmoSquare(mapReference,Square.RoomColor.BLUE, null);
    mapSquares[0][2] = new SpawnSquare(mapReference,Square.RoomColor.BLUE, null);
    mapSquares[0][3] = null;

    mapSquares[1][0] = new SpawnSquare(mapReference,Square.RoomColor.RED, null);
    mapSquares[1][1] = new AmmoSquare(mapReference,Square.RoomColor.PURPLE, null);
    mapSquares[1][2] = new AmmoSquare(mapReference,Square.RoomColor.PURPLE, null);
    mapSquares[1][3] = new AmmoSquare(mapReference,Square.RoomColor.YELLOW, null);

    mapSquares[2][0] = new AmmoSquare(mapReference,Square.RoomColor.WHITE, null);
    mapSquares[2][1] = new AmmoSquare(mapReference,Square.RoomColor.WHITE, null);
    mapSquares[2][2] = new AmmoSquare(mapReference,Square.RoomColor.WHITE, null);
    mapSquares[2][3] = new SpawnSquare(mapReference,Square.RoomColor.YELLOW, null);

    // walls
    mapSquares[1][0].setBlocked(false, true, false, true);
    mapSquares[1][1].setBlocked(false, false, false, true);
    mapSquares[1][2].setBlocked(false, false, true, false);
    mapSquares[2][2].setBlocked(true, false, true, false);
    mapSquares[0][2].setBlocked(true, true, false, false);
    mapSquares[1][3].setBlocked(true, true, false, false);


    /*
    //add square adjacencies
    List<Direction> currentAdjacencies = new ArrayList<>();
    for(int i = 0; i<3; i++){
      for(int j = 0; j<4; j++){
        if(mapSquares[i][j] != null){
          currentAdjacencies.clear();
          //west adjacency
          if(j!=0){
            currentAdjacencies.add(new Direction(mapSquares[i][j - 1], false));
          }
          else{
            currentAdjacencies.add(new Direction(null, true));
          }
          //south adjacency
          if(i!=2){
            currentAdjacencies.add(new Direction(mapSquares[i + 1][j], false));
          }
          else{
            currentAdjacencies.add(new Direction(null, true));
          }
          //east adjacency
          if(j!=3){
            currentAdjacencies.add(new Direction(mapSquares[i][j + 1], false));
          }
          else{
            currentAdjacencies.add(new Direction(null, true));
          }
          //north adjacency
          if(i!=0){
            currentAdjacencies.add(new Direction(mapSquares[i - 1][j], false));
          }
          else{
            currentAdjacencies.add(new Direction(null, true));
          }
          mapSquares[i][j].setAdjacencies(currentAdjacencies);
        }
      }
    }
    */

    return mapSquares;
  }

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
   * Get the list of visible squares from the square passed as parameter
   * A square is visible if either is adjacent to the base square or either
   * belongs to the same room of a square adjacent to the base
   *
   * @param b   Base square to find visible square from
   *
   * @return The list of visible squares from the base
   *
   * __INFO__ The result is duplicate-free
   */
  public List<Square> getVisibleSquares(Square b){
    List<Square.RoomColor> visibleRooms = this.getReachableSquares(b, 1).stream()
            .map(Square::getIdRoom)
            .distinct()
            .collect(Collectors.toList());

    return Arrays.stream(mapSquares)
            .flatMap(Arrays::stream)
            .filter(Objects::nonNull)
            .filter((Square s) ->
                    visibleRooms.stream().anyMatch(s.getIdRoom()::equals)
            )
            .distinct()
            .collect(Collectors.toList());
  }

  /**
   * Get all players on a list of squares
   *
   * @param b List of square find players onto
   *
   * @return The list of players found on squares passed as parameter
   *
   * __INFO__ The result is duplicate-free
   */
  public List<Player> getPlayersOnSquares(List<Square> b){
    return this.gameBoard.getPlayers().stream()
            .filter((Player p) -> b.contains(p.getPosition()))
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
            .filter(Objects::nonNull)
            .filter(Direction::isAccessible)
            .map(Direction::getSquare)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
  }
}
