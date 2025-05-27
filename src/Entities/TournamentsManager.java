package Entities;

import java.util.ArrayList;
import java.util.HashMap;


public class TournamentsManager {

    private HashMap<Integer, Tournament> tournaments;

    private static TournamentsManager instance=null;



    private TournamentsManager(){
        tournaments = new HashMap<>();
    }


    public static TournamentsManager getInstance() {
        if (instance == null) {
            instance = new TournamentsManager();
        }
        return instance;
    }

    public HashMap<Integer, Tournament> getTournaments(){
        return tournaments;
    }

    public void addTournament(Tournament tournament){
        tournaments.put(tournament.getId(),tournament);
    }

    public void removeTournament(int id){
        tournaments.remove(id);
    }


}
