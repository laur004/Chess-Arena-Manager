package Entities;

import java.util.ArrayList;

public class Round {

    int roundId;
    private int tournamentId;
    private ArrayList<Game> games;

    public Round(){
        games=new ArrayList<>();
    }

    public ArrayList<Game> getGames(){return games;}
    public void setGames(ArrayList<Game> games){
        this.games=games;
    }
    public void addGame(Game game){
        games.add(game);
    }

    public void printRound(){
        for(Game game : games){
            System.out.print(game.getWhite().getLastName()+" "+game.getWhite().getFirstName()+" ");
            if(game.getResult()==null){
                System.out.print(" - ");
            }
            else {
                switch (game.getResult()) {
                    case W:
                        System.out.print(" 1-0 ");
                        break;
                    case B:
                        System.out.print(" 0-1 ");
                        break;

                    case D:
                        System.out.print(" 1/2-1/2 ");
                        break;

                    case Z:
                        System.out.print(" 0-0 ");
                        break;

                    default:
                        System.out.print(" - ");
                }
            }
            System.out.println(game.getBlack().getLastName() + " " + game.getBlack().getFirstName() + " ");
        }
    }
}
