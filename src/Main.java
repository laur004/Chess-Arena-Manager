import Service.ServiceClass;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        Scanner obj=new Scanner(System.in);


        String option;
        boolean stop=false;

        ServiceClass sv=new ServiceClass();

        while(true){

            System.out.println("Options:");
            System.out.println("0. Exit app");
            System.out.println("1. Show all tournaments");
            System.out.println("2. Create a tournament");
            System.out.println("3. Remove a tournament");
            System.out.println("4. Show the starting list of a registered tournament");
            System.out.println("5. Add a player to a tournament");
            System.out.println("6. Remove a player from a tournament by FIDE ID");
            System.out.println("7. Start a tournament");
            System.out.println("8. Show the pairings");
            System.out.println("9. Set points to each player");
            System.out.println("10. Show the tournament ranking list");
            System.out.println("11. Add an arbiter to a tournament");
            System.out.println("12. Show the tournament's arbiters");
            System.out.println("13. Show all players");
            System.out.println("14. Show all arbiters");
            System.out.println("15. Show all organizers");
            System.out.println("16. Show all people");
            System.out.println("17. Update person");
            System.out.println("18. Create a player");
            System.out.println("19. Create an arbiter");
            System.out.println("20. Create an organizer");
            System.out.println("21. Create a person");
            System.out.println("22. Delete a player");
            System.out.println("23. Delete an arbiter");
            System.out.println("24. Delete an organizer");
            System.out.println("25. Delete a person");
            System.out.println("26. Update a player");
            System.out.println("27. Update an arbiter");
            System.out.println("28. Update an organizer");
            System.out.println("29. Remove tournament arbiter");
            System.out.println("Type your option: ");
            option=obj.nextLine();


            switch (option) {
                case "0":
                    stop = true;
                    sv.closeService();
                    break;
                case "1":
                    sv.showAllTournaments();
                    break;
                case "2":
                    sv.createTournament();
                    break;
                case "3":
                    sv.deleteTournament();
                    break;
                case "4":
                    sv.showTournamentStartingList();
                    break;
                case "5":
                    sv.createTournamentPlayer();
                    break;
                case "6":
                    sv.deleteTournamentPlayer();
                    break;
                case "7":
                    sv.startTournament();
                    break;
                case "8":
                    sv.showRounds();
                    break;
                case "9":
                    sv.setPlayersPoints();
                    break;
                case "10":
                    sv.showRanking();
                    break;
                case "11":
                    sv.createTournamentArbiter();
                    break;
                case "12":
                    sv.showTournamentArbiters();
                    break;
                case "13":
                    sv.showAllPlayers();
                    break;
                case "14":
                    sv.showAllArbiters();
                    break;
                case "15":
                    sv.showAllOrganizers();
                    break;
                case "16":
                    sv.showAllPeople();
                    break;
                case "17":
                    sv.updatePerson();
                    break;
                case "18":
                    sv.createPlayer();
                    break;
                case "19":
                    sv.createArbiter();
                    break;
                case "20":
                    sv.createOrganizer();
                    break;
                case "21":
                    sv.createPerson();
                    break;
                case "22":
                    sv.deletePlayer();
                    break;
                case "23":
                    sv.deleteArbiter();
                    break;
                case "24":
                    sv.deleteOrganizer();
                    break;
                case "25":
                    sv.deletePerson();
                    break;
                case "26":
                    sv.updatePlayer();
                    break;
                case "27":
                    sv.updateArbiter();
                    break;
                case "28":
                    sv.updateOrganizer();
                    break;
                case "29":
                    sv.deleteTournamentArbiter();
                    break;
                default:
                    System.err.println("Non-valid option! Choose again");
            }

            if(stop)
                break;

            System.out.println('\n');
        }

    }
}