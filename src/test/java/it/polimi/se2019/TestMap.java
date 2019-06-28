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

//  /**
//   * The map is fully enclosed in the class, we cannot completely test it
//   * cause the logic is private.
//   * We must trust it and just check if returned data is plausible
//   */
//  @Test
//  public void TestGetRoot() {
//    GameBoard gameBoard = new GameBoard(0);
//    Square returnedSquare;
//
//    try {
//      returnedSquare = gameBoard.getMap().getRoot();
//    }
//    catch (Exception e){
//      fail("something bad happened");
//    }
//  }

//  @Test
//  public void testSquareAdjacencies(){
//    GameBoard gameBoard = new GameBoard(0);
//    Map map = gameBoard.getMap();
//    List<Direction> currentAdjacencies = new ArrayList<>();
//    for(int i = 0; i<3; i++){
//      for(int j = 0; j<4; j++){
//        if(map.getMapSquares()[i][j] != null){
//          assert(map.getMapSquares()[i][j].getAdjacencies() != null);
//
//          assert(!map.getMapSquares()[i][j].getAdjacencies().contains(null));
//
//          currentAdjacencies = map.getMapSquares()[i][j].getAdjacencies();
//          for(int k = 0; k<currentAdjacencies.size(); k++){
//            if(!currentAdjacencies.get(k).isBlocked()){
//              assert(currentAdjacencies.get(k).getSquare() != null);
//            }
//          }
//
//          assert(map.getMapSquares()[i][j].getDecks() != null);
//        }
//      }
//    }
//  }
//
//  @Test
//  public void testDecks(){
//    GameBoard gameBoard = new GameBoard(0);
//    Map map = gameBoard.getMap();
//    assert(map.getGameBoard().getDecks() != null);
//  }
//
//  @Test
//  public void testRefill(){
//    GameBoard gameBoard = new GameBoard(0);
//    for(int i = 0; i<3; i++){
//      for(int j = 0; j<4; j++){
//        if(gameBoard.getMap().getMapSquares()[i][j] != null){
//          gameBoard.getMap().getMapSquares()[i][j].refill();
//          assert(gameBoard.getMap().getMapSquares()[i][j].getItem().get(0) != null);
//        }
//      }
//    }
//  }

//  @Test
//  public void testGetVisibleSquares(){
//    GameBoard gameBoard = new GameBoard(0);
//    Map map = gameBoard.getMap();
//    List<Square> visibleSquares = new ArrayList<>();
//    for(int i = 0; i<3; i++){
//      for(int k = 0; k<4; k++){
//        if(map.getMapSquares()[i][k] != null){
//          visibleSquares.clear();
//          visibleSquares = map.getVisibleSquares(map.getMapSquares()[i][k]);
//          assert(!visibleSquares.isEmpty());
//        }
//      }
//    }
//  }

//  @Test
//  public void testGetVisiblePlayers(){
//    GameBoard gameBoard = new GameBoard(0);
//    Map map = gameBoard.getMap();
//    Player player1 = new Player("player1", "character1", gameBoard);
//    Player player2 = new Player("player2", "character2", gameBoard);
//    List<Player> playerList = new ArrayList<>();
//    playerList.add(player1);
//    playerList.add(player2);
//    gameBoard.addPlayers(playerList);
//    player1.moveToSquare(map.getMapSquares()[0][0]);
//    player2.moveToSquare(map.getMapSquares()[0][1]);
//    List<Player> visiblePlayers = new ArrayList<>();
//    map.getPlayersOnSquares(
//            map.getVisibleSquares(
//                    map.getMapSquares()[0][0]
//            )
//    );
//    assert(visiblePlayers.contains(player2));
//    assert(!visiblePlayers.contains(player1));
//    visiblePlayers.clear();
//    visiblePlayers = map.getPlayersOnSquares(
//            map.getVisibleSquares(
//                    map.getMapSquares()[1][0]
//            )
//    );
//    assert(visiblePlayers.contains(player1));
//    assert(visiblePlayers.contains(player2));
//  }

//  @Test
//  public void testGetSquareCoordinates(){
//    GameBoard gameBoard = new GameBoard(0);
//    Map map = gameBoard.getMap();
//    List<Integer> coordinates = new ArrayList<>();
//    for(int i = 0; i<3; i++){
//      for(int k = 0; k<4; k++){
//        if(map.getMapSquares()[i][k] != null){
//          coordinates = map.getSquareCoordinates(map.getMapSquares()[i][k]);
//          assert(map.getMapSquares()[i][k].equals(map.getMapSquares()[coordinates.get(0)][coordinates.get(1)]));
//        }
//      }
//    }
//  }
//
//  @Test
//  public void testCalculateDistance(){
//    GameBoard gameBoard = new GameBoard(0);
//    Map map = gameBoard.getMap();
//    Integer distance = map.calculateDistance(map.getMapSquares()[0][0], map.getMapSquares()[2][2]);
//    assert(distance == 4);
//  }

//  @Test
//  public void testGetOneMoveAway(){
//    GameBoard gameBoard = new GameBoard(0);
//    Map map = gameBoard.getMap();
//    Player player1 = new Player("player1", "character1", gameBoard);
//    List<Player> playerList = new ArrayList<>();
//    playerList.add(player1);
//    gameBoard.addPlayers(playerList);
//    player1.moveToSquare(map.getMapSquares()[0][0]);
//    assert(player1.getPosition().equals(map.getMapSquares()[0][0]));
//    List<Player> oneMoveAway = map.getOneMoveAway(map.getMapSquares()[0][1]);
//    assert(oneMoveAway.contains(player1));
//    oneMoveAway.clear();
//    oneMoveAway = map.getOneMoveAway(map.getMapSquares()[1][1]);
//    assert(!oneMoveAway.contains(player1));
//  }

//  @Test
//  public void testGetPlayersOnSquare(){
//    GameBoard gameBoard = new GameBoard(0);
//    Map map = gameBoard.getMap();
//    Player player1 = new Player("player1", "character1", gameBoard);
//    Player player2 = new Player("player2", "character2", gameBoard);
//    List<Player> playerList = new ArrayList<>();
//    playerList.add(player1);
//    playerList.add(player2);
//    gameBoard.addPlayers(playerList);
//    player1.moveToSquare(map.getMapSquares()[0][0]);
//    player2.moveToSquare(map.getMapSquares()[0][0]);
//    List<Player> playersOnSquare = new ArrayList<>();
//    playersOnSquare = map.getPlayersOnSquare(map.getMapSquares()[0][0]);
//    assert(playersOnSquare.contains(player1));
//    assert(playersOnSquare.contains(player2));
//    player2.moveToSquare(map.getMapSquares()[1][1]);
//    playersOnSquare.clear();
//    playersOnSquare = map.getPlayersOnSquare(map.getMapSquares()[0][0]);
//    assert(playersOnSquare.contains(player1));
//    assert(!playersOnSquare.contains(player2));
//    playersOnSquare.clear();
//    playersOnSquare = map.getPlayersOnSquare(map.getMapSquares()[2][2]);
//    assert(playersOnSquare.isEmpty());
//  }

//  @Test
//  public void testGetOpenDirections(){
//    GameBoard gameBoard = new GameBoard(0);
//    Map map = gameBoard.getMap();
//    List<Integer> openDirections = new ArrayList<>();
//    for(int i = 0; i<3; i++){
//      for(int k = 0; k<4; k++){
//        if(map.getMapSquares()[i][k] != null){
//          openDirections.clear();
//          openDirections = map.getOpenDirections(map.getMapSquares()[i][k]);
//          for(int l = 0; l<openDirections.size(); l++){
//            assert(!map.getMapSquares()[i][k].getAdjacencies().get(l).isBlocked());
//          }
//        }
//      }
//    }
//  }

//  @Test
//  public void testGetTwoMovesAway(){
//    GameBoard gameBoard = new GameBoard(0);
//    Map map = gameBoard.getMap();
//    Player player1 = new Player("player1", "character1", gameBoard);
//    List<Player> playerList = new ArrayList<>();
//    playerList.add(player1);
//    gameBoard.addPlayers(playerList);
//    player1.moveToSquare(map.getMapSquares()[0][0]);
//    List<Player> twoMovesAway = new ArrayList<>();
//    twoMovesAway = map.getTwoMovesAway(map.getMapSquares()[0][0]);
//    assert(twoMovesAway.contains(player1));
//    twoMovesAway = map.getTwoMovesAway(map.getMapSquares()[1][1]);
//    assert(twoMovesAway.contains(player1));
//    twoMovesAway = map.getTwoMovesAway(map.getMapSquares()[2][2]);
//    assert(!twoMovesAway.contains(player1));
//  }

}