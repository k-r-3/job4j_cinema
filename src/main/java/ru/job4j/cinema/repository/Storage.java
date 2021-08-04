package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Storage {
    private static final Logger LOG = LoggerFactory.getLogger(Storage.class.getName());
    private final BasicDataSource pool = new BasicDataSource();

    private Storage() {
        Properties config = new Properties();
        try (InputStream in = Storage.class.getClassLoader()
                .getResourceAsStream("app.properties")) {
            config.load(in);
            Class.forName(config.getProperty("driver_class"));
            pool.setDriverClassName(config.getProperty("driver_class"));
            pool.setUrl(config.getProperty("url"));
            pool.setUsername(config.getProperty("username"));
            pool.setPassword(config.getProperty("password"));
            pool.setMinIdle(5);
            pool.setMaxIdle(10);
            pool.setMaxOpenPreparedStatements(100);
        } catch (IOException | ClassNotFoundException e) {
            LOG.debug("initial exception", e);
        }
    }

    private static final class Lazy {
        private static final Storage STORAGE = new Storage();
    }

    public static Storage instOf() {
        return Lazy.STORAGE;
    }

    public Optional<Place> findPlaceBySession(int sessionId) {
        Optional<Place> place = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement statement = cn.prepareStatement("SELECT * FROM place "
                     + "WHERE user_session = ?")
        ) {
            statement.setInt(1, sessionId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    place.get().setName(String.valueOf(result.getInt("id")));
                    place.get().setRow(result.getInt("row"));
                    place.get().setCell(result.getInt("cell"));
                    place.get().setStatus(result.getBoolean("status"));
                    place.get().setPrice(result.getInt("price"));
                }
            }
        } catch (SQLException e) {
            LOG.debug("find Place by session exception", e);
        }
        return place;
    }

    public Place findById(int id) {
        Place place = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement statement = cn.prepareStatement("SELECT * FROM place WHERE id = ?")
        ) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    place = new Place();
                    place.setName(String.valueOf(result.getInt("id")));
                    place.setRow(result.getInt("row"));
                    place.setCell(result.getInt("cell"));
                    place.setStatus(result.getBoolean("status"));
                    place.setPrice(result.getInt("price"));
                }
            }
        } catch (SQLException e) {
            LOG.debug("find Place by id exception", e);
        }
        return place;
    }

    public Collection<Place> findOccupiedPlace() {
        List<Place> placeList = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement statement = cn.prepareStatement("SELECT * FROM place "
                     + "WHERE status = FALSE")
        ) {
           try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    Place place = new Place();
                    place.setName(String.valueOf(result.getInt("id")));
                    place.setRow(result.getInt("row"));
                    place.setCell(result.getInt("cell"));
                    placeList.add(place);
                }
           }
        } catch (SQLException e) {
            LOG.debug("find Occupied Place exception", e);
        }
        return placeList;
    }

    public boolean changePlaceStatus(Place place, int sessionId) {
        System.out.println("place name " + place.getName());
        boolean result = false;
        boolean status = true;
        int userSession = 0;
        Optional<Place> placeForUpdate = findPlaceBySession(sessionId);
        if (!placeForUpdate.isPresent()) {
            status = false;
            userSession = sessionId;
        }
        try (Connection cn = pool.getConnection();
             PreparedStatement statement = cn.prepareStatement("UPDATE place "
                     + "SET status = ?, user_session = ? "
                     + "WHERE id = ?")
        ) {
            statement.setBoolean(1, status);
            statement.setInt(2, userSession);
            statement.setInt(3, Integer.parseInt(place.getName()));
            result = statement.execute();
        } catch (SQLException e) {
            LOG.debug("change Place Status", e);
        }
        return result;
    }
}
