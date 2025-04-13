import java.util.Scanner;

public class ServiceClass {
    private TournamentsManager tournamentsManager;
    private final Scanner scanner;

    public ServiceClass() {
        tournamentsManager = TournamentsManager.getInstance();
        scanner = new Scanner(System.in);
    }

    public void newTournament(String tournamentName, String organizerFirstName, String organizerLastName) {
        Person o = new Person(organizerFirstName, organizerLastName);
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

        System.out.print("First name: ");
        String fn = scanner.nextLine();
        System.out.print("Last name: ");
        String ln = scanner.nextLine();
        System.out.print("Rating: ");
        int rating = Integer.parseInt(scanner.nextLine());

        Player p = new Player(fn, ln, rating);

        t.addPlayer(p);
        System.out.println("Player added.");
    }

    public void removePlayer() {
        Tournament t = getTournamentByIndex();
        if (t == null) return;

        System.out.print("Enter FIDE ID of player to remove: ");
        String id = scanner.nextLine();
        t.removePlayer(id);
        System.out.println("Player removed.");
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
            int i = 1;
            for (Round r : t.getRounds()) {
                System.out.println("Round " + i++ + ":");
                r.printRound();
            }
        }
    }

    public void setPlayersPoints() {
        Tournament t = getTournamentByIndex();
        if (t != null) {
            t.setPointsToAllPLayers();
            System.out.println("Points updated.");
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
