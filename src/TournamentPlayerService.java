import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TournamentPlayerService {
    private static TournamentPlayerService tournamentPlayerService;

    private TournamentPlayerService(){}

    public static TournamentPlayerService getInstance(){
        if(tournamentPlayerService==null){
            tournamentPlayerService=new TournamentPlayerService();
        }
        return tournamentPlayerService;
    }


    public void create(TournamentPlayer tournamentPlayer, int tournamentId) throws SQLException {
        String sql = "INSERT INTO tournamentplayer VALUES(?,?,?)";
        PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(sql);
        ps.setString(1,tournamentPlayer.getFideId());
        ps.setInt(2,tournamentId);
        ps.setDouble(3,0.0);
        ps.executeUpdate();
    }
//    public TournamentPlayer read(TournamentPlayer tournamentPlayer, int tournamentId){
//        String sql="SELECT * FROM "
//    }

    public void delete(TournamentPlayer tournamentPlayer, int tournamentId) throws SQLException{
        String sql = "DELETE FROM tournamentplayer WHERE fideId=?";
        PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(sql);
        ps.setString(1,tournamentPlayer.getFideId());
        ps.executeUpdate();
    }


}
