package it.polimi.se2019.view;

/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

//package it.polimi.se2019.engine.io;

import it.polimi.se2019.RMI.ViewFacadeInterfaceRMIClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

public final class CliIO{
    /**
     * Namespace this class logs to
     */
    private static final String LOG_NAMESPACE = "CliIO";

    /**
     * Contains data to print to screen on render
     */
    private StringBuilder pendingUpdate = new StringBuilder();

    /**
     * Contains commands to print to screen on render
     */
    private StringBuilder pendingCommands = new StringBuilder();

    /**
     * Add a new function that can be called
     *
     * @param ns     Namespace the call belongs to
     * @param f      Name of the function to call
     * @param params Params of the call
     * @param desc   Description of the function
     */
    private void addCommand(String ns, String f, String params, String desc) {
        this.pendingCommands.append("?) ");
        this.pendingCommands.append(ns);
        this.pendingCommands.append(" ");
        this.pendingCommands.append(f);
        this.pendingCommands.append(" ");
        this.pendingCommands.append(params);
        this.pendingCommands.append(" -> ");
        this.pendingCommands.append(desc);
        this.pendingCommands.append("\n");
    }

    /**
     * Quando ho finito di pensare la chiamo e stampo a schermo
     */
    private void displayRender() {
        //System.console().writer().println("\033[H\033[2J");

        System.console().writer().write(this.pendingUpdate.toString());
        System.console().writer().write("\n");
        System.console().writer().write(this.pendingCommands.toString());
        System.console().writer().write("> ");

        this.pendingCommands.delete(0, this.pendingCommands.length());
        this.pendingUpdate.delete(0, this.pendingUpdate.length());

        System.console().writer().flush();
    }

    /**
     * Stampo una riga
     *
     * @param data Riga da stampare
     */
    private synchronized void writeLn(String data){
        this.pendingUpdate.append(data);
        this.pendingUpdate.append("\n");
    }

    /**
     * Stampo un titolo di una sezione
     *
     * @param title Title of the section
     */
    private void markSection(String title) {
        this.pendingUpdate.append("_");
        for (int i = 0; i < (title.length()); i++){
            this.pendingUpdate.append("_");
        }
        this.pendingUpdate.append("_");

        this.pendingUpdate.append("\n");

        this.pendingUpdate.append("|");
        this.pendingUpdate.append(title);
        this.pendingUpdate.append("|");

        this.pendingUpdate.append("\n");

        this.pendingUpdate.append("|");
        for (int i = 0; i < (title.length()); i++){
            this.pendingUpdate.append("_");
        }
        this.pendingUpdate.append("|");

        this.pendingUpdate.append("\n");
    }

    /**
     * Recupero una riga dal terminale
     */
    private static String readLine() {
        StringBuilder sb = new StringBuilder();
        int c;

        try {
            while ((c = System.in.read()) != '\n') {
                sb.append((char) c);
            }
        }
        catch (IOException e){
            Logger.getLogger(LOG_NAMESPACE).log(
                    Level.SEVERE,
                    "Disconnected from terminal!",
                    e
            );
            System.console().writer().write("\n\n\n[!!!]Please restart the app\n\n");
        }

        return sb.toString();
    }

    /**
     * Esempio
     */
    public CliIO(){
        this.loop();
    }
    List<ArrayList<ArrayList<String>>> mapInfo = new ArrayList<ArrayList<ArrayList<String>>>();
    private void loop() {
        while (true) {
            /*
            // Leggo
            String input = readLine();
            // Stampo il titolo della sezione
            this.markSection("Hai Inserito");
            // Stampo una riga
            this.writeLn(input);
            // Stampo un altra riga
            this.writeLn("Ti chiederemo altra roba da ristampare ;)");
            // Serve solo per farti vedere quando effettivamente stampa, poi elimina questa riga
            System.out.println("Press ENTER");
            //System.console().readLine();
            // Stampo effettivamente a schermo
            this.displayRender();
            */


            for(int i = 0; i<3; i++){
                mapInfo.add(new ArrayList<ArrayList<String>>());
                for(int k = 0; k<4; k++){
                    mapInfo.get(i).add(new ArrayList<String>());
                }
            }

            for(int i = 0; i<3; i++){
                for(int k = 0; k<4; k++){
                    mapInfo.get(i).get(k).add(0, "room "+i+k);
                }
            }
            /*
            for(int i = 0; i<3; i++){
              for(int k = 0; k<4; k++){
                System.out.println(mapInfo.get(i).get(k).get(0));
              }
            }
            */


            String name;
            inputName();
            String action;
            chooseAction("NormalState");

            List<String> powerUps = new ArrayList<>();
            powerUps.add("NewtonBlue");
            powerUps.add("TargetingScopeRed");

            chooseSpawnLocation(powerUps);
            int map = chooseMap();
            int numberOfPlayers = chooseNumberOfPlayers();
            List<String> weapons = new ArrayList<>();
            weapons.add("electroshish");
            weapons.add("heat seeker");
            String weapon = chooseWeapon(weapons);
            List<String> targets = new ArrayList<>();
            targets.add("noob_master_69");
            targets.add("fatThor");
            String target = chooseTargets(targets);
            String reloadThis = chooseWeaponToReload(weapons);

            List<Integer> cards = choosePowerUpCardsForReload(powerUps);

            List<String> effects = new ArrayList<>();
            effects.add("tactical nuke");
            effects.add("barbecue");
            Integer index = chooseIndex(effects);
            Integer item = chooseItemToGrab();
            Boolean firingMode = chooseFiringMode("this is the description");
            Boolean chacha = chooseBoolean("Is this the best game ever?");
            List<String> rooms = new ArrayList<>();
            rooms.add("RED");
            rooms.add("BLUE");
            String room = chooseRoom(rooms);
            List<List<Integer>> squares = new ArrayList<>();
            List<Integer> square1 = new ArrayList<>();
            List<Integer> square2 = new ArrayList<>();
            square1.add(0);
            square1.add(0);
            square2.add(1);
            square2.add(3);
            squares.add(square1);
            squares.add(square2);
            List<Integer> chosenSquare = chooseTargetSquare(squares);
            List<Integer> directions = new ArrayList<>();
            directions.add(0);
            directions.add(1);
            directions.add(2);
            directions.add(3);
            Integer direction = chooseDirection(directions);

        }
    }

    public static void main(String[] args){
        new CliIO();
    }



















    //private List<ArrayList<ArrayList<String>>> mapInfo;
    private String name;


    public String getName(){
        return name;
    }

    public void inputName(){
        markSection("What is your name?");
        displayRender();
        name = readLine();
    }

    public String chooseAction(String state){
        displayMap();
        markSection("Choose your action!");
        markSection("You are in state " + state);
        if(!state.equals("Adrenaline2State")){
            markSection("runAround");
        }
        markSection("grabStuff");
        markSection("shootPeople");
        displayRender();
        return readLine();
    }

    public int chooseSpawnLocation(List<String> powerUps){
        displayMap();
        markSection("Discard a power up card to spawn.");
        for(String s : powerUps){
            markSection(s);
        }
        markSection("please input a number");
        displayRender();
        Scanner scanner = new Scanner(System.in);
        int result = scanner.nextInt();

        return result;
    }


    public int chooseMap(){
        markSection("Choose the map for the game.");
        markSection("0 : good for 3/4 players");
        markSection("1 : good for any number of players");
        markSection("2 : good for 4/5 players");
        markSection("3 : bonus map");
        markSection("please input a number");
        displayRender();
        Scanner scanner = new Scanner(System.in);
        int result = scanner.nextInt();

        return result;
    }


    public int chooseNumberOfPlayers(){
        markSection("Choose how many players will player the match.");
        markSection("please input a number");
        displayRender();
        Scanner scanner = new Scanner(System.in);
        int result = scanner.nextInt();

        return result;
    }


    public String chooseWeapon(List<String> weapons){
        markSection("Which weapon will you use?");
        for(String s : weapons){
            markSection(s);
        }
        displayRender();
        return readLine();
    }


    public String chooseTargets(List<String> possibleTargets){
        displayMap();
        markSection("Who would you like to target?");
        for(String s : possibleTargets){
            markSection(s);
        }
        displayRender();
        return readLine();
    }


    public String chooseWeaponToReload(List<String> weapons){
        markSection("Which weapon will you reload?");
        for(String s : weapons){
            markSection(s);
        }
        displayRender();
        return readLine();
    }


    public List<Integer> choosePowerUpCardsForReload(List<String> powerUps){

        Scanner scanner = new Scanner(System.in);
        markSection("Choose power ups to use for reloading.");
        for(String s : powerUps){
            markSection(s);
        }
        markSection("please input one number at a time, -1 to stop");
        displayRender();
        List<Integer> choices = new ArrayList<>();
        Integer choice = 0;
        while(choice != -1){
            choice = scanner.nextInt();
            choices.add(choice);
        }

        return choices;
    }


    public Integer chooseIndex(List<String> availableEffects){
        Scanner scanner = new Scanner(System.in);
        markSection("Choose one of the following:");
        for(String s : availableEffects){
            markSection(s);
        }
        Integer result = scanner.nextInt();

        return result;
    }


    public int chooseItemToGrab(){
        markSection("Choose which one you would like to pick up:");
        markSection("0/1/2");
        displayRender();
        Scanner scanner = new Scanner(System.in);
        int result = scanner.nextInt();
        return result;
    }


    public Boolean chooseFiringMode(String description){
        markSection("Choose firing mode");
        markSection(description);
        markSection("input 0 for basic or 1 for alternative");
        displayRender();
        Scanner scanner = new Scanner(System.in);
        Integer choice = scanner.nextInt();

        if(choice == 1){
            return true;
        }
        else return false;
    }


    public Boolean chooseBoolean(String description){
        markSection(description);
        markSection("yes/no");
        markSection("please input yes/no");
        displayRender();
        String choice = readLine();
        if(choice.equals("yes")){
            return  true;
        }
        return false;
    }


    public String chooseRoom(List<String> rooms){
        displayMap();
        markSection("Choose target room");
        for(String s : rooms){
            markSection(s);
        }
        return readLine();
    }


    public List<Integer> chooseTargetSquare(List<List<Integer>> targettableSquareCoordinates){
        displayMap();
        markSection("Choose target square");
        markSection("please choose from the following possible");
        List<Integer> coordinates = new ArrayList<>();
        int currentX;
        int currentY;
        for(int i = 0; i<targettableSquareCoordinates.size(); i++){
            currentX = targettableSquareCoordinates.get(i).get(0);
            currentY = targettableSquareCoordinates.get(i).get(0);
            markSection("x : " + currentX + " " + " y : " + currentY);
        }
        displayRender();
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        coordinates = targettableSquareCoordinates.get(choice);
        return coordinates;
    }


    public Integer chooseDirection(List<Integer> possibleDirections){
        displayMap();
        markSection("Choose which way you want to go");
        StringBuilder directions = new StringBuilder();
        for(Integer s : possibleDirections){
            if(s == 0){
                directions.append("/north/");
            }
            if(s == 1){
                directions.append("/east/");
            }
            if(s == 2){
                directions.append("/south/");
            }
            if(s == 3){
                directions.append("/west/");
            }
        }
        markSection(directions.toString());
        displayRender();
        String choice = readLine();
        if(choice.equals("north") || choice.equals("up") || choice.equals("0")){
            return 0;
        }
        else if(choice.equals("east") || choice.equals("right") || choice.equals("1")){
            return 1;
        }
        else if(choice.equals("south") || choice.equals("down") || choice.equals("2")){
            return 2;
        }
        else{
            return 3;
        }

    }


    public void sendMapInfo(List<ArrayList<ArrayList<String>>> m){
        mapInfo = m;
    }

    public void displayMap(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i<40; i++){
            stringBuilder.append("[]");
        }
        stringBuilder.append("\n");
        for(int o = 0; o<3; o++){
            for(int m = 0; m<10; m++){
                for(int l = 0; l<4; l++){
                    stringBuilder.append("[]");
                    //System.out.println(o + " " + l);
                    if(!mapInfo.get(o).get(l).get(0).equals("NR")){
                        if(m < mapInfo.get(o).get(l).size()){
                            stringBuilder.append(mapInfo.get(o).get(l).get(m));
                            for(int k = 0; k< 16 - mapInfo.get(o).get(l).get(m).length(); k++){
                                stringBuilder.append(" ");
                            }
                        }
                        else{
                            for(int k = 0; k< 16; k++){
                                stringBuilder.append(" ");
                            }
                        }
                    }
                    else{
                        for(int n = 0; n<8; n++){
                            stringBuilder.append("[]");
                        }
                    }
                    stringBuilder.append("[]");
                }
                stringBuilder.append("\n");
            }
            for(int i = 0; i<40; i++){
                stringBuilder.append("[]");
            }
            stringBuilder.append("\n");
        }
        System.console().writer().write(stringBuilder.toString());
    }

}
