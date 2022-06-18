package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpSession;

@Controller
@ThreadSafe
public class IndexControl {

    /**
     * Работа с TickeDBStore через промежуточный слой TicketService
     */
    private final TicketService ticketService;

    public IndexControl(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * Обрабатывает переход на главную страницу index.html
     * Spring создаст сам этот обьект и загрузит его данные
     * в предсавлении (html странице).
     * @param model
     * @param session
     * @return String
     */
    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUserName("Гость");
        }
        model.addAttribute("user", user);
        return "index";
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
