public final class IDGenerator {
    private static int fideId=1;
    private static int tournamentId=1;

    public static int getFideId(){
        return fideId++;
    }
    public static int getTournamentId(){
        return tournamentId++;
    }
}
