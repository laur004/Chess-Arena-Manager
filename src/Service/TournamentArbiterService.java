package Service;

import Entities.ArbiterTitle;
import Entities.PlayerTitle;
import Entities.Tournament;
import Entities.TournamentArbiter;
import Utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TournamentArbiterService {

    private static TournamentArbiterService instance;

    private TournamentArbiterService() {}

    public static TournamentArbiterService getInstance() {
        if (instance == null) {
            instance = new TournamentArbiterService();
        }
        return instance;
    }


    public void create(TournamentArbiter ta) throws SQLException {
        String sql = "INSERT INTO tournamentarbiter (fideId, tournamentId, role) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ta.getFideId());
            ps.setInt(2, ta.getTournamentId());
            ps.setString(3, ta.getRole());
            ps.executeUpdate();
        }
    }


    public List<TournamentArbiter> readAll() throws SQLException {
        String sql = "SELECT ta.fideId, ta.tournamentId, ta.role, " +
                "pe.firstName, pe.lastName, " +
                "a.title " +
                "FROM tournamentarbiter ta " +
                "JOIN arbiter a ON a.fideId = ta.fideId " +
                "JOIN person pe ON pe.fideId = a.fideId ";

        List<TournamentArbiter> list = new ArrayList<>();

        try(Connection conn = DatabaseUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ArbiterTitle title = rs.getString("title") != null
                        ? ArbiterTitle.valueOf(rs.getString("title"))
                        : null;
                TournamentArbiter ta = new TournamentArbiter(
                        rs.getString("fideId"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        title,
                        rs.getInt("tournamentId"),
                        rs.getString("role")
                );
                list.add(ta);
            }

        }
        return list;
    }


    public List<TournamentArbiter> readByTournamentId(int tournamentId) throws SQLException {
        String sql = "SELECT ta.fideId, ta.tournamentId, ta.role, " +
                "pe.firstName, pe.lastName, " +
                "a.title " +
                "FROM tournamentarbiter ta " +
                "JOIN arbiter a ON a.fideId = ta.fideId " +
                "JOIN person pe ON pe.fideId = a.fideId " +
                "WHERE ta.tournamentId = ?";

        List<TournamentArbiter> list = new ArrayList<>();

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tournamentId);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    ArbiterTitle title = rs.getString("title") != null
                            ? ArbiterTitle.valueOf(rs.getString("title"))
                            : null;
                    TournamentArbiter ta = new TournamentArbiter(
                            rs.getString("fideId"),
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            title,
                            rs.getInt("tournamentId"),
                            rs.getString("role")
                    );
                    list.add(ta);
                }
            }
        }

        return list;
    }


    public List<Tournament> readAllTournamentsByArbiterId(String fideId) throws SQLException{

        String sql= "SELECT ta.tournamentId FROM tournamentarbiter ta " +
                    "JOIN tournament t ON ta.tournamentId=t.tournamentId "+
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



    public void updateRole(String fideId, int tournamentId, String newRole) throws SQLException {
        String sql = "UPDATE tournamentarbiter SET role = ? WHERE fideId = ? AND tournamentId = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newRole);
            ps.setString(2, fideId);
            ps.setInt(3, tournamentId);
            ps.executeUpdate();
        }
    }


    public void updateTournamentArbiter(String oldFideId, int tournamentId, String newFideId) throws SQLException {
        String sql = "UPDATE tournamentarbiter SET fideId = ? WHERE fideId = ? AND tournamentId = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newFideId);
            ps.setString(2, oldFideId);
            ps.setInt(3, tournamentId);
            ps.executeUpdate();
        }
    }


    public void delete(String fideId, int tournamentId) throws SQLException {
        String sql = "DELETE FROM tournamentarbiter WHERE fideId = ? AND tournamentId = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fideId);
            ps.setInt(2, tournamentId);
            ps.executeUpdate();
        }
    }
}
