package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.persistence.SessionDBStore;

import java.util.*;

/**
 * Слой службы.
 * Вычисление свободных мест для сеанса
 */
@Service
public class SessionService {

    /**
     * Работа с БД через слой персистенции.
     */
    private final SessionDBStore sessionDBStore;
    private final HallService hallService;
    private final TicketService ticketService;

    public SessionService(SessionDBStore sessionDBStore, HallService hallService, TicketService ticketService) {
        this.sessionDBStore = sessionDBStore;
        this.hallService = hallService;
        this.ticketService = ticketService;
    }

    public void create(Session session) {
    }

    public List<Session> findAllSession() {
        return sessionDBStore.findAllSession();
    }

    /**
     * Метод возвращает список свободных мест в зале.
     * @param id идентификационный номер зала.
     * @return List<Seat> список свободных мест в зале.
     */
    public List<Seat> findFreeSeats(int id) {
        List<Ticket> tickets = ticketService.findAll();
        List<Seat> seats = hallService.findById(id);
        List<Seat> reservedSeats = new ArrayList<>();
        for (Seat seat : seats) {
            for (Ticket ticket: tickets) {
                if (seat.getRow() == ticket.getRow()
                        && seat.getCell() == ticket.getCell()
                        && id == ticket.getFilmSession_id()) {
                    reservedSeats.add(seat);
                }
            }
        }
        reservedSeats.forEach(seats::remove);
        return seats;
    }

    public Optional<Session> findById(int id) {
        return sessionDBStore.findSessionById(id);
    }

}
