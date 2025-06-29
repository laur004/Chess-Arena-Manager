package Service;

import Entities.Organizer;
import Utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrganizerService {

    private static OrganizerService organizerService;

    private OrganizerService() {}

    public static OrganizerService getInstance() {
        if (organizerService == null) {
            organizerService = new OrganizerService();
        }
        return organizerService;
    }

    public void create(Organizer organizer) throws SQLException {


        String sql = "INSERT INTO organizer(organizerId, phoneNumber, email) VALUES (?, ?, ?)";
        try(Connection conn = DatabaseUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, organizer.getPersonId());
            ps.setString(2, organizer.getPhoneNumber());
            ps.setString(3, organizer.getEmail());
            ps.executeUpdate();
        }
    }

    public List<Organizer> readAll() throws SQLException {
        List<Organizer> organizers = new ArrayList<>();
        String sql = "SELECT o.organizerId, p.firstName, p.lastName, o.phoneNumber, o.email " +
                "FROM organizer o JOIN person p ON o.organizerId = p.personId";

        try(Connection conn = DatabaseUtils.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                organizers.add(new Organizer(
                        rs.getInt("organizerId"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("phoneNumber"),
                        rs.getString("email")
                ));
            }
        }
        return organizers;
    }

    public Organizer readByOrganizerId(int organizerId) throws SQLException {
        String sql = "SELECT o.organizerId, p.firstName, p.lastName, o.phoneNumber, o.email " +
                "FROM organizer o JOIN person p ON o.organizerId = p.personId " +
                "WHERE o.organizerId = ?";

        try(Connection conn = DatabaseUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, organizerId);
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return new Organizer(
                            rs.getInt("organizerId"),
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getString("phoneNumber"),
                            rs.getString("email")
                    );
                }
            }
        }
        //System.err.println("Organizer with id: " + organizerId + " doesn't exist!");
        return null;
    }

    public void updatePhoneNumber(int organizerId,String phoneNumber) throws SQLException{
        String sql = "UPDATE organizer SET phoneNumber=? WHERE organizerId=?";
        try(Connection conn = DatabaseUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(2, organizerId);
            ps.setString(1, phoneNumber);
            ps.executeUpdate();
        }
    }

    public void updateEmail(int organizerId,String email) throws SQLException{
        String sql = "UPDATE organizer SET email=? WHERE organizerId=?";
        try(Connection conn = DatabaseUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(2, organizerId);
            ps.setString(1, email);
            ps.executeUpdate();
        }
    }


    public void delete(int organizerId) throws SQLException {
        String sql1 = "UPDATE tournament SET organizerId = NULL WHERE organizerId = ?";
        String sql2 = "DELETE FROM organizer WHERE organizerId = ?";

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement ps1 = conn.prepareStatement(sql1);
             PreparedStatement ps2 = conn.prepareStatement(sql2)) {

            ps1.setInt(1, organizerId);
            ps1.executeUpdate();

            ps2.setInt(1, organizerId);
            ps2.executeUpdate();
        }
    }

}
