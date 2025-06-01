package Service;

//import *;
import Entities.*;
import Utils.DatabaseUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ServiceClass {
    private TournamentsManager tournamentsManager;
    private final Scanner scanner;


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
                    System.out.println("Invalid title. Valid options: " + Arrays.toString(PlayerTitle.values()));
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
                    System.out.println("Invalid title. Valid options: " + Arrays.toString(ArbiterTitle.values()) + "OR press ENTER to skip");
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

    public void createTournament() throws SQLException{
        System.out.print("Tournament name: ");
        String tournamentName = scanner.nextLine();
        System.out.print("Organizer's ID: ");
        int organizerId = Integer.parseInt(scanner.nextLine());
        TournamentService.getInstance().create(new Tournament(tournamentName, organizerId));

        System.out.println("Tournament has been created!");
    }

    public void deleteTournament() {

        showAllTournaments();

        try{
            int tournamentId = readValidTournamentId();

            //remove from TournamentsManager
            tournamentsManager.removeTournament(tournamentId);
            //
            TournamentService.getInstance().delete(tournamentId);

        }catch (SQLException e){
            e.printStackTrace();
        }

        System.out.println("Tournament has been deleted!");
    }


    public void showAllTournaments(){

        try{
            List<Tournament> tournamentsList=TournamentService.getInstance().readAll();
            tournamentsList.forEach(t->{

                Organizer organizer;
                try {
                    organizer = OrganizerService.getInstance().readByOrganizerId(t.getOrganizerId());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Tournament ID: "+t.getId()+
                        " "+t.getName()+
                        " organized by "+organizer);

            });
        } catch ( SQLException e) {
            e.printStackTrace();
        }
    }

    public void showTournamentStartingList() {

        showAllTournaments();

        int tournamentId = readValidTournamentId();

            try {

                List<TournamentPlayer> tournamentPlayerList = TournamentPlayerService.getInstance().readAllByTournamentId(tournamentId);
                if (tournamentPlayerList.isEmpty()) {
                    System.out.println("No players found for this tournament.");
                    return;
                }
                //or get starting list from tournament manager
                tournamentPlayerList.stream().sorted((p1,p2)->p2.getRating()-p1.getRating()).forEach(System.out::println);

            }catch (SQLException e){
                e.printStackTrace();
            }

    }

    public void createTournamentPlayer() {

        showAllTournaments();

        int tournamentId = readValidTournamentId();

        List<TournamentPlayer> tournamentPlayerList=null;

        try {
            tournamentPlayerList = TournamentPlayerService.getInstance().readAllByTournamentId(tournamentId);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (tournamentPlayerList.isEmpty()) {
            System.out.println("No players found for this tournament.");
        }
        else{
            tournamentPlayerList.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);
        }


        String fideId= readValidFideId();

        try{
            TournamentPlayerService.getInstance().create(fideId,tournamentId);
        }catch (SQLException e){
            e.printStackTrace();
        }

        System.out.println("Player was added to the tournament");

    }

    public void deleteTournamentPlayer() {

        showAllTournaments();

        int tournamentId = readValidTournamentId();

        List<TournamentPlayer> tournamentPlayerList=null;

        try{
            tournamentPlayerList = TournamentPlayerService.getInstance().readAllByTournamentId(tournamentId);
        }catch (SQLException e){
            e.printStackTrace();
        }

        if (tournamentPlayerList.isEmpty()) {
            System.out.println("No players found for this tournament.");
            return;
        }
        //or get starting list from tournament manager
        tournamentPlayerList.stream()
                .sorted(Comparator.reverseOrder())
                .forEach((p)->System.out.println("FIDE ID: "+p.getFideId()+" "+p) );

        String fideId=readValidFideId();

        try {
            TournamentPlayerService.getInstance().delete(fideId,tournamentId);
        }catch (SQLException e){
            e.printStackTrace();
        }

        System.out.println("Player was removed from the tournament!");
    }

    public void startTournament() {

        showAllTournaments();

        int tournamentId = readValidTournamentId();

        if(tournamentsManager.getTournaments().containsKey(tournamentId)){
            System.out.println("Tournament has already started!");
        }
        else {
            try {
                Tournament tournament = TournamentService.getInstance().readByTournamentId(tournamentId);

                if (tournament != null) {

                    List<TournamentPlayer> tournamentPlayerList = TournamentPlayerService
                            .getInstance()
                            .readAllByTournamentId(tournamentId);
                    tournamentPlayerList.forEach(tournament::addPlayer);
                    tournament.pairingSystem();

                    tournamentsManager.addTournament(tournament);

                }
            }catch (IllegalStateException e){
                System.out.println(e.getMessage());
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }

    }

    public void showRounds() {

        showAllTournaments();

        int tournamentId = readValidTournamentId();

        if(!tournamentsManager.getTournaments().containsKey(tournamentId)){
            System.out.println("Tournament has not started yet!");
        }
        else{
            tournamentsManager.getTournaments().get(tournamentId).showRounds();
        }

    }

    public void setPlayersPoints() {

        showAllTournaments();

        int tournamentId = readValidTournamentId();

        if(!tournamentsManager.getTournaments().containsKey(tournamentId)){
            System.out.println("Tournament has not started yet!");
        }
        else{
            try {
                tournamentsManager.getTournaments().get(tournamentId).showRounds();


                List<TournamentPlayer> tournamentPlayerList = TournamentPlayerService
                        .getInstance()
                        .readAllByTournamentId(tournamentId);

                tournamentPlayerList.forEach(p->{
                    System.out.println(p);
                    System.out.print("Set points: ");
                    double points = Double.parseDouble(scanner.nextLine());
                    p.setPoints(points);
                });

                TournamentPlayerService.getInstance().updateAllTournamentPlayersPoints(tournamentId,tournamentPlayerList);

            }catch (SQLException e){
                e.printStackTrace();
            }

        }

    }

    public void showRanking() {

        showAllTournaments();

        int tournamentId = readValidTournamentId();

        if(!tournamentsManager.getTournaments().containsKey(tournamentId)){
            System.out.println("Tournament has not started yet!");
        }
        else{
            try {
                List<TournamentPlayer> tournamentPlayerList = TournamentPlayerService.getInstance().readAllByTournamentId(tournamentId);
                if (tournamentPlayerList.isEmpty()) {
                    System.out.println("No players found for this tournament.");
                    return;
                }
                //or get starting list from tournament manager
                tournamentPlayerList.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);

            }catch (SQLException e){
                e.printStackTrace();
            }

        }

    }


    public void createTournamentArbiter() {
        showAllTournaments();

        int tournamentId = readValidTournamentId();

        try {
            List<TournamentArbiter> arbitersList = TournamentArbiterService.getInstance().readByTournamentId(tournamentId);
            if (!arbitersList.isEmpty()) {
                System.out.println("Current tournament's arbiters:");
                arbitersList.forEach(a -> System.out.println("FIDE ID: " + a.getFideId() + " " + a + " Role: " + a.getRole()));
            } else {
                System.out.println("This tournament has no arbiters yet!");
            }

            System.out.println("Type Arbiter's FIDE ID: ");
            String fideId = scanner.nextLine();
            System.out.println("Type Arbiter's role: ");
            String role = scanner.nextLine();

            TournamentArbiterService.getInstance().create(new TournamentArbiter(fideId, tournamentId, role));

        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    public void deleteTournamentArbiter() throws SQLException {
        showAllTournaments();
        System.out.print("Type Tournament's ID: ");
        int tournamentId = Integer.parseInt(scanner.nextLine());

        List<TournamentArbiter> arbitersList= TournamentArbiterService.getInstance().readByTournamentId(tournamentId);
        if(!arbitersList.isEmpty()){
            System.out.println("Current tournament's arbiters:");
            arbitersList.forEach(a-> System.out.println("FIDE ID: "+a.getFideId()+" "+a+" Role: "+a.getRole()));
        }
        else{
            System.out.println("This tournament has no arbiters yet!");
            return;
        }

        System.out.println("Type Arbiter's FIDE ID: ");
        String fideId = scanner.nextLine();


        TournamentArbiterService.getInstance().delete(fideId,tournamentId);

    }


    public void showTournamentArbiters(){
        showAllTournaments();

        int tournamentId = readValidTournamentId();

        try {
            List<TournamentArbiter> arbitersList= TournamentArbiterService.getInstance().readByTournamentId(tournamentId);
            arbitersList.forEach(a-> System.out.println("FIDE ID: "+a.getFideId()+" "+a+" Role: "+a.getRole()));

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void showAllPlayers() {

        try{
            List<Player> playersList= PlayerService.getInstance().readAll();
            playersList.stream()
                    .sorted((p1,p2)->p2.getRating()- p1.getRating())
                    .forEach(p-> System.out.println("FIDE ID: "+p.getFideId()+" "+p));
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void showAllArbiters(){

        try{
            List<Arbiter> arbitersList= ArbiterService.getInstance().readAll();
            arbitersList.forEach(a-> System.out.println("FIDE ID: "+a.getFideId()+" "+a));

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void showAllOrganizers() {

        try{
            List<Organizer> organizersList=OrganizerService.getInstance().readAll();
            organizersList.forEach(o-> System.out.println("ID: "+o.getPersonId()+" "+o+" Phone Number: "+
                    o.getPhoneNumber()+" Email: "+o.getEmail()));
        }catch (SQLException e){
            e.printStackTrace();
        }


    }

    public void showAllPeople() {

        try{
            List<Person> peopleList= PersonService.getInstance().readAll();
            peopleList.forEach(p-> System.out.println("Person ID: "+p.getPersonId()+" "+p+" FIDE ID: "+p.getFideId()));

        }catch (SQLException e){
            e.printStackTrace();
        }


    }

    public void updatePerson() {
        showAllPeople();

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

        System.out.print("First name: ");
        String fn = scanner.nextLine();
        System.out.print("Last name: ");
        String ln = scanner.nextLine();

        String fideId="";

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


        try{
            PersonService.getInstance().update(personId,fn,ln,fideId);
        }catch (SQLException e){
            e.printStackTrace();
        }



    }

    public void createPlayer() {

        String fideId=readValidFideId();

        PlayerTitle title= readValidPlayerTitle();

        int rating = readValidFideRating();


        try{
            if(title!=null) {
                PlayerService.getInstance().create(new Player(fideId, title, rating));
            }
            else{
                PlayerService.getInstance().create(new Player(fideId, (PlayerTitle) null, rating));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void createArbiter(){

        String fideId=readValidFideId();

        ArbiterTitle arbiterTitle = readValidArbiterTitle();

        try{
            if(arbiterTitle==null){
                ArbiterService.getInstance().create(new Arbiter(fideId, arbiterTitle));
            }
            else{
                ArbiterService.getInstance().create(new Arbiter(fideId, (ArbiterTitle) null));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void createOrganizer(){

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



        String phoneNumber= readValidPhoneNumber();
        phoneNumber = phoneNumber.isEmpty() ?null:phoneNumber;

        String email=readValidEmail();
        email = email.isEmpty() ?null:email;


        try{
            OrganizerService.getInstance().create(new Organizer(organizerId,phoneNumber,email));

        }catch (SQLException e){
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

            if(fideId.matches("[0-9]{1,13}") || fideId.isEmpty()){
                isValid=true;
            }
            else{
                System.out.println("Please introduce a valid fide ID OR press ENTER to skip!");
            }
        }

        fideId = fideId.isEmpty() ?null:fideId;


        try{
            PersonService.getInstance().create(new Person(fideId,fn,ln));

        }catch (SQLException e){
            e.printStackTrace();
        }


    }

    public void deletePlayer() {
        showAllPlayers();

        String fideId=readValidFideId();


        try{
            PlayerService.getInstance().delete(fideId);

        }catch (SQLException e){
            e.printStackTrace();
        }


    }
    public void deleteArbiter() {
        showAllArbiters();

        String fideId=readValidFideId();

        try{
            ArbiterService.getInstance().delete(fideId);

        }catch (SQLException e){
            e.printStackTrace();
        }


    }
    public void deleteOrganizer(){
        showAllOrganizers();

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
            OrganizerService.getInstance().delete(organizerId);

        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    public void deletePerson(){
        showAllPeople();

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
            PersonService.getInstance().delete(personId);

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void updatePlayer() {

        showAllPlayers();

        String fideId=readValidFideId();

        PlayerTitle title= readValidPlayerTitle();

        int rating = readValidFideRating();


        try{
            if(title!=null) {
                PlayerService.getInstance().create(new Player(fideId, title, rating));
            }
            else{
                PlayerService.getInstance().create(new Player(fideId, (PlayerTitle) null, rating));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }



        try{
            PlayerService.getInstance().updateTitle(fideId, title);
            PlayerService.getInstance().updateRating(fideId,rating);

        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    public void updateArbiter() {

        showAllArbiters();

        String fideId=readValidFideId();

        ArbiterTitle arbiterTitle = readValidArbiterTitle();

        try{
            if(arbiterTitle==null){
                ArbiterService.getInstance().create(new Arbiter(fideId, arbiterTitle));
            }
            else{
                ArbiterService.getInstance().create(new Arbiter(fideId, (ArbiterTitle) null));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }



    }
    public void updateOrganizer() {

        showAllOrganizers();

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

        String phoneNumber= readValidPhoneNumber();
        phoneNumber = phoneNumber.isEmpty() ?null:phoneNumber;

        String email=readValidEmail();
        email = email.isEmpty() ?null:email;


        try{
            OrganizerService.getInstance().create(new Organizer(organizerId,phoneNumber,email));

        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
