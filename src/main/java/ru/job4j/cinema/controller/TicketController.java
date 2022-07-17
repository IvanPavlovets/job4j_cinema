package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

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
    /**
     * Работа с SessionDBStore через промежуточный слой SessionService
     */
    private final SessionService sessionService;

    public TicketController(TicketService ticketService, SessionService sessionService) {
        this.ticketService = ticketService;
        this.sessionService = sessionService;
    }

    /**
     * Обрабатывает переход на hall.html
     * Используется Thymeleaf для поиска объектов,
     * которые нужны отобразить на виде.
     * Загружаем из текущей ссесии User и добовляем в Model
     * Spring создаст сам этот обьект и загрузит его данные
     * в предсавлении (html странице).
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
        model.addAttribute("films", sessionService.findAllSession());
        return "hall";
    }


    @GetMapping("/payment/{filmId}")
    public String payment(Model model, HttpSession session,
                          @PathVariable("filmId") int id) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUserName("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("film", sessionService.findById(id).get());
        model.addAttribute("rows", sessionService.findFreeSeats().keySet());
        model.addAttribute("cells", sessionService.findFreeSeats().values());
        return "payment";
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
