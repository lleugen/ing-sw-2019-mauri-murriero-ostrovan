package it.polimi.se2019.view.map;

import it.polimi.se2019.model.map.Map;

public class CLIView implements RemoteView {

    private ViewData data;

    public CLIView(){
        //TO DO: parameter is what returns server when the game starts
        data = new ViewData("1");
    }

    @Override
    public void ShowMap() {
        Map gameMap = data.getGameMap();
    }

    @Override
    public void ShowPlayers() {

    }

    @Override
    public void ShowInventory() {

    }
}
