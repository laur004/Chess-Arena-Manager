package Service;

import Entities.Tournament;
import Utils.DatabaseUtils;

import java.sql.*;
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
        try(Connection conn = DatabaseUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tournament.getName());
            ps.setInt(2, tournament.getOrganizerId());
            ps.executeUpdate();
        }
    }

    public int createAndReturnId(Tournament tournament) throws SQLException {
        String sql = "INSERT INTO tournament(name, organizerId) VALUES(?,?)";
        try(Connection conn = DatabaseUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, tournament.getName());
            ps.setInt(2, tournament.getOrganizerId());
            ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()){
                if(rs.next()){
                    return rs.getInt(1);
                }
                else {
                    throw new SQLException("Creating tournament failed: no ID obtained.");
                }
            }
        }
    }


    public List<Tournament> readAll() throws SQLException{
        String sql="SELECT * FROM tournament";
        try(Connection conn = DatabaseUtils.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs= stmt.executeQuery(sql)) {

            List<Tournament> list = new ArrayList<>();
            while (rs.next()) {
                String sql2 = "SELECT o.organizerId, p.firstName, p.lastName, o.phoneNumber, o.email " +
                        "FROM organizer o JOIN person p ON o.organizerId = p.personId " +
                        "WHERE o.organizerId = ?";

                try(PreparedStatement ps2 = conn.prepareStatement(sql2)) {
                    ps2.setInt(1, rs.getInt("organizerId"));
                    try(ResultSet rs2 = ps2.executeQuery()) {
                        if (rs2.next()) {
                            list.add(new Tournament(rs.getInt(1),
                                    rs.getString(2), rs2.getInt("organizerId"))
                            );
                        }
                    }
                }
            }

            return list;
        }
    }

    public Tournament readByTournamentId(int tournamentId) throws SQLException {
        String sql = "SELECT * FROM tournament WHERE tournamentId = ?";
        try(Connection conn = DatabaseUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tournamentId);
            try(ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    int id = rs.getInt("tournamentId");
                    String name = rs.getString("name");
                    int organizerId = rs.getInt("organizerId");
                    return new Tournament(id, name, organizerId);
                }

                //System.err.println("Tournament with ID: " + tournamentId + " doesn't exist!");
                return null;
            }
        }
    }



    public List<Tournament> readAllByOrganizerId(int organizerId) throws SQLException{

        String sql="SELECT * FROM tournament WHERE organizerId=?";
        try(Connection conn = DatabaseUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, organizerId);

            try(ResultSet rs = ps.executeQuery()) {

                List<Tournament> list = new ArrayList<>();
                while (rs.next()) {
                    String sql2 = "SELECT o.organizerId, p.firstName, p.lastName, o.phoneNumber, o.email " +
                            "FROM organizer o JOIN person p ON o.organizerId = p.personId " +
                            "WHERE o.organizerId = ?";

                    try(PreparedStatement ps2 = conn.prepareStatement(sql2)) {
                        ps2.setInt(1, rs.getInt("organizerId"));
                        try(ResultSet rs2 = ps2.executeQuery()) {
                            if (rs2.next()) {
                                list.add(new Tournament(rs.getInt(1),
                                        rs.getString(2), rs2.getInt("organizerId"))
                                );
                            }
                        }
                    }

                }
                return list;
            }
        }
    }




    public void updateName(int tournamentId, String newName) throws SQLException {
        String sql = "UPDATE tournament SET name = ? WHERE tournamentId = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newName);
            ps.setInt(2, tournamentId);
            ps.executeUpdate();
        }
    }

    public void updateOrganizer(int tournamentId, int newOrganizerId) throws SQLException {
        String sql = "UPDATE tournament SET organizerId = ? WHERE tournamentId = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newOrganizerId);
            ps.setInt(2, tournamentId);
            ps.executeUpdate();
        }
    }



    public void delete(int tournamentId) throws SQLException {
        String sql1 = "DELETE FROM tournamentplayer WHERE tournamentId=?";
        String sql2 = "DELETE FROM tournamentarbiter WHERE tournamentId=?";
        String sql3 = "DELETE FROM tournament WHERE tournamentId=?";

        try (Connection conn = DatabaseUtils.getConnection()) {

            try (PreparedStatement ps1 = conn.prepareStatement(sql1);
                 PreparedStatement ps2 = conn.prepareStatement(sql2);
                 PreparedStatement ps3 = conn.prepareStatement(sql3)) {

                ps1.setInt(1, tournamentId);
                ps1.executeUpdate();

                ps2.setInt(1, tournamentId);
                ps2.executeUpdate();

                ps3.setInt(1, tournamentId);
                ps3.executeUpdate();

            }
        }
    }


}
