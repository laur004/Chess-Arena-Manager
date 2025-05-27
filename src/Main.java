import Entities.PlayerTitle;
import Entities.TournamentPlayer;
import Entities.TournamentsManager;
import Service.ServiceClass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {


        Scanner obj=new Scanner(System.in);

        File auditFile = new File("audit.csv");
        boolean fileExists = auditFile.exists();
        FileWriter fout = new FileWriter(auditFile,true);
        if(!fileExists){
            fout.write("ACTION_NAME,TIMESTAMP\n");
        }


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
                    fout.write("Exit App,"+ LocalDateTime.now()+"\n");
                    break;
                case "1":
                    try {
                        sv.showAllTournaments();
                        fout.write("Show all tournaments,"+ LocalDateTime.now()+"\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "2":
                    try {
                    sv.createTournament();
                    fout.write("Create a tournament,"+ LocalDateTime.now()+"\n");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
                case "3": try {
                    sv.deleteTournament();
                    fout.write("Delete a tournament,"+ LocalDateTime.now()+"\n");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
                case "4":
                    try {
                        sv.showTournamentStartingList();
                        fout.write("Show tournament's starting list,"+ LocalDateTime.now()+"\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "5":
                    try {
                        sv.createTournamentPlayer();
                        fout.write("Create a tournament player,"+ LocalDateTime.now()+"\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "6":
                    try {
                        sv.deleteTournamentPlayer();
                        fout.write("Delete a tournament player,"+ LocalDateTime.now()+"\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "7":
                    try {
                        sv.startTournament();
                        fout.write("Start a tournament,"+ LocalDateTime.now()+"\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "8":
                    try {
                        sv.showRounds();
                        fout.write("Show tournament's rounds,"+ LocalDateTime.now()+"\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "9":
                    try {
                        sv.setPlayersPoints();
                        fout.write("Set points to tournament players,"+ LocalDateTime.now()+"\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "10":
                    try {
                        sv.showRanking();
                        fout.write("Show the tournament's ranking list,"+ LocalDateTime.now()+"\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "11":
                    try{
                        sv.createTournamentArbiter();
                        fout.write("Add a tournament arbiter,"+ LocalDateTime.now()+"\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "12":
                    try{
                        sv.showTournamentArbiters();
                        fout.write("Show tournament's arbiters,"+ LocalDateTime.now()+"\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "13":
                    try{
                        sv.showAllPlayers();
                        fout.write("Show all players,"+ LocalDateTime.now()+"\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "14":
                    try{
                        sv.showAllArbiters();
                        fout.write("Show all arbiters,"+ LocalDateTime.now()+"\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "15":
                    try{
                        sv.showAllOrganizers();
                        fout.write("Show all organizers,"+ LocalDateTime.now()+"\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "16":
                    try{
                        sv.showAllPeople();
                        fout.write("Show all people,"+ LocalDateTime.now()+"\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "17":
                    try{
                        sv.updatePerson();
                        fout.write("Update a person,"+ LocalDateTime.now()+"\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "18":
                    try{
                        sv.createPlayer();
                        fout.write("Create a player,"+ LocalDateTime.now()+"\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "19":
                    try{
                        sv.createArbiter();
                        fout.write("Create an arbiter,"+ LocalDateTime.now()+"\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "20":
                    try{
                        sv.createOrganizer();
                        fout.write("Create an organizer,"+ LocalDateTime.now()+"\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "21":
                    try{
                        sv.createPerson();
                        fout.write("Create a person,"+ LocalDateTime.now()+"\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "22":
                    try{
                        sv.deletePlayer();
                        fout.write("Delete a player,"+ LocalDateTime.now()+"\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "23":
                    try{
                        sv.deleteArbiter();
                        fout.write("Delete an arbiter,"+ LocalDateTime.now()+"\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "24":
                    try{
                        sv.deleteOrganizer();
                        fout.write("Delete an organizer,"+ LocalDateTime.now()+"\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "25":
                    try{
                        sv.deletePerson();
                        fout.write("Delete a person,"+ LocalDateTime.now()+"\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "26":
                    try{
                        sv.updatePlayer();
                        fout.write("Update a player,"+ LocalDateTime.now()+"\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "27":
                    try{
                        sv.updateArbiter();
                        fout.write("Update an arbiter,"+ LocalDateTime.now()+"\n");

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "28":
                    try{
                        sv.updateOrganizer();
                        fout.write("Update an organizer,"+ LocalDateTime.now()+"\n");

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "29":
                    try{
                        sv.deleteTournamentArbiter();
                        fout.write("Delete a tournament arbiter,"+ LocalDateTime.now()+"\n");
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

        fout.close();
    }
}