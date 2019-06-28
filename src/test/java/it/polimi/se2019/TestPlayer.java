package it.polimi.se2019;

import it.polimi.se2019.RMI.UserTimeoutException;
import it.polimi.se2019.model.GameBoard;
import it.polimi.se2019.model.grabbable.Ammo;
import it.polimi.se2019.model.grabbable.Weapon;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.UnknownMapTypeException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerViewOnServer;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class TestPlayer {
    @Test
    public void testCreate()throws UnknownMapTypeException {
        GameBoard gameBoard = new GameBoard(0);
        Player player1 = new Player("player1", "character1", gameBoard);
        assert(player1.getInventory() != null);
        assert(player1.getBoard() != null);
        assert(player1.getCharacter().equals("character1"));
        assert(player1.getName().equals("player1"));
        assert(player1.getPoints() == 0);
        assert(player1.getState() == 0);
    }
    @Test
    public void testTakeDamage()throws UnknownMapTypeException {
        GameBoard gameBoard = new GameBoard(0);
        Player playerDefending = new Player("defender", "d", gameBoard);
        Player playerAttacking = new Player("attacker", "a",gameBoard);
        Player playerAttackingB = new Player("attackerB", "a",gameBoard);
        playerDefending.takeDamage(playerAttacking, 1);
        playerDefending.takeDamage(playerAttackingB, 1);
        assert((playerDefending.getBoard().getDamageReceived().get(0) == playerAttacking) & (playerDefending.getBoard().getDamageReceived().get(1) == playerAttackingB));
    }
    @Test
    public void testTakeMarks()throws UnknownMapTypeException {
        GameBoard gameBoard = new GameBoard(0);
        Player playerDefending = new Player("defender","d", gameBoard);
        Player playerAttacking = new Player("attacker", "a", gameBoard);
        playerDefending.takeMarks(playerAttacking, 1);
        assert(playerDefending.getBoard().getMarksAssigned().get(0) == playerAttacking);
    }
    /*
    @Test
    public void testMoveShouldSucceed(){
        Map map = new Map(1, );
        Player player = new Player(null, null);
        //il metodo respawn di model>player deve ricevere il quadrato dove fare spawn, questo viene determinato dal
        //metodo spawn di controller>playerController
        //problema : resolveDeath di model>player è già stato implementato e usa respawn senza argomento
        //spawn è necessario per istanziare il contesto per cui lascio in sospeso i test per i metodi respawn e move
    }
    */
    @Test
    public void testResolveDeathNormalModeShouldSucceed() throws UnknownMapTypeException {
        GameBoard gameBoard = new GameBoard(0);
        Player deadPlayer = new Player("dead", "d",gameBoard);
        Player attacker1 = new Player("attackerA", "a",gameBoard);
        Player attacker2 = new Player("attackerB", "a",gameBoard);
        deadPlayer.takeDamage(attacker1, 6);
        deadPlayer.takeDamage(attacker2, 6);
        deadPlayer.resolveDeath();
        assert(attacker1.getPoints() == 1 + deadPlayer.getBoard().getDeathValue().get(0)) & (attacker2.getPoints() == deadPlayer.getBoard().getDeathValue().get(1));
    }
    @Test
    public void testResolveDeadFrenzyModeShouldSucceed() throws UnknownMapTypeException {
        GameBoard gameBoard = new GameBoard(0);
        Player deadPlayer = new Player("dead", "d", gameBoard);
        Player attacker1 = new Player("attackerA", "a", gameBoard);
        Player attacker2 = new Player("attackerB", "a", gameBoard);
        deadPlayer.getBoard().turnAround();
        deadPlayer.takeDamage(attacker1, 6);
        deadPlayer.takeDamage(attacker2, 6);
        deadPlayer.resolveDeath();
        assert((attacker1.getPoints() == 2) & (attacker2.getPoints() == 1));
    }

    /*
    //this test will be for the controller
    @Mock
    PlayerViewOnServer client;
    @Test
    public void testReloadWeapon(){
        GameBoard gameBoard = new GameBoard(0);
        Player player1 = new Player("player1", "character1", gameBoard);
        List<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        gameBoard.addPlayers(playerList);
        Weapon weapon = new Weapon("weapon1", new Ammo(0,0,0), new Ammo(1,1,1));
        player1.getInventory().addWeaponToInventory(weapon);
        player1.getInventory().getWeapons().get(0).unload();
        List<String> weapons = new ArrayList<>();
        weapons.add(weapon.getName());
        try{
            Mockito.when(client.chooseBoolean(any())).thenReturn(true);
            Mockito.when(client.chooseWeaponToReload(weapons)).thenReturn(weapons.get(0));
            player1.reloadWeapon(client);
        }
        catch(UserTimeoutException e){
            fail();
        }
        assert(player1.getInventory().getWeapons().get(0).isLoaded());
        assert(player1.getInventory().getAmmo().getRed() == 0);
        assert(player1.getInventory().getAmmo().getBlue() == 0);
        assert(player1.getInventory().getAmmo().getYellow() == 0);

    }
    */
}
