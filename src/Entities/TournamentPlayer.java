package Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class TournamentPlayer extends Player implements Comparable<TournamentPlayer> {

    private int tournamentId;
    private double points;
    private List<Game> gamesList=new ArrayList<>();


    public TournamentPlayer(){
        super();
    }

    public TournamentPlayer(String firstName, String lastName, int rating,
                            PlayerTitle title, String fideID, int tournamentId, double points){
        super(firstName, lastName, rating, title, fideID);
        this.tournamentId=tournamentId;
        this.points=points;
    }

    public TournamentPlayer(String fideId, int tournamentId, double points ){
        super(fideId);
        this.tournamentId=tournamentId;
        this.points=points;

    }


    public TournamentPlayer(String firstName, String lastName, int rating){
        super(firstName,lastName,rating);
    }
    public TournamentPlayer(String firstName, String lastName, int rating, PlayerTitle title, String fideID){
        super(firstName,lastName,rating,title,fideID);
    }
    public TournamentPlayer(TournamentPlayer original) {
        super(original);
        this.points= original.points;
    }



    public double getPoints(){
        return points;
    }

    public void setPoints(double points){
        this.points=points;
    }

    public int getTournamentId(){return tournamentId;}

    public void setTournamentId(int tournamentId){this.tournamentId=tournamentId;}


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int compareTo(TournamentPlayer p) {
        if(points!=p.points){
            return  Double.compare(points, p.points);
        }
//        else if(getBuchholzTieBreakVariable()!=p.getBuchholzTieBreakVariable()){
//            return Double.compare(getBuchholzTieBreakVariable(), p.getBuchholzTieBreakVariable());
//        }

        Predicate<Game> p1 = game -> game.getWhite().equals(p);
        Predicate<Game> p2 = game -> game.getBlack().equals(p);
        if(gamesList.stream()
                        .anyMatch(game -> p1.or(p2).test(game))) {

            Optional<Game> optionalGame = gamesList.stream()
                    .filter(game -> p1.or(p2).test(game))
                    .findAny();
            if (optionalGame.isPresent()) {
                if (optionalGame.get().getBlack().equals(p)) {
                    if (optionalGame.get().getResult() == GameResult.W) {
                        return 1;
                    } else if (optionalGame.get().getResult() == GameResult.B) {
                        return -1;
                    }
                } else {
                    if (optionalGame.get().getResult() == GameResult.W) {
                        return -1;
                    } else if (optionalGame.get().getResult() == GameResult.B) {
                        return 1;
                    }
                }
            }

            if(rating!=p.rating){
                return rating-p.rating;
            }

            return getFideId().compareTo(p.getFideId());
        }

        if(rating!=p.rating){
            return rating-p.rating;
        }
        return getFideId().compareTo(p.getFideId());
    }


    public String toString(){
        return super.toString() +" "+points;
    }


//    public double getBuchholzTieBreakVariable(){
//        if(!gamesList.isEmpty()){
//            return gamesList.stream()
//                                    .mapToDouble(Entities.TournamentPlayer::getPoints).sum();
//        }
//        return 0.0;
//    }


}