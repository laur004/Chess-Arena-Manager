package Service;

import Entities.Arbiter;
import Entities.ArbiterTitle;
import Utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArbiterService {

    private static ArbiterService instance;

    private ArbiterService() {}

    public static ArbiterService getInstance() {
        if (instance == null) {
            instance = new ArbiterService();
        }
        return instance;
    }


    public void create(Arbiter arbiter) throws SQLException {


        String sql = "INSERT INTO arbiter(fideId, title) VALUES (?, ?)";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, arbiter.getFideId());
            ps.setString(2, arbiter.getTitle() != null ? arbiter.getTitle().name() : null);
            ps.executeUpdate();
        }
    }


    public Arbiter readByFideId(String fideId) throws SQLException {
        String sql = "SELECT a.fideId, p.firstName, p.lastName, a.title " +
                "FROM arbiter a JOIN person p ON a.fideId = p.fideId " +
                "WHERE a.fideId = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fideId);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    ArbiterTitle title = rs.getString("title") != null
                            ? ArbiterTitle.valueOf(rs.getString("title"))
                            : null;

                    Arbiter arbiter = new Arbiter(
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            title
                    );
                    arbiter.setFideId(rs.getString("fideId"));
                    return arbiter;
                }
            }
        }
        return null;
    }


    public List<Arbiter> readAll() throws SQLException {
        List<Arbiter> arbiters = new ArrayList<>();
        String sql = "SELECT a.fideId, p.firstName, p.lastName, a.title " +
                "FROM arbiter a JOIN person p ON a.fideId = p.fideId";

        try (Connection conn = DatabaseUtils.getConnection();
             Statement ps = conn.createStatement();
             ResultSet rs = ps.executeQuery(sql)) {

            while (rs.next()) {
                ArbiterTitle title = rs.getString("title") != null
                        ? ArbiterTitle.valueOf(rs.getString("title"))
                        : null;

                Arbiter arbiter = new Arbiter(
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        title
                );
                arbiter.setFideId(rs.getString("fideId"));
                arbiters.add(arbiter);
            }
        }
        return arbiters;
    }


    public void updateTitle(String fideId, ArbiterTitle newTitle) throws SQLException {
        String sql = "UPDATE arbiter SET title = ? WHERE fideId = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);) {

            if(newTitle!=null){
                ps.setString(1, newTitle.name());
            }
            else{
                ps.setString(1, null);
            }

            ps.setString(2, fideId);
            ps.executeUpdate();
        }
    }


    public void delete(String fideId) throws SQLException {
        String sql = "DELETE FROM arbiter WHERE fideId = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fideId);
            ps.executeUpdate();
        }

    }
}
