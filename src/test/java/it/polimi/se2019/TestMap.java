package it.polimi.se2019;

import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.map.Direction;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.map.UnknownMapTypeException;
import it.polimi.se2019.model.player.Player;
import javafx.print.PageLayout;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestMap {

  /**
   * The map is fully enclosed in the class, we cannot completely test it
   * cause the logic is private.
   * We must trust it and just check if returned data is plausible
   */
  @Test
  public void TestGetRoot() {
    try{
      GameBoard gameBoard = new GameBoard(0);
      Square returnedSquare;
      returnedSquare = gameBoard.getMap().getRoot();
      assert(returnedSquare != null);
    }
    catch(UnknownMapTypeException e){
      fail("could not generate game board");
    }

  }

  @Test
  public void testSquareAdjacencies(){
    try{
      GameBoard gameBoard = new GameBoard(0);
      Map map = gameBoard.getMap();
      List<Direction> currentAdjacencies = new ArrayList<>();
      for(int i = 0; i<3; i++){
        for(int j = 0; j<4; j++){
          if(map.getMapSquares()[i][j] != null){
            assert(map.getMapSquares()[i][j].getAdjacencies() != null);

            assert(!map.getMapSquares()[i][j].getAdjacencies().contains(null));

            currentAdjacencies = map.getMapSquares()[i][j].getAdjacencies();
            for(int k = 0; k<currentAdjacencies.size(); k++){
              if(!currentAdjacencies.get(k).isBlocked()){
                //System.err.println(i + " " + j + " " + k);
                assert(currentAdjacencies.get(k).getSquare() != null);
              }
            }

            assert(map.getMapSquares()[i][j].getDecks() != null);
          }
        }
      }
    }
    catch(UnknownMapTypeException e){
      fail("could not generate game board");
    }

  }

  @Test
  public void testDecks(){
    try{
      GameBoard gameBoard = new GameBoard(0);
      Map map = gameBoard.getMap();
      assert(map.getGameBoard().getDecks() != null);
    }
    catch(UnknownMapTypeException e){
      fail("could not generate game board");
    }

  }

  @Test
  public void testRefill(){
    try{
      GameBoard gameBoard = new GameBoard(0);
      for(int i = 0; i<3; i++){
        for(int j = 0; j<4; j++){
          if(gameBoard.getMap().getMapSquares()[i][j] != null){
            gameBoard.getMap().getMapSquares()[i][j].refill();
            assert(gameBoard.getMap().getMapSquares()[i][j].getItem().get(0) != null);
          }
        }
      }
    }
    catch(UnknownMapTypeException e){
      fail("could not generate game board");
    }

  }

  @Test
  public void testGetVisibleSquares(){
    try{
      GameBoard gameBoard = new GameBoard(0);
      Map map = gameBoard.getMap();
      List<Square> visibleSquares = new ArrayList<>();
      for(int i = 0; i<3; i++){
        for(int k = 0; k<4; k++){
          if(map.getMapSquares()[i][k] != null){
            visibleSquares.clear();
            visibleSquares = map.getVisibleSquares(map.getMapSquares()[i][k]);
            assert(!visibleSquares.isEmpty());
          }
        }
      }
    }
    catch(UnknownMapTypeException e){
      fail("could not generate game board");
    }

  }

  @Test
  public void testGetVisiblePlayers(){
    try{
      GameBoard gameBoard = new GameBoard(0);
      Map map = gameBoard.getMap();
      Player player1 = new Player("player1", "character1", gameBoard);
      Player player2 = new Player("player2", "character2", gameBoard);
      List<Player> playerList = new ArrayList<>();
      playerList.add(player1);
      playerList.add(player2);
      gameBoard.addPlayers(playerList);
      player1.moveToSquare(map.getMapSquares()[0][0]);
      player2.moveToSquare(map.getMapSquares()[0][1]);
      List<Player> visiblePlayers = new ArrayList<>();
      List<Square> visibleSquares = map.getVisibleSquares(map.getMapSquares()[0][0]);
      visiblePlayers =  map.getPlayersOnSquares(visibleSquares);
      assert(visiblePlayers.contains(player2));
      assert(visiblePlayers.contains(player1));
      visiblePlayers.clear();
      visiblePlayers = map.getPlayersOnSquares(
              map.getVisibleSquares(
                      map.getMapSquares()[1][0]
              )
      );
      assert(visiblePlayers.contains(player1));
      assert(visiblePlayers.contains(player2));
    }
    catch(UnknownMapTypeException e){
      fail("could not generate game board");
    }

  }

  @Test
  public void testGetSquareCoordinates(){
    try{
      GameBoard gameBoard = new GameBoard(0);
      Map map = gameBoard.getMap();
      List<Integer> coordinates = new ArrayList<>();
      for(int i = 0; i<3; i++){
        for(int k = 0; k<4; k++){
          if(map.getMapSquares()[i][k] != null){
            coordinates = map.getSquareCoordinates(map.getMapSquares()[i][k]);
            //System.err.println(i + " " + k);
            assert map.getMapSquares()[coordinates.get(0)][coordinates.get(1)].equals(map.getMapSquares()[i][k]);
          }
        }
      }
    }
    catch(UnknownMapTypeException e){
      fail("could not generate game board");
    }

  }

//  @Test
//  public void testCalculateDistance(){
//    try{
//      GameBoard gameBoard = new GameBoard(0);
//      Map map = gameBoard.getMap();
//      Integer distance = map.calculateDistance(map.getMapSquares()[0][0], map.getMapSquares()[2][2]);
//      assert(distance == 4);
//    }
//    catch(UnknownMapTypeException e){
//      fail("could not generate game board");
//    }
//
//  }

  @Test
  public void testGetOneMoveAway(){
    try{
      GameBoard gameBoard = new GameBoard(0);
      Map map = gameBoard.getMap();
      Player player1 = new Player("player1", "character1", gameBoard);
      List<Player> playerList = new ArrayList<>();
      playerList.add(player1);
      gameBoard.addPlayers(playerList);
      player1.moveToSquare(map.getMapSquares()[0][0]);
      assert(player1.getPosition().equals(map.getMapSquares()[0][0]));
      List<Square> squares = new ArrayList<>();
      squares.add(map.getMapSquares()[0][1]);
      List<Player> oneMoveAway = map.getPlayersOnSquares(squares);
      assert(oneMoveAway.contains(player1));
      oneMoveAway.clear();
      squares.clear();
      squares.add(map.getMapSquares()[1][1]);
      oneMoveAway = map.getPlayersOnSquares(squares);
      assert(!oneMoveAway.contains(player1));
    }
    catch(UnknownMapTypeException e){
      fail("could not generate game board");
    }

  }

  @Test
  public void testGetPlayersOnSquare(){
    try{
      GameBoard gameBoard = new GameBoard(0);
      Map map = gameBoard.getMap();
      Player player1 = new Player("player1", "character1", gameBoard);
      Player player2 = new Player("player2", "character2", gameBoard);
      List<Player> playerList = new ArrayList<>();
      playerList.add(player1);
      playerList.add(player2);
      gameBoard.addPlayers(playerList);
      player1.moveToSquare(map.getMapSquares()[0][0]);
      player2.moveToSquare(map.getMapSquares()[0][0]);
      List<Player> playersOnSquare = new ArrayList<>();
      List<Square> squares = new ArrayList<>();
      squares.add(map.getMapSquares()[0][0]);
      playersOnSquare = map.getPlayersOnSquares(squares);
      assert(playersOnSquare.contains(player1));
      assert(playersOnSquare.contains(player2));
      player2.moveToSquare(map.getMapSquares()[1][1]);
      playersOnSquare.clear();
      squares.clear();
      squares.add(map.getMapSquares()[0][0]);
      playersOnSquare = map.getPlayersOnSquares(squares);
      assert(playersOnSquare.contains(player1));
      assert(!playersOnSquare.contains(player2));
      playersOnSquare.clear();
      squares.clear();
      squares.add(map.getMapSquares()[2][2]);
      playersOnSquare = map.getPlayersOnSquares(squares);
      assert(playersOnSquare.isEmpty());
    }
    catch(UnknownMapTypeException e){
      fail("could not generate game board");
    }

  }

  @Test
  public void testGetOpenDirections(){
    try{
      GameBoard gameBoard = new GameBoard(0);
      Map map = gameBoard.getMap();
      List<Integer> openDirections = new ArrayList<>();
      for(int i = 0; i<3; i++){
        for(int k = 0; k<4; k++){
          if(map.getMapSquares()[i][k] != null){
            openDirections.clear();
            openDirections = map.getOpenDirections(map.getMapSquares()[i][k]);
            for(int l = 0; l<openDirections.size(); l++){
              //System.err.println(i + " " + k + " " + openDirections.get(l));
              //System.err.println((map.getMapSquares()[i][k].getAdjacencies().get(l).isBlocked() ? "blocked" :
              // "open"));

              assert(!map.getMapSquares()[i][k].getAdjacencies().get(openDirections.get(l)).isBlocked());
            }
          }
        }
      }
    }
    catch(UnknownMapTypeException e){
      fail("could not generate game board");
    }

  }

  @Test
  public void testGetTwoMovesAway(){
    try{
      GameBoard gameBoard = new GameBoard(0);
      Map map = gameBoard.getMap();
      Player player1 = new Player("player1", "character1", gameBoard);
      List<Player> playerList = new ArrayList<>();
      playerList.add(player1);
      gameBoard.addPlayers(playerList);
      player1.moveToSquare(map.getMapSquares()[0][0]);
      List<Player> twoMovesAway = new ArrayList<>();
      List<Square> squares = new ArrayList<>();
      squares.addAll(map.getReachableSquares(map.getMapSquares()[0][0], 2));
      twoMovesAway = map.getPlayersOnSquares(squares);
      assert(twoMovesAway.contains(player1));
      squares.clear();
      squares.addAll(map.getReachableSquares(map.getMapSquares()[1][1], 2));
      twoMovesAway = map.getPlayersOnSquares(squares);
      assert(twoMovesAway.contains(player1));
      squares.clear();
      squares.addAll(map.getReachableSquares(map.getMapSquares()[2][2], 2));
      twoMovesAway = map.getPlayersOnSquares(squares);
      assert(!twoMovesAway.contains(player1));
    }
    catch(UnknownMapTypeException e){
      fail("could not generate game board");
    }

  }

  @Test
  public void testSpawnPoints() throws UnknownMapTypeException {
    try{
      GameBoard gameBoard = new GameBoard(0);
      assert(gameBoard.getMap().getRedSpawnPoint() != null);
      assert(gameBoard.getMap().getBlueSpawnPoint() != null);
      assert(gameBoard.getMap().getYellowSpawnPoint() != null);
    }
    catch(UnknownMapTypeException e){
      fail("could not generate game board");
    }

  }


  @Test
  public void testGenMap1(){
    try{
      GameBoard gameBoard = new GameBoard(1);
      for(int i = 0; i<3; i++){
        for(int k = 0; k<4; k++){
          if(i != 2 && k != 0){
            assert gameBoard.getMap().getMapSquares()[i][k] != null;
          }
        }
      }
    }
    catch(UnknownMapTypeException e){
      fail("could not generate game board");
    }
  }

  @Test
  public void testGenMap2(){
    try{
      GameBoard gameBoard = new GameBoard(2);
      for(int i = 0; i<3; i++){
        for(int k = 0; k<4; k++){
          assert gameBoard.getMap().getMapSquares()[i][k] != null;
        }
      }
    }
    catch(UnknownMapTypeException e){
      fail("could not generate game board");
    }
  }

  @Test
  public void testGenMap3(){
    try{
      GameBoard gameBoard = new GameBoard(3);
      for(int i = 0; i<3; i++){
        for(int k = 0; k<4; k++){
          if(i != 0 && k != 3){
            assert gameBoard.getMap().getMapSquares()[i][k] != null;
          }
        }
      }
    }
    catch(UnknownMapTypeException e){
      fail("could not generate game board");
    }
  }

}