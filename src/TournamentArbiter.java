public class TournamentArbiter {
    private String fideId;
    private int tournamentId;
    private String role;

    public TournamentArbiter(String fideId, int tournamentId, String role) {
        this.fideId = fideId;
        this.tournamentId = tournamentId;
        this.role = role;
    }


    public String getFideId() { return fideId; }
    public int getTournamentId() { return tournamentId; }
    public String getRole() { return role; }

}
