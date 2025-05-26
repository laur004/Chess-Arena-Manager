import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){



        TournamentsManager t=TournamentsManager.getInstance();
        t.addTournament(new Tournament("CN",new Organizer("SAH","FR")));
        t.addTournament(new Tournament("Cupa de Primavara",new Organizer("Mihai","Petre")));
        t.addTournament(new Tournament("Cupa de Primavara",new Organizer("Mihai","Georgescu")));
        //t.showTournaments();
        t.removeTournament(2);
        //System.out.println("Dupa eliminarea turneului 2:");
        //t.showTournaments();


        TournamentPlayer p1=new TournamentPlayer("Magnus", "Carlsen", 2838,PlayerTitle.GM, "1503014");
        TournamentPlayer p2=new TournamentPlayer("Hikaru","Nakamura",2801,PlayerTitle.GM, "2016192");
        TournamentPlayer p3=new TournamentPlayer("Claudiu","Popescu",1500);
        TournamentPlayer p4=new TournamentPlayer("Andrei","Pop",1500);
        TournamentPlayer p5=new TournamentPlayer("Mircea","Popa",1500);

        //System.out.println(p3.getFideID());
        //System.out.println(p4.getFideID());

        t.getTournaments().get(0).addPlayer(p2);
        t.getTournaments().get(0).addPlayer(p3);
        t.getTournaments().get(0).addPlayer(p4);
        t.getTournaments().get(0).addPlayer(p5);
        //t.getTournaments().get(0).showStartingList();


        t.getTournaments().get(0).addPlayer(p1);
        //t.getTournaments().get(0).showStartingList();

        //t.getTournaments().get(0).removePlayer("1503014");
        //t.getTournaments().get(0).showStartingList();

//        t.getTournaments().get(0).pairingSystem();
//        for(int i=0;i<t.getTournaments().get(0).getRounds().size();i++) {
//            System.out.println("Runda "+(i+1)+":");
//            t.getTournaments().get(0).getRounds().get(i).printRound();
//        }
        //t.getTournaments().get(0).setPointsToAllPLayers();
        //t.getTournaments().get(0).showRanking();


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
            System.out.println("6. Remove a player from a tournament by ID");
            System.out.println("7. Start a tournament");
            System.out.println("8. Show the pairings");
            System.out.println("9. Set points to each player");
            System.out.println("10. Show the tournament ranking list");
            System.out.println("11. Assign an arbiter");
            System.out.println("12. Show the tournament arbiter");
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
            System.out.println("Type your option: ");
            option=obj.nextLine();


            switch (option) {
                case "0":
                    stop = true;
                    break;
                case "1":
                    sv.showTournaments();
                    break;
                case "2": {
                    String tn, fn, ln;
                    System.out.print("Tournament name: ");
                    tn = obj.nextLine();
                    System.out.print("Organizer's first name: ");
                    fn = obj.nextLine();
                    System.out.print("Organizer's last name: ");
                    ln = obj.nextLine();
                    sv.newTournament(tn, fn, ln);
                }
                break;
                case "3": {
                    sv.showTournaments();
                    System.out.print("Type the tournament index: ");
                    int i = Integer.parseInt(obj.nextLine());
                    sv.removeTournament(i-1);
                }
                break;
                case "4":
                    sv.showTournamentPlayers();
                    break;
                case "5":
                    sv.addPlayer();
                    break;
                case "6":
                    sv.removePlayer();
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
                    sv.setTournamentArbiter();
                    break;
                case "12":
                    sv.showTournamentArbiter();
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
                default:
                    System.err.println("Non-valid option! Choose again");
            }

            if(stop)
                break;

            System.out.println('\n');
        }
    }
}