import Entities.PlayerTitle;
import Entities.TournamentPlayer;
import Entities.TournamentsManager;
import Service.ServiceClass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Arrays;
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
                    sv.showAllTournaments();
                    fout.write("Show all tournaments,"+ LocalDateTime.now()+"\n");
                    break;
                case "2":
                    try {
                    sv.createTournament();
                    fout.write("Create a tournament,"+ LocalDateTime.now()+"\n");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
                case "3":
                    sv.deleteTournament();
                    fout.write("Delete a tournament,"+ LocalDateTime.now()+"\n");
                    break;
                case "4":
                    sv.showTournamentStartingList();
                    fout.write("Show tournament's starting list,"+ LocalDateTime.now()+"\n");
                    break;
                case "5":
                    sv.createTournamentPlayer();
                    fout.write("Add a player to a tournament,"+ LocalDateTime.now()+"\n");
                    break;
                case "6":
                    sv.deleteTournamentPlayer();
                    fout.write("Remove a player from a tournament,"+ LocalDateTime.now()+"\n");
                    break;
                case "7":
                    sv.startTournament();
                    fout.write("Start a tournament,"+ LocalDateTime.now()+"\n");
                    break;
                case "8":
                    sv.showRounds();
                    fout.write("Show tournament's rounds,"+ LocalDateTime.now()+"\n");
                    break;
                case "9":
                    sv.setPlayersPoints();
                    fout.write("Set points to tournament players,"+ LocalDateTime.now()+"\n");
                    break;
                case "10":
                    sv.showRanking();
                    fout.write("Show the tournament's ranking list,"+ LocalDateTime.now()+"\n");
                    break;
                case "11":
                    sv.createTournamentArbiter();
                    fout.write("Add a tournament arbiter,"+ LocalDateTime.now()+"\n");
                    break;
                case "12":
                    sv.showTournamentArbiters();
                    fout.write("Show tournament's arbiters,"+ LocalDateTime.now()+"\n");
                    break;
                case "13":
                    sv.showAllPlayers();
                    fout.write("Show all players,"+ LocalDateTime.now()+"\n");
                    break;
                case "14":
                    sv.showAllArbiters();
                    fout.write("Show all arbiters,"+ LocalDateTime.now()+"\n");
                    break;
                case "15":
                    sv.showAllOrganizers();
                    fout.write("Show all organizers,"+ LocalDateTime.now()+"\n");
                    break;
                case "16":
                    sv.showAllPeople();
                    fout.write("Show all people,"+ LocalDateTime.now()+"\n");
                    break;
                case "17":
                    sv.updatePerson();
                    fout.write("Update a person,"+ LocalDateTime.now()+"\n");
                    break;
                case "18":
                    sv.createPlayer();
                    fout.write("Create a player,"+ LocalDateTime.now()+"\n");
                    break;
                case "19":
                    sv.createArbiter();
                    fout.write("Create an arbiter,"+ LocalDateTime.now()+"\n");
                    break;
                case "20":
                    sv.createOrganizer();
                    fout.write("Create an organizer,"+ LocalDateTime.now()+"\n");
                    break;
                case "21":
                    sv.createPerson();
                    fout.write("Create a person,"+ LocalDateTime.now()+"\n");
                    break;
                case "22":
                    sv.deletePlayer();
                    fout.write("Delete a player,"+ LocalDateTime.now()+"\n");
                    break;
                case "23":
                    sv.deleteArbiter();
                    fout.write("Delete an arbiter,"+ LocalDateTime.now()+"\n");
                    break;
                case "24":
                    sv.deleteOrganizer();
                    fout.write("Delete an organizer,"+ LocalDateTime.now()+"\n");
                    break;
                case "25":
                    sv.deletePerson();
                    fout.write("Delete a person,"+ LocalDateTime.now()+"\n");
                    break;
                case "26":
                    sv.updatePlayer();
                    fout.write("Update a player,"+ LocalDateTime.now()+"\n");
                    break;
                case "27":
                    sv.updateArbiter();
                    fout.write("Update an arbiter,"+ LocalDateTime.now()+"\n");

                    break;
                case "28":
                    sv.updateOrganizer();
                    fout.write("Update an organizer,"+ LocalDateTime.now()+"\n");

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

        System.out.println("Type a valid option: "+ Arrays.toString(PlayerTitle.values()));
    }
}