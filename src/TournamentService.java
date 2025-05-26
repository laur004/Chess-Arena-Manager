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
        ps.setInt(2,tournament.getOrganizer().getPersonId());
        ps.executeUpdate();
    }


    public List<Tournament> readAll() throws SQLException{
        String sql="SELECT * FROM tournament";
        ResultSet rs=DatabaseUtils.getConnection().createStatement().executeQuery(sql);
        List<Tournament> list = new ArrayList<>();
        while(rs.next()){
            String sql2 = "SELECT o.organizerId, p.firstName, p.lastName, o.phoneNumber, o.email " +
                    "FROM organizer o JOIN person p ON o.organizerId = p.personId " +
                    "WHERE o.organizerId = ?";

            PreparedStatement ps2=DatabaseUtils.getConnection().prepareStatement(sql2);
            ps2.setInt(1,rs.getInt("organizerId"));
            ResultSet rs2 = ps2.executeQuery();
            if(rs2.next()){
                list.add(new Tournament(rs.getInt(1),
                        rs.getString(2),
                        new Organizer(rs2.getInt("organizerId"),
                                rs2.getString("firstName"),
                                rs2.getString("lastName"),
                                rs2.getString("phoneNumber"),
                                rs.getString("email")) ) );
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

            // obține obiectul Organizer complet
            Organizer organizer = OrganizerService.getInstance().readByOrganizerId(organizerId);
            if (organizer == null) {
                System.err.println("Organizer not found for ID: " + organizerId);
                return null;
            }

            return new Tournament(id, name, organizer);
        }

        System.err.println("Tournament with ID: " + tournamentId + " doesn't exist!");
        return null;
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
        String sql = "DELETE FROM tournament WHERE tournamentId=?";
        PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(sql);
        ps.setInt(1,tournamentId);
        ps.executeUpdate();
    }
}
