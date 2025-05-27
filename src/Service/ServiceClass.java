package Service;

//import *;
import Entities.*;
import Utils.DatabaseUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ServiceClass {
    private TournamentsManager tournamentsManager;
    private final Scanner scanner;

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
    }

    public void deleteTournament() throws SQLException {
        showAllTournaments();
        System.out.print("Type Tournament's ID: ");
        int tournamentId = Integer.parseInt(scanner.nextLine());

        //remove from TournamentsManager
        tournamentsManager.removeTournament(tournamentId);
        //


        TournamentService.getInstance().delete(tournamentId);
    }


    public void showAllTournaments() throws SQLException{
        String sql="SELECT * FROM tournament";
        ResultSet rs = DatabaseUtils.getConnection().createStatement().executeQuery(sql);

        while (rs.next()){
            Organizer organizer = OrganizerService.getInstance().readByOrganizerId(rs.getInt("organizerId"));
            System.out.println("ID: "+rs.getInt("tournamentId")+
                    " "+rs.getString("name")+
                    " organizat de "+organizer);
        }
    }

    public void showTournamentStartingList() throws SQLException {
        showAllTournaments();
        System.out.print("Type Tournament's ID: ");
        int tournamentId = Integer.parseInt(scanner.nextLine());

        List<TournamentPlayer> tournamentPlayerList = TournamentPlayerService.getInstance().readAllByTournamentId(tournamentId);
        if (tournamentPlayerList.isEmpty()) {
            System.out.println("No players found for this tournament.");
            return;
        }
        //or get starting list from tournament manager
        tournamentPlayerList.stream().sorted((p1,p2)->p2.getRating()-p1.getRating()).forEach(System.out::println);
    }

    public void createTournamentPlayer() throws SQLException{
        showAllTournaments();
        System.out.print("Type Tournament's ID: ");
        int tournamentId = Integer.parseInt(scanner.nextLine());

        List<TournamentPlayer> tournamentPlayerList = TournamentPlayerService.getInstance().readAllByTournamentId(tournamentId);
        if (tournamentPlayerList.isEmpty()) {
            System.out.println("No players found for this tournament.");
        }
        //or get starting list from tournament manager
        tournamentPlayerList.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);

        System.out.print("Type Player's FIDE ID: ");
        String fideId= scanner.nextLine();

        TournamentPlayerService.getInstance().create(fideId,tournamentId);
    }

    public void deleteTournamentPlayer() throws SQLException {

        showAllTournaments();
        System.out.print("Type Tournament's ID: ");
        int tournamentId = Integer.parseInt(scanner.nextLine());

        List<TournamentPlayer> tournamentPlayerList = TournamentPlayerService.getInstance().readAllByTournamentId(tournamentId);
        if (tournamentPlayerList.isEmpty()) {
            System.out.println("No players found for this tournament.");
            return;
        }
        //or get starting list from tournament manager
        tournamentPlayerList.stream()
                .sorted(Comparator.reverseOrder())
                .forEach((p)->System.out.println("FIDE ID: "+p.getFideId()+" "+p) );

        System.out.print("Type Player's FIDE ID: ");
        String fideId= scanner.nextLine();

        TournamentPlayerService.getInstance().delete(fideId,tournamentId);

    }

    public void startTournament() throws SQLException {

        showAllTournaments();
        System.out.print("Type Tournament's ID: ");
        int tournamentId = Integer.parseInt(scanner.nextLine());
        if(tournamentsManager.getTournaments().containsKey(tournamentId)){
            System.out.println("Tournament has already started!");
        }
        else {
            Tournament tournament = TournamentService.getInstance().readByTournamentId(tournamentId);

            if(tournament!=null){
                tournamentsManager.addTournament(tournament);
                List<TournamentPlayer> tournamentPlayerList = TournamentPlayerService
                        .getInstance()
                        .readAllByTournamentId(tournamentId);
                tournamentPlayerList.forEach(tournament::addPlayer);
                tournament.pairingSystem();
            }

        }

    }

    public void showRounds() throws SQLException {

        showAllTournaments();
        System.out.print("Type Tournament's ID: ");
        int tournamentId = Integer.parseInt(scanner.nextLine());
        if(!tournamentsManager.getTournaments().containsKey(tournamentId)){
            System.out.println("Tournament has not started yet!");
        }
        else{
            tournamentsManager.getTournaments().get(tournamentId).showRounds();
        }

    }

    public void setPlayersPoints() throws SQLException {

        showAllTournaments();
        System.out.print("Type Tournament's ID: ");
        int tournamentId = Integer.parseInt(scanner.nextLine());
        if(!tournamentsManager.getTournaments().containsKey(tournamentId)){
            System.out.println("Tournament has not started yet!");
        }
        else{
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
        }

    }

    public void showRanking() throws SQLException {

        showAllTournaments();
        System.out.print("Type Tournament's ID: ");
        int tournamentId = Integer.parseInt(scanner.nextLine());

        if(!tournamentsManager.getTournaments().containsKey(tournamentId)){
            System.out.println("Tournament has not started yet!");
        }
        else{
            List<TournamentPlayer> tournamentPlayerList = TournamentPlayerService.getInstance().readAllByTournamentId(tournamentId);
            if (tournamentPlayerList.isEmpty()) {
                System.out.println("No players found for this tournament.");
                return;
            }
            //or get starting list from tournament manager
            tournamentPlayerList.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);

        }

    }

    public void createTournamentArbiter() throws SQLException {
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
        }

        System.out.println("Type Arbiter's FIDE ID: ");
        String fideId = scanner.nextLine();
        System.out.println("Type Arbiter's role: ");
        String role = scanner.nextLine();

        TournamentArbiterService.getInstance().create(new TournamentArbiter(fideId,tournamentId,role));

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


    public void showTournamentArbiters() throws SQLException {
        showAllTournaments();
        System.out.print("Type Tournament's ID: ");
        int tournamentId = Integer.parseInt(scanner.nextLine());


        List<TournamentArbiter> arbitersList= TournamentArbiterService.getInstance().readByTournamentId(tournamentId);
        arbitersList.forEach(a-> System.out.println("FIDE ID: "+a.getFideId()+" "+a+" Role: "+a.getRole()));

    }

    public void showAllPlayers() throws SQLException {
        List<Player> playersList= PlayerService.getInstance().readAll();
        playersList.stream()
                .sorted((p1,p2)->p2.getRating()- p1.getRating())
                .forEach(p-> System.out.println("FIDE ID: "+p.getFideId()+" "+p));
    }

    public void showAllArbiters() throws SQLException {
        List<Arbiter> arbitersList= ArbiterService.getInstance().readAll();
        arbitersList.forEach(a-> System.out.println("FIDE ID: "+a.getFideId()+" "+a));
    }

    public void showAllOrganizers() throws SQLException {
        List<Organizer> organizersList=OrganizerService.getInstance().readAll();
        organizersList.forEach(o-> System.out.println("ID: "+o.getPersonId()+" "+o+" Phone Number: "+
                o.getPhoneNumber()+" Email: "+o.getEmail()));
    }

    public void showAllPeople() throws SQLException {
        List<Person> peopleList= PersonService.getInstance().readAll();
        peopleList.forEach(p-> System.out.println("Person ID: "+p.getPersonId()+" "+p+" FIDE ID: "+p.getFideId()));
    }

    public void updatePerson() throws SQLException{
        showAllPeople();
        System.out.print("Type the Person's ID: ");
        int personId=Integer.parseInt(scanner.nextLine());
        System.out.print("First name: ");
        String fn = scanner.nextLine();
        System.out.print("Last name: ");
        String ln = scanner.nextLine();
        System.out.print("FIDE ID: ");
        String fideId = scanner.nextLine();
        fideId = fideId.isEmpty()?null:fideId;

        PersonService.getInstance().update(personId,fn,ln,fideId);

    }

    public void createPlayer() throws SQLException{
        System.out.print("Type the Player's FIDE ID: ");
        String fideId=scanner.nextLine();
        System.out.print("Type the Player's FIDE title: ");
        String titleString= scanner.nextLine();
        System.out.print("Type the Player's FIDE rating: ");
        int rating = Integer.parseInt(scanner.nextLine());

        if(!titleString.isEmpty()) {
            PlayerService.getInstance().create(new Player(fideId, PlayerTitle.valueOf(titleString.toUpperCase()), rating));
        }
        else{
            PlayerService.getInstance().create(new Player(fideId, (PlayerTitle) null, rating));
        }
    }

    public void createArbiter() throws SQLException {
        System.out.print("Type the Arbiter's FIDE ID: ");
        String fideId=scanner.nextLine();
        System.out.print("Type the Arbiter's FIDE title: ");
        String titleString= scanner.nextLine();

        if(!titleString.isEmpty()){
            ArbiterService.getInstance().create(new Arbiter(fideId, ArbiterTitle.valueOf(titleString.toUpperCase())));
        }
        else{
            ArbiterService.getInstance().create(new Arbiter(fideId, (ArbiterTitle) null));
        }
    }

    public void createOrganizer() throws SQLException{
        System.out.print("Type the Organizer's ID: ");
        int organizerId=Integer.parseInt(scanner.nextLine());
        System.out.print("Type the Organizer's phone number: ");
        String phoneNumber= scanner.nextLine();
        phoneNumber = phoneNumber.isEmpty() ?null:phoneNumber;
        System.out.print("Type the Organizer's email: ");
        String email=scanner.nextLine();
        email = email.isEmpty() ?null:email;

        OrganizerService.getInstance().create(new Organizer(organizerId,phoneNumber,email));
    }

    public void createPerson() throws SQLException{
        System.out.print("First name: ");
        String fn = scanner.nextLine();
        System.out.print("Last name: ");
        String ln = scanner.nextLine();
        System.out.print("FIDE ID: ");
        String fideId = scanner.nextLine();
        fideId = fideId.isEmpty() ?null:fideId;

        PersonService.getInstance().create(new Person(fideId,fn,ln));
    }

    public void deletePlayer() throws SQLException{
        showAllPlayers();
        System.out.print("Type the Player's FIDE ID: ");
        String fideId=scanner.nextLine();
        PlayerService.getInstance().delete(fideId);
    }
    public void deleteArbiter() throws SQLException{
        showAllArbiters();
        System.out.print("Type the Arbiter's FIDE ID: ");
        String fideId=scanner.nextLine();
        ArbiterService.getInstance().delete(fideId);
    }
    public void deleteOrganizer() throws SQLException{
        showAllOrganizers();
        System.out.print("Type the Organizer's ID: ");
        int organizerId=Integer.parseInt(scanner.nextLine());
        OrganizerService.getInstance().delete(organizerId);
    }
    public void deletePerson() throws SQLException{
        showAllPeople();
        System.out.print("Type the Person's ID: ");
        int personId=Integer.parseInt(scanner.nextLine());
        PersonService.getInstance().delete(personId);

    }

    public void updatePlayer() throws SQLException{
        showAllPlayers();
        System.out.print("Type the Player's FIDE ID: ");
        String fideId=scanner.nextLine();
        System.out.print("Type the Player's FIDE title: ");
        String title = scanner.nextLine();
        System.out.print("Type the Player's FIDE rating: ");
        int rating = Integer.parseInt(scanner.nextLine());

        PlayerService.getInstance().updateTitle(fideId, PlayerTitle.valueOf(title.toUpperCase()));
        PlayerService.getInstance().updateRating(fideId,rating);

    }
    public void updateArbiter() throws SQLException{
        showAllArbiters();
        System.out.print("Type the Arbiter's FIDE ID: ");
        String fideId=scanner.nextLine();
        System.out.print("Type the Arbiter's FIDE title: ");
        String title = scanner.nextLine();

        ArbiterService.getInstance().updateTitle(fideId, ArbiterTitle.valueOf(title.toUpperCase()));

    }
    public void updateOrganizer() throws SQLException{
        showAllOrganizers();
        System.out.print("Type organizer's ID: ");
        int organizerId= Integer.parseInt(scanner.nextLine());
        System.out.print("Type organizer's phone number: ");
        String phoneNumber=scanner.nextLine();
        System.out.print("Type organizer's email: ");
        String email=scanner.nextLine();

        OrganizerService.getInstance().updatePhoneNumber(organizerId,phoneNumber);
        OrganizerService.getInstance().updateEmail(organizerId,email);
    }
}
