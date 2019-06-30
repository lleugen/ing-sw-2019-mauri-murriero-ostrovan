package it.polimi.se2019.view;

import it.polimi.se2019.RMI.ViewFacadeInterfaceRMIClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

public final class CliIO implements ViewFacadeInterfaceRMIClient {
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
     * Physically print to screen buffered data
     */
    private void displayRender() {
        System.console().writer().write(this.pendingUpdate.toString());
        System.console().writer().write("\n");
        System.console().writer().write(this.pendingCommands.toString());
        System.console().writer().write("> ");

        this.pendingCommands.delete(0, this.pendingCommands.length());
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
     * Read a text line from the terminal
     *
     * @return The read line
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

    List<ArrayList<ArrayList<String>>> mapInfo = new ArrayList<>();

    private String name;

    @Override
    public String getName(){
        return name;
    }

    public void inputName(){
        markSection("What is your name?");
        displayRender();
        name = readLine();
    }

    @Override
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

    @Override
    public int chooseSpawnLocation(List<String> powerUps){
        displayMap();
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
        markSection("Which weapon will you use?");
        for(String s : weapons){
            markSection(s);
        }
        displayRender();
        return readLine();
    }

    @Override
    public String chooseTargets(List<String> possibleTargets){
        displayMap();
        markSection("Who would you like to target?");
        for(String s : possibleTargets){
            markSection(s);
        }
        displayRender();
        return readLine();
    }

    @Override
    public String chooseWeaponToReload(List<String> weapons){
        markSection("Which weapon will you reload?");
        for(String s : weapons){
            markSection(s);
        }
        displayRender();
        return readLine();
    }

    @Override
    public List<Integer> choosePowerUpCardsForReload(List<String> powerUps){

        Scanner scanner = new Scanner(System.in);
        markSection("Choose power ups to use for reloading.");
        for(String s : powerUps){
            markSection(s);
        }
        markSection("please input one number at a time, -1 to stop");
        displayRender();
        List<Integer> choices = new ArrayList<>();
        int choice = 0;
        while(choice != -1){
            choice = scanner.nextInt();
            choices.add(choice);
        }

        return choices;
    }

    @Override
    public Integer chooseIndex(List<String> availableEffects){
        Scanner scanner = new Scanner(System.in);
        markSection("Choose one of the following:");
        for(String s : availableEffects){
            markSection(s);
        }
        return scanner.nextInt();
    }

    @Override
    public int chooseItemToGrab(){
        markSection("Choose which one you would like to pick up:");
        markSection("0/1/2");
        displayRender();
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    @Override
    public Boolean chooseFiringMode(String description){
        markSection("Choose firing mode");
        markSection(description);
        markSection("input 0 for basic or 1 for alternative");
        displayRender();
        Scanner scanner = new Scanner(System.in);

        return (scanner.nextInt() == 1);
    }

    @Override
    public Boolean chooseBoolean(String description){
        markSection(description);
        markSection("yes/no");
        markSection("please input yes/no");
        displayRender();
        return "yes".equals(readLine());
    }


    public String chooseRoom(List<String> rooms){
        displayMap();
        markSection("Choose target room");
        for(String s : rooms){
            markSection(s);
        }
        return readLine();
    }

    @Override
    public List<Integer> chooseTargetSquare(List<List<Integer>> targettableSquareCoordinates){
        displayMap();
        markSection("Choose target square");
        markSection("please choose from the following possible");
        int currentX;
        int currentY;
        for (List<Integer> targettableSquareCoordinate : targettableSquareCoordinates) {
            currentX = targettableSquareCoordinate.get(0);
            currentY = targettableSquareCoordinate.get(0);
            markSection("x : " + currentX + " " + " y : " + currentY);
        }
        displayRender();
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        return targettableSquareCoordinates.get(choice);
    }

    @Override
    public Integer chooseDirection(List<Integer> possibleDirections){
        displayMap();
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
        else{
            return 3;
        }

    }

    @Override
    public void sendMapInfo(List<ArrayList<ArrayList<String>>> m){
        mapInfo = m;
    }

    @Override
    public void sendPlayerInfo(List<ArrayList<String>> playerInfo) throws RemoteException {
        // TODO
        System.out.println("sendPlayerInfo");
    }

    @Override
    public void sendKillScoreBoardInfo(List<ArrayList<String>> killScoreBoardInfo) throws RemoteException {
        // TODO
        System.out.println("sendKillScoreBoardInfo");
    }
}
