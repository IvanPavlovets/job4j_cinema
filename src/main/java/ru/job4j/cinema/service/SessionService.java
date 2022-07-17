package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.persistence.SessionDBStore;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Вычисление свободных мест для сеанса
 */
@Service
public class SessionService {

    /**
     * Работа с БД через слой персистенции.
     */
    private final SessionDBStore sessionDBStore;

    public SessionService(SessionDBStore sessionDBStore) {
        this.sessionDBStore = sessionDBStore;
    }

    public void create(Session session) {
    }

    public List<Session> findAllSession() {
        return sessionDBStore.findAllSession();
    }

    public Map<Integer, List<Integer>> findFreeSeats() {
        Map<Integer, List<Integer>> hall = Map.of(1,Arrays.asList(1, 2, 3),
                2,Arrays.asList(1, 2, 3),
                3,Arrays.asList(1, 2, 3)
        );
        return hall;
    }

    public Optional<Session> findById(int id) {
        return sessionDBStore.findSessionById(id);
    }

}
