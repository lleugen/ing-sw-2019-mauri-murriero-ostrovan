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
 *
 * @author Eugenio Ostrovan
 * @author Fabio Mauri
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

  /**
   * Specifies whether the players have been added to the game board
   */
  private boolean playersAdded;

  /**
   * Specifies whether the game is in frenzy mode or not
   */
  private boolean isFrenzy;

  /**
   * Inits the gameBoard
   *
   * @param mapType Type of the map to generate
   *
   * @throws NullPointerException     if players contains a null player
   * @throws UnknownMapTypeException  if the selected map does not exist
   */
  public GameBoard(int mapType) throws UnknownMapTypeException {
    isFrenzy = false;
    playersAdded = false;
    this.currentPlayer = firstPlayer;

    //create decks
    decks = new Decks(
            this.genWeaponDeck(),
            this.genPowerUpDeck(),
            this.genAmmoDeck()
    );
    this.createKillScoreBoard(
            5,
            new Integer[]{8, 6, 4, 2, 1, 1}
    );
    this.map = new Map(mapType, this);
  }

  /**
   *
   * @return whether the game is in frenzy mode
   */
  public boolean isFrenzy(){
    return isFrenzy;
  }

  /**
   * set the game to frenzy mode
   */
  public void setFrenzy(){
    isFrenzy = true;
  }

  /**
   * Generate the deck of ammunition tiles
   * @return a list of ammunition tiles
   */
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

  /**
   * Generate the deck of power up cards
   * @return a list of power up cards
   */
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

  /**
   * Generate the deck of weapons
   * @return a list of weapons
   */
  private List<Weapon> genWeaponDeck(){
    List<Weapon> deck = new ArrayList<>();

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

  /**
   *
   * @return whether players have been added to the game board
   */
  public boolean getPlayersAdded(){
    return playersAdded;
  }

  /**
   * add players to the game board
   * @param p players to be added to the game board
   */
  public void addPlayers(List<Player> p){
    players = p;
    playersAdded = true;
  }

  /**
   *
   * @return the list of players playing on this game board
   */
  public List<Player> getPlayers(){
    return players;
  }

  /**
   *
   * @param skulls number of kills that will be scored before frenzy mode starts
   * @param scores the numbers of points that will be assigned to players based on the number of kills they have scored
   */
  private void createKillScoreBoard(Integer skulls, Integer[] scores){
    this.killScoreBoard = new KillScoreBoard(skulls, scores);
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
