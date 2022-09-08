package ru.job4j.cinema.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TicketDBStore {

    /**
     * Внутри создаются коннекты к базе данных,
     * которые находятся в многопоточной очереди
     */
    private final BasicDataSource pool;

    public TicketDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * Добавление купленного билета в БД.
     * @param ticket
     * @return Optional<Ticket>
     */
    public Optional<Ticket> add(Ticket ticket) {
        Optional<Ticket> result = Optional.empty();
        String insertStmnt = "INSERT INTO ticket(session_id, row, cell, user_id) VALUES (?, ?, ?, ?)";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(insertStmnt, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getFilmSession_id());
            ps.setInt(2, ticket.getRow());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, ticket.getUser_id());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    ticket.setId(id.getInt(1));
                }
                result = Optional.of(ticket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * Вернуть все билеты
     * @return
     */
    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        Ticket ticket;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM ticket")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    ticket = new Ticket(it.getInt("id"),
                            it.getInt("session_id"), it.getInt("row"),
                            it.getInt("cell"), it.getInt("user_id")
                    );
                    tickets.add(ticket);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tickets;
    }

    /**
     * Метод ищет все билеты, приобретенные пользователем.
     * @param user идентификационный номер пользователя.
     * @return список билетов.
     */
    public List<Ticket> findByUser(User user) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM ticket WHERE user_id = ?")
        ) {
            ps.setInt(1, user.getId());
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    Ticket result = new Ticket(it.getInt("id"), it.getInt("session_id"),
                            it.getInt("row"), it.getInt("cell"), it.getInt("user_id"));
                    tickets.add(result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tickets;
    }
}
