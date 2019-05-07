package it.polimi.se2019.view.map;

import it.polimi.se2019.model.map.Map;

public class ViewData {

    private Map gameMap;

    public ViewData(String mapType){
        gameMap = new Map(mapType);
    }

    public Map getGameMap(){
        return gameMap;
    }
}
