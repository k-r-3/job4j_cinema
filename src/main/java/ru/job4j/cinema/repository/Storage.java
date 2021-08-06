package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
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
        Place place = null;
        System.out.println("findPlaceBySession session id:" + sessionId);
        try (Connection cn = pool.getConnection();
             PreparedStatement statement = cn.prepareStatement("SELECT * FROM place "
                     + "WHERE user_session = ?")
        ) {
            statement.setInt(1, sessionId);
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
            LOG.debug("find Place by session exception", e);
        }
        Optional<Place> optional = Optional.ofNullable(place);
        System.out.println(optional.isPresent());
        return optional;
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
        System.out.println("status from db " + place.getStatus());
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

    public void changePlaceStatus(Place place, int sessionId) {
        System.out.println("place name " + place.getName());
        boolean status = true;
        int userSession = 0;
        Optional<Place> placeForUpdate = findPlaceBySession(sessionId);
        System.out.println("placeForUpdate present? " + placeForUpdate.isPresent());
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
            statement.execute();
            System.out.println("change complete");
        } catch (SQLException e) {
            LOG.debug("change Place Status", e);
        }
    }

    public Account saveAccount(Account account) {
        Account withId = account;
        System.out.println("saveAccount start");
        try (Connection cn = pool.getConnection();
             PreparedStatement stat = cn.prepareStatement("INSERT INTO account (username, email, phone) "
                     + "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)
        ) {
            stat.setString(1, account.getName());
            stat.setString(2, account.getEmail());
            stat.setString(3, account.getPhone());
            stat.execute();
            try (ResultSet result = stat.getGeneratedKeys()) {
                System.out.println("save acc resultset");
                if (result.next()) {
                    withId.setId(result.getInt(1));
                }
            }
            System.out.println("save acc end");
        } catch (SQLException e) {
            Set<ConstraintViolation<String>> set = new HashSet<>();
            throw new ConstraintViolationException(set);
        }
        return withId;
    }

    public boolean checkAccount(Account account) {
        Account base = new Account();
        try (Connection cn = pool.getConnection();
             PreparedStatement stat = cn.prepareStatement("SELECT * FROM account a WHERE a.email = ?")
        ) {
            stat.setString(1, account.getEmail());
            try (ResultSet result = stat.executeQuery()) {
                while (result.next()) {
                    base.setName(result.getString("username"));
                    base.setEmail(result.getString("email"));
                    base.setPhone(result.getString("phone"));
                }
            }
        } catch (SQLException e) {
            LOG.debug("check account exception", e);
        }
        return account.equals(base);
    }

    public Ticket saveTicket(Ticket ticket) {
        Ticket withId = new Ticket();
        try (Connection cn = pool.getConnection();
             PreparedStatement stat = cn.prepareStatement("INSERT INTO ticket (session_id, row, cell, account_id) "
                     + "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)
        ) {
            stat.setInt(1, 0);
            stat.setInt(2, ticket.getRow());
            stat.setInt(3, ticket.getCell());
            stat.setInt(4, ticket.getAccountId());
            stat.execute();
            try (ResultSet result = stat.getGeneratedKeys()) {
                if (result.next()) {
                    withId.setId(result.getInt(1));
                }
            }
            System.out.println("save ticket end");
        } catch (SQLException e) {
            Set<ConstraintViolation<String>> set = new HashSet<>();
            throw new ConstraintViolationException(set);
        }
        return withId;
    }

    public boolean isFree(Ticket ticket) {
        boolean check = true;
        try (Connection cn = pool.getConnection();
             PreparedStatement stat = cn.prepareStatement("SELECT * FROM ticket t WHERE t.account_id = ?")
        ) {
            stat.setInt(1, ticket.getAccountId());
            check = stat.execute();
        } catch (SQLException e) {
            LOG.debug("ticket check exception", e);
        }
        return check;
    }


}
