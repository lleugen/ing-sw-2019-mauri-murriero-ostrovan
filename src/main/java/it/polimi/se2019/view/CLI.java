package it.polimi.se2019.view;

import it.polimi.se2019.rmi.ViewFacadeInterfaceRMIClient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * @author Eugenio Ostrovan
 * @author Fabio Mauri
 */
public final class CLI extends UnicastRemoteObject
        implements ViewFacadeInterfaceRMIClient {

    /**
     * Information to display about players
     */
    List<ArrayList<String>> playerInfo;
    /**
     * Namespace this class logs to
     */
    private static final String LOG_NAMESPACE = "CLI";

    /**
     * Contains data to print to screen on render
     */
    private StringBuilder pendingUpdate = new StringBuilder();

    /**
     * Contains information about the map received from the server
     */
    private List<ArrayList<ArrayList<String>>> mapInfo = new ArrayList<>();

    /**
     * Contains information about the global scoreboard
     */
    List<ArrayList<String>> killScoreBoardInfo = new ArrayList<>();

    /**
     * Contain the name of the player currently playing
     */
    private String name;

    /**
     * Contain the character of the player
     */
    private String character;

    public CLI() throws RemoteException {
        this.writeLn("Insert Username");
        this.displayRender();
        this.name = this.readLine();

        this.writeLn("Insert Character");
        this.displayRender();
        this.character = this.readLine();
    }

    /**
     * Physically print to screen buffered data
     */
    private void displayRender() {
        System.console().writer().write(this.pendingUpdate.toString());
        System.console().writer().write("\n");

        this.pendingUpdate.delete(0, this.pendingUpdate.length());

        System.console().writer().flush();
    }

    /**
     * Mark a section
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
     * Buffer a line
     *
     * @param data Line to buffer
     */
    private void writeLn(String data){
        this.pendingUpdate.append(data);
    }

    /**
     * Read a text line from the terminal
     *
     * @return The read line
     */
    private String readLine() {
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
        sb.append(scanner.nextLine());

        return sb.toString();
    }

    /**
     * Read an integer from the console input
     *
     * @return the read integer. This function blocks till an int is inserted
     *         in the console
     */
    private Integer readInt(){
        boolean ok = false;
        int toReturn = 0;

        while (!ok) {
            markSection("please input a number");
            displayRender();
            Scanner scanner = new Scanner(System.in);

            try {
                toReturn = scanner.nextInt();
                ok = true;
            }
            catch (InputMismatchException e){
                Logger.getLogger(LOG_NAMESPACE).log(
                        Level.FINE,
                        "Invalid input",
                        e
                );
            }
        }

        return toReturn;

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
                    if (
                            mapInfo != null && (mapInfo.size() > o) &&
                                    mapInfo.get(o) != null && (mapInfo.get(o).size() > l) &&
                                    mapInfo.get(o).get(l) != null && (!mapInfo.get(o).get(l).isEmpty()) &&
                                    mapInfo.get(o).get(l).get(0) != null
                    ) {
                        if (!mapInfo.get(o).get(l).get(0).equals("NR")) {
                            if (m < mapInfo.get(o).get(l).size()) {
                                stringBuilder.append(mapInfo.get(o).get(l).get(m));
                                for (int k = 0; k < 16 - mapInfo.get(o).get(l).get(m).length(); k++) {
                                    stringBuilder.append(" ");
                                }
                            } else {
                                for (int k = 0; k < 16; k++) {
                                    stringBuilder.append(" ");
                                }
                            }
                        } else {
                            for (int n = 0; n < 8; n++) {
                                stringBuilder.append("[]");
                            }
                        }
                        stringBuilder.append("[]");
                    }
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

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public String getCharacter() { return this.character; }

    public void inputName(){
        markSection("What is your name?");
        displayRender();
        name = readLine();
    }

    @Override
    public String chooseAction(String state){
        showTime();
        Scanner scanner = new Scanner(System.in);
        markSection("Choose your action!");
        markSection("You are in state " + state);
        if(!state.equals("Adrenaline2State")){
            markSection("run");
        }
        markSection("grab");
        markSection("shoot");
        markSection("powerUp");
        displayRender();
        String result = "run";
        boolean done = false;
        while(!done){
            result = scanner.nextLine();
            if(result.equals("run") || result.equals("grab") || result.equals("shoot") || result.equals("powerUp"))
            {
                done = true;
            }
        }

        return result;
    }

    @Override
    public int chooseSpawnLocation(List<String> powerUps){
        markSection("Discard a power up card to spawn.");
        for(String s : powerUps){
            markSection(s);
        }
        return readInt();
    }

    @Override
    public int chooseMap(){
        markSection("Choose the map for the game.");
        markSection("0 : good for 3/4 players");
        markSection("1 : good for any number of players");
        markSection("2 : good for 4/5 players");
        markSection("3 : bonus map");
        return readInt();
    }

    @Override
    public int chooseNumberOfPlayers(){
        markSection("Choose how many players will player the match.");
        return readInt();
    }

    @Override
    public String chooseWeapon(List<String> weapons){
        showTime();
        Scanner scanner = new Scanner(System.in);
        int result = 0;
        boolean done = false;

        markSection("Which weapon will you use?");
        for(String s : weapons){
            markSection(s);
        }
        displayRender();

        while(!done){
            result = scanner.nextInt();
            if((result >= 0) && (result < weapons.size())){
                done = true;
            }
        }
        return weapons.get(result);
    }

    @Override
    public String chooseTargets(List<String> possibleTargets){
        showTime();
        Scanner scanner = new Scanner(System.in);
        markSection("Who would you like to target?");
        for(String s : possibleTargets){
            markSection(s);
        }
        displayRender();
        int result;
        do {
           result = scanner.nextInt();
        } while (
                result >= possibleTargets.size() ||
                result < 0
        );

        return possibleTargets.get(result);
    }

    @Override
    public String chooseWeaponToReload(List<String> weapons){
        showTime();
        markSection("Which weapon will you reload?");
        for(String s : weapons){
            markSection(s);
        }
        displayRender();
        return readLine();
    }

    @Override
    public List<Integer> choosePowerUpCardsForReload(List<String> powerUps){
        showTime();
        List<Integer> choices = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        markSection("Choose power ups to use");
        for(String s : powerUps){
            markSection(s);
        }
        markSection("please input one number at a time, -1 to stop");
        displayRender();

        int choice = 0;
        while(choice != -1){
            choice = scanner.nextInt();
            if(choice != -1){
                choices.add(choice);
            }
        }

        return choices;
    }

    @Override
    public Integer chooseIndex(List<String> availableEffects){
        showTime();
        Scanner scanner = new Scanner(System.in);
        markSection("Choose one of the following:");
        for(String s : availableEffects){
            markSection(s);
        }
        return scanner.nextInt();
    }

    @Override
    public int chooseItemToGrab(){
        showTime();
        markSection("Choose which one you would like to pick up:");
        markSection("0/1/2");
        displayRender();
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean done = false;
        while(!done){
            choice = scanner.nextInt();
            if(choice==0 || choice == 1 || choice == 2){
                done = true;
            }
        }
        return choice;
    }

    @Override
    public Boolean chooseFiringMode(String description){
        showTime();
        markSection("Choose firing mode");
        markSection(description);
        markSection("input 0 for basic or 1 for alternative");
        displayRender();
        Scanner scanner = new Scanner(System.in);

        return (scanner.nextInt() == 1);
    }

    @Override
    public Boolean chooseBoolean(String description){
        showTime();
        markSection(description);
        markSection("yes/no");
        markSection("please input yes/no");
        displayRender();
        return "yes".equals(readLine());
    }


    public String chooseRoom(List<String> rooms){
        showTime();
        markSection("Choose target room");
        for(String s : rooms){
            markSection(s);
        }
        return readLine();
    }

    @Override
    public List<Integer> chooseTargetSquare(List<List<Integer>> targettableSquareCoordinates){
        showTime();
        markSection("Choose target square");
        markSection("please choose from the following possible");
        int currentX;
        int currentY;
        for (List<Integer> targettableSquareCoordinate : targettableSquareCoordinates) {
            currentX = targettableSquareCoordinate.get(0);
            currentY = targettableSquareCoordinate.get(1);
            markSection("x : " + currentX + " " + " y : " + currentY);
        }
        displayRender();
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        return targettableSquareCoordinates.get(choice);
    }

    @Override
    public Integer chooseDirection(List<Integer> possibleDirections){
        showTime();
        markSection("Choose which way you want to go");
        String[] availableDirections = new String[]{
                "/north/",
                "/east/",
                "/south/",
                "/west/"
        };

        StringBuilder directions = new StringBuilder();
        for (Integer s : possibleDirections) {
            directions.append(availableDirections[s]);
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
        else if(choice.equals("west") || choice.equals("left") || choice.equals("3")){
            return 3;
        }
        else{
            return 4;
        }
    }

    public void displayKillScoreBoard(){
        StringBuilder buffer = new StringBuilder();
        if(!killScoreBoardInfo.isEmpty()){
            buffer.append("KILLS : ");
            if(killScoreBoardInfo.get(0).isEmpty()){
                buffer.append("no kills yet");
            }
            else{
                for(int i = 0; i<killScoreBoardInfo.get(0).size(); i++){

                    buffer.append(killScoreBoardInfo.get(0).get(i));
                    buffer.append(" ");
                }
            }
            buffer.append('\n');

            buffer.append("DOUBLE KILLS : ");
            if(killScoreBoardInfo.get(1).isEmpty()){
                buffer.append("no double kills yet");
            }
            else{
                for(int i = 0; i<killScoreBoardInfo.get(1).size(); i++){

                    buffer.append(killScoreBoardInfo.get(1).get(i));
                    buffer.append(" ");
                }
            }

            buffer.append('\n');
        }
        System.out.printf(buffer.toString());
    }

    public void displayPlayerInfo(){
        StringBuilder buffer = new StringBuilder();
        if(playerInfo != null){
            buffer.append("damage taken: " + "(" + playerInfo.get(0).size() + ") ");
            for(int i = 0; i<playerInfo.get(0).size(); i++){
                buffer.append(playerInfo.get(0).get(i));
                buffer.append(" ");
            }
            buffer.append('\n');
            buffer.append("marks assigned: " + "(" + playerInfo.get(1).size() + ") ");
            for(int i = 0; i<playerInfo.get(1).size(); i++){
                buffer.append(playerInfo.get(1).get(i));
                buffer.append(" ");
            }
            buffer.append('\n');
            System.console().writer().write(buffer.toString());
        }
    }

    public void showTime(){
        displayMap();
        displayPlayerInfo();
        displayKillScoreBoard();
    }

    @Override
    public void sendGenericMessage(String message){
        showTime();
        markSection(message);
        displayRender();
    }

    @Override
    public void sendMapInfo(List<ArrayList<ArrayList<String>>> m){
        mapInfo = m;
        //displayMap();
    }

    @Override
    public void sendPlayerInfo(List<ArrayList<String>> pInfo)
            throws RemoteException {
        playerInfo = pInfo;
    }

    @Override
    public void sendKillScoreBoardInfo(List<ArrayList<String>> killBoardInfo)
            throws RemoteException {
        killScoreBoardInfo = killBoardInfo;
    }

    @Override
    public void sendCharacterInfo(List<String> cInfo)
            throws RemoteException {
        // Implemented only because defined in the interface.
        // Empty cause the gui doesn't needs those data
    }
}
