import Service.ServiceClass;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        ServiceClass sv = new ServiceClass();

        Scanner obj = new Scanner(System.in);
        boolean stop = false;
        String option;

        while (true) {
            System.out.println("==== MENU ====");
            System.out.println("0. Exit app");

            System.out.println("\n--- Tournaments ---");
            System.out.println("1. Show all tournaments");
            System.out.println("2. Create a tournament");
            System.out.println("3. Delete a tournament");
            System.out.println("4. Show starting list of a tournament");
            System.out.println("5. Add a player to a tournament");
            System.out.println("6. Remove a player from a tournament");
            System.out.println("7. Add an arbiter to a tournament");
            System.out.println("8. Remove an arbiter from a tournament");
            System.out.println("9. Show arbiters of a tournament");
            System.out.println("10. Start a tournament");
            System.out.println("11. Show tournament pairings");
            System.out.println("12. Set points to players");
            System.out.println("13. Show tournament ranking");

            System.out.println("\n--- Players ---");
            System.out.println("14. Show all players");
            System.out.println("15. Create a player");
            System.out.println("16. Update a player");
            System.out.println("17. Delete a player");

            System.out.println("\n--- Arbiters ---");
            System.out.println("18. Show all arbiters");
            System.out.println("19. Create an arbiter");
            System.out.println("20. Update an arbiter");
            System.out.println("21. Delete an arbiter");

            System.out.println("\n--- Organizers ---");
            System.out.println("22. Show all organizers");
            System.out.println("23. Create an organizer");
            System.out.println("24. Update an organizer");
            System.out.println("25. Delete an organizer");

            System.out.println("\n--- Persons ---");
            System.out.println("26. Show all people");
            System.out.println("27. Create a person");
            System.out.println("28. Update a person");
            System.out.println("29. Delete a person");

            System.out.print("\nType your option: ");
            option = obj.nextLine();

            switch (option) {
                case "0":
                    stop = true;
                    sv.closeService();
                    break;

                // Tournaments
                case "1": sv.showAllTournaments(); break;
                case "2": sv.createTournament(); break;
                case "3": sv.deleteTournament(); break;
                case "4": sv.showTournamentStartingList(); break;
                case "5": sv.createTournamentPlayer(); break;
                case "6": sv.deleteTournamentPlayer(); break;
                case "7": sv.createTournamentArbiter(); break;
                case "8": sv.deleteTournamentArbiter(); break;
                case "9": sv.showTournamentArbiters(); break;
                case "10": sv.startTournament(); break;
                case "11": sv.showRounds(); break;
                case "12": sv.setPlayersPoints(); break;
                case "13": sv.showRanking(); break;

                // Players
                case "14": sv.showAllPlayers(); break;
                case "15": sv.createPlayer(); break;
                case "16": sv.updatePlayer(); break;
                case "17": sv.deletePlayer(); break;

                // Arbiters
                case "18": sv.showAllArbiters(); break;
                case "19": sv.createArbiter(); break;
                case "20": sv.updateArbiter(); break;
                case "21": sv.deleteArbiter(); break;

                // Organizers
                case "22": sv.showAllOrganizers(); break;
                case "23": sv.createOrganizer(); break;
                case "24": sv.updateOrganizer(); break;
                case "25": sv.deleteOrganizer(); break;

                // Persons
                case "26": sv.showAllPeople(); break;
                case "27": sv.createPerson(); break;
                case "28": sv.updatePerson(); break;
                case "29": sv.deletePerson(); break;

                default:
                    System.err.println("Non-valid option! Choose again");
            }

            if (stop)
                break;

            System.out.println('\n');
        }

    }
}