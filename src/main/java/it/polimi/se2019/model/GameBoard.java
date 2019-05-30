package it.polimi.se2019.model;

import it.polimi.se2019.model.deck.Decks;
import it.polimi.se2019.model.grabbable.Ammo;
import it.polimi.se2019.model.grabbable.AmmoTile;
import it.polimi.se2019.model.grabbable.PowerUpCard;
import it.polimi.se2019.model.grabbable.Weapon;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.UnknownMapTypeException;
import it.polimi.se2019.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * The game board is the root class of each game.
 * It contains and initializes every element of the game, and is responsible
 * for managing the highest tasks of the match (AKA the game manager)
 */
public class GameBoard {
  /**
   * Contains a reference to the Kill Scoreboard for the current game
   */
  private KillScoreBoard killScoreBoard;

  /**
   * Contains a reference to the selected Map for the current game
   */
  private Map map;

  /**
   * Contains the list of the players for the current game
   */
  private List<Player> players;

  /**
   * Contains the id of the first player in the players list
   */
  private Integer firstPlayer = 0;

  /**
   * Contains the id of the player currently playing the turn
   */
  private Integer currentPlayer;

  private boolean playersAdded;

  private boolean killScoreBoardCreated;
  public boolean isKillScoreBoardCreated(){
    return killScoreBoardCreated;
  }

  /**
   * Inits the gameBoard
   *
   * @param mapType Type of the map to generate
   *
   * @throws NullPointerException if players contains a null player
   */
  public GameBoard(int mapType){
    playersAdded = false;
    killScoreBoardCreated = false;
    this.currentPlayer = firstPlayer;
    this.map = new Map(mapType, this);

    /*
    Integer i;
    Player tmp;
    for (i = 0; i < players.size(); i++){
      tmp = players.get(i);
      this.players.add(tmp);
    }
    */

    //create decks
    decks = new Decks(
            this.genWeaponDeck(),
            this.genPowerUpDeck(),
            this.genAmmoDeck()
    );
  }

  private List<AmmoTile> genAmmoDeck(){
    List<AmmoTile> deck = new ArrayList<>();

    deck.add(new AmmoTile(0, 2, 0, true));
    deck.add(new AmmoTile(0, 1, 1, true));
    deck.add(new AmmoTile(0, 2, 1, false));
    deck.add(new AmmoTile(0, 0, 2, true));
    deck.add(new AmmoTile(0, 1, 2, false));
    deck.add(new AmmoTile(1, 1, 0, true));
    deck.add(new AmmoTile(1, 2, 0, false));
    deck.add(new AmmoTile(1, 0, 1, true));
    deck.add(new AmmoTile(1, 0, 2, false));
    deck.add(new AmmoTile(2, 0, 0, true));
    deck.add(new AmmoTile(2, 1, 0, false));
    deck.add(new AmmoTile(2, 0, 1, false));

    deck.add(new AmmoTile(0, 2, 0, true));
    deck.add(new AmmoTile(0, 1, 1, true));
    deck.add(new AmmoTile(0, 2, 1, false));
    deck.add(new AmmoTile(0, 0, 2, true));
    deck.add(new AmmoTile(0, 1, 2, false));
    deck.add(new AmmoTile(1, 1, 0, true));
    deck.add(new AmmoTile(1, 2, 0, false));
    deck.add(new AmmoTile(1, 0, 1, true));
    deck.add(new AmmoTile(1, 0, 2, false));
    deck.add(new AmmoTile(2, 0, 0, true));
    deck.add(new AmmoTile(2, 1, 0, false));
    deck.add(new AmmoTile(2, 0, 1, false));

    deck.add(new AmmoTile(0, 2, 0, true));
    deck.add(new AmmoTile(0, 1, 1, true));
    deck.add(new AmmoTile(0, 2, 1, false));
    deck.add(new AmmoTile(0, 0, 2, true));
    deck.add(new AmmoTile(0, 1, 2, false));
    deck.add(new AmmoTile(1, 1, 0, true));
    deck.add(new AmmoTile(1, 2, 0, false));
    deck.add(new AmmoTile(1, 0, 1, true));
    deck.add(new AmmoTile(1, 0, 2, false));
    deck.add(new AmmoTile(2, 0, 0, true));
    deck.add(new AmmoTile(2, 1, 0, false));
    deck.add(new AmmoTile(2, 0, 1, false));

    return deck;
  }

  private List<PowerUpCard> genPowerUpDeck(){
    List<PowerUpCard> deck = new ArrayList<>();

    deck.add(new PowerUpCard(new Ammo(1, 0, 0), "NewtonRed"));
    deck.add(new PowerUpCard(new Ammo(0, 1, 0), "NewtonBlue"));
    deck.add(new PowerUpCard(new Ammo(0, 0, 1), "NewtonYellow"));
    deck.add(new PowerUpCard(new Ammo(1, 0, 0), "TeleporterRed"));
    deck.add(new PowerUpCard(new Ammo(0, 1, 0), "TeleporterBlue"));
    deck.add(new PowerUpCard(new Ammo(0, 0, 1), "TeleporterYellow"));
    deck.add(new PowerUpCard(new Ammo(1, 0, 0), "TagbackGrenadeRed"));
    deck.add(new PowerUpCard(new Ammo(0, 1, 0), "TagbackGrenadeBlue"));
    deck.add(new PowerUpCard(new Ammo(0, 0, 1), "TagbackGrenadeYellow"));
    deck.add(new PowerUpCard(new Ammo(1, 0, 0), "TargetingScopeRed"));
    deck.add(new PowerUpCard(new Ammo(0, 1, 0), "TargetingScopeBlue"));
    deck.add(new PowerUpCard(new Ammo(0, 0, 1), "TargetingScopeYellow"));

    return deck;
  }

  private List<Weapon> genWeaponDeck(){
    List<Weapon> deck = new ArrayList<Weapon>();

    deck.add(
            new Weapon(
                    "CyberBlade",
                    new Ammo(1, 0, 0),
                    new Ammo(1, 0, 1)
            )
    );
    deck.add(
            new Weapon(
                    "Electroscythe",
                    new Ammo(0, 0, 0),
                    new Ammo(0, 1, 0)
            )
    );
    deck.add(
            new Weapon(
                    "PlasmaGun",
                    new Ammo(0, 0, 1),
                    new Ammo(0, 1, 1)
            )
    );
    deck.add(
            new Weapon(
                    "GrenadeLauncher",
                    new Ammo(0, 0, 0),
                    new Ammo(1, 0, 0)
            )
    );
    deck.add(
            new Weapon(
                    "RocketLauncher",
                    new Ammo(1, 0, 0),
                    new Ammo(2, 0, 0)
            )
    );
    deck.add(
            new Weapon(
                    "Hellion",
                    new Ammo(0, 0, 1),
                    new Ammo(1, 0, 1)
            )
    );
    deck.add(
            new Weapon(
                    "TractorBeam",
                    new Ammo(0, 0, 0),
                    new Ammo(0, 1, 0)
            )
    );
    deck.add(
            new Weapon(
                    "LockRifle",
                    new Ammo(0, 1, 0),
                    new Ammo(0, 2, 0)
            )
    );
    deck.add(
            new Weapon(
                    "VortexCannon",
                    new Ammo(0, 1, 0),
                    new Ammo(1, 1, 0)
            )
    );
    deck.add(
            new Weapon(
                    "MachineGun",
                    new Ammo(1, 0, 0),
                    new Ammo(1, 1, 0)
            )
    );
    deck.add(
            new Weapon(
                    "Thor",
                    new Ammo(1, 0, 0),
                    new Ammo(1, 1, 0)
            )
    );
    deck.add(
            new Weapon(
                    "HeatSeeker",
                    new Ammo(1, 0, 1),
                    new Ammo(2, 0, 1)
            )
    );
    deck.add(
            new Weapon(
                    "Whisper",
                    new Ammo(0, 1, 1),
                    new Ammo(0, 2, 1)
            )
    );
    deck.add(
            new Weapon(
                    "Furnace",
                    new Ammo(0, 1, 0),
                    new Ammo(1, 1, 0)
            )
    );
    deck.add(
            new Weapon(
                    "RailGun",
                    new Ammo(0, 1, 1),
                    new Ammo(0, 1, 2)
            )
    );
    deck.add(
            new Weapon(
                    "Shotgun",
                    new Ammo(0, 0, 1),
                    new Ammo(0, 0, 2)
            )
    );
    deck.add(
            new Weapon(
                    "ZX2",
                    new Ammo(1, 0, 0),
                    new Ammo(1, 0, 1)
            )
    );
    deck.add(
            new Weapon(
                    "FlameThrower",
                    new Ammo(0, 0, 0),
                    new Ammo(1, 0, 0)
            )
    );
    deck.add(
            new Weapon(
                    "PowerGlove",
                    new Ammo(0, 1, 0),
                    new Ammo(0, 1, 1)
            )
    );
    deck.add(
            new Weapon(
                    "Shockwave",
                    new Ammo(0, 0, 0),
                    new Ammo(0, 0, 1)
            )
    );
    deck.add(
            new Weapon(
                    "SledgeHammer",
                    new Ammo(0, 0, 0),
                    new Ammo(0, 0, 1)
            )
    );


    return deck;
  }

  public boolean getPlayersAdded(){
    return playersAdded;
  }
  public void addPlayers(List<Player> p){
    players = p;
    playersAdded = true;
  }

  public List<Player> getPlayers(){
    return players;
  }

  public void createKillScoreBoard(Integer skulls, Integer[] scores){
    killScoreBoard = new KillScoreBoard(skulls, scores);
  }

  /**
   * Updates the gameboard to set the next player as active
   */
  public synchronized void nextPlayer(){
    Integer nextPlayerId = this.currentPlayer + 1;

    // If last player, restarting from the beginning

    if (nextPlayerId == (this.players.size() - 1)){
      nextPlayerId = 0;
    }
    this.currentPlayer = nextPlayerId;
  }

  /**
   * @return the current player
   */
  public Player getCurrentPlayer(){
    return this.players.get(this.currentPlayer);
  }

  /**
   * @return the kill scoreboard
   */
  public KillScoreBoard getKillScoreBoard(){
    return this.killScoreBoard;
  }

  /**
   * @return the map
   */
  public Map getMap() {
    return this.map;
  }

  /**
   * Contains the list of decks associated to this GameBoard
   */
  private Decks decks;

  /**
   * @return the decks
   */
  public Decks getDecks(){
    return this.decks;
  }
}
