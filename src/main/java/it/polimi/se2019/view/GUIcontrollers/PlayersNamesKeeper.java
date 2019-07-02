package it.polimi.se2019.view.GUIcontrollers;

import java.util.ArrayList;
import java.util.List;

public class PlayersNamesKeeper {

    public class PlayerNamesData{
        private String name, character, folder;

        public PlayerNamesData(String name, String character){
            this.name = name; this.character = character; this.folder = "char" + GUILogin.indexOfCharacter(character);
        }

        public String getName(){
            return name;
        }

        public String getCharacter(){
            return character;
        }

        public String getFolder(){
            return folder;
        }
    }

    private List<PlayerNamesData> players;

    public PlayersNamesKeeper(){
        players = new ArrayList<>();
    }

    public void addPlayer(String name, String character){
        players.add(new PlayerNamesData(name, character));
    }

    public PlayerNamesData find(String playerName){
        for(PlayerNamesData p : players)
            if(p.getName().equals(playerName))
                return p;
        return null;
    }

    public String findCharacter(String playerName){
        for(PlayerNamesData p : players)
            if(p.getName().equals(playerName))
                return p.getCharacter();
            return null;
    }

    public String findFolder(String playerName){
        for(PlayerNamesData p : players)
            if(p.getName().equals(playerName))
                return p.getFolder();
        return null;
    }

    public int findIndex(String playerName){
        for(int i = 0; i < players.size(); i++)
            if(players.get(i).getName().equals(playerName))
                return i;
        return 0;
    }
}
