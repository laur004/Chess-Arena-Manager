package Entities;

public class TournamentArbiter extends Arbiter {
    private int tournamentId;
    private String role;


    public TournamentArbiter(String fideId, String firstName, String lastName,
                             ArbiterTitle title,
                             int tournamentId, String role) {
        super(fideId, firstName, lastName, title);
        this.tournamentId = tournamentId;
        this.role = role;
    }


    public TournamentArbiter(String fideId, int tournamentId, String role) {

        super(fideId);
        this.tournamentId = tournamentId;
        this.role = role;
    }


    public int getTournamentId() { return tournamentId; }
    public String getRole() { return role; }

    public String toString(){
        return super.toString();
    }
}
