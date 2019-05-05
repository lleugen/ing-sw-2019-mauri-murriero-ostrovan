package it.polimi.se2019.controller;

import it.polimi.se2019.controller.weapons.WeaponController;
import it.polimi.se2019.controller.weapons.alternative_effects.*;
import it.polimi.se2019.controller.weapons.optional_effects.*;
import it.polimi.se2019.controller.weapons.ordered_effects.*;
import it.polimi.se2019.controller.weapons.simple.*;
import it.polimi.se2019.model.deck.Deck;
import it.polimi.se2019.model.grabbable.Weapon;
import it.polimi.se2019.model.player.Player;

/**
 * The game board controller is the game manager, it initializes a game by
 * generating a map and instantiating the players, it then runs the turns until
 * the number of skulls reaches 0, after that it runs the final frenzy round and
 * ends the game.
 */
public class GameBoardController {
  public GameBoardController() {
  }

  /**
   * The player who's turn it is currently
   */
  private Player currentPlayer;

  /**
   * This method generates one of the four possible maps for the game based
   * on the first player's input
   *
   * @param mapType is an integer that specifies the map to be generated
   */
  public void generateMap(int mapType) {
  }

  /**
   * This method creates a player class (model) for each player who joins the
   * game and adds it to the "players" list in the game board model.
   * A player is created with an inventory containing only one power up
   * card and a player board with empty damage and marks lists,
   * 0 deaths, "8 6 4 2 1 1" death value and board side '0'.
   */
  public void initializePlayers() {
  }

  /**
   * Initializes the whole set of weapons and adds them to the deck
   */
  public void initializeWeapons(){
    Deck<Weapon> weaponDeck;

    CyberBladeController cyberBladeController = new CyberBladeController();
    ElectroscytheController electroscytheController = new ElectroscytheController();
    PlasmaGunController plasmaGunController = new PlasmaGunController();
    GrenadeLauncherController grenadeLauncherController = new GrenadeLauncherController();
    RocketLauncherController rocketLauncherController = new RocketLauncherController();
    HellionController hellionController = new HellionController();
    TractorBeamController tractorBeamController = new TractorBeamController();
    LockRifleController lockRifleController = new LockRifleController();
    VortexCannonController  vortexCannonController = new  VortexCannonController();
    MachineGunController machineGunController = new MachineGunController();
    ThorController thorController = new ThorController();
    HeatSeekerController heatSeekerController = new HeatSeekerController();
    WhisperController whisperController = new WhisperController();
    FurnaceController furnaceController = new FurnaceController();
    RailGunController railGunController = new RailGunController();
    ShotgunController shotgunController = new ShotgunController();
    ZX2Controller zX2Controller = new ZX2Controller();
    FlameThrowerController flameThrowerController = new FlameThrowerController();
    PowerGloveController powerGloveController = new PowerGloveController();
    ShockwaveController shockwaveController = new ShockwaveController();
    SledgeHammerController sledgeHammerController = new SledgeHammerController();

    Weapon cyberBladeModel = new Weapon();
    Weapon electroscytheModel = new Weapon();
    Weapon plasmaGunModel = new Weapon();
    Weapon grenadeLauncherModel = new Weapon();
    Weapon rocketLauncherModel = new Weapon();
    Weapon hellionModel = new Weapon();
    Weapon tractorBeamModel = new Weapon();
    Weapon lockRifleModel = new Weapon();
    Weapon vortexCannonModel = new Weapon();
    Weapon machineGunModel = new Weapon();
    Weapon thorModel = new Weapon();
    Weapon heatSeekerModel = new Weapon();
    Weapon whisperModel = new Weapon();
    Weapon furnaceModel = new Weapon();
    Weapon railGunModel = new Weapon();
    Weapon shotgunModel = new Weapon();
    Weapon zX2Model = new Weapon();
    Weapon flameThrowerModel = new Weapon();
    Weapon powerGloveModel = new Weapon();
    Weapon shockwaveModel = new Weapon();
    Weapon sledgeHammerModel = new Weapon();

    cyberBladeModel.setDescription_eng("" +
            "Combining all effects allows you to move onto a square and " +
            "whack 2 people;\n" +
            "or whack somebody, move, and whack somebody else;\n" +
            "or whack 2 people and then move."
    );
    cyberBladeModel.setName_eng("" +
            "CYBERBLADE"
    );
    cyberBladeModel.setDescription_ita("" +
            "Combinare tutti gli effetti permette di muoversi in un quadrato\n" +
            "e colpire 2 persone;\n" +
            "oppure di colpire qualcuno, muovere e colpire qualcun altro.\n" +
            "Oppure ancora colpire 2 persone e poi andare via."
    );
    cyberBladeModel.setName_ita("" +
            "SPADA FOTONICA"
    );
    cyberBladeModel.setImg("" +
            "REPLACE"
    );
    cyberBladeModel.setEffect1Name_eng("" +
            "With Shadowstep"
    );
    cyberBladeModel.setEffect1Name_ita("" +
            "Passo d'Ombra"
    );
    cyberBladeModel.setEffect1Desc_eng(
            "Move 1 square before or after the basic effect"
    );
    cyberBladeModel.setEffect1Desc_ita("" +
            "Muovi di 1 quadrato prima o dopo l'effetto base."
    );
    cyberBladeModel.setEffect2Name_eng("" +
            "With Slice and Dice"
    );
    cyberBladeModel.setEffect2Name_ita("" +
            "Modalità Sminuzzare"
    );
    cyberBladeModel.setEffect2Desc_eng(
            "Deal 2 damage to a different target on your square.\n" +
            "The shadowstep may be used before or after this effect."
    );
    cyberBladeModel.setEffect2Desc_ita("" +
            "Dai 2 danni a un bersaglio differente nel quadrato " +
            "in cui ti trovi.\n" +
            "Il passo d'ombra può essere usato prima o dopo questo effetto."
    );
    cyberBladeModel.setMode1Name_eng("" +
            "Basic Effect"
    );
    cyberBladeModel.setMode1Name_ita("" +
            "Effetto Base"
    );
    cyberBladeModel.setMode1Desc_eng("" +
            "Deal 2 damage to 1 target on your square."
    );
    cyberBladeModel.setMode1Desc_ita("" +
            "Dai 2 danni a 1 bersaglio nel quadrato in cui ti trovi"
    );
    cyberBladeModel.setMode2Name_eng("" +
            ""
    );
    cyberBladeModel.setMode2Name_ita("" +
            ""
    );
    cyberBladeModel.setMode2Desc_eng("" +
            ""
    );
    cyberBladeModel.setMode2Desc_ita("" +
            ""
    );

    electroscytheModel.setDescription_eng("" +
            "No Notes"
    );
    electroscytheModel.setName_eng("" +
            "ELECTROSCYTHE"
    );
    electroscytheModel.setDescription_ita("" +
            "Nessuna Nota"
    );
    electroscytheModel.setName_ita("" +
            "FALCE PROTONICA"
    );
    electroscytheModel.setImg("" +
            "REPLACE"
    );
    electroscytheModel.setEffect1Name_eng("" +
            "In Reaper Mode"
    );
    electroscytheModel.setEffect1Name_ita("" +
            "Modalità Mietitore"
    );
    electroscytheModel.setEffect1Desc_eng("" +
            "Deal 2 damage to every other player on your square."
    );
    electroscytheModel.setEffect1Desc_ita("" +
            "Dai 2 danni a ogni altro giocatore presente nel quadrato " +
            "in cui ti trovi."
    );
    electroscytheModel.setEffect2Name_eng("" +
            ""
    );
    electroscytheModel.setEffect2Name_ita("" +
            ""
    );
    electroscytheModel.setEffect2Desc_eng("" +
            ""
    );
    electroscytheModel.setEffect2Desc_ita("" +
            ""
    );
    electroscytheModel.setMode1Name_eng("" +
            "Basic Mode"
    );
    electroscytheModel.setMode1Name_ita("" +
            "Modalità Base"
    );
    electroscytheModel.setMode1Desc_eng("" +
            "Deal 1 damage to every other player on your square."
    );
    electroscytheModel.setMode1Desc_ita("" +
            "Dai 1 danno a ogni altro giocatore presente " +
            "nel quadrato in cui ti trovi."
    );
    electroscytheModel.setMode2Name_eng("" +
            ""
    );
    electroscytheModel.setMode2Name_ita("" +
            ""
    );
    electroscytheModel.setMode2Desc_eng("" +
            ""
    );
    electroscytheModel.setMode2Desc_ita("" +
            ""
    );

    plasmaGunModel.setDescription_eng("" +
            "The two moves have no ammo cost. \n" +
            "You don't have to be able to see your target when you play the card.\n" +
            "For example, you can move 2 squares and shoot a target you now see. \n" +
            "You cannot use 1 move before shooting and 1 move after."
    );
    plasmaGunModel.setName_eng(
            "PLASMA GUN"
    );
    plasmaGunModel.setDescription_ita("" +
            "I due movimenti non hanno costo in munizioni.\n" +
            "Non hai bisogno di vedere il tuo bersaglio quando giochi la carta.\n" +
            "Per esempio puoi muovere di 2 quadrati e sparare al bersaglio che " +
            "ora puoi vedere.\n" +
            "Non puoi usare 1 movimento prima di sparare e 1 dopo aver sparato."
    );
    plasmaGunModel.setName_ita("" +
            "FUCILE AL PLASMA"
    );
    plasmaGunModel.setImg("" +
            "REPLACE"
    );
    plasmaGunModel.setEffect1Name_eng("" +
            "With Phase Glide"
    );
    plasmaGunModel.setEffect1Name_ita("" +
            "Slittamento di Fase"
    );
    plasmaGunModel.setEffect1Desc_eng("" +
            "Move 1 or 2 squares.\n" +
            "This effect can be used either before or after the basic effect."
    );
    plasmaGunModel.setEffect1Desc_ita("" +
            "Muovi di 1 o 2 quadrati.\n" +
            "Questo effetto può essere usato prima o dopo l'effetto base."
    );
    plasmaGunModel.setEffect2Name_eng("" +
            "With Charged Shot"
    );
    plasmaGunModel.setEffect2Name_ita("" +
            "Colpo Sovraccarico"
    );
    plasmaGunModel.setEffect2Desc_eng(
            "Deal 1 additional damage to your target"
    );
    plasmaGunModel.setEffect2Desc_ita("" +
            "Dai 1 danno aggiuntivo al tuo bersaglio."
    );
    plasmaGunModel.setMode1Name_eng("" +
            "Basic Effect"
    );
    plasmaGunModel.setMode1Name_ita("" +
            "Effetto Base"
    );
    plasmaGunModel.setMode1Desc_eng("" +
            "Deal 2 damage to 1 target you can see"
    );
    plasmaGunModel.setMode1Desc_ita("" +
            "Dai 2 danni a 1 bersaglio che puoi vedere."
    );
    plasmaGunModel.setMode2Name_eng("" +
            ""
    );
    plasmaGunModel.setMode2Name_ita("" +
            ""
    );
    plasmaGunModel.setMode2Desc_eng("" +
            ""
    );
    plasmaGunModel.setMode2Desc_ita("" +
            ""
    );

    grenadeLauncherModel.setDescription_eng("" +
            "For example, you can shoot a target, move it onto a square with" +
            "other targets, then damage everyone including the first target.\n" +
            "Or you can deal 2 to a main target, 1 to everyone else on that " +
            "square, then move the main target.\n" +
            "Or you can deal 1 to an isolated target and 1 to everyone " +
            "on a different square.\n" +
            "If you target your own square, you will not be moved or damaged."
    );
    grenadeLauncherModel.setName_eng(
            "GRENADE LAUNCHER"
    );
    grenadeLauncherModel.setDescription_ita("" +
            "Per esempio puoi sparare a un bersaglio, muoverlo in un " +
            "quadrato con altri bersagli e danneggiare chiunque, incluso il " +
            "primo bersaglio.\n" +
            "Oppure puoi dare 2 danni a un bersaglio principale e 1 " +
            "a tutti gli altri in quel quadrato, e poi muovere il " +
            "bersaglio principale.\n" +
            "Oppure puoi dare 1 danno a un bersaglio isolato e 1 a chiunque " +
            "in quadrato diverso.\n" +
            "Se bersagli il quadrato in cui ti trovi non potrai essere mosso " +
            "o danneggiato."
    );
    grenadeLauncherModel.setName_ita("" +
            "LANCIAGRANATE"
    );
    grenadeLauncherModel.setImg("" +
            "REPLACE"
    );
    grenadeLauncherModel.setEffect1Name_eng("" +
            "With Extra Grenade"
    );
    grenadeLauncherModel.setEffect1Name_ita("" +
            "Granata Extra"
    );
    grenadeLauncherModel.setEffect1Desc_eng("" +
            "Deal 1 damage to every player on a square you can see.\n" +
            "You can use this before or after the basic effect's move."
    );
    grenadeLauncherModel.setEffect1Desc_ita("" +
            "Dai 1 danno a ogni giocatore in quadrato che puoi vedere.\n" +
            "Puoi usare questo effetto prima o dopo il movimento dell'effetto base"
    );
    grenadeLauncherModel.setEffect2Name_eng("" +
            ""
    );
    grenadeLauncherModel.setEffect2Name_ita("" +
            ""
    );
    grenadeLauncherModel.setEffect2Desc_eng("" +
            ""
    );
    grenadeLauncherModel.setEffect2Desc_ita("" +
            ""
    );
    grenadeLauncherModel.setMode1Name_eng("" +
            "Effetto Base"
    );
    grenadeLauncherModel.setMode1Name_ita("" +
            "Effetto Base"
    );
    grenadeLauncherModel.setMode1Desc_eng("" +
            "Deal 1 damage to 1 target you can see.\n" +
            "Then you may move the target 1 square."
    );
    grenadeLauncherModel.setMode1Desc_ita("" +
            "Dai 1 danno a 1 bersaglio che puoi vedere.\n" +
            "Poi puoi muovere il bersaglio di 1 quadrato."
    );
    grenadeLauncherModel.setMode2Name_eng("" +
            ""
    );
    grenadeLauncherModel.setMode2Name_ita("" +
            ""
    );
    grenadeLauncherModel.setMode2Desc_eng("" +
            ""
    );
    grenadeLauncherModel.setMode2Desc_ita("" +
            ""
    );

    rocketLauncherModel.setDescription_eng("" +
            "If you use the rocket jump before the basic effect, you consider " +
            "only your new square when determining if a target is legal.\n" +
            "You can even move off a square so you can shoot someone on it.\n" +
            "If you use the fragmenting warhead, you deal damage to everyone " +
            "on the target's square before you move the target – your target " +
            "will take 3 damage total."
    );
    rocketLauncherModel.setName_eng(
            "ROCKET LAUNCHER"
    );
    rocketLauncherModel.setDescription_ita("" +
            "Se usi i razzi portatili prima dell'effetto base " +
            "considera solo il tuo nuovo quadrato per determinare se " +
            "il bersaglio è valido.\n" +
            "Puoi anche spostarti di 1 quadrato per potergli sparare.\n" +
            "Usando la testata a frammentazione dai danno a chiunque si\n" +
            "trovi nel quadrato in cui il bersaglio si trovava " +
            "prima di muoversi.\n" +
            "Il bersaglio riceve così 3 danni in totale."
    );
    rocketLauncherModel.setName_ita("" +
            "LANCIARAZZI"
    );
    rocketLauncherModel.setImg("" +
            "REPLACE"
    );
    rocketLauncherModel.setEffect1Name_eng("" +
            "With Rocket Jump"
    );
    rocketLauncherModel.setEffect1Name_ita("" +
            "Razzi Portatili"
    );
    rocketLauncherModel.setEffect1Desc_eng("" +
            "Move 1 or 2 squares. \n" +
            "This effect can be used either before or after the basic effect"
    );
    rocketLauncherModel.setEffect1Desc_ita("" +
            "Muovi di 1 o 2 quadrati.\n" +
            "Questo effetto può essere usato prima o dopo l'effetto base."
    );
    rocketLauncherModel.setEffect2Name_eng("" +
            "With Fragmenting Warhead"
    );
    rocketLauncherModel.setEffect2Name_ita("" +
            "Testata a Frammentazione"
    );
    rocketLauncherModel.setEffect2Desc_eng("" +
            "During the basic effect, deal 1 damage to every player on your " +
            "target's original square – including the target, even if you move it."
    );
    rocketLauncherModel.setEffect2Desc_ita("" +
            "Durante l'effetto base, dai 1 danno a ogni giocatore presente " +
            "nel quadrato in cui si trovava originariamente il bersaglio, " +
            "incluso il bersaglio, anche se lo hai mosso."
    );
    rocketLauncherModel.setMode1Name_eng("" +
            "Basic Effect"
    );
    rocketLauncherModel.setMode1Name_ita("" +
            "Effetto Base"
    );
    rocketLauncherModel.setMode1Desc_eng("" +
            "Deal 2 damage to 1 target you can see that is not on your square." +
            "Then you may move the target 1 square."
    );
    rocketLauncherModel.setMode1Desc_ita("" +
            "Dai 2 danni a 1 bersaglio che puoi vedere e che non si trova" +
            "nel tuo quadrato.\n" +
            "Poi puoi muovere il bersaglio di 1 quadrato."
    );
    rocketLauncherModel.setMode2Name_eng("" +
            ""
    );
    rocketLauncherModel.setMode2Name_ita("" +
            ""
    );
    rocketLauncherModel.setMode2Desc_eng("" +
            ""
    );
    rocketLauncherModel.setMode2Desc_ita("" +
            ""
    );

    hellionModel.setDescription_eng("" +
            ""
    );
    hellionModel.setName_eng(
            "HELLION"
    );
    hellionModel.setDescription_ita("" +
            ""
    );
    hellionModel.setName_ita("" +
            "RAGGIO SOLARE"
    );
    hellionModel.setImg("" +
            "REPLACE"
    );
    hellionModel.setEffect1Name_eng("" +
            "In Nano-Tracer Mode"
    );
    hellionModel.setEffect1Name_ita("" +
            "Modalità Nano-Traccianti"
    );
    hellionModel.setEffect1Desc_eng("" +
            "Deal 1 damage to 1 target you can see at least 1 move away.\n" +
            "Then give 2 marks to that target and everyone else on that square."
    );
    hellionModel.setEffect1Desc_ita("" +
            "Dai 1 danno a 1 bersaglio che puoi vedere e distante " +
            "almeno 1 movimento.\n" +
            "Poi dai 2 marchi a quel bersaglio e a chiunque " +
            "altro in quel quadrato."
    );
    hellionModel.setEffect2Name_eng("" +
            ""
    );
    hellionModel.setEffect2Name_ita("" +
            ""
    );
    hellionModel.setEffect2Desc_eng("" +
            ""
    );
    hellionModel.setEffect2Desc_ita("" +
            ""
    );
    hellionModel.setMode1Name_eng("" +
            "Basic Mode"
    );
    hellionModel.setMode1Name_ita("" +
            "Modalità Base"
    );
    hellionModel.setMode1Desc_eng("" +
            "Deal 1 damage to 1 target you can see at least 1 move away.\n" +
            "Then give 1 mark to that target and everyone else on that square."
    );
    hellionModel.setMode1Desc_ita("" +
            "Dai 1 danno a 1 bersaglio che puoi vedere e distante " +
            "almeno 1 movimento.\n" +
            "Poi dai un marchio a quel bersaglio e a chiunque " +
            "altro in quel quadrato."
    );
    hellionModel.setMode2Name_eng("" +
            ""
    );
    hellionModel.setMode2Name_ita("" +
            ""
    );
    hellionModel.setMode2Desc_eng("" +
            ""
    );
    hellionModel.setMode2Desc_ita("" +
            ""
    );

    tractorBeamModel.setDescription_eng("" +
            "You can move a target even if you can't see it.\n" +
            "The target ends up in a place where you can see and damage it.\n" +
            "The moves do not have to be in the same direction."
    );
    tractorBeamModel.setName_eng(
            "TRACTOR BEAM"
    );
    tractorBeamModel.setDescription_ita("" +
            "Puoi muovere un bersaglio anche se non puoi vederlo.\n" +
            "Il bersaglio finisce in una posizione in cui puoi vederlo e" +
            "danneggiarlo.\n" +
            "I movimenti possono non essere nella stessa direzione."
    );
    tractorBeamModel.setName_ita("" +
            "RAGGIO TRAENTE"
    );
    tractorBeamModel.setImg("" +
            "REPLACE"
    );
    tractorBeamModel.setEffect1Name_eng("" +
            "In Punisher Mode"
    );
    tractorBeamModel.setEffect1Name_ita("" +
            "Modalità Punitore"
    );
    tractorBeamModel.setEffect1Desc_eng("" +
            "Choose a target 0, 1, or 2 moves away from you.\n" +
            "Move the target to your square and deal 3 damage to it."
    );
    tractorBeamModel.setEffect1Desc_ita("" +
            "Scegli un bersaglio 0, 1, o 2 movimenti da te.\n" +
            "Muovi quel bersaglio nel quadrato in cui ti trovi e dagli 3 danni."
    );
    tractorBeamModel.setEffect2Name_eng("" +
            ""
    );
    tractorBeamModel.setEffect2Name_ita("" +
            ""
    );
    tractorBeamModel.setEffect2Desc_eng("" +
            ""
    );
    tractorBeamModel.setEffect2Desc_ita("" +
            ""
    );
    tractorBeamModel.setMode1Name_eng("" +
            "Basic Mode"
    );
    tractorBeamModel.setMode1Name_ita("" +
            "Modalità Base:"
    );
    tractorBeamModel.setMode1Desc_eng("" +
            "Move a target 0, 1, or 2 squares to a square you can see," +
            "and give it 1 damage."
    );
    tractorBeamModel.setMode1Desc_ita("" +
            "Muovi un bersaglio di 0, 1 o 2 quadrati fino a un quadrato che " +
            "puoi vedere e dagli 1 danno."
    );
    tractorBeamModel.setMode2Name_eng("" +
        ""
    );
    tractorBeamModel.setMode2Name_ita("" +
        ""
    );
    tractorBeamModel.setMode2Desc_eng("" +
            ""
    );
    tractorBeamModel.setMode2Desc_ita("" +
        ""
    );

    lockRifleModel.setDescription_eng("" +
            "No Notes"
    );
    lockRifleModel.setName_eng("" +
            "LOCK RIFLE"
    );
    lockRifleModel.setDescription_ita("" +
            "Nessuna Nota"
    );
    lockRifleModel.setName_ita("" +
            "DISTRUTTORE"
    );
    lockRifleModel.setImg("" +
            "REPLACE"
    );
    lockRifleModel.setEffect1Name_eng("" +
            "With Second Lock"
    );
    lockRifleModel.setEffect1Name_ita("" +
            "Secondo Aggancio"
    );
    lockRifleModel.setEffect1Desc_eng(
            "Deal 1 mark to a different target you can see."
    );
    lockRifleModel.setEffect1Desc_ita("" +
            "Dai 1 marchio a un altro bersaglio che puoi vedere."
    );
    lockRifleModel.setEffect2Name_eng("" +
            ""
    );
    lockRifleModel.setEffect2Name_ita("" +
            ""
    );
    lockRifleModel.setEffect2Desc_eng("" +
            ""
    );
    lockRifleModel.setEffect2Desc_ita("" +
            ""
    );
    lockRifleModel.setMode1Name_eng("" +
            "Basic Effect"
    );
    lockRifleModel.setMode1Name_ita("" +
            "Effetto Base"
    );
    lockRifleModel.setMode1Desc_eng(
            "Deal 2 damage and 1 mark to 1 target you can see."
    );
    lockRifleModel.setMode1Desc_ita("" +
            "Dai 2 danni e 1 marchio a 1 bersaglio che puoi vedere."
    );
    lockRifleModel.setMode2Name_eng("" +
            ""
    );
    lockRifleModel.setMode2Name_ita("" +
            ""
    );
    lockRifleModel.setMode2Desc_eng("" +
            ""
    );
    lockRifleModel.setMode2Desc_ita("" +
            ""
    );

    vortexCannonModel.setDescription_eng("" +
            "The 3 targets must be different, but some might start on" +
            "the same square.\n" +
            "It is legal to choose targets on your square, on the vortex, " +
            "or even on squares you can't see.\n" +
            "They all end up on the vortex."
    );
    vortexCannonModel.setName_eng(
            "VORTEX CANNON"
    );
    vortexCannonModel.setDescription_ita("" +
            "I 3 bersagli devono essere diversi, ma alcuni di loro potrebbero " +
            "partire dallo stesso quadrato (anche nel quadrato in cui " +
            "ti trovi tu).\n" +
            "Finiscono tutti sullo stesso quadrato con il vortice.\n" +
            "Non hai bisogno di vedere i bersagli.\n" +
            "Spari al quadrato che puoi vedere, crei un vortice lì e i tuoi" +
            "bersagli vi vengono risucchiati."
    );
    vortexCannonModel.setName_ita("" +
            "CANNONE VORTEX"
    );
    vortexCannonModel.setImg("" +
            "REPLACE"
    );
    vortexCannonModel.setEffect1Name_eng("" +
            "With Black Hole"
    );
    vortexCannonModel.setEffect1Name_ita("" +
            "Buco Nero"
    );
    vortexCannonModel.setEffect1Desc_eng("" +
            "Choose up to 2 other targets on the vortex or 1 move away from it.\n" +
            "Move them onto the vortex and give them each 1 damage."
    );
    vortexCannonModel.setEffect1Desc_ita("" +
            "Scegli fino ad altri 2 bersagli nel quadrato in ui si trova il" +
            "vortice o distanti 1 movimento.\n" +
            "Muovili nel quadrato in cui si trova il vortice e dai loro " +
            "1 danno ciascuno."
    );
    vortexCannonModel.setEffect2Name_eng("" +
            ""
    );
    vortexCannonModel.setEffect2Name_ita("" +
            ""
    );
    vortexCannonModel.setEffect2Desc_eng("" +
            ""
    );
    vortexCannonModel.setEffect2Desc_ita("" +
            ""
    );
    vortexCannonModel.setMode1Name_eng("" +
            "Basic Effect"
    );
    vortexCannonModel.setMode1Name_ita("" +
            "Effetto Base"
    );
    vortexCannonModel.setMode1Desc_eng("" +
            "Choose a square you can see, but not your square. \n" +
            "Call it \"the vortex\".\n" +
            "Choose a target on the vortex or 1 move away from it.\n" +
            "Move it onto the vortex and give it 2 damage."
    );
    vortexCannonModel.setMode1Desc_ita("" +
            "Scegli un quadrato che puoi vedere ad almeno 1 movimento di distanza.\n" +
            "Un vortice si apre in quel punto.\n" +
            "Scegli un bersaglio nel quadrato in cui si trova il vortice " +
            "o distante 1 movimento.\n" +
            "Muovi il bersaglio nel quadrato in cui si trova il vortice \n" +
            "e dagli 2 danni."
    );
    vortexCannonModel.setMode2Name_eng("" +
            ""
    );
    vortexCannonModel.setMode2Name_ita("" +
            ""
    );
    vortexCannonModel.setMode2Desc_eng("" +
            ""
    );
    vortexCannonModel.setMode2Desc_ita("" +
            ""
    );

    machineGunModel.setDescription_eng("" +
            "If you deal both additional points of damage, they must be " +
            "dealt to 2 different targets.\n" +
            "If you see only 2 targets, you deal 2 to each if you use" +
            "both optional effects.\n" +
            "If you use the basic effect on only 1 target, you can still use " +
            "the the turret tripod to give it 1 additional damage."
    );
    machineGunModel.setName_eng(
            "MACHINE GUN"
    );
    machineGunModel.setDescription_ita("" +
            "Se hai dato entrambi i punti aggiuntivi di danno, devono essere " +
            "dati a 2 bersagli diversi.\n" +
            "Se puoi vedere solo 2 bersagli, dai 2 danni a entrambi se usi " +
            "entrambi gli effetti opzionali.\n" +
            "Se usi l'effetto base solo su 1 bersaglio, puoi comunque usare il" +
            "tripode di supporto per dare 1 danno aggiuntivo."
    );
    machineGunModel.setName_ita("" +
            "MITRAGLIATRICE"
    );
    machineGunModel.setImg("" +
            "REPLACE"
    );
    machineGunModel.setEffect1Name_eng("" +
            "With Focus Shot"
    );
    machineGunModel.setEffect1Name_ita("" +
            "Colpo Focalizzato"
    );
    machineGunModel.setEffect1Desc_eng("" +
            "Deal 1 additional damage to one of those targets."
    );
    machineGunModel.setEffect1Desc_ita("" +
            "Dai 1 danno aggiuntivo a uno dei due bersagli."
    );
    machineGunModel.setEffect2Name_eng("" +
            "With Turret Tripod"
    );
    machineGunModel.setEffect2Name_ita("" +
            "Tripode di Supporto"
    );
    machineGunModel.setEffect2Desc_eng("" +
            "Deal 1 additional damage to the other of those targets " +
            "and/or deal 1 damage to a different target you can see"
    );
    machineGunModel.setEffect2Desc_ita("" +
            "Dai 1 danno aggiuntivo all'altro dei bersagli e/o dai 1 danno a " +
            "un bersaglio differente che puoi vedere."
    );
    machineGunModel.setMode1Name_eng("" +
            "Basic Effect"
    );
    machineGunModel.setMode1Name_ita("" +
            "Effetto Base"
    );
    machineGunModel.setMode1Desc_eng("" +
            "Choose 1 or 2 targets you can see and deal 1 damage to each."
    );
    machineGunModel.setMode1Desc_ita("" +
            "Effetto Base"
    );
    machineGunModel.setMode2Name_eng("" +
        ""
    );
    machineGunModel.setMode2Name_ita("" +
        ""
    );
    machineGunModel.setMode2Desc_eng("" +
        ""
    );
    machineGunModel.setMode2Desc_ita("" +
        ""
    );

    thorModel.setDescription_eng("" +
            "This card constrains the order in which you can use its effects." +
            "(Most cards don't.)\n" +
            "Also note that each target must be a different player."
    );
    thorModel.setName_eng("" +
            "T.H.O.R."
    );
    thorModel.setDescription_ita("" +
            "Questa carta limita l'ordine in cui puoi utilizzare i suoi" +
            "effetti. (Molte carte non lo fanno.)\n" +
            "Inoltre ogni bersaglio deve essere un giocatore differente."
    );
    thorModel.setName_ita("" +
            "TORPEDINE"
    );
    thorModel.setImg("" +
            "REPLACE"
    );
    thorModel.setEffect1Name_eng("" +
            "With Chain Reaction"
    );
    thorModel.setEffect1Name_ita("" +
            "Reazione a Catena"
    );
    thorModel.setEffect1Desc_eng("" +
            "Deal 1 damage to a second target that your first target can see."
    );
    thorModel.setEffect1Desc_ita("" +
            "Dai 1 danno a un secondo bersaglio che " +
            "il tuo primo bersaglio può vedere."
    );
    thorModel.setEffect2Name_eng("" +
            "With High Voltage"
    );
    thorModel.setEffect2Name_ita("" +
            "Alta Tensione"
    );
    thorModel.setEffect2Desc_eng("" +
            "Deal 2 damage to a third target that your second target can see." +
            "You cannot use this effect unless you first use the chain reaction."
    );
    thorModel.setEffect2Desc_ita("" +
            "Dai 2 danni a un terzo bersaglio che il tuo secondo " +
            "bersaglio può vedere.\n" +
            "Non puoi usare questo effetto se prima non hai usato " +
            "reazione a catena."
    );
    thorModel.setMode1Name_eng("" +
            "Basic Effect"
    );
    thorModel.setMode1Name_ita("" +
            "Effetto Base"
    );
    thorModel.setMode1Desc_eng("" +
            "Deal 2 damage to 1 target you can see."
    );
    thorModel.setMode1Desc_ita("" +
            "Dai 2 danni a 1 bersaglio che puoi vedere."
    );
    thorModel.setMode2Name_eng("" +
            ""
    );
    thorModel.setMode2Name_ita("" +
            ""
    );
    thorModel.setMode2Desc_eng("" +
            ""
    );
    thorModel.setMode2Desc_ita("" +
            ""
    );

    heatSeekerModel.setDescription_eng("" +
            "Yes, this can only hit targets you cannot see."
    );
    heatSeekerModel.setName_eng("" +
            "HEATSEEKER"
    );
    heatSeekerModel.setDescription_ita("" +
            "Si, può colpire solo bersagli che non puoi vedere"
    );
    heatSeekerModel.setName_ita("" +
            "RAZZO TERMICO"
    );
    heatSeekerModel.setImg("" +
            "REPLACE"
    );
    heatSeekerModel.setEffect1Name_eng("" +
            ""
    );
    heatSeekerModel.setEffect1Name_ita("" +
            ""
    );
    heatSeekerModel.setEffect1Desc_eng("" +
            ""
    );
    heatSeekerModel.setEffect1Desc_ita("" +
            ""
    );
    heatSeekerModel.setEffect2Name_eng("" +
            "REPLACE"
    );
    heatSeekerModel.setEffect2Name_ita("" +
            ""
    );
    heatSeekerModel.setEffect2Desc_eng("" +
            ""
    );
    heatSeekerModel.setEffect2Desc_ita("" +
            ""
    );
    heatSeekerModel.setMode1Name_eng("" +
            "Effect"
    );
    heatSeekerModel.setMode1Name_ita("" +
            "Effetto"
    );
    heatSeekerModel.setMode1Desc_eng("" +
            "Choose 1 target you cannot see and deal 3 damage to it."
    );
    heatSeekerModel.setMode1Desc_ita("" +
            "Scegli 1 bersaglio che non puoi vedere e dagli 3 danni."
    );
    heatSeekerModel.setMode2Name_eng("" +
            ""
    );
    heatSeekerModel.setMode2Name_ita("" +
            ""
    );
    heatSeekerModel.setMode2Desc_eng("" +
            ""
    );
    heatSeekerModel.setMode2Desc_ita("" +
            ""
    );

    whisperModel.setDescription_eng("" +
            "For example, in the 2-by-2 room, you cannot shoot a target on an " +
            "adjacent square, but you can shoot a target on the diagonal.\n" +
            "If you are beside a door, you can't shoot a target on the other" +
            "side of the door, but you can shoot a target on a different " +
            "square of that room."
    );
    whisperModel.setName_eng("" +
            "WHISPER"
    );
    whisperModel.setDescription_ita("" +
            "Per esempio, nella stanza 2x2, non puoi sparare a un bersaglio " +
            "in un quadrato adiacente, ma puoi sparare a un bersaglio lungo" +
            "la diagonale.\n" +
            "Se sei vicino a una porta non puoi sparare a un bersaglio " +
            "dall'altra parte della porta, ma puoi sparare a un bersaglio su " +
            "un quadrato diverso in quella stanza."
    );
    whisperModel.setName_ita("" +
            "FUCILE DI PRECISIONE"
    );
    whisperModel.setImg("" +
            "REPLACE"
    );
    whisperModel.setEffect1Name_eng("" +
            "REPLACE"
    );
    whisperModel.setEffect1Name_ita("" +
            ""
    );
    whisperModel.setEffect1Desc_eng("" +
            ""
    );
    whisperModel.setEffect1Desc_ita("" +
            ""
    );
    whisperModel.setEffect2Name_eng("" +
            ""
    );
    whisperModel.setEffect2Name_ita("" +
            ""
    );
    whisperModel.setEffect2Desc_eng("" +
            ""
    );
    whisperModel.setEffect2Desc_ita("" +
            ""
    );
    whisperModel.setMode1Name_eng("" +
            "Effect"
    );
    whisperModel.setMode1Name_ita("" +
            "Effetto"
    );
    whisperModel.setMode1Desc_eng("" +
            "Deal 3 damage and 1 mark to 1 target you can see.\n" +
            "Your target must be at least 2 moves away from you."
    );
    whisperModel.setMode1Desc_ita("" +
            "Dai 3 danni e 1 marchio a 1 bersaglio che puoi vedere.\n" +
            "Il bersaglio deve essere ad almeno 2 movimenti da te."
    );
    whisperModel.setMode2Name_eng("" +
            ""
    );
    whisperModel.setMode2Name_ita("" +
            ""
    );
    whisperModel.setMode2Desc_eng("" +
            ""
    );
    whisperModel.setMode2Desc_ita("" +
            ""
    );

    furnaceModel.setDescription_eng("" +
            ""
    );
    furnaceModel.setName_eng("" +
            "FURNACE"
    );
    furnaceModel.setDescription_ita("" +
            ""
    );
    furnaceModel.setName_ita("" +
            "VULCANIZZATORE"
    );
    furnaceModel.setImg("" +
            "REPLACE"
    );
    furnaceModel.setEffect1Name_eng("" +
            "In Cozy Fire Mode"
    );
    furnaceModel.setEffect1Name_ita("" +
            "Modalità Fuoco Confortevole"
    );
    furnaceModel.setEffect1Desc_eng("" +
            "Choose a square exactly one move away.\n" +
            "Deal 1 damage and 1 mark to everyone on that square."
    );
    furnaceModel.setEffect1Desc_ita("" +
            "Scegli un quadrato distantee sattamente 1 movimento.\n" +
            "Dai 1 danno e 1 marchio a ognuno in quel quadrato."
    );
    furnaceModel.setEffect2Name_eng("" +
            ""
    );
    furnaceModel.setEffect2Name_ita("" +
            ""
    );
    furnaceModel.setEffect2Desc_eng("" +
            ""
    );
    furnaceModel.setEffect2Desc_ita("" +
            ""
    );
    furnaceModel.setMode1Name_eng("" +
            "Basic Mode"
    );
    furnaceModel.setMode1Name_ita("" +
            "Modalità Base"
    );
    furnaceModel.setMode1Desc_eng("" +
            "Choose a room you can see, but not the room you are in.\n" +
            "Deal 1 damage to everyone in that room."
    );
    furnaceModel.setMode1Desc_ita("" +
            "Scegli una stanza che puoi vedere, ma non la stanza in cui ti trovi.\n" +
            "Dai 1 danno a ognuno in quella stanza."
    );
    furnaceModel.setMode2Name_eng("" +
            ""
    );
    furnaceModel.setMode2Name_ita("" +
            ""
    );
    furnaceModel.setMode2Desc_eng("" +
            ""
    );
    furnaceModel.setMode2Desc_ita("" +
            ""
    );

    railGunModel.setDescription_eng("" +
            "Basically, you're shooting in a straight line " +
            "and ignoring walls.\n" +
            "You don't have to pick a target on the other side of a wall " +
            "– it could even be someone on your own square – " +
            "but shooting through walls sure is fun.\n" +
            "There are only 4 cardinal directions.\n" +
            "You imagine facing one wall or door, square-on, and firing\n" +
            "in that direction.\n" +
            "Anyone on a square in that direction (including yours) " +
            "is a valid target.\n" +
            "In piercing mode, the 2 targets can be on the same square " +
            "or on different squares."
    );
    railGunModel.setName_eng("" +
            "RAILGUN"
    );
    railGunModel.setDescription_ita("" +
            "In pratica spari in linea retta ignorando i muri.\n" +
            "Non hai bisogno di scegliere un bersaglio dall'altro lato " +
            "del muro, potrebbe anche essere qualcuno nel tuo stesso quadrato," +
            "ma sparare attraverso i muri è sicuramente divertente.\n" +
            "Ci sono solo 4 direzioni cardinali.\n" +
            "Immagina di essere di fronte a un muro o una porta, imbracciare" +
            "il fucile e sparare in quella direzione.\n" +
            "Chiunque si trovi su un quadrato in quella direzione (incluso " +
            "il tuo) è un bersaglio valido.\n" +
            "In modalità perforazione i 2 bersagli possono essere nello " +
            "stesso quadrato o in quadrati diversi."
    );
    railGunModel.setName_ita("" +
            "FUCILE LASER"
    );
    railGunModel.setImg("" +
            ""
    );
    railGunModel.setEffect1Name_eng("" +
            ""
    );
    railGunModel.setEffect1Name_ita("" +
            ""
    );
    railGunModel.setEffect1Desc_eng("" +
            ""
    );
    railGunModel.setEffect1Desc_ita("" +
            ""
    );
    railGunModel.setEffect2Name_eng("" +
            ""
    );
    railGunModel.setEffect2Name_ita("" +
            ""
    );
    railGunModel.setEffect2Desc_eng("" +
            ""
    );
    railGunModel.setEffect2Desc_ita("" +
            ""
    );
    railGunModel.setMode1Name_eng("" +
            "Basic Mode"
    );
    railGunModel.setMode1Name_ita("" +
            "Modalità Base"
    );
    railGunModel.setMode1Desc_eng("" +
            "Choose a cardinal direction and 1 target in that direction.\n" +
            "Deal 3 damage to it."
    );
    railGunModel.setMode1Desc_ita("" +
            "Scegli una direzione cardinale e 1 bersaglio in quella direzione.\n" +
            "Dagli 3 danni."
    );
    railGunModel.setMode2Name_eng("" +
            "In Piercing Mode"
    );
    railGunModel.setMode2Name_ita("" +
            "Modalità Perforazione"
    );
    railGunModel.setMode2Desc_eng("" +
            "Choose a cardinal direction and 1 or 2 targets in that direction.\n" +
            "Deal 2 damage to each."
    );
    railGunModel.setMode2Desc_ita("" +
            "Scegli una direzione cardinale e 1 o 2 bersagli " +
            "in quella direzione.\n" +
            "Dai 2 danni a ciascuno."
    );

    shotgunModel.setDescription_eng("" +
            ""
    );
    shotgunModel.setName_eng("" +
            "SHOTGUN"
    );
    shotgunModel.setDescription_ita("" +
            ""
    );
    shotgunModel.setName_ita("" +
            "FUCILE A POMPA"
    );
    shotgunModel.setImg("" +
            "REPLACE"
    );
    shotgunModel.setEffect1Name_eng("" +
            "In Long Barrel Mode"
    );
    shotgunModel.setEffect1Name_ita("" +
            "Modalità Canna Lunga"
    );
    shotgunModel.setEffect1Desc_eng("" +
            "Deal 2 damage to 1 target on any square exactly one move away."
    );
    shotgunModel.setEffect1Desc_ita("" +
            "Dai 2 danni a 1 bersaglio in un quadrato distante" +
            "esattamente 1 movimento."
    );
    shotgunModel.setEffect2Name_eng("" +
            ""
    );
    shotgunModel.setEffect2Name_ita("" +
            ""
    );
    shotgunModel.setEffect2Desc_eng("" +
            ""
    );
    shotgunModel.setEffect2Desc_ita("" +
            ""
    );
    shotgunModel.setMode1Name_eng("" +
            "Basic Mode"
    );
    shotgunModel.setMode1Name_ita("" +
            "Modalità Base"
    );
    shotgunModel.setMode1Desc_eng("" +
            "Deal 3 damage to 1 target on your square.\n" +
            "If you want, you may then move the target 1 square."
    );
    shotgunModel.setMode1Desc_ita("" +
            "Dai 3 danni a 1 bersaglio nel quadrato in cui ti trovi.\n" +
            "Se vuoi puoi muovere quel bersaglio di 1 quadrato."
    );
    shotgunModel.setMode2Name_eng("" +
            ""
    );
    shotgunModel.setMode2Name_ita("" +
            ""
    );
    shotgunModel.setMode2Desc_eng("" +
            ""
    );
    shotgunModel.setMode2Desc_ita("" +
            ""
    );

    zX2Model.setDescription_eng("" +
            "Remember that the 3 targets can be in 3 different rooms."
    );
    zX2Model.setName_eng("" +
            "ZX-2"
    );
    zX2Model.setDescription_ita("" +
            "Ricorda che i 3 bersagli possono anche\n" +
            "essere in 3 stanze diverse."
    );
    zX2Model.setName_ita("" +
            "ZX-2"
    );
    zX2Model.setImg("" +
            ""
    );
    zX2Model.setEffect1Name_eng("" +
            ""
    );
    zX2Model.setEffect1Name_ita("" +
            ""
    );
    zX2Model.setEffect1Desc_eng("" +
            ""
    );
    zX2Model.setEffect1Desc_ita("" +
            ""
    );
    zX2Model.setEffect2Name_eng("" +
            ""
    );
    zX2Model.setEffect2Name_ita("" +
            ""
    );
    zX2Model.setEffect2Desc_eng("" +
            ""
    );
    zX2Model.setEffect2Desc_ita("" +
            ""
    );
    zX2Model.setMode1Name_eng("" +
            "Basic Mode"
    );
    zX2Model.setMode1Name_ita("" +
            "Modalità Base"
    );
    zX2Model.setMode1Desc_eng("" +
            "Deal 1 damage and 2 marks to 1 target you can see"
    );
    zX2Model.setMode1Desc_ita("" +
            "Dai 1 danno e 2 marchi a 1 bersaglio che puoi vedere"
    );
    zX2Model.setMode2Name_eng("" +
            "In Scanner Mode"
    );
    zX2Model.setMode2Name_ita("" +
            "Modalità Scanner"
    );
    zX2Model.setMode2Desc_eng("" +
            "Choose up to 3 targets you can see and deal 1 mark to each."
    );
    zX2Model.setMode2Desc_ita("" +
            "Scegli fino a 3 bersagli che puoi vedere " +
            "e dai 1 marchio a ciascuno."
    );

    flameThrowerModel.setDescription_eng("" +
            "This weapon cannot damage anyone in your square.\n" +
            "However, it can sometimes damage a target you can't see –" +
            "the flame won't go through walls, but it will go through doors.\n" +
            "Think of it as a straight-line blast of flame that can travel " +
            "2 squares in a cardinal direction."
    );
    flameThrowerModel.setName_eng("" +
            "FLAMETHROWER"
    );
    flameThrowerModel.setDescription_ita("" +
            "Quest'arma non può danneggiare nessuno nel tuo quadrato.\n" +
            "Tuttavia può a volte danneggiare un bersaglio che non vedi," +
            "le fiamme non passano attraverso i muri ma passano " +
            "attraverso le porte.\n" +
            "Immaginatelo come una esplosione di fuoco in linea" +
            "retta che si muove di 2 quadrati in una direzione cardinale."
    );
    flameThrowerModel.setName_ita("" +
            "LANCIAFIAMME"
    );
    flameThrowerModel.setImg("" +
            ""
    );
    flameThrowerModel.setEffect1Name_eng("" +
            ""
    );
    flameThrowerModel.setEffect1Name_ita("" +
            ""
    );
    flameThrowerModel.setEffect1Desc_eng("" +
            ""
    );
    flameThrowerModel.setEffect1Desc_ita("" +
            ""
    );
    flameThrowerModel.setEffect2Name_eng("" +
            ""
    );
    flameThrowerModel.setEffect2Name_ita("" +
            ""
    );
    flameThrowerModel.setEffect2Desc_eng("" +
            ""
    );
    flameThrowerModel.setEffect2Desc_ita("" +
            ""
    );
    flameThrowerModel.setMode1Name_eng("" +
            "Basic Mode"
    );
    flameThrowerModel.setMode1Name_ita("" +
            "Modalità Base"
    );
    flameThrowerModel.setMode1Desc_eng("" +
            "Choose a square 1 move away and possibly a second square" +
            "1 more move away in the same direction.\n" +
            "On each square, you may choose 1 target and give it 1 damage."
    );
    flameThrowerModel.setMode1Desc_ita("" +
            "Scegli un quadrato distante 1 movimento e possibilmente un secondo " +
            "quadrato distante ancora 1 movimento nella stessa direzione.\n" +
            "In ogni quadrato puoi scegliere 1 bersaglio e dargli 1 danno."
    );
    flameThrowerModel.setMode2Name_eng("" +
            "In Barbecue Mode"
    );
    flameThrowerModel.setMode2Name_ita("" +
            "Modalità Barbecue"
    );
    flameThrowerModel.setMode2Desc_eng("" +
            "Choose 2 squares as above.\n" +
            "Deal 2 damage to everyone on the first square and 1 damage " +
            "to everyone on the second square."
    );
    flameThrowerModel.setMode2Desc_ita("" +
            "Scegli 2 quadrati come prima. \n" +
            "Dai 2 danni a chiunque sia nel primo quadrato e 1 danno a " +
            "chiunque si trovi nel secondo quadrato."
    );

    powerGloveModel.setDescription_eng("" +
            "In rocket fist mode, you're flying 2 squares in a straight line," +
            "punching 1 person per square."
    );
    powerGloveModel.setName_eng("" +
            "POWER GLOVE"
    );
    powerGloveModel.setDescription_ita("" +
            "Nella modalità cento pugni stai volando per 2 quadrati in " +
            "linea retta, prendendo a pugni 1 persona per quadrato."
    );
    powerGloveModel.setName_ita("" +
            "CYBERGUANTO"
    );
    powerGloveModel.setImg("" +
            "REPLACE"
    );
    powerGloveModel.setEffect1Name_eng("" +
            ""
    );
    powerGloveModel.setEffect1Name_ita("" +
            ""
    );
    powerGloveModel.setEffect1Desc_eng("" +
            ""
    );
    powerGloveModel.setEffect1Desc_ita("" +
            ""
    );
    powerGloveModel.setEffect2Name_eng("" +
            ""
    );
    powerGloveModel.setEffect2Name_ita("" +
            ""
    );
    powerGloveModel.setEffect2Desc_eng("" +
            ""
    );
    powerGloveModel.setEffect2Desc_ita("" +
            ""
    );
    powerGloveModel.setMode1Name_eng("" +
            "Basic Mode"
    );
    powerGloveModel.setMode1Name_ita("" +
            "Modalità Base"
    );
    powerGloveModel.setMode1Desc_eng("" +
            "Choose 1 target on any square exactly 1 move away.\n" +
            "Move onto that square and give the target 1 damage and 2 marks."
    );
    powerGloveModel.setMode1Desc_ita("" +
            "Modalità Base"
    );
    powerGloveModel.setMode2Name_eng("" +
            "In Rocket Fist Mode"
    );
    powerGloveModel.setMode2Name_ita("" +
            "Modalità Cento Pugni"
    );
    powerGloveModel.setMode2Desc_eng("" +
            "Choose a square exactly 1 move away.\n" +
            "Move onto that square.\n" +
            "You may deal 2 damage to 1 target there.\n" +
            "If you want, you may move 1 more square in that same direction" +
            " (but only if it is a legalmove).\n" +
            "You may deal 2 damage to 1 target there, as well."
    );
    powerGloveModel.setMode2Desc_ita("" +
            "Scegli un quadrato distante esattamente 1 movimento.\n" +
            "Muovi in quel quadrato.\n" +
            "Puoi dare 2 danni a 1 bersaglio in quel quadrato.\n" +
            "Se vuoi puoi muovere ancora di 1 quadrato nella stessa direzione" +
            "(ma solo se è un movimento valido).\n" +
            "Puoi dare 2 danni a un bersaglio anche in quel quadrato."
    );

    shockwaveModel.setDescription_eng("" +
            ""
    );
    shockwaveModel.setName_eng("" +
            "SHOCKWAVE"
    );
    shockwaveModel.setDescription_ita("" +
            ""
    );
    shockwaveModel.setName_ita("" +
            "ONDA D'URTO"
    );
    shockwaveModel.setImg("" +
            "REPLACE"
    );
    shockwaveModel.setEffect1Name_eng("" +
            ""
    );
    shockwaveModel.setEffect1Name_ita("" +
            ""
    );
    shockwaveModel.setEffect1Desc_eng("" +
            ""
    );
    shockwaveModel.setEffect1Desc_ita("" +
            ""
    );
    shockwaveModel.setEffect2Name_eng("" +
            ""
    );
    shockwaveModel.setEffect2Name_ita("" +
            ""
    );
    shockwaveModel.setEffect2Desc_eng("" +
            ""
    );
    shockwaveModel.setEffect2Desc_ita("" +
            ""
    );
    shockwaveModel.setMode1Name_eng("" +
            "Basic Mode"
    );
    shockwaveModel.setMode1Name_ita("" +
            "Modalità Base"
    );
    shockwaveModel.setMode1Desc_eng("" +
            "Choose up to 3 targets on different squares, " +
            "each exactly 1 move away.\n" +
            "Deal 1 damage to each target."
    );
    shockwaveModel.setMode1Desc_ita("" +
            "Scegli fino a 3 bersagli su quadrati differenti, ognuno distante" +
            "esattamente 1 movimento.\n" +
            "Dai 1 danno a ogni bersaglio."
    );
    shockwaveModel.setMode2Name_eng("" +
            "In Tsunami Mode"
    );
    shockwaveModel.setMode2Name_ita("" +
            "Modalità Tsunami"
    );
    shockwaveModel.setMode2Desc_eng("" +
            "Deal 1 damage to all targets that are exactly 1 move away."
    );
    shockwaveModel.setMode2Desc_ita("" +
            "Dai 1 danno a tutti i bersagli che sono distanti " +
            "esattamente 1 movimento."
    );

    sledgeHammerModel.setDescription_eng("" +
            "Remember that moves go through doors, but not walls."
    );
    sledgeHammerModel.setName_eng("" +
            "SLEDGEHAMMER"
    );
    sledgeHammerModel.setDescription_ita("" +
            "Ricordati che i movimenti passano attraverso le porte " +
            "ma non attraverso le pareti."
    );
    sledgeHammerModel.setName_ita("" +
            "MARTELLO IONICO"
    );
    sledgeHammerModel.setImg("" +
            "REPLACE"
    );
    sledgeHammerModel.setEffect1Name_eng("" +
            ""
    );
    sledgeHammerModel.setEffect1Name_ita("" +
            ""
    );
    sledgeHammerModel.setEffect1Desc_eng("" +
            ""
    );
    sledgeHammerModel.setEffect1Desc_ita("" +
            ""
    );
    sledgeHammerModel.setEffect2Name_eng("" +
            ""
    );
    sledgeHammerModel.setEffect2Name_ita("" +
            ""
    );
    sledgeHammerModel.setEffect2Desc_eng("" +
            ""
    );
    sledgeHammerModel.setEffect2Desc_ita("" +
            ""
    );
    sledgeHammerModel.setMode1Name_eng("" +
            "Basic Mode"
    );
    sledgeHammerModel.setMode1Name_ita("" +
            "Modalità Base"
    );
    sledgeHammerModel.setMode1Desc_eng("" +
            "Deal 2 damage to 1 target on your square."
    );
    sledgeHammerModel.setMode1Desc_ita("" +
            "Dai 2 danni a 1 bersaglio nel quadrato in cui ti trovi."
    );
    sledgeHammerModel.setMode2Name_eng("" +
            "In Pulverize Mode"
    );
    sledgeHammerModel.setMode2Name_ita("" +
            "Modalità Polverizzare"
    );
    sledgeHammerModel.setMode2Desc_eng("" +
            "Deal 3 damage to 1 target on your square, then move that target" +
            "0, 1, or 2 squares in one direction."
    );
    sledgeHammerModel.setMode2Desc_ita("" +
            "Dai 3 danni a 1 bersaglio nel quadrato in cui ti trovi, " +
            "poi muovi quel bersaglio di 0, 1 o 2 quadrati in una direzione."
    );

    cyberBladeModel.setController(new CyberBladeController());
    electroscytheModel.setController(new ElectroscytheController());
    plasmaGunModel.setController(new PlasmaGunController());
    grenadeLauncherModel.setController(new GrenadeLauncherController());
    rocketLauncherModel.setController(new RocketLauncherController());
    hellionModel.setController(new HellionController());
    tractorBeamModel.setController(new TractorBeamController());
    lockRifleModel.setController(new LockRifleController());
    vortexCannonModel.setController(new VortexCannonController());
    machineGunModel.setController(new MachineGunController());
    thorModel.setController(new ThorController());
    heatSeekerModel.setController(new HeatSeekerController());
    whisperModel.setController(new WhisperController());
    furnaceModel.setController(new FurnaceController());
    railGunModel.setController(new RailGunController());
    shotgunModel.setController(new ShotgunController());
    zX2Model.setController(new ZX2Controller());
    flameThrowerModel.setController(new FlameThrowerController());
    powerGloveModel.setController(new PowerGloveController());
    shockwaveModel.setController(new ShockwaveController());
    sledgeHammerModel.setController(new SledgeHammerController());
  }

  /**
   * This method is the main game loop, it makes players do actions on their
   * turn, replaces resources that have been picked up during a turn and
   * resolves player deaths.
   */
  public void playTurns() {
  }

  /**
   * This method starts the game by generating a map, initializing players
   * and starting the main game loop
   */
  public void startGame() {
  }

  /**
   * This method manages the final frenzy round which is played when the
   * number of skulls on the scoreboard reaches 0 after this final round the
   * scoreboard will be resolved and the game will end
   */
  public void playFrenzyTurn() {
  }
}
