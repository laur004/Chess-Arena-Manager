import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Tournament {
    private int id;
    private String name;
    private Organizer organizer;
    private Arbiter arbiter;
    //ArrayList<TournamentPlayer> players; //vreau sa fie sortata dupa rating la fiecare adaugare de jucator
    private TreeSet<TournamentPlayer> startingList = new TreeSet<>(Comparator.reverseOrder());
    private ArrayList<Round> rounds;
    private TreeSet<TournamentPlayer> ranking;
    private int noRounds;
    //TimeControl timeControl;


    public Tournament(int id, String name, Organizer organizer) {
        this.id = id;
        this.name = name;
        this.organizer = organizer;
    }



    public Tournament(String name, Organizer organizer){
        this.name=name;
        this.organizer=organizer;
        this.id=IDGenerator.getTournamentId();
    }

    public int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }
    public Person getOrganizer() {
        return organizer;
    }

    public Arbiter getArbiter() {
        return arbiter;
    }
    public void setArbiter(Arbiter arbiter) {
        this.arbiter = arbiter;
    }

    public ArrayList<Round> getRounds(){
        return rounds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        Tournament other = (Tournament) o;
        return Objects.equals(this.id, other.getId());
    }

    public void addPlayer(TournamentPlayer player){
        boolean exists=false;
        for(TournamentPlayer p:startingList){
            if(player.equals(p)){
                exists=true;
                break;
            }
        }
        if(!exists) {
            startingList.add(player);
            //players.sort(TournamentPlayer::compareTo);
        }
    }
    public void removePlayer(String fideId){
        for(TournamentPlayer p:startingList){
            if(fideId.equals(p.getFideId())){
                startingList.remove(p);
                break;
            }
        }
    }

    public void showRounds() {

        if(rounds!=null) {
            int i=1;
            for (Round r : rounds) {
                System.out.println("Round " + i++ + ":");
                r.printRound();
            }
        }
        else{
            System.out.println("Tournament has not started yet!");
        }
    }

    public void showStartingList(){
        System.out.println("Starting List:");
        AtomicInteger i = new AtomicInteger(1);
        startingList.forEach(p-> System.out.println(i.getAndIncrement()+"."+p));
    }


    public String toString(){
        return name+" organizat de "+organizer;
    }

    public void setPointsToAllPLayers(){
        if(ranking!=null) {
            Scanner obj = new Scanner(System.in);
            double points;
            System.out.println("Set points for each player:");
            for (TournamentPlayer tp : ranking) {
                System.out.println(tp);
                System.out.print("Points: ");
                points = Double.parseDouble(obj.nextLine());
                tp.setPoints(points);
            }

        }
        else{
            System.out.println("Tournament has not started yet!");
        }

    }

    public void showRanking(){
        if(ranking!=null){
        AtomicInteger i = new AtomicInteger(1);
        ranking.forEach(p -> System.out.println(
                i.getAndIncrement() + " " + p + " Points: " + p.getPoints()));
        }
        else {
            System.out.println("Tournament has not started yet!");
        }
    }

    public void pairingSystem() {

        ranking = new TreeSet<>(Comparator.reverseOrder());;
        for (TournamentPlayer player : startingList) {
            TournamentPlayer playerCopy = new TournamentPlayer(player.getLastName(), player.getFirstName(), player.getRating());
            ranking.add(playerCopy);
        }
        noRounds=(startingList.size()%2==1)? startingList.size() : startingList.size()-1;

        rounds = new ArrayList<>();
        for (int i = 0; i < noRounds; i++) {
            rounds.add(new Round());
        }

        boolean whiteColor = true;
        ArrayList<TournamentPlayer> rotatedPlayers = new ArrayList<>(startingList);


        if (startingList.size() % 2 == 1) {
            for (int roundIndex = 0; roundIndex < noRounds; roundIndex++) {
                Round currentRound = (Round) rounds.get(roundIndex);
                int noPlayers = startingList.size();

                ArrayList<Game> games = new ArrayList<>(noPlayers / 2 + 1);
                for (int i = 0; i <= noPlayers / 2; i++) {
                    games.add(new Game());
                }
                currentRound.setGames(games);

                if (roundIndex != noRounds - 1) {

                    for (int gameIndex = 0; gameIndex < noPlayers / 2; gameIndex++) {
                        Game game = (Game) games.get(gameIndex);
                        if (whiteColor) {
                            game.setWhite((TournamentPlayer) rotatedPlayers.get(gameIndex));
                            game.setBlack((TournamentPlayer) rotatedPlayers.get(rotatedPlayers.size() - gameIndex - 1));
                        } else {
                            game.setBlack((TournamentPlayer) rotatedPlayers.get(gameIndex));
                            game.setWhite((TournamentPlayer) rotatedPlayers.get(rotatedPlayers.size() - gameIndex - 1));
                        }
                    }


                    Game byeGame = (Game) games.get(noPlayers / 2);
                    byeGame.setWhite((TournamentPlayer) rotatedPlayers.get(noPlayers / 2));
                    byeGame.setBlack(new TournamentPlayer("Bye", "", 0));
                    byeGame.setResult(GameResult.W);


                    TournamentPlayer temp = (TournamentPlayer) rotatedPlayers.get(1);
                    rotatedPlayers.remove(1);
                    rotatedPlayers.add(temp);
                } else {

                    // Last round
                    Game byeGame = (Game) games.get(noPlayers / 2);
                    byeGame.setWhite((TournamentPlayer) rotatedPlayers.get(0));
                    byeGame.setBlack(new TournamentPlayer("Bye", "", 0));
                    byeGame.setResult(GameResult.W);

                    for (int gameIndex = 1; gameIndex <= noPlayers / 2; gameIndex++) {
                        Game game = (Game) games.get(gameIndex - 1);
                        if (whiteColor) {
                            game.setWhite((TournamentPlayer) rotatedPlayers.get(gameIndex));
                            game.setBlack((TournamentPlayer) rotatedPlayers.get(rotatedPlayers.size() - gameIndex));
                        } else {
                            game.setBlack((TournamentPlayer) rotatedPlayers.get(gameIndex));
                            game.setWhite((TournamentPlayer) rotatedPlayers.get(rotatedPlayers.size() - gameIndex));
                        }
                    }
                }

                whiteColor = !whiteColor;
            }
        } else {
            for (int roundIndex = 0; roundIndex < rounds.size(); roundIndex++) {
                Round round = (Round) rounds.get(roundIndex);
                int noPlayers = startingList.size();

                ArrayList<Game> games = new ArrayList<>(noPlayers / 2);
                for (int i = 0; i < noPlayers / 2; i++) {
                    games.add(new Game());
                }
                round.setGames(games);

                for (int gameIndex = 0; gameIndex < noPlayers / 2; gameIndex++) {
                    Game game = (Game) games.get(gameIndex);
                    if (whiteColor) {
                        game.setWhite((TournamentPlayer) rotatedPlayers.get(gameIndex));
                        game.setBlack((TournamentPlayer) rotatedPlayers.get(rotatedPlayers.size() - gameIndex - 1));
                    } else {
                        game.setBlack((TournamentPlayer) rotatedPlayers.get(gameIndex));
                        game.setWhite((TournamentPlayer) rotatedPlayers.get(rotatedPlayers.size() - gameIndex - 1));
                    }
                }

                whiteColor = !whiteColor;


                if (roundIndex < rounds.size() - 1) {

                    TournamentPlayer temp = (TournamentPlayer) rotatedPlayers.get(1);
                    rotatedPlayers.remove(1);
                    rotatedPlayers.add(temp);
                }
            }
        }
    }
}
