package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpSession;

/**
 * Контролер для работы с залом.
 * Выборор фильма. Ряда и места.
 */
@Controller
@ThreadSafe
public class TicketController {

    /**
     * Работа с TickeDBStore через промежуточный слой TicketService
     */
    private final TicketService ticketService;

    private final SessionService sessionService;

    public TicketController(TicketService ticketService, SessionService sessionService) {
        this.ticketService = ticketService;
        this.sessionService = sessionService;
    }

    /**
     * Обрабатывает переход на страницу hall.html
     * Spring создаст сам этот обьект и загрузит его данные
     * в предсавлении (html странице).
     * @param model
     * @param session
     * @return String
     */
    @GetMapping("/hall")
    public String hall(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUserName("Гость");
        }
        model.addAttribute("user", user);
        return "hall";
    }

    /**
     * Обрабатывает добавление данных в post
     * и их сохранение в store.
     * Города в обьекте post не имеют имени,
     * поэтому достаем его из славоря через службу.
     * @return String
     */
    @PostMapping("/createSession")
    public String createSession(@ModelAttribute Session session) {
        sessionService.create(session);
        return "redirect:/payment";
    }

    /**
     * Обрабатывает добавление данных в post
     * и их сохранение в store.
     * Города в обьекте post не имеют имени,
     * поэтому достаем его из славоря через службу.
     * @param ticket
     * @return String
     */
    @PostMapping("/createTicket")
    public String createTicket(@ModelAttribute Ticket ticket) {
        ticketService.create(ticket);
        return "redirect:/payment";
    }
}
