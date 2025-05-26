import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonService {

    private static PersonService personService;

    private PersonService() {}

    public static PersonService getInstance() {
        if (personService == null) {
            personService = new PersonService();
        }
        return personService;
    }


    public void create(Person person) throws SQLException {
        String sql = "INSERT INTO person(firstName, lastName, fideId) VALUES (?, ?, ?)";
        try (PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, person.getFirstName());
            ps.setString(2, person.getLastName());
            ps.setString(3, person.getFideId());
            ps.executeUpdate();


            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                person.setPersonId(rs.getInt(1));
            }
        }
    }


    public Person readByPersonId(int personId) throws SQLException {
        String sql = "SELECT * FROM person WHERE personId = ?";
        try (PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(sql)) {
            ps.setInt(1, personId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Person(
                        rs.getInt("personId"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("fideId")
                );
            }
        }
        return null;
    }


    public List<Person> readAll() throws SQLException {
        List<Person> people = new ArrayList<>();
        String sql = "SELECT * FROM person";
        try (Statement stmt = DatabaseUtils.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                people.add(new Person(
                        rs.getInt("personId"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("fideId")
                ));
            }
        }
        return people;
    }


    public void update(int personId, String newFirstName, String newLastName, String newFideId) throws SQLException {
        String sql = "UPDATE person SET firstName = ?, lastName = ?, fideId = ? WHERE personId = ?";
        try (PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(sql)) {
            ps.setString(1, newFirstName);
            ps.setString(2, newLastName);
            ps.setString(3,newFideId);
            ps.setInt(4, personId);
            ps.executeUpdate();
        }
    }


    public void delete(int personId) throws SQLException {
        String sql = "DELETE FROM person WHERE personId = ?";
        try (PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(sql)) {
            ps.setInt(1, personId);
            ps.executeUpdate();
        }
    }


    public Person readByFideId(String fideId) throws SQLException {
        String sql = "SELECT * FROM person WHERE fideId = ?";
        try (PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(sql)) {
            ps.setString(1, fideId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Person(
                        rs.getInt("personId"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("fideId")
                );
            }
        }
        return null;
    }
}
