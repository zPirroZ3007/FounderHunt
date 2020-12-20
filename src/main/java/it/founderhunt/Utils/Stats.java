package it.founderhunt.Utils;

import it.founderhunt.Objects.Data;
import it.founderhunt.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class Stats {

    public static HashMap<String, Data> MAP = new HashMap<>();

    public static void loadAll() {

        try (PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM stats;")) {
            ResultSet rs = ps.executeQuery();

            while (rs.next())
                MAP.put(rs.getString("username"), new Data(rs.getInt("points"), rs.getInt("kills"), rs.getInt("assists"), rs.getInt("deaths")));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void loadPlayer(String username) {
        try (PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM stats WHERE username=?;")) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
                MAP.put(rs.getString("username"), new Data(rs.getInt("points"), rs.getInt("kills"), rs.getInt("assists"), rs.getInt("deaths")));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
