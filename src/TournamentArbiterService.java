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
        try (PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(sql)) {
            ps.setString(1, ta.getFideId());
            ps.setInt(2, ta.getTournamentId());
            ps.setString(3, ta.getRole());
            ps.executeUpdate();
        }
    }


    public List<TournamentArbiter> readAll() throws SQLException {
        String sql = "SELECT * FROM tournamentarbiter";
        List<TournamentArbiter> list = new ArrayList<>();
        try (Statement stmt = DatabaseUtils.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new TournamentArbiter(
                        rs.getString("fideId"),
                        rs.getInt("tournamentId"),
                        rs.getString("role")
                ));
            }
        }
        return list;
    }


    public List<TournamentArbiter> readByTournamentId(int tournamentId) throws SQLException {
        String sql = "SELECT * FROM tournamentarbiter WHERE tournamentId = ?";
        List<TournamentArbiter> list = new ArrayList<>();
        try (PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(sql)) {
            ps.setInt(1, tournamentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new TournamentArbiter(
                        rs.getString("fideId"),
                        rs.getInt("tournamentId"),
                        rs.getString("role")
                ));
            }
        }
        return list;
    }


    public void updateRole(String fideId, int tournamentId, String newRole) throws SQLException {
        String sql = "UPDATE tournamentarbiter SET role = ? WHERE fideId = ? AND tournamentId = ?";
        try (PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(sql)) {
            ps.setString(1, newRole);
            ps.setString(2, fideId);
            ps.setInt(3, tournamentId);
            ps.executeUpdate();
        }
    }


    public void updateTournamentArbiter(String oldFideId, int tournamentId, String newFideId) throws SQLException {
        String sql = "UPDATE tournamentarbiter SET fideId = ? WHERE fideId = ? AND tournamentId = ?";
        try (PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(sql)) {
            ps.setString(1, newFideId);
            ps.setString(2, oldFideId);
            ps.setInt(3, tournamentId);
            ps.executeUpdate();
        }
    }


    public void delete(String fideId, int tournamentId) throws SQLException {
        String sql = "DELETE FROM tournamentarbiter WHERE fideId = ? AND tournamentId = ?";
        try (PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(sql)) {
            ps.setString(1, fideId);
            ps.setInt(2, tournamentId);
            ps.executeUpdate();
        }
    }
}
