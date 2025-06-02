package Service;

import Entities.Tournament;
import Utils.DatabaseUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TournamentService{

    private static TournamentService tournamentService;

    private TournamentService(){}

    public static TournamentService getInstance(){
        if(tournamentService==null){
            tournamentService=new TournamentService();
        }
        return tournamentService;
    }


    public void create(Tournament tournament) throws SQLException {
        String sql = "INSERT INTO tournament(name, organizerId) VALUES(?,?)";
        PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(sql);
        ps.setString(1,tournament.getName());
        ps.setInt(2,tournament.getOrganizerId());
        ps.executeUpdate();
    }


    public List<Tournament> readAll() throws SQLException{
        String sql="SELECT * FROM tournament";
        ResultSet rs= DatabaseUtils.getConnection().createStatement().executeQuery(sql);
        List<Tournament> list = new ArrayList<>();
        while(rs.next()){
            String sql2 = "SELECT o.organizerId, p.firstName, p.lastName, o.phoneNumber, o.email " +
                    "FROM organizer o JOIN person p ON o.organizerId = p.personId " +
                    "WHERE o.organizerId = ?";

            PreparedStatement ps2= DatabaseUtils.getConnection().prepareStatement(sql2);
            ps2.setInt(1,rs.getInt("organizerId"));
            ResultSet rs2 = ps2.executeQuery();
            if(rs2.next()){
                list.add(new Tournament(rs.getInt(1),
                        rs.getString(2),rs2.getInt("organizerId"))
                        );
            }

        }
        return list;
    }

    public Tournament readByTournamentId(int tournamentId) throws SQLException {
        String sql = "SELECT * FROM tournament WHERE tournamentId = ?";
        PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(sql);
        ps.setInt(1, tournamentId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("tournamentId");
            String name = rs.getString("name");
            int organizerId = rs.getInt("organizerId");
            return new Tournament(id, name, organizerId);
        }

        //System.err.println("Tournament with ID: " + tournamentId + " doesn't exist!");
        return null;
    }



    public List<Tournament> readAllByOrganizerId(int organizerId) throws SQLException{

        String sql="SELECT * FROM tournament WHERE organizerId=?";
        PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(sql);
        ps.setInt(1, organizerId);

        ResultSet rs= ps.executeQuery();

        List<Tournament> list = new ArrayList<>();
        while(rs.next()){
            String sql2 = "SELECT o.organizerId, p.firstName, p.lastName, o.phoneNumber, o.email " +
                    "FROM organizer o JOIN person p ON o.organizerId = p.personId " +
                    "WHERE o.organizerId = ?";

            PreparedStatement ps2= DatabaseUtils.getConnection().prepareStatement(sql2);
            ps2.setInt(1,rs.getInt("organizerId"));
            ResultSet rs2 = ps2.executeQuery();
            if(rs2.next()){
                list.add(new Tournament(rs.getInt(1),
                        rs.getString(2),rs2.getInt("organizerId"))
                );
            }

        }
        return list;
    }




    public void updateName(int tournamentId, String newName) throws SQLException {
        String sql = "UPDATE tournament SET name = ? WHERE tournamentId = ?";
        try (PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(sql)) {
            ps.setString(1, newName);
            ps.setInt(2, tournamentId);
            ps.executeUpdate();
        }
    }

    public void updateOrganizer(int tournamentId, int newOrganizerId) throws SQLException {
        String sql = "UPDATE tournament SET organizerId = ? WHERE tournamentId = ?";
        try (PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(sql)) {
            ps.setInt(1, newOrganizerId);
            ps.setInt(2, tournamentId);
            ps.executeUpdate();
        }
    }



    public void delete(int tournamentId) throws SQLException{

        String sql2 = "DELETE FROM tournamentplayer WHERE tournamentId=?";
        PreparedStatement ps2 = DatabaseUtils.getConnection().prepareStatement(sql2);
        ps2.setInt(1,tournamentId);
        ps2.executeUpdate();

        String sql3 = "DELETE FROM tournamentarbiter WHERE tournamentId=?";
        PreparedStatement ps3 = DatabaseUtils.getConnection().prepareStatement(sql3);
        ps3.setInt(1,tournamentId);
        ps3.executeUpdate();



        String sql = "DELETE FROM tournament WHERE tournamentId=?";
        PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(sql);
        ps.setInt(1,tournamentId);
        ps.executeUpdate();
    }
}
