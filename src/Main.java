import Entities.PlayerTitle;
import Entities.TournamentPlayer;
import Entities.TournamentsManager;
import Service.ServiceClass;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){


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
            System.out.println("7. Start a tournament??");
            System.out.println("8. Show the pairings??");
            System.out.println("9. Set points to each player??");
            System.out.println("10. Show the tournament ranking list??");
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
                    break;
                case "1":
                    try {
                        sv.showAllTournaments();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "2":
                    try {
                    sv.createTournament();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
                case "3": try {
                    sv.deleteTournament();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
                case "4":
                    try {
                        sv.showTournamentStartingList();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "5":
                    try {
                        sv.createTournamentPlayer();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "6":
                    try {
                        sv.deleteTournamentPlayer();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "7":
                    try {
                        sv.startTournament();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "8":
                    try {
                        sv.showRounds();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "9":
                    try {
                        sv.setPlayersPoints();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "10":
                    try {
                        sv.showRanking();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "11":
                    try{
                        sv.createTournamentArbiter();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "12":
                    try{
                        sv.showTournamentArbiters();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "13":
                    try{
                        sv.showAllPlayers();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "14":
                    try{
                        sv.showAllArbiters();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "15":
                    try{
                        sv.showAllOrganizers();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "16":
                    try{
                        sv.showAllPeople();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "17":
                    try{
                        sv.updatePerson();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "18":
                    try{
                        sv.createPlayer();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "19":
                    try{
                        sv.createArbiter();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "20":
                    try{
                        sv.createOrganizer();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "21":
                    try{
                        sv.createPerson();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "22":
                    try{
                        sv.deletePlayer();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "23":
                    try{
                        sv.deleteArbiter();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "24":
                    try{
                        sv.deleteOrganizer();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "25":
                    try{
                        sv.deletePerson();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "26":
                    try{
                        sv.updatePlayer();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "27":
                    try{
                        sv.updateArbiter();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "28":
                    try{
                        sv.updateOrganizer();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "29":
                    try{
                        sv.deleteTournamentArbiter();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
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