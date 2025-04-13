import java.util.ArrayList;

public class TournamentsManager {
    private ArrayList<Tournament> tournaments;
    private static TournamentsManager instance=null;

    private TournamentsManager(){
        tournaments=new ArrayList<>();
    }


    public static TournamentsManager getInstance() {
        if (instance == null) {
            instance = new TournamentsManager();
        }
        return instance;
    }

    public ArrayList<Tournament> getTournaments(){
        return tournaments;
    }
    public void addTournament(Tournament tournament){
        tournaments.add(tournament);
    }
    public void removeTournament(int id){
                tournaments.remove(id);
        }

    public void showTournaments(){
        if(tournaments.isEmpty()){
            System.out.println("No tournaments registered yet!");
        }
        else {
            System.out.println("List of tournaments:");
            int i = 0;
            for (Tournament t : tournaments) {
                System.out.println((i + 1) + "." + t);
                i++;
            }
        }
    }

}
