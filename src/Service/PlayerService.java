package Service;

import Entities.Player;
import Entities.PlayerTitle;
import Utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerService {

    private static PlayerService instance;

    private PlayerService() {}

    public static PlayerService getInstance() {
        if (instance == null) {
            instance = new PlayerService();
        }
        return instance;
    }


    public void create(Player player) throws SQLException {


        String sql = "INSERT INTO player(fideId, title, rating) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, player.getFideId());
            ps.setString(2, player.getTitle() != null ? player.getTitle().name() : null);
            ps.setInt(3, player.getRating());
            ps.executeUpdate();
        }
    }


    public Player readByFideId(String fideId) throws SQLException {
        String sql = "SELECT p.fideId, per.firstName, per.lastName, p.title, p.rating " +
                "FROM player p JOIN person per ON p.fideId = per.fideId " +
                "WHERE p.fideId = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fideId);
            try(ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    PlayerTitle title = rs.getString("title") != null
                            ? PlayerTitle.valueOf(rs.getString("title"))
                            : null;

                    Player player = new Player(
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getInt("rating"),
                            title,
                            rs.getString("fideId")
                    );
                    return player;
                }
            }
        }
        return null;
    }


    public List<Player> readAll() throws SQLException {
        List<Player> list = new ArrayList<>();
        String sql = "SELECT p.fideId, per.firstName, per.lastName, p.title, p.rating " +
                "FROM player p JOIN person per ON p.fideId = per.fideId";

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PlayerTitle title = rs.getString("title") != null
                        ? PlayerTitle.valueOf(rs.getString("title"))
                        : null;

                Player player = new Player(
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getInt("rating"),
                        title,
                        rs.getString("fideId")
                );
                list.add(player);
            }
        }
        return list;
    }


    public void updateRating(String fideId, int newRating) throws SQLException {
        String sql = "UPDATE player SET rating = ? WHERE fideId = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newRating);
            ps.setString(2, fideId);
            ps.executeUpdate();
        }
    }


    public void updateTitle(String fideId, PlayerTitle newTitle) throws SQLException {
        String sql = "UPDATE player SET title = ? WHERE fideId = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
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
        String sql = "DELETE FROM player WHERE fideId = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fideId);
            ps.executeUpdate();
        }


    }
}
