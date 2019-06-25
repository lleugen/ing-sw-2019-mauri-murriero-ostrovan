//package it.polimi.se2019;
//
//import it.polimi.se2019.model.GameBoard;
//import it.polimi.se2019.model.map.Map;
//import it.polimi.se2019.model.player.Player;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//public class TestPlayer {
//    @Test
//    public void testTakeDamage(){
//        GameBoard gameBoard = new GameBoard(0);
//        Player playerDefending = new Player("defender", "d", gameBoard);
//        Player playerAttacking = new Player("attacker", "a",gameBoard);
//        Player playerAttackingB = new Player("attackerB", "a",gameBoard);
//        playerDefending.takeDamage(playerAttacking, 1);
//        playerDefending.takeDamage(playerAttackingB, 1);
//        assert((playerDefending.getBoard().getDamageReceived().get(0) == playerAttacking) & (playerDefending.getBoard().getDamageReceived().get(1) == playerAttackingB));
//    }
//    @Test
//    public void testTakeMarks(){
//        GameBoard gameBoard = new GameBoard(0);
//        Player playerDefending = new Player("defender","d", gameBoard);
//        Player playerAttacking = new Player("attacker", "a", gameBoard);
//        playerDefending.takeMarks(playerAttacking, 1);
//        assert(playerDefending.getBoard().getMarksAssigned().get(0) == playerAttacking);
//    }
//    /*
//    @Test
//    public void testMoveShouldSucceed(){
//        Map map = new Map(1, );
//        Player player = new Player(null, null);
//        //il metodo respawn di model>player deve ricevere il quadrato dove fare spawn, questo viene determinato dal
//        //metodo spawn di controller>playerController
//        //problema : resolveDeath di model>player è già stato implementato e usa respawn senza argomento
//        //spawn è necessario per istanziare il contesto per cui lascio in sospeso i test per i metodi respawn e move
//    }
//    */
//    @Test
//    public void testResolveDeathNormalModeShouldSucceed(){
//        GameBoard gameBoard = new GameBoard(0);
//        Player deadPlayer = new Player("dead", "d",gameBoard);
//        Player attacker1 = new Player("attackerA", "a",gameBoard);
//        Player attacker2 = new Player("attackerB", "a",gameBoard);
//        deadPlayer.takeDamage(attacker1, 6);
//        deadPlayer.takeDamage(attacker2, 6);
//        deadPlayer.resolveDeath();
//        assert(attacker1.getPoints() == 1 + deadPlayer.getBoard().getDeathValue().get(0)) & (attacker2.getPoints() == deadPlayer.getBoard().getDeathValue().get(1));
//    }
//    @Test
//    public void testResolveDeadFrenzyModeShouldSucceed(){
//        GameBoard gameBoard = new GameBoard(0);
//        Player deadPlayer = new Player("dead", "d", gameBoard);
//        Player attacker1 = new Player("attackerA", "a", gameBoard);
//        Player attacker2 = new Player("attackerB", "a", gameBoard);
//        deadPlayer.getBoard().turnAround();
//        deadPlayer.takeDamage(attacker1, 6);
//        deadPlayer.takeDamage(attacker2, 6);
//        deadPlayer.resolveDeath();
//        assert((attacker1.getPoints() == 2) & (attacker2.getPoints() == 1));
//    }
//}
