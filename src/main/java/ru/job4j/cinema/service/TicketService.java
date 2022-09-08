package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.persistence.TicketDBStore;

import java.util.List;
import java.util.Optional;

/**
 * Слой службы, работа с куплеными билетами.
 */
@Service
public class TicketService {

    private final TicketDBStore ticketDBStore;

    public TicketService(TicketDBStore ticketDBStore) {
        this.ticketDBStore = ticketDBStore;
    }
    /**
     * Метод создания билета в кино.
     * @param Optional<Ticket>
     */
    public Optional<Ticket> create(Ticket ticket) {
        return ticketDBStore.add(ticket);
    }

    public List<Ticket> findAll() {
        return ticketDBStore.findAll();
    }

    /**
     * Метод возвращает все билеты купленные конкретным пользователем.
     * @param user пользователь.
     * @return список билетов List<Ticket>
     */
    public List<Ticket> findByUser(User user) {
        return ticketDBStore.findByUser(user);
    }

}
