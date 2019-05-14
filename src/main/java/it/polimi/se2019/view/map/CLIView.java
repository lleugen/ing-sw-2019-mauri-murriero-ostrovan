package it.polimi.se2019.view.map;

import it.polimi.se2019.model.grabbable.AmmoTile;
import it.polimi.se2019.model.grabbable.Grabbable;
import it.polimi.se2019.model.grabbable.Weapon;
import it.polimi.se2019.model.map.AmmoSquare;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.SpawnSquare;
import it.polimi.se2019.model.map.Square;

public class CLIView implements RemoteView {

    private ViewData data;

    public CLIView(){
        //TO DO: parameter is what returns server when the game starts
        data = new ViewData("1");
    }

    @Override
    public void ShowMap() {
        Map gameMap = data.getGameMap();
        Square[][] mapSquares = gameMap.getMapSquares();
        String mapString = "";
        char firstChar = 'A';
        int squaresCounter = 0;

        switch (gameMap.getMapType()){
            case 0:
                mapString =  "Game map: (» = doors)";
                mapString += "╔===========╗    \n";
                mapString += "║ A   B   C ║    \n";
                mapString += "╠ » ===== » ╬===╗\n";
                mapString += "║ D   E   F » G ║\n";
                mapString += "╚===╦ » ====╣   ║\n";
                mapString += "    ║ H   I » J ║\n";
                mapString += "    ╚=======╩===╝\n";
                break;
            case 1:
                mapString =  "Game map: (» = doors)";
                mapString += "╔===========╦===╗\n";
                mapString += "║ A   B   C » D ║\n";
                mapString += "╠ » ====╦ » ╩ » ╣\n";
                mapString += "║ E   F ║ G   H ║\n";
                mapString += "╚===╦ » ╣       ║\n";
                mapString += "    ║ I » J   K ║\n";
                mapString += "    ╚===╩=======╝\n";
                break;
            case 2:
                mapString =  "Game map: (» = doors)";
                mapString += "╔===╦=======╦===╗\n";
                mapString += "║ A » B   C » D ║\n";
                mapString += "║   ╠ » ╦ » ╩ » ╣\n";
                mapString += "║ E ║ F ║ G   H ║\n";
                mapString += "╠ » ╩ » ╣       ║\n";
                mapString += "║ I   J » K   L ║\n";
                mapString += "╚=======╩=======╝\n";
                break;
            case 3:
                mapString =  "Game map: (» = doors)";
                mapString += "╔===╦=======╗    \n";
                mapString += "║ A » B   C ║    \n";
                mapString += "║   ╠ » = » ╬===╗\n";
                mapString += "║ D ║ E   F » G ║\n";
                mapString += "╠ » ╩ » ====╣   ║\n";
                mapString += "║ H   I   J » K ║\n";
                mapString += "╚===========╩===╝\n";
                break;
        }
        mapString += "\n\n";
        for(int i = 0; i < mapSquares.length; i++){
            for(int j = 0; j < mapSquares[0].length; j++){
                mapString += (char)(firstChar+squaresCounter) + ": ";

                if(mapSquares[i][j].isSpawnPoint()){
                    mapString += "SPAWN | Color: " + mapSquares[i][j].getIdRoom() + "Weapons: ";
                    for(Weapon s : ((SpawnSquare)mapSquares[i][j]).getWeaponList()){
                        if(s != null)
                            mapString += s.toString() + " -";
                        else
                            mapString += " !EMPTY! -";
                    }
                }else{
                    mapString += "AMMO | RoomID: " + mapSquares[i][j].getIdRoom() +" present ammos: ";
                    AmmoTile ammoTile = (AmmoTile)((AmmoSquare)mapSquares[i][j]).getItem();
                    if(ammoTile != null) {
                        mapString += ammoTile.getAmmo().getRed() + "x RED, " + ammoTile.getAmmo().getYellow() + "x YELLOW, " + ammoTile.getAmmo().getBlue() + "x BLUE";
                        if (ammoTile.getPowerUp())
                            mapString += ", 1 PowerUp";
                    }else{
                        mapString += "!EMPTY!";
                    }
                }
                squaresCounter++;
            }
        }


    }

    @Override
    public void ShowPlayers() {

    }

    @Override
    public void ShowInventory() {

    }
}
