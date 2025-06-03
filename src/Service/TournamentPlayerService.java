package Service;

import Entities.Player;
import Entities.PlayerTitle;
import Entities.Tournament;
import Entities.TournamentPlayer;
import Utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
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


    public void create(String fideId, int tournamentId) throws SQLException {
        String sql = "INSERT INTO tournamentplayer VALUES(?,?,?)";
        try(Connection conn = DatabaseUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fideId);
            ps.setInt(2, tournamentId);
            ps.setDouble(3, 0.0);
            ps.executeUpdate();
        }
    }
//    public Entities.TournamentPlayer read(Entities.TournamentPlayer tournamentPlayer, int tournamentId){
//        String sql="SELECT * FROM "
//    }

    public List<TournamentPlayer> readAll() throws SQLException {
        String sql = "SELECT pe.personId, pe.firstName, pe.lastName, pe.fideId, " +
                "p.title, p.rating, t.tournamentId, t.points " +
                "FROM tournamentplayer t " +
                "JOIN player p ON p.fideId = t.fideId " +
                "JOIN person pe ON pe.fideId = p.fideId";
        List<TournamentPlayer> list = new ArrayList<>();
        try (Connection conn = DatabaseUtils.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                String titleStr = rs.getString("title");
                PlayerTitle title = titleStr != null ? PlayerTitle.valueOf(titleStr) : null;


                list.add(new TournamentPlayer(
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getInt("rating"),
                        title,
                        rs.getString("fideId"),
                        rs.getInt("tournamentId"),
                        rs.getDouble("points")
                ));
            }
        }
        return list;
    }

    public List<TournamentPlayer> readAllByTournamentId(int tournamentId) throws SQLException {
        String sql = "SELECT pe.personId, pe.firstName, pe.lastName, pe.fideId, " +
                "p.title, p.rating, t.tournamentId, t.points " +
                "FROM tournamentplayer t " +
                "JOIN player p ON p.fideId = t.fideId " +
                "JOIN person pe ON pe.fideId = p.fideId " +
                "WHERE tournamentId=?";
        List<TournamentPlayer> list = new ArrayList<>();

        try(Connection conn = DatabaseUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tournamentId);
            try(ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    String stitle = rs.getString("title");
                    PlayerTitle title;
                    if (stitle == null) {
                        title = null;
                    } else {
                        title = PlayerTitle.valueOf(stitle);
                    }

                    list.add(new TournamentPlayer(
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getInt("rating"),
                            title,
                            rs.getString("fideId"),
                            rs.getInt("tournamentId"),
                            rs.getDouble("points")
                    ));
                }
            }
        }

        return list;
    }


    public List<Tournament> readAllTournamentsByPlayerId(String fideId) throws SQLException{

        String sql= "SELECT tp.tournamentId FROM tournamentplayer tp " +
                "JOIN tournament t ON tp.tournamentId=t.tournamentId "+
                "WHERE fideId=? ";
        try(Connection conn = DatabaseUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fideId);

            try(ResultSet rs = ps.executeQuery()) {

                List<Tournament> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(TournamentService.getInstance().readByTournamentId(rs.getInt("tournamentId")));
                }
                return list;
            }
        }
    }


    public void updateTournamentPlayerPoints(int tournamentId, String fideId, double points) throws SQLException {

        String sql = "UPDATE tournamentplayer SET points=? WHERE tournamentId=? AND fideId=?";
        try(Connection conn = DatabaseUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, points);
            ps.setInt(2, tournamentId);
            ps.setString(3, fideId);
            ps.executeUpdate();
        }

    }

    public void updateAllTournamentPlayersPoints(int tournamentId, List<TournamentPlayer> tournamentPlayerList) throws SQLException{

        tournamentPlayerList.forEach(p->
                {
                    try {
                        updateTournamentPlayerPoints(
                                tournamentId,
                                p.getFideId(),
                                p.getPoints()
                        );
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }


    public void delete(String fideId, int tournamentId) throws SQLException{
        String sql = "DELETE FROM tournamentplayer WHERE fideId=? AND tournamentId=?";
        try(Connection conn = DatabaseUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fideId);
            ps.setInt(2, tournamentId);
            ps.executeUpdate();
        }
    }


}
