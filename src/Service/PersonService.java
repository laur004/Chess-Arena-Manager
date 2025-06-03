package Service;

import Entities.Person;
import Utils.DatabaseUtils;

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
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, person.getFirstName());
            ps.setString(2, person.getLastName());
            ps.setString(3, person.getFideId());
            ps.executeUpdate();
        }
    }

    public int createAndReturnId(Person person) throws SQLException {
        String sql = "INSERT INTO person(firstName, lastName, fideId) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, person.getFirstName());
            ps.setString(2, person.getLastName());
            ps.setString(3, person.getFideId());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Creating person failed: no ID obtained.");
                }
            }
        }
    }



    public Person readByPersonId(int personId) throws SQLException {
        String sql = "SELECT * FROM person WHERE personId = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, personId);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Person(
                            rs.getInt("personId"),
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getString("fideId")
                    );
                }
            }
        }
        return null;
    }


    public List<Person> readAll() throws SQLException {
        List<Person> people = new ArrayList<>();
        String sql = "SELECT * FROM person";
        try (Connection conn = DatabaseUtils.getConnection();
             Statement stmt = conn.createStatement();
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
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newFirstName);
            ps.setString(2, newLastName);
            ps.setString(3,newFideId);
            ps.setInt(4, personId);
            ps.executeUpdate();
        }
    }


    public void delete(int personId) throws SQLException {
        String sql = "DELETE FROM person WHERE personId = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, personId);
            ps.executeUpdate();
        }
    }


    public Person readByFideId(String fideId) throws SQLException {
        String sql = "SELECT * FROM person WHERE fideId = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fideId);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Person(
                            rs.getInt("personId"),
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getString("fideId")
                    );
                }
            }
        }
        return null;
    }
}
