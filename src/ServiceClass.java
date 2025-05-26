import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServiceClass {
    private TournamentsManager tournamentsManager;
    private final Scanner scanner;

    public ServiceClass() {
        tournamentsManager = TournamentsManager.getInstance();
        scanner = new Scanner(System.in);
    }

    public void newTournament(String tournamentName, String organizerFirstName, String organizerLastName) {
        Organizer o = new Organizer(organizerFirstName, organizerLastName);
        Tournament t = new Tournament(tournamentName, o);
        tournamentsManager.addTournament(t);
    }

    public void removeTournament(int i) {
        tournamentsManager.removeTournament(i);
    }

    public void showTournaments() {
        tournamentsManager.showTournaments();
    }

    public void showTournamentPlayers() {
        Tournament t = getTournamentByIndex();
        if (t != null) {
            t.showStartingList();
        }
    }

    public void addPlayer() {
        Tournament t = getTournamentByIndex();
        if (t == null) return;
        if(t.getRounds()==null) {
            System.out.print("First name: ");
            String fn = scanner.nextLine();
            System.out.print("Last name: ");
            String ln = scanner.nextLine();
            System.out.print("Rating: ");
            int rating = Integer.parseInt(scanner.nextLine());

            TournamentPlayer p = new TournamentPlayer(fn, ln, rating);

            t.addPlayer(p);
            System.out.println("Player added.");
        }
        else{
            System.out.println("Tournament has already started!");
        }
    }

    public void removePlayer() {
        Tournament t = getTournamentByIndex();
        if (t == null) return;
        if(t.getRounds()==null) {
            System.out.print("Enter FIDE ID of player to remove: ");
            String id = scanner.nextLine();
            t.removePlayer(id);
            System.out.println("Player removed.");
        }
        else{
            System.out.println("Tournament has already started!");
        }
    }

    public void startTournament() {
        Tournament t = getTournamentByIndex();
        if (t != null) {
            t.pairingSystem();
            System.out.println("Tournament started.");
        }
    }

    public void showRounds() {
        Tournament t = getTournamentByIndex();
        if (t != null) {
            t.showRounds();
        }
    }

    public void setPlayersPoints() {
        Tournament t = getTournamentByIndex();
        if (t != null) {
            t.setPointsToAllPLayers();
            //System.out.println("Points updated.");
        }
    }

    public void showRanking() {
        Tournament t = getTournamentByIndex();
        if (t != null) {
            t.showRanking();
        }
    }

    public void setTournamentArbiter() {
        Tournament t = getTournamentByIndex();
        if (t == null) return;

        System.out.print("Arbiter first name: ");
        String fn = scanner.nextLine();
        System.out.print("Arbiter last name: ");
        String ln = scanner.nextLine();
        t.setArbiter(new Arbiter(fn, ln));
        System.out.println("Arbiter assigned.");
    }

    public void showTournamentArbiter() {
        Tournament t = getTournamentByIndex();
        if (t != null && t.getArbiter() != null) {
            System.out.println("Arbiter: " + t.getArbiter());
        } else {
            System.out.println("No arbiter assigned.");
        }
    }

    public void showAllPlayers() throws SQLException {
        List<Player> playersList=PlayerService.getInstance().readAll();
        playersList.forEach(p-> System.out.println("FIDE ID: "+p.getFideId()+" "+p));
    }

    public void showAllArbiters() throws SQLException {
        List<Arbiter> arbitersList=ArbiterService.getInstance().readAll();
        arbitersList.forEach(a-> System.out.println("FIDE ID: "+a.getFideId()+" "+a));
    }

    public void showAllOrganizers() throws SQLException {
        List<Organizer> organizersList=OrganizerService.getInstance().readAll();
        organizersList.forEach(o-> System.out.println("ID: "+o.getPersonId()+" "+o+" Phone Number: "+
                o.getPhoneNumber()+" Email: "+o.getEmail()));
    }

    public void showAllPeople() throws SQLException {
        List<Person> peopleList=PersonService.getInstance().readAll();
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

        PersonService.getInstance().update(personId,fn,ln,fideId);

    }

    public void createPlayer() throws SQLException{
        System.out.print("Type the Player's FIDE ID: ");
        String fideId=scanner.nextLine();
        System.out.println("Type the Player's FIDE title");
        String titleString= scanner.nextLine();
        System.out.println("Type the Player's FIDE rating");
        int rating = Integer.parseInt(scanner.nextLine());

        PlayerService.getInstance().create(new Player(fideId,PlayerTitle.valueOf(titleString.toUpperCase()),rating));
    }

    public void createArbiter() throws SQLException {
        System.out.print("Type the Arbiter's FIDE ID: ");
        String fideId=scanner.nextLine();
        System.out.print("Type the Arbiter's FIDE title: ");
        String titleString= scanner.nextLine();

        ArbiterService.getInstance().create(new Arbiter(fideId,ArbiterTitle.valueOf(titleString.toUpperCase())));
    }

    public void createOrganizer() throws SQLException{
        System.out.print("Type the Organizer's ID: ");
        int organizerId=Integer.parseInt(scanner.nextLine());
        System.out.print("Type the Organizer's phone number: ");
        String phoneNumber= scanner.nextLine();
        System.out.print("Type the Organizer's email: ");
        String email=scanner.nextLine();

        OrganizerService.getInstance().create(new Organizer(organizerId,phoneNumber,email));
    }

    public void createPerson() throws SQLException{
        System.out.print("First name: ");
        String fn = scanner.nextLine();
        System.out.print("Last name: ");
        String ln = scanner.nextLine();
        System.out.print("FIDE ID: ");
        String fideId = scanner.nextLine();

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

        PlayerService.getInstance().updateTitle(fideId,PlayerTitle.valueOf(title.toUpperCase()));
        PlayerService.getInstance().updateRating(fideId,rating);

    }
    public void updateArbiter() throws SQLException{
        showAllArbiters();
        System.out.print("Type the Arbiter's FIDE ID: ");
        String fideId=scanner.nextLine();
        System.out.print("Type the Arbiter's FIDE title: ");
        String title = scanner.nextLine();

        ArbiterService.getInstance().updateTitle(fideId,ArbiterTitle.valueOf(title.toUpperCase()));

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

    private Tournament getTournamentByIndex() {
        showTournaments();
        System.out.print("Enter tournament index: ");
        int idx;
        try {
            idx = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
            return null;
        }

        if (idx < 0 || idx >= tournamentsManager.getTournaments().size()) {
            System.out.println("Index out of bounds.");
            return null;
        }

        return tournamentsManager.getTournaments().get(idx);
    }
}
