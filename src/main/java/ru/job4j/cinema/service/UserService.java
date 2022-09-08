package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.persistence.UserDBStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@ThreadSafe
public class UserService {
    private final UserDBStore userDBStore;
    private final TicketService ticketService;
    private final SessionService sessionService;

    public UserService(UserDBStore userDBStore, TicketService ticketService, SessionService sessionService) {
        this.userDBStore = userDBStore;
        this.ticketService = ticketService;
        this.sessionService = sessionService;
    }


    /**
     * добавляет usera в userDBStore
     * @param user
     * @return Optional<User>
     */
    public Optional<User> add(User user) {
        return userDBStore.add(user);
    }

    /**
     * Найти запись в БД по условию или пустой Optional
     * @param userName
     * @param email
     * @return Optional<User>
     */
    public Optional<User> findUserByUserNameAndEmail(String userName, String email) {
        return userDBStore.findUserByUserNameAndEmail(userName, email);
    }

    /**
     * Заменить запись во внутренем хранилище
     * на вновь переданую в аргументе.
     * @param user
     */
    public Optional<User> update(User user) {
        return userDBStore.update(user);
    }

    /**
     * Метод возвращает список билетов купленых пользователем.
     * @param user Пользователь.
     * @return Список купленных билетов.
     */
    public List<Ticket> findUserTickets(User user) {
        List<Ticket> tickets = new ArrayList<>(ticketService.findByUser(user));
        tickets.forEach(
                ticket -> ticket.setSession(
                        sessionService.findById(ticket.getFilmSessionId()).get()
                )
        );
        return tickets;
    }

}
