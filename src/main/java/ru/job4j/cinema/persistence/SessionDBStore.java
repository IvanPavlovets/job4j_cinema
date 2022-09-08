package ru.job4j.cinema.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class SessionDBStore {

    /**
     * Внутри создаются коннекты к базе данных,
     * которые находятся в многопоточной очереди
     */
    private final BasicDataSource pool;

    public SessionDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * Достает все значения из хранилища (БД)
     * @return List<Session>
     */
    public List<Session> findAllSession() {
        List<Session> sessions = new ArrayList<>();
        Session session;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM sessions")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    session = new Session(it.getInt("id"),
                            it.getString("name")
                    );
                    sessions.add(session);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessions;
    }

    /**
     * Метод ищет сеанс по id
     * @param id
     * @return сеанс
     */
    public Optional<Session> findSessionById(int id) {
        Session session;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM sessions WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    session = new Session(it.getInt("id"),
                            it.getString("name")
                    );
                    return Optional.of(session);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
