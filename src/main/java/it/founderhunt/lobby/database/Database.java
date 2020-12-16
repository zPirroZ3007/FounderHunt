package it.founderhunt.lobby.database;

import it.founderhunt.lobby.Main;
import net.tecnocraft.utils.objects.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    private static Connection con;

    public static void disconnect() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        if (con == null || con.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String database = Main.getDatabaseConf().getString("database.database");
                String password = Main.getDatabaseConf().getString("database.password");
                String username = Main.getDatabaseConf().getString("database.username");
                String port = Main.getDatabaseConf().getString("database.port");
                String host = Main.getDatabaseConf().getString("database.host");

                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", username, password);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return con;
    }

    public static void update(String sql) {
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void update(String sql, Object... args) {
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            int i = 1;
            for(Object arg : args) {
                setParameter(ps, i, arg);
                i++;
            }
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("ALL")
    private static void setParameter(PreparedStatement statement, int index, Object param) throws SQLException {
        if (param == null) {
            throw new SQLException("Parameter " + index + " for prepared statement cannot be null");
        } else {
            Class clazz;
            Object value;
            if (param instanceof Nullable) {
                Nullable nullable = (Nullable)param;
                clazz = nullable.getNullType();
                value = nullable.getValue();
            } else {
                clazz = param.getClass();
                value = param;
            }

            if (clazz == Integer.TYPE) {
                statement.setInt(index, (Integer)value);
            } else if (clazz == Integer.class) {
                statement.setInt(index, (Integer)value);
            } else if (clazz == Long.TYPE) {
                statement.setLong(index, (Long)value);
            } else if (clazz == Long.class) {
                statement.setLong(index, (Long)value);
            } else if (clazz == String.class) {
                statement.setString(index, (String)value);
            } else if (clazz == Boolean.TYPE) {
                statement.setBoolean(index, (Boolean)value);
            } else if (clazz == Boolean.class) {
                statement.setBoolean(index, (Boolean)value);
            } else if (clazz == Double.TYPE) {
                statement.setDouble(index, (Double)value);
            } else if (clazz == Double.class) {
                statement.setDouble(index, (Double)value);
            } else if (clazz == Float.TYPE) {
                statement.setFloat(index, (Float)value);
            } else if (clazz == Float.class) {
                statement.setFloat(index, (Float)value);
            } else if (clazz == Short.TYPE) {
                statement.setShort(index, (Short)value);
            } else if (clazz == Short.class) {
                statement.setShort(index, (Short)value);
            } else if (clazz == Byte.TYPE) {
                statement.setByte(index, (Byte)value);
            } else {
                if (clazz != Byte.class) {
                    throw new SQLException("Unknown or unsupported parameter type: " + clazz.getSimpleName());
                }

                statement.setByte(index, (Byte)value);
            }

        }
    }
}