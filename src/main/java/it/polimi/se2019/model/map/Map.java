package it.polimi.se2019.model.map;

import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.GameBoard;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A map is a virtual collection of squares, linked together.
 * In reality, only one square is inserted in this class, cause all square are
 * linked together, and therefore the list of squares can easily be retrived
 *
 * @author Eugenio Ostrovan
 * @author Fabio Mauri
 */
public class Map {
  /**
   * The top left square of the map
   */
  private Square root;

  /**
   * The game board this map belongs to
   */
  private GameBoard gameBoard;

  /**
   * The matrix of all the squares that make the map
   */
  private Square[][] mapSquares;

  /**
   * Points to the square that is the red spawn point
   */
  private SpawnSquare redSpawnPoint;
  /**
   * Points to the square that is the blue spawn point
   */
  private SpawnSquare blueSpawnPoint;
  /**
   * Points to the square that is the yellow spawn point
   */
  private SpawnSquare yellowSpawnPoint;

  /**
   * @return a pointer to the red spawn point
   */
  public SpawnSquare getRedSpawnPoint(){
    return redSpawnPoint;
  }
  /**
   * @return a pointer to the blue spawn point
   */
  public SpawnSquare getBlueSpawnPoint(){
    return blueSpawnPoint;
  }
  /**
   * @return a pointer to the yellow spawn point
   */
  public SpawnSquare getYellowSpawnPoint(){
    return yellowSpawnPoint;
  }

  /**
   * @return a pointer to the game board that this map belongs to
   */
  public GameBoard getGameBoard(){
    return gameBoard;
  }


  /**
   * Init a new map
   *
   * @param mapType The type of the map to generate
   * @param g the game board to which the map belongs
   * @throws UnknownMapTypeException if the user asks for a non existent map
   */
  public Map(Integer mapType, GameBoard g) throws UnknownMapTypeException {
    this.gameBoard = g;

    this.mapSquares = genMap(this, mapType.toString());

    this.root = this.mapSquares[0][0];
    this.redSpawnPoint = (SpawnSquare)this.mapSquares[0][2];
    this.blueSpawnPoint = (SpawnSquare)this.mapSquares[1][0];
    this.yellowSpawnPoint = (SpawnSquare)this.mapSquares[2][3];

    //add square adjacencies
    for(int i = 0; i<3; i++){
      for(int j = 0; j<4; j++){
        updateAdjiacent(this.mapSquares, i, j);
      }
    }

    //put up walls
    switch (mapType){
      case 0: genWalls0();
      break;
      case 1: genWalls1();
      break;
      case 2: genWalls2();
      break;
      case 3: genWalls3();
      break;
      default: System.out.println("the map is of unknown type");
    }

    this.refillAll();
    for(int i = 0; i<3; i++){
      for(int k = 0; k<4; k++){
        if(getMapSquares()[i][k] != null){
          if(getMapSquares()[i][k] instanceof SpawnSquare){
            System.out.println("spawn");
          }
          else{
            System.out.println("ammo");
          }
        }
      }
    }
  }

  /**
   * Update the adjacency for a square passed as paramater
   *
   * @param map Map to update adjacency onto
   * @param x   X coordinate of the square in mapSquare
   * @param y   Y coordinate of the square
   */
  private static void updateAdjiacent(Square[][] map, int y, int x){
    List<Direction> currentAdjacencies = new ArrayList<>(4);
    if(map[y][x] != null){
      //north adjacency
      if(y > 0){
        currentAdjacencies.add(0,new Direction(map[y - 1][x] , false));
      }
      else{
        currentAdjacencies.add(0,new Direction(null, true));
      }
      //east adjacency
      if(x < (map[y].length - 1)){
        currentAdjacencies.add(1,new Direction(map[y][x + 1] , false));
      }
      else{
        currentAdjacencies.add(1,new Direction(null, true));
      }
      //south adjacency
      if(y < (map.length - 1)){
        currentAdjacencies.add(2,new Direction(map[y + 1][x] , false));
      }
      else{
        currentAdjacencies.add(2,new Direction(null, true));
      }
      //west adjacency
      if(x > 0){
        currentAdjacencies.add(3,new Direction(map[y][x - 1] , false));
      }
      else{
        currentAdjacencies.add(3,new Direction(null, true));
      }
      map[y][x].setAdjacencies(currentAdjacencies);
    }
  }

  /**
   *
   * @return the matrix of all the squares of the map
   */
  public Square[][]getMapSquares(){
    return mapSquares;
  }
  /**
   * Gets the root square
   * @return the root square of the map, mapSquares[0][0]
   */
  public Square getRoot() {
    return this.root;
  }

  /**
   * get the coordinates of a square
   * @return a list of two integers representing the row and the column that identify the square
   * @param position the square of which the coordinates are to be calculated
   */
  public List<Integer> getSquareCoordinates(Square position){
    int xCoordinate = 0;
    int yCoordinate = 0;
    for(int i = 0; i<3; i++){
      for(int j = 0; j<4; j++){
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
   * @throws UnknownMapTypeException if the user specifies a non existing map
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



    return mapSquares;
  }

  private void genWalls0(){
    if(mapSquares != null){
      // walls
      mapSquares[0][0].setBlocked(true, false, false,true);
      mapSquares[0][1].setBlocked(true, false, true,false);
      mapSquares[0][2].setBlocked(true, true,false,false);
      mapSquares[1][0].setBlocked(false, false, true, true);
      mapSquares[1][1].setBlocked(true, false, false, false);
      mapSquares[1][2].setBlocked(false, false, true, false);
      mapSquares[1][3].setBlocked(true, true, false, false);
      mapSquares[2][1].setBlocked(false, false, true, true);
      mapSquares[2][2].setBlocked(true, false, true, false);
      mapSquares[2][3].setBlocked(false, true, true, false);
    }

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



    return mapSquares;
  }

  private void genWalls1(){
    if(mapSquares != null){
      // walls
      mapSquares[0][1].setBlocked(true, false, true, false);
      mapSquares[1][1]. setBlocked(true, true, false, false);
      mapSquares[1][2].setBlocked(false, false, false, true);
    }
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



    return mapSquares;
  }

  private void genWalls2(){
    if(mapSquares != null){
      // walls
      mapSquares[1][0].setBlocked(false, true, false, true);
      mapSquares[1][1].setBlocked(false, true, false, true);
      mapSquares[1][2].setBlocked(false, false, false, true);
    }
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



    return mapSquares;
  }

  private void genWalls3(){
    if(mapSquares != null){
      // walls
      mapSquares[1][0].setBlocked(false, true, false, true);
      mapSquares[1][1].setBlocked(false, false, false, true);
      mapSquares[1][2].setBlocked(false, false, true, false);
      mapSquares[2][2].setBlocked(true, false, true, false);
      mapSquares[0][2].setBlocked(true, true, false, false);
      mapSquares[1][3].setBlocked(true, true, false, false);
    }
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

    //List<Square> currentAdjacents = new ArrayList<>();
    for (int i = 0; i < d; i++){
      toReturn.addAll(this.getAdjacients(toReturn));
    }

    List<Square> l = toReturn.stream().distinct().collect(Collectors.toList());
    //l.forEach(System.out::println);
    //l.stream().map(this::getSquareCoordinates).forEach(System.out::println);

    return l;
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
            .filter(Objects::nonNull)
            .map(Square::getIdRoom)
            .distinct()
            .collect(Collectors.toList());

    return Arrays.stream(mapSquares)
            .filter(Objects::nonNull)
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
    List<Player> players = new ArrayList<>();
    for(Player p : gameBoard.getPlayers()){
      if(b.contains(p.getPosition())){
        players.add(p);
      }
    }
    return players;
//    return this.gameBoard.getPlayers().stream()
//            .filter(Objects::nonNull)
//            .filter((Player p) -> b.contains(p.getPosition()))
//            .distinct()
//            .collect(Collectors.toList());
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
            .filter(Objects::nonNull)
            .map(Square::getAdjacencies)
            .flatMap(List::stream)
            .filter(Objects::nonNull)
            .filter(Direction::isAccessible)
            .map(Direction::getSquare)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
  }

  /**
   * Get the list of directions accessible from the square passed as parameter
   *
   * @param position Square to calculate accessible directions from
   *
   * @return the list of accessible directions
   */
  public List<Integer> getOpenDirections(Square position){
    List<Integer> openDirections = new ArrayList<>();
    if (position != null) {
      for (int i = 0; i < 4; i++) {
        Direction dir = position.getAdjacencies().get(i);
        if ((dir != null) && (!dir.isBlocked())) {
          openDirections.add(i);
        }
      }
    }
    return openDirections;
  }
}
