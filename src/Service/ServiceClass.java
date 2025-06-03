package Service;

import Entities.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class ServiceClass {
    private TournamentsManager tournamentsManager;
    private final Scanner scanner;



    public void closeService(){
        AuditService.getInstance().log("Exited App");
        AuditService.getInstance().close();
    }



    private int readValidTournamentId(){

            int tournamentId = 0;

            boolean isValid = false;
            while (!isValid) {
                try {
                    System.out.print("Type Tournament's ID: ");
                    tournamentId = Integer.parseInt(scanner.nextLine());
                    isValid = true;
                } catch (NumberFormatException e) {
                    System.out.println("Please introduce a valid id!");
                }
            }

            return tournamentId;

    }


    private String readValidFideId()
    {
        String fideId =null;
        boolean isValid=false;

        while(!isValid) {
            System.out.print("Type FIDE ID: ");
            fideId= scanner.nextLine();

            if(fideId.matches("[0-9]{1,13}")){
                isValid=true;
            }
            else{
                System.out.println("Please introduce a valid fide ID!");
            }
        }

        return fideId;
    }




    private PlayerTitle readValidPlayerTitle(){
        PlayerTitle title = null;
        boolean isValid = false;

        while (!isValid) {

            System.out.print("Type the Player's FIDE title: ");
            String titleString = scanner.nextLine().toUpperCase().trim();

            if(titleString.isEmpty()){
                isValid=true;
            }
            else {
                try {
                    title = PlayerTitle.valueOf(titleString);
                    isValid = true;
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid title. Valid options: " + Arrays.toString(PlayerTitle.values())+" OR press ENTER to skip!" );
                }
            }

        }
        return title;
    }



    private int readValidFideRating()
    {
        int fideRating =0;
        boolean isValid=false;

        while(!isValid) {
            try{
                System.out.print("Type Player's FIDE rating: ");
                fideRating= Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please introduce a valid FIDE rating");
            }
            if(fideRating<0 || fideRating>100000){
                System.out.println("Please introduce a FIDE rating: >=0 and <=100000");
            }
            else {
                isValid=true;
            }

        }

        return fideRating;
    }


    private ArbiterTitle readValidArbiterTitle(){

        ArbiterTitle arbiterTitle=null;

        boolean isValid=false;

        while (!isValid){

            System.out.print("Type arbiter's FIDE title: ");
            String stringTitle = scanner.nextLine().toUpperCase().trim();

            if(stringTitle.isEmpty()){
                isValid=true;
            }
            else {
                try {
                    arbiterTitle = ArbiterTitle.valueOf(stringTitle);
                    isValid = true;
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid title. Valid options: " + Arrays.toString(ArbiterTitle.values()) + " OR press ENTER to skip!");
                }
            }

        }

        return arbiterTitle;
    }



    private String readValidPhoneNumber(){
        String phoneNumber =null;
        boolean isValid=false;

        while(!isValid) {
            System.out.print("Type phone number: ");
            phoneNumber= scanner.nextLine();

            if(phoneNumber.matches("[0-9]{3,13}") || phoneNumber.isEmpty()){
                isValid=true;
            }
            else{
                System.out.println("Please introduce a valid phone number! Length: 3-13  OR press ENTER to skip");
            }
        }

        return phoneNumber;
    }

    private String readValidEmail(){
        String email =null;
        boolean isValid=false;

        while(!isValid) {
            System.out.print("Type email address: ");
            email= scanner.nextLine();

            if(email.matches("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$") || email.isEmpty()){
                isValid=true;
            }
            else{
                System.out.println("Please introduce a valid email address OR press ENTER to skip!");
            }
        }

        return email;
    }



    public ServiceClass() {
        tournamentsManager = TournamentsManager.getInstance();
        scanner = new Scanner(System.in);
    }


    public void createTournament() {
        System.out.print("Tournament name: ");
        String tournamentName = scanner.nextLine();

        int organizerId=0;

        boolean isValid = false;

        while (!isValid){
            try{
                System.out.print("Organizer's ID: ");
                organizerId = Integer.parseInt(scanner.nextLine());
                isValid=true;

            } catch (NumberFormatException e) {
                System.out.println("Please introduce a valid ID!");
            }

        }

        try{

            Organizer organizer = OrganizerService.getInstance().readByOrganizerId(organizerId);
            if(organizer==null){
                System.out.println("There is no organizer with the ID: "+organizerId);
            }
            else {
                int generatedId=TournamentService.getInstance().createAndReturnId(new Tournament(tournamentName, organizerId));

                System.out.println("Tournament has been created!");
                AuditService.getInstance().log("Create tournament with id: "+generatedId);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }


    }



    public void deleteTournament() {

        showAllTournaments();

        try{
            List<Tournament> tournamentList = TournamentService.getInstance().readAll();

            if(tournamentList.isEmpty()){
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }


        try{
            int tournamentId = readValidTournamentId();

            Tournament tournament = TournamentService.getInstance().readByTournamentId(tournamentId);
            if(tournament!=null) {

                //remove from TournamentsManager
                tournamentsManager.removeTournament(tournamentId);
                //
                TournamentService.getInstance().delete(tournamentId);


                AuditService.getInstance().log("Delete tournament with id: "+tournamentId);
                System.out.println("Deleted tournament with id: "+tournamentId);
            }
            else {
                System.out.println("There is no tournament with the id: " + tournamentId);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }



    public void showAllTournaments() {

        AuditService.getInstance().log("Show all tournaments");

        try {
            List<Tournament> tournamentsList = TournamentService.getInstance().readAll();

            if (tournamentsList.isEmpty()) {
                System.out.println("No tournaments found.");


                return;
            }

            System.out.printf("%-5s | %-40s | %-30s%n", "ID", "Tournament Name", "Organizer");
            System.out.println("---------------------------------------------------------------");

            tournamentsList.forEach(t -> {
                Organizer organizer;
                try {
                    organizer = OrganizerService.getInstance().readByOrganizerId(t.getOrganizerId());
                } catch (SQLException e) {
                    organizer = null;
                }

                String organizerName = (organizer != null)
                        ? organizer.getLastName() + " " + organizer.getFirstName()
                        : "[Organizer not found]";

                System.out.printf("%-5d | %-40s | %-30s%n",
                        t.getId(),
                        t.getName(),
                        organizerName);
            });


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void showTournamentStartingList() {

        showAllTournaments();

        try{
            List<Tournament> tournamentList = TournamentService.getInstance().readAll();

            if(tournamentList.isEmpty()){
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        int tournamentId = readValidTournamentId();

            try {
                Tournament tournament = TournamentService.getInstance().readByTournamentId(tournamentId);
                if(tournament==null){
                    System.out.println("There is no tournament with the id: " + tournamentId);
                }
                else{

                    AuditService.getInstance().log("Show starting list" +
                            " of tournament with id: "+tournamentId);

                    List<TournamentPlayer> tournamentPlayerList = TournamentPlayerService.getInstance().readAllByTournamentId(tournamentId);
                    if (tournamentPlayerList.isEmpty()) {
                        System.out.println("No players registered yet for this tournament.");

                        return;
                    }

                    System.out.printf("%-4s | %-10s | %-25s | %-6s%n", "No.", "FIDE ID", "Name", "Rating");
                    System.out.println("--------------------------------------------------------------");

                    AtomicInteger index = new AtomicInteger(1);
                    tournamentPlayerList.stream()
                            .sorted((p1,p2)->p2.getRating()-p1.getRating())
                            .forEach(player -> System.out.printf("%-4d | %-10s | %-25s | %-6d%n",
                                    index.getAndIncrement(),
                                    player.getFideId(),
                                    player.getLastName() + " " + player.getFirstName(),
                                    player.getRating()));


                }

            }catch (SQLException e){
                e.printStackTrace();
            }

    }


    public void createTournamentPlayer() {

        showAllTournaments();

        try{
            List<Tournament> tournamentList = TournamentService.getInstance().readAll();

            if(tournamentList.isEmpty()){
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        int tournamentId = readValidTournamentId();

        try {
            Tournament tournament = TournamentService.getInstance().readByTournamentId(tournamentId);
            if (tournament == null) {
                System.out.println("There is no tournament with id: " + tournamentId);
            }
            else {

                if (tournamentsManager.getTournaments().containsKey(tournamentId)) {
                    System.out.println("Tournament has already started!");
                }
                else {

                    List<TournamentPlayer> tournamentPlayerList = null;

                    tournamentPlayerList = TournamentPlayerService.getInstance().readAllByTournamentId(tournamentId);


                    if (tournamentPlayerList.isEmpty()) {
                        System.out.println("No players registered yet for this tournament.");
                    } else {


                        System.out.printf("%-4s | %-10s | %-25s | %-6s%n", "No.", "FIDE ID", "Name", "Rating");
                        System.out.println("--------------------------------------------------------------");

                        AtomicInteger index = new AtomicInteger(1);
                        tournamentPlayerList.stream()
                                .sorted(Comparator.reverseOrder())
                                .forEach(player -> System.out.printf("%-4d | %-10s | %-25s | %-6d%n",
                                        index.getAndIncrement(),
                                        player.getFideId(),
                                        player.getLastName() + " " + player.getFirstName(),
                                        player.getRating()));



                    }


                    String fideId = readValidFideId();

                    if (tournamentPlayerList.stream().anyMatch(tp -> tp.getFideId().equals(fideId))) {
                        System.out.println("There is already a player in this tournament" +
                                " with the FIDE ID: " + fideId);
                    } else {

                        if (PlayerService.getInstance().readByFideId(fideId) == null) {
                            System.out.println("No player found with FIDE ID: " + fideId);
                            return;
                        }

                        TournamentPlayerService.getInstance().create(fideId, tournamentId);

                        System.out.println("Player was added to the tournament");

                        AuditService.getInstance().log("Player with FIDE ID: "+
                                fideId+" added to tournament " +
                                "with id: "+tournamentId);
                    }


                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public void deleteTournamentPlayer() {

        showAllTournaments();

        try{
            List<Tournament> tournamentList = TournamentService.getInstance().readAll();

            if(tournamentList.isEmpty()){
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        int tournamentId = readValidTournamentId();

        try {
            Tournament tournament = TournamentService.getInstance().readByTournamentId(tournamentId);
            if (tournament == null) {
                System.out.println("There is no tournament with the id: " + tournamentId);
            }
            else {

                if (tournamentsManager.getTournaments().containsKey(tournamentId)) {
                    System.out.println("Tournament has already started!");
                } else {


                    List<TournamentPlayer> tournamentPlayerList = null;


                    tournamentPlayerList = TournamentPlayerService.getInstance().readAllByTournamentId(tournamentId);


                    if (tournamentPlayerList.isEmpty()) {
                        System.out.println("No players registered yet for this tournament.");
                        return;
                    }

                    System.out.printf("%-4s | %-10s | %-25s | %-6s%n", "No.", "FIDE ID", "Name", "Rating");
                    System.out.println("--------------------------------------------------------------");

                    AtomicInteger index = new AtomicInteger(1);
                    tournamentPlayerList.stream()
                            .sorted(Comparator.reverseOrder()) // presupun că reverseOrder sortează după rating
                            .forEach(player -> System.out.printf("%-4d | %-10s | %-25s | %-6d%n",
                                    index.getAndIncrement(),
                                    player.getFideId(),
                                    player.getLastName() + " " + player.getFirstName(),
                                    player.getRating()));


                    String fideId = readValidFideId();

                    if (tournamentPlayerList.stream().anyMatch(tp -> tp.getFideId().equals(fideId))) {

                        TournamentPlayerService.getInstance().delete(fideId, tournamentId);

                        System.out.println("Player was removed from the tournament!");

                        AuditService.getInstance().log("Player with FIDE ID: "+
                                fideId+" removed from tournament " +
                                "with id: "+tournamentId);

                    } else {
                        System.out.println("There is no player with FIDE ID: " + fideId + " in this tournament.");
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }



    public void startTournament() {

        showAllTournaments();

        try{
            List<Tournament> tournamentList = TournamentService.getInstance().readAll();

            if(tournamentList.isEmpty()){
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        int tournamentId = readValidTournamentId();

        try{
            Tournament tournament = TournamentService.getInstance().readByTournamentId(tournamentId);
            if (tournament == null) {
                System.out.println("There is no tournament with the id: " + tournamentId);
            }
            else{

                if(tournamentsManager.getTournaments().containsKey(tournamentId)){
                    System.out.println("Tournament has already started!");
                }
                else {

                    List<TournamentPlayer> tournamentPlayerList = TournamentPlayerService
                            .getInstance()
                            .readAllByTournamentId(tournamentId);
                    tournamentPlayerList.forEach(tournament::addPlayer);
                    tournament.pairingSystem();

                    tournamentsManager.addTournament(tournament);

                    System.out.println("Tournament with id: "+tournamentId+" has been started!");

                    AuditService.getInstance().log("Tournament with id: "+
                            tournamentId+" has been started");

                }
            }

        } catch (IllegalStateException e){
            System.out.println(e.getMessage());
        }
        catch (SQLException e){
            e.printStackTrace();
        }


    }


    public void showRounds() {

        showAllTournaments();

        try{
            List<Tournament> tournamentList = TournamentService.getInstance().readAll();

            if(tournamentList.isEmpty()){
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }


        int tournamentId = readValidTournamentId();

        try {
            Tournament tournament = TournamentService.getInstance().readByTournamentId(tournamentId);
            if (tournament != null) {

                if (!tournamentsManager.getTournaments().containsKey(tournamentId)) {
                    System.out.println("Tournament has not started yet!");
                } else {
                    tournamentsManager.getTournaments().get(tournamentId).showRounds();

                    AuditService.getInstance().log("Show rounds of tournament with id: "+tournamentId);
                }

            } else {
                System.out.println("There is no tournament with the id: " + tournamentId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void setPlayersPoints() {

        showAllTournaments();

        try{
            List<Tournament> tournamentList = TournamentService.getInstance().readAll();

            if(tournamentList.isEmpty()){
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        int tournamentId = readValidTournamentId();

        try{

            Tournament tournament = TournamentService.getInstance().readByTournamentId(tournamentId);
            if(tournament!=null){

                if(!tournamentsManager.getTournaments().containsKey(tournamentId)){
                    System.out.println("Tournament has not started yet!");
                }
                else{

                    tournamentsManager.getTournaments().get(tournamentId).showRounds();


                    List<TournamentPlayer> tournamentPlayerList = TournamentPlayerService
                            .getInstance()
                            .readAllByTournamentId(tournamentId);



                    tournamentPlayerList.forEach(p -> {
                        System.out.println(p);
                        double points = -1;

                        boolean valid = false;
                        while (!valid) {
                            System.out.print("Set points: ");
                            try {
                                points = Double.parseDouble(scanner.nextLine());

                                if (points >= 0 && (points * 2) % 1 == 0) {
                                    valid = true;
                                } else {
                                    System.out.println("Please enter a valid number (0, 0.5, 1, 1.5, ...).");
                                }

                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a number.");
                            }
                        }

                        p.setPoints(points);
                    });

                    TournamentPlayerService.getInstance().updateAllTournamentPlayersPoints(tournamentId,tournamentPlayerList);

                    System.out.println("Updated completed!");

                    AuditService.getInstance().log("Set points to all players" +
                            " of tournament with id: "+tournamentId);

                }

            }
            else{
                System.out.println("There is no tournament with the id: "+tournamentId);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    public void showRanking() {

        showAllTournaments();

        try{
            List<Tournament> tournamentList = TournamentService.getInstance().readAll();

            if(tournamentList.isEmpty()){
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        int tournamentId = readValidTournamentId();

        try{

            Tournament tournament = TournamentService.getInstance().readByTournamentId(tournamentId);
            if(tournament!=null){

                if(!tournamentsManager.getTournaments().containsKey(tournamentId)){
                    System.out.println("Tournament has not started yet!");
                }
                else{

                    List<TournamentPlayer> tournamentPlayerList = TournamentPlayerService.getInstance().readAllByTournamentId(tournamentId);
                    if (tournamentPlayerList.isEmpty()) {
                        System.out.println("No players registered yet for this tournament.");
                        return;
                    }


                    System.out.printf("%-4s | %-10s | %-25s | %-6s | %-4s%n", "No.", "FIDE ID", "Name", "Rating", "Points");
                    System.out.println("--------------------------------------------------------------");

                    AtomicInteger index = new AtomicInteger(1);
                    tournamentPlayerList.stream()
                            .sorted(Comparator.reverseOrder()) // presupun că reverseOrder sortează după rating
                            .forEach(player -> System.out.printf("%-4d | %-10s | %-25s | %-6s | %-4s%n",
                                    index.getAndIncrement(),
                                    player.getFideId(),
                                    player.getLastName() + " " + player.getFirstName(),
                                    player.getRating(),
                                    player.getPoints()
                                    ));

                    AuditService.getInstance().log("Show ranking list of" +
                            " tournament with id: "+tournamentId);
                }

            }
            else{
                System.out.println("There is no tournament with the id: "+tournamentId);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    public void createTournamentArbiter() {
        showAllTournaments();

        try{
            List<Tournament> tournamentList = TournamentService.getInstance().readAll();

            if(tournamentList.isEmpty()){
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        int tournamentId = readValidTournamentId();

        try {
            Tournament tournament = TournamentService.getInstance().readByTournamentId(tournamentId);
            if(tournament!=null){

                List<TournamentArbiter> arbitersList = TournamentArbiterService.getInstance().readByTournamentId(tournamentId);
                if (!arbitersList.isEmpty()) {
                    System.out.println("Current tournament's arbiters:");


                    System.out.printf("%-10s | %-25s | %-20s%n", "FIDE ID", "Name", "Role");
                    System.out.println("----------------------------------------------------------");

                    arbitersList.forEach(a -> {
                        try {
                            Arbiter arbiter = ArbiterService.getInstance().readByFideId(a.getFideId());

                            String fullName = arbiter.getLastName() + " " + arbiter.getFirstName();

                            System.out.printf("%-10s | %-25s | %-20s%n",
                                    a.getFideId(),
                                    fullName,
                                    a.getRole());

                        } catch (SQLException e) {
                            System.out.printf("%-10s | %-25s | %-20s%n",
                                    a.getFideId(),
                                    "[Arbiter not found]",
                                    a.getRole());
                        }
                    });




                } else {
                    System.out.println("This tournament has no arbiters yet!");
                }

                System.out.println("Type Arbiter's FIDE ID");
                String fideId = readValidFideId();


                Arbiter arbiter = ArbiterService.getInstance().readByFideId(fideId);
                if(arbiter==null){
                    System.out.println("There is no arbiter registered with the id: "+fideId);
                    return;
                }

                if(arbitersList.stream()
                                    .noneMatch(a->a.getFideId().equals(fideId)))
                {
                    System.out.println("Type Arbiter's role: ");
                    String role = scanner.nextLine();

                    TournamentArbiterService.getInstance().create(new TournamentArbiter(
                            fideId, tournamentId, role)
                    );

                    AuditService.getInstance().log("Added arbiter with FIDE ID: "+fideId+" to " +
                            "tournament with id: "+tournamentId);

                    System.out.println("Added the arbiter with the FIDE ID: "+fideId+" to the " +
                            "tournament with the id: "+tournamentId);
                }
                else{
                    System.out.println("There is already an arbiter in this tournament" +
                            " with the FIDE ID: "+fideId);
                }

            }
            else{
                System.out.println("There is no tournament with the id: "+tournamentId);
            }



        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    public void deleteTournamentArbiter() {
        showAllTournaments();

        try{
            List<Tournament> tournamentList = TournamentService.getInstance().readAll();

            if(tournamentList.isEmpty()){
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        int tournamentId = readValidTournamentId();

        try {
            Tournament tournament = TournamentService.getInstance().readByTournamentId(tournamentId);
            if(tournament!=null){


                List<TournamentArbiter> arbitersList= TournamentArbiterService.getInstance().readByTournamentId(tournamentId);
                if(!arbitersList.isEmpty()){
                    System.out.println("Current tournament's arbiters:");


                    System.out.printf("%-10s | %-25s | %-20s%n", "FIDE ID", "Name", "Role");
                    System.out.println("----------------------------------------------------------");

                    arbitersList.forEach(a -> {
                        try {
                            Arbiter arbiter = ArbiterService.getInstance().readByFideId(a.getFideId());

                            String fullName = arbiter.getLastName() + " " + arbiter.getFirstName();

                            System.out.printf("%-10s | %-25s | %-20s%n",
                                    a.getFideId(),
                                    fullName,
                                    a.getRole());

                        } catch (SQLException e) {
                            System.out.printf("%-10s | %-25s | %-20s%n",
                                    a.getFideId(),
                                    "[Arbiter not found]",
                                    a.getRole());
                        }
                    });


                }
                else{
                    System.out.println("This tournament has no arbiters yet!");
                    return;
                }

                System.out.println("Type Arbiter's FIDE ID");
                String fideId = readValidFideId();

                if(arbitersList.stream()
                        .anyMatch(a -> a.getFideId().equals(fideId))){
                    TournamentArbiterService.getInstance().delete(fideId,tournamentId);

                    AuditService.getInstance().log("Removed arbiter with FIDE ID: "+fideId+" from " +
                            "tournament with id: "+tournamentId);

                    System.out.println("Removed the arbiter with the FIDE ID: "+fideId+" from the " +
                            "tournament with the id: "+tournamentId);

                }
                else{
                    System.out.println("There is no arbiter in this tournament with the id: "+fideId);
                }

            }
            else {
                System.out.println("There is no tournament with the id: "+tournamentId);
            }


        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    public void showTournamentArbiters(){
        showAllTournaments();

        try{
            List<Tournament> tournamentList = TournamentService.getInstance().readAll();

            if(tournamentList.isEmpty()){
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        int tournamentId = readValidTournamentId();

        try {
            Tournament tournament = TournamentService.getInstance().readByTournamentId(tournamentId);
            if(tournament!=null){

                List<TournamentArbiter> arbitersList= TournamentArbiterService.getInstance().readByTournamentId(tournamentId);



                if(!arbitersList.isEmpty()){
                    System.out.println("Current tournament's arbiters:");


                    System.out.printf("%-10s | %-25s | %-20s%n", "FIDE ID", "Name", "Role");
                    System.out.println("----------------------------------------------------------");

                    arbitersList.forEach(a -> {
                        try {
                            Arbiter arbiter = ArbiterService.getInstance().readByFideId(a.getFideId());

                            String fullName = arbiter.getLastName() + " " + arbiter.getFirstName();

                            System.out.printf("%-10s | %-25s | %-20s%n",
                                    a.getFideId(),
                                    fullName,
                                    a.getRole());

                        } catch (SQLException e) {
                            System.out.printf("%-10s | %-25s | %-20s%n",
                                    a.getFideId(),
                                    "[Arbiter not found]",
                                    a.getRole());
                        }
                    });


                }
                else{
                    System.out.println("This tournament has no arbiters yet!");
                }

                AuditService.getInstance().log("Show all arbiters from " +
                        "tournament with id: "+tournamentId);

            }
            else {
                System.out.println("There is no tournament with the id: "+tournamentId);
            }


        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void showAllPlayers() {

        try{
            AuditService.getInstance().log("Show all players");

            List<Player> playersList= PlayerService.getInstance().readAll();
            if (playersList.isEmpty()) {
                System.out.println("No players registered yet.");


                return;
            }


            System.out.printf("%-4s | %-10s | %-25s | %-6s%n", "No.", "FIDE ID", "Name", "Rating");
            System.out.println("--------------------------------------------------------------");

            AtomicInteger index = new AtomicInteger(1);
            playersList.stream()
                    .sorted((p1,p2)->p2.getRating()- p1.getRating())
                    .forEach(player -> System.out.printf("%-4d | %-10s | %-25s | %-6s%n",
                            index.getAndIncrement(),
                            player.getFideId(),
                            player.getLastName() + " " + player.getFirstName(),
                            player.getRating()
                    ));

        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void showAllArbiters() {
        try {

            AuditService.getInstance().log("Show all arbiters");

            List<Arbiter> arbitersList = ArbiterService.getInstance().readAll();

            if (arbitersList.isEmpty()) {
                System.out.println("No arbiters registered yet!");
                return;
            }

            System.out.printf("%-4s | %-10s | %-25s | %-20s%n", "No.", "FIDE ID", "Name", "Title");
            System.out.println("---------------------------------------------------------------");

            AtomicInteger index = new AtomicInteger(1);
            arbitersList.forEach(a -> {
                String fullName = a.getLastName() + " " + a.getFirstName();
                String title = a.getTitle() != null ? a.getTitle().toString() : "-";

                System.out.printf("%-4d | %-10s | %-25s | %-20s%n",
                        index.getAndIncrement(),
                        a.getFideId(),
                        fullName,
                        title);
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void showAllOrganizers() {
        try {

            AuditService.getInstance().log("Show all organizers");

            List<Organizer> organizersList = OrganizerService.getInstance().readAll();

            if (organizersList.isEmpty()) {
                System.out.println("No organizers registered yet!");
                return;
            }

            System.out.printf("%-4s | %-5s | %-25s | %-15s | %-30s%n",
                    "No.", "ID", "Name", "Phone Number", "Email");
            System.out.println("-------------------------------------------------------------------------------");

            AtomicInteger index = new AtomicInteger(1);
            organizersList.forEach(o -> {
                String fullName = o.getLastName() + " " + o.getFirstName();
                String phone = o.getPhoneNumber() != null ? o.getPhoneNumber() : "-";
                String email = o.getEmail() != null ? o.getEmail() : "-";

                System.out.printf("%-4d | %-5d | %-25s | %-15s | %-30s%n",
                        index.getAndIncrement(),
                        o.getPersonId(),
                        fullName,
                        phone,
                        email);
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void showAllPeople() {
        try {

            AuditService.getInstance().log("Show all people");

            List<Person> peopleList = PersonService.getInstance().readAll();

            if (peopleList.isEmpty()) {
                System.out.println("No people registered yet!");
                return;
            }

            System.out.printf("%-4s | %-5s | %-25s | %-13s%n", "No.", "ID", "Name", "FIDE ID");
            System.out.println("----------------------------------------------------------");

            AtomicInteger index = new AtomicInteger(1);
            peopleList.forEach(p -> {
                String fullName = p.getLastName() + " " + p.getFirstName();
                String fideId = p.getFideId() != null ? p.getFideId() : "-";

                System.out.printf("%-4d | %-5d | %-25s | %-13s%n",
                        index.getAndIncrement(),
                        p.getPersonId(),
                        fullName,
                        fideId);
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updatePerson() {
        showAllPeople();


        try{
            List<Person> personList = PersonService.getInstance().readAll();

            if(personList.isEmpty()){
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }


        int personId=0;

        boolean isValid = false;
        while (!isValid) {

            try {
                System.out.print("Type the Person's ID: ");
                personId = Integer.parseInt(scanner.nextLine());
                isValid=true;

            } catch (NumberFormatException e) {
                System.out.println("Please introduce a valid id!");
            }
        }


        try{
            Person person = PersonService.getInstance().readByPersonId(personId);
            if(person!=null){

                System.out.print("First name: ");
                String fn = scanner.nextLine();
                System.out.print("Last name: ");
                String ln = scanner.nextLine();

                String fideId="";

                Player player = PlayerService.getInstance().readByFideId(person.getFideId());
                Arbiter arbiter = ArbiterService.getInstance().readByFideId(person.getFideId());

                if(player==null && arbiter==null){

                    isValid=false;
                    while(!isValid){
                        System.out.print("FIDE ID: ");
                        fideId = scanner.nextLine();
                        if(fideId.matches("[0-9]{1,13}|") || fideId.isEmpty()){
                            isValid=true;
                        }
                        else{
                            System.out.println("Invalid FIDE ID! Introduce a valid one OR press ENTER to skip");
                        }
                    }

                    fideId = fideId.isEmpty()?null:fideId;

                    PersonService.getInstance().update(personId,fn,ln,fideId);

                }
                else{
                    System.out.println("If you wanted to edit the FIDE ID: ");
                    if(player!=null){
                        System.out.println("Can not edit FIDE ID because he is a "+
                                "registered player!");
                        System.out.println("If you still want to edit his FIDE ID, firstly you must delete " +
                                "him from the players registry!");
                    }
                    if(arbiter!=null){
                        System.out.println("Can not edit FIDE ID because he is a "+
                                "registered arbiter!");
                        System.out.println("If you still want to edit his FIDE ID, firstly you must delete " +
                                "him from the arbiters registry!");
                    }

                    PersonService.getInstance().update(personId,fn,ln,person.getFideId());
                }

                System.out.println("Person with id: "+personId+" was updated!");
                AuditService.getInstance().log("Update person with id: "+personId);
            }
            else{
                System.out.println("There is no person registered with the id: "+personId);
            }


        }catch (SQLException e){
            e.printStackTrace();
        }

    }



    public void createPlayer() {

        String fideId=readValidFideId();

        try{
            Person person = PersonService.getInstance().readByFideId(fideId);
            if(person!=null) {

                Player player = PlayerService.getInstance().readByFideId(fideId);
                if(player==null){

                    int rating = readValidFideRating();

                    PlayerTitle title= readValidPlayerTitle();

                    if (title != null) {
                        PlayerService.getInstance().create(new Player(fideId, title, rating));
                    } else {
                        PlayerService.getInstance().create(new Player(fideId, (PlayerTitle) null, rating));
                    }

                    AuditService.getInstance().log("Make person with id: " +person.getPersonId()+
                            " a player with FIDE ID:"+fideId);

                }
                else{
                    System.out.println("There is already a player with the FIDE ID: "+fideId);
                }

            }
            else{
                System.out.println("There is no person with the FIDE ID: "+fideId);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public void createArbiter(){

        String fideId=readValidFideId();

        try{

            Person person = PersonService.getInstance().readByFideId(fideId);
            if(person!=null){

                Arbiter arbiter = ArbiterService.getInstance().readByFideId(fideId);
                if(arbiter==null){
                    ArbiterTitle arbiterTitle = readValidArbiterTitle();

                    if(arbiterTitle!=null){
                        ArbiterService.getInstance().create(new Arbiter(fideId, arbiterTitle));
                    }
                    else{
                        ArbiterService.getInstance().create(new Arbiter(fideId, (ArbiterTitle) null));
                    }

                    AuditService.getInstance().log("Make person with id: " +person.getPersonId()+
                            " an arbiter with FIDE ID:"+fideId);

                }
                else{
                    System.out.println("There is already an arbiter with the FIDE ID: "+fideId);
                }
            }
            else {
                System.out.println("There is no person with the FIDE ID: "+fideId);
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public void createOrganizer(){

        //it's actually the personID
        int organizerId=0;

        boolean isValid = false;
        while (!isValid) {

            try {
                System.out.print("Type the Person's ID: ");
                organizerId = Integer.parseInt(scanner.nextLine());
                isValid=true;

            } catch (NumberFormatException e) {
                System.out.println("Please introduce a valid id!");
            }
        }

        //verify if there is already an organizer with that id.

        try {
            Person person = PersonService.getInstance().readByPersonId(organizerId);
            if(person!=null){

                Organizer organizer = OrganizerService.getInstance().readByOrganizerId(organizerId);
                if (organizer==null) {

                    String phoneNumber= readValidPhoneNumber();
                    phoneNumber = phoneNumber.isEmpty() ?null:phoneNumber;

                    String email=readValidEmail();
                    email = email.isEmpty() ?null:email;

                    OrganizerService.getInstance().create(new Organizer(organizerId,phoneNumber,email));

                    AuditService.getInstance().log("Make person with id: " +person.getPersonId()+
                            " an organizer with organizer ID: "+organizerId);

                } else {
                    System.out.println("There is already an organizer with the ID: "+organizerId);
                }

            }
            else{
                System.out.println("There is no person with the ID: "+organizerId);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void createPerson() {
        System.out.print("First name: ");
        String fn = scanner.nextLine();
        System.out.print("Last name: ");
        String ln = scanner.nextLine();


        String fideId =null;
        boolean isValid=false;

        while(!isValid) {
            System.out.print("Type FIDE ID: ");
            fideId= scanner.nextLine();

            if(fideId.isEmpty()){
                isValid=true;
            }
            else if(fideId.matches("[0-9]{1,13}")){
                try {
                    Person person = PersonService.getInstance().readByFideId(fideId);
                    if(person==null){
                        isValid=true;
                    }
                    else{
                        System.out.println("There is already a person with the FIDE ID: "+fideId);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            else{
                System.out.println("Please introduce a valid fide ID OR press ENTER to skip!");
            }
        }

        fideId = fideId.isEmpty() ?null:fideId;


        try{

            int generatedId=PersonService.getInstance().createAndReturnId(new Person(fideId,fn,ln));

            AuditService.getInstance().log("Create person with id: "+generatedId);

            System.out.println("Created person with id: "+generatedId);

        }catch (SQLException e){
            e.printStackTrace();
        }


    }

    public void deletePlayer() {
        showAllPlayers();

        try{
            List<Player> playerList = PlayerService.getInstance().readAll();

            if(playerList.isEmpty()){
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        String fideId=readValidFideId();

        //verify if he plays in active tournaments

        try{
            Player player = PlayerService.getInstance().readByFideId(fideId);
            if(player!=null) {

                List<Tournament> tournamentList = TournamentPlayerService.getInstance().readAllTournamentsByPlayerId(fideId);

                if(tournamentList.isEmpty()){
                    PlayerService.getInstance().delete(fideId);

                    System.out.println("Player with FIDE ID: "+fideId+" was deleted!");
                    AuditService.getInstance().log("Delete player with FIDE ID: "+fideId);

                }
                else{
                    System.out.println("Can not delete the player because there are " +
                            tournamentList.size() +" tournaments that he plays in.");
                    System.out.println("If you still want to delete him, firstly you must delete him " +
                            "from all the tournaments that he plays in.");
                    System.out.print("Tournament IDs: ");
                    tournamentList.forEach(t-> System.out.print(t.getId()+" ") );
                    System.out.println();

                }

            }
            else{
                System.out.println("There is no player registered with the id: "+fideId);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }


    }
    public void deleteArbiter() {
        showAllArbiters();

        try{
            List<Arbiter> arbiterList = ArbiterService.getInstance().readAll();

            if(arbiterList.isEmpty()){
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        String fideId=readValidFideId();

        //verify if he arbitrates tournaments

        try{
            Arbiter arbiter = ArbiterService.getInstance().readByFideId(fideId);
            if(arbiter!=null) {
                List<Tournament> tournamentList = TournamentArbiterService.getInstance().readAllTournamentsByArbiterId(fideId);

                if(tournamentList.isEmpty()){
                    ArbiterService.getInstance().delete(fideId);

                    System.out.println("Arbiter with FIDE ID: "+fideId+" was deleted!");
                    AuditService.getInstance().log("Delete arbiter with FIDE ID: "+fideId);
                }
                else{
                    System.out.println("Can not delete the arbiter because there are " +
                            tournamentList.size() +" tournaments arbitrated by him.");
                    System.out.println("If you still want to delete him, firstly you must delete him " +
                            "from all the tournaments arbitrated by him!");
                    System.out.print("Tournament IDs: ");
                    tournamentList.forEach(t-> System.out.print(t.getId()+" ") );
                    System.out.println();

                }

            }
            else{
                System.out.println("There is no arbiter registered with the id: "+fideId);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }


    }
    public void deleteOrganizer(){
        showAllOrganizers();


        try{
            List<Organizer> organizerList = OrganizerService.getInstance().readAll();

            if(organizerList.isEmpty()){
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        int organizerId=0;

        boolean isValid = false;
        while (!isValid) {

            try {
                System.out.print("Type the Organizer's ID: ");
                organizerId = Integer.parseInt(scanner.nextLine());
                isValid=true;

            } catch (NumberFormatException e) {
                System.out.println("Please introduce a valid id!");
            }
        }

        try {
            Organizer organizer = OrganizerService.getInstance().readByOrganizerId(organizerId);
            if(organizer!=null){

                List<Tournament> tournamentList = TournamentService.getInstance().readAllByOrganizerId(organizerId);
                if (!tournamentList.isEmpty()) {
                    System.out.println("Can not delete the organizer because there are " +
                            tournamentList.size() +" tournaments organized by him.");
                    System.out.println("If you still want to delete him, firstly you must delete " +
                            "all the tournaments organized by him!");
                    System.out.print("Tournament IDs: ");
                    tournamentList.forEach(t-> System.out.print(t.getId()+" ") );
                    System.out.println();
                }
                else{
                    OrganizerService.getInstance().delete(organizerId);

                    System.out.println("Organizer with ID: "+organizerId+" was deleted!");
                    AuditService.getInstance().log("Delete organizer with organizer ID: "+organizerId);
                }

            }
            else{
                System.out.println("There is no organizer registered with the id: "+organizerId);
            }


        } catch (SQLException e) {
                e.printStackTrace();
            }

    }


    public void deletePerson(){
        showAllPeople();

        try{
            List<Person> personList = PersonService.getInstance().readAll();

            if(personList.isEmpty()){
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        int personId=0;

        boolean isValid = false;
        while (!isValid) {

            try {
                System.out.print("Type the Person's ID: ");
                personId = Integer.parseInt(scanner.nextLine());
                isValid=true;

            } catch (NumberFormatException e) {
                System.out.println("Please introduce a valid id!");
            }
        }

        try{
            Person person = PersonService.getInstance().readByPersonId(personId);
            if(person!=null){

                Organizer organizer = OrganizerService.getInstance().readByOrganizerId(personId);
                Player player = PlayerService.getInstance().readByFideId(person.getFideId());
                Arbiter arbiter = ArbiterService.getInstance().readByFideId(person.getFideId());

                if(player==null && organizer==null && arbiter==null){
                    PersonService.getInstance().delete(personId);

                    System.out.println("Person with id: "+personId+" was deleted!");
                    AuditService.getInstance().log("Delete person with id: "+personId);
                }
                else{
                    if(player!=null){
                        System.out.println("Can not delete the person because he is a "+
                                "registered player!");
                        System.out.println("If you still want to delete him, firstly you must delete " +
                                "him from the players registry!");
                    }
                    if(organizer!=null){
                        System.out.println("Can not delete the person because he is a "+
                                "registered organizer!");
                        System.out.println("If you still want to delete him, firstly you must delete " +
                                "him from the organizers registry!");
                    }
                    if(arbiter!=null){
                        System.out.println("Can not delete the person because he is a "+
                                "registered arbiter!");
                        System.out.println("If you still want to delete him, firstly you must delete " +
                                "him from the arbiters registry!");
                    }
                }
            }
            else{
                System.out.println("There is no person registered with the id: "+personId);
            }


        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void updatePlayer() {

        showAllPlayers();

        try{
            List<Player> playerList = PlayerService.getInstance().readAll();

            if(playerList.isEmpty()){
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        String fideId=readValidFideId();

        try{

            Player player = PlayerService.getInstance().readByFideId(fideId);
            if(player!=null){

                PlayerTitle title= readValidPlayerTitle();

                int rating = readValidFideRating();

                if(title!=null) {
                    PlayerService.getInstance().updateTitle(fideId, title);

                }
                else{
                    PlayerService.getInstance().updateTitle(fideId, (PlayerTitle) null);
                }

                PlayerService.getInstance().updateRating(fideId, rating);

                System.out.println("Player with FIDE ID: "+ fideId+" was updated!");
                AuditService.getInstance().log("Player with FIDE ID: "+ fideId+" was updated");
            }
            else {
                System.out.println("There is no player registered with the id: "+fideId);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }


    }


    public void updateArbiter() {

        showAllArbiters();

        try{
            List<Arbiter> arbiterList = ArbiterService.getInstance().readAll();

            if(arbiterList.isEmpty()){
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }


        String fideId=readValidFideId();

        try{
            Arbiter arbiter = ArbiterService.getInstance().readByFideId(fideId);
            if(arbiter!=null) {

                ArbiterTitle arbiterTitle = readValidArbiterTitle();

                if (arbiterTitle != null) {
                    ArbiterService.getInstance().updateTitle(fideId, arbiterTitle);
                } else {
                    ArbiterService.getInstance().updateTitle(fideId, (ArbiterTitle) null);
                }

                System.out.println("Arbiter with FIDE ID: "+ fideId+" was updated!");
                AuditService.getInstance().log("Arbiter with FIDE ID: "+ fideId+" was updated");

            }
            else{
                System.out.println("There is no arbiter registered with the id: "+fideId);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }



    }


    public void updateOrganizer() {

        showAllOrganizers();

        try{
            List<Organizer> organizerList = OrganizerService.getInstance().readAll();

            if(organizerList.isEmpty()){
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        int organizerId=0;

        boolean isValid = false;
        while (!isValid) {

            try {
                System.out.print("Type the Organizer's ID: ");
                organizerId = Integer.parseInt(scanner.nextLine());
                isValid=true;

            } catch (NumberFormatException e) {
                System.out.println("Please introduce a valid id!");
            }
        }

        try{
        Organizer organizer = OrganizerService.getInstance().readByOrganizerId(organizerId);
        if(organizer!=null) {
            String phoneNumber= readValidPhoneNumber();
            phoneNumber = phoneNumber.isEmpty() ?null:phoneNumber;

            String email=readValidEmail();
            email = email.isEmpty() ?null:email;

            OrganizerService.getInstance().updatePhoneNumber(organizerId,phoneNumber);
            OrganizerService.getInstance().updateEmail(organizerId,email);

            System.out.println("Organizer with id: "+ organizerId+" was updated!");
            AuditService.getInstance().log("Organizer with id: "+ organizerId+" was updated!");

        }
        else{
            System.out.println("There is no organizer registered with the id: "+organizerId);
        }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
