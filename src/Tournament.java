import java.util.*;

public class Tournament {
    private int id;
    private String name;
    private Person organizer;
    private Arbiter arbiter;
    //ArrayList<Player> players; //vreau sa fie sortata dupa rating la fiecare adaugare de jucator
    private TreeSet<Player> players;
    private ArrayList<Round> rounds;
    private Ranking ranking;
    private int noRounds;
    //TimeControl timeControl;



    public Tournament(String name, Person organizer){
        this.name=name;
        this.organizer=organizer;
        this.id=IDGenerator.getTournamentId();
        this.players =new TreeSet<>();
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

    public void setOrganizer(Person organizer) {
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

    public boolean equals(Tournament tournament) {
        return id==tournament.getId();
    }

    public void addPlayer(Player player){
        boolean exists=false;
        for(Player p:players){
            if(player.equals(p)){
                exists=true;
                break;
            }
        }
        if(!exists) {
            players.add(player);
            //players.sort(Player::compareTo);
        }
    }
    public void removePlayer(String fideId){
        for(Player p:players){
            if(fideId.equals(p.getFideID())){
                players.remove(p);
                break;
            }
        }
    }

    public void showStartingList(){
        System.out.println("Starting List:");
        int i=1;
        for(Player p: players){
            System.out.println(i+"."+p);
            i++;
        }
    }


    public String toString(){
        return name+" organizat de "+organizer;
    }

    public void setPointsToAllPLayers(){
        ranking.setPointsToAllPlayers();
    }

    public void showRanking(){
        ranking.showRanking();
    }

    public void pairingSystem() {

        ranking = new Ranking();
        for (Player player : players) {
            Player playerCopy = new Player(player.getLastName(), player.getFirstName(), player.getRating());
            ranking.addPlayer(playerCopy);
        }
        noRounds=(players.size()%2==1)? players.size() : players.size()-1;

        rounds = new ArrayList();
        for (int i = 0; i < noRounds; i++) {
            rounds.add(new Round());
        }

        boolean whiteColor = true;
        ArrayList rotatedPlayers = new ArrayList(players);


        if (players.size() % 2 == 1) {
            for (int roundIndex = 0; roundIndex < noRounds; roundIndex++) {
                Round currentRound = (Round) rounds.get(roundIndex);
                int noPlayers = players.size();

                ArrayList games = new ArrayList(noPlayers / 2 + 1);
                for (int i = 0; i <= noPlayers / 2; i++) {
                    games.add(new Game());
                }
                currentRound.setGames(games);

                if (roundIndex != noRounds - 1) {

                    for (int gameIndex = 0; gameIndex < noPlayers / 2; gameIndex++) {
                        Game game = (Game) games.get(gameIndex);
                        if (whiteColor) {
                            game.setWhite((Player) rotatedPlayers.get(gameIndex));
                            game.setBlack((Player) rotatedPlayers.get(rotatedPlayers.size() - gameIndex - 1));
                        } else {
                            game.setBlack((Player) rotatedPlayers.get(gameIndex));
                            game.setWhite((Player) rotatedPlayers.get(rotatedPlayers.size() - gameIndex - 1));
                        }
                    }


                    Game byeGame = (Game) games.get(noPlayers / 2);
                    byeGame.setWhite((Player) rotatedPlayers.get(noPlayers / 2));
                    byeGame.setBlack(new Player("Bye", "", 0));
                    byeGame.setResult(GameResult.W);


                    Player temp = (Player) rotatedPlayers.get(1);
                    rotatedPlayers.remove(1);
                    rotatedPlayers.add(temp);
                } else {

                    // Last round
                    Game byeGame = (Game) games.get(noPlayers / 2);
                    byeGame.setWhite((Player) rotatedPlayers.get(0));
                    byeGame.setBlack(new Player("Bye", "", 0));
                    byeGame.setResult(GameResult.W);

                    for (int gameIndex = 1; gameIndex <= noPlayers / 2; gameIndex++) {
                        Game game = (Game) games.get(gameIndex - 1);
                        if (whiteColor) {
                            game.setWhite((Player) rotatedPlayers.get(gameIndex));
                            game.setBlack((Player) rotatedPlayers.get(rotatedPlayers.size() - gameIndex));
                        } else {
                            game.setBlack((Player) rotatedPlayers.get(gameIndex));
                            game.setWhite((Player) rotatedPlayers.get(rotatedPlayers.size() - gameIndex));
                        }
                    }
                }

                whiteColor = !whiteColor;
            }
        } else {
            for (int roundIndex = 0; roundIndex < rounds.size(); roundIndex++) {
                Round round = (Round) rounds.get(roundIndex);
                int noPlayers = players.size();

                ArrayList games = new ArrayList(noPlayers / 2);
                for (int i = 0; i < noPlayers / 2; i++) {
                    games.add(new Game());
                }
                round.setGames(games);

                for (int gameIndex = 0; gameIndex < noPlayers / 2; gameIndex++) {
                    Game game = (Game) games.get(gameIndex);
                    if (whiteColor) {
                        game.setWhite((Player) rotatedPlayers.get(gameIndex));
                        game.setBlack((Player) rotatedPlayers.get(rotatedPlayers.size() - gameIndex - 1));
                    } else {
                        game.setBlack((Player) rotatedPlayers.get(gameIndex));
                        game.setWhite((Player) rotatedPlayers.get(rotatedPlayers.size() - gameIndex - 1));
                    }
                }

                whiteColor = !whiteColor;


                if (roundIndex < rounds.size() - 1) {

                    Player temp = (Player) rotatedPlayers.get(1);
                    rotatedPlayers.remove(1);
                    rotatedPlayers.add(temp);
                }
            }
        }
    }
}
