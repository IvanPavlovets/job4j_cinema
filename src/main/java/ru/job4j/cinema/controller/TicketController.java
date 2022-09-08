package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

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

    /**
     * Получить размер зала.
     */
    private final HallService hallService;

    public TicketController(TicketService ticketService, SessionService sessionService, HallService hallService) {
        this.ticketService = ticketService;
        this.sessionService = sessionService;
        this.hallService = hallService;
    }

    /**
     * Обрабатывает переход на hall.html -
     * загружает страницу всех сеансов.
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


    /**
     * Обрабатывает переход на selectSeat.html -
     * страница с выбором ряда и места на сеанс.
     * Передает параметр filmId выбраный ранее.
     * @param model
     * @param session
     * @param id передаваемый параметр
     * @return String
     */
    @GetMapping("/selectSeat/{filmId}")
    public String selectSeat(Model model, HttpSession session,
                              @PathVariable("filmId") int id) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUserName("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("hallSize", hallService.getHall(id).getName());
        model.addAttribute("seats", sessionService.findFreeSeats(id));
        model.addAttribute("film", sessionService.findById(id).get());
        return "selectSeat";
    }

    /**
     * Обрабатывает переход на payment.html -
     * страница с итогами выбора. Передаються
     * параметры выбранные ранее filmId, row, cell
     * @param model
     * @param id
     * @param row
     * @param cell
     * @param session
     * @return String
     */
    @GetMapping("/payment/{filmId}/{row}/{cell}")
    public String payment(Model model,
                               @PathVariable("filmId") int id,
                               @PathVariable("row") int row,
                               @PathVariable("cell") int cell,
                               HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUserName("Гость");
        }
        model.addAttribute("film", sessionService.findById(id).get());
        model.addAttribute("user", user);
        model.addAttribute("row", row);
        model.addAttribute("cell", cell);
        return "payment";
    }

    /**
     * Покупка билета на основе ранее выбранных
     * данных и их сохранение в store.
     * Затем купленый билет отображаеться
     * в личной картачке пользователя.
     * @param ticket
     * @return String
     */
    @PostMapping("/createTicket")
    public String createTicket(@ModelAttribute Ticket ticket) {
        Optional<Ticket> addTicket = ticketService.create(ticket);
        if (addTicket.isEmpty()) {
            return "redirect:/hall?fail=true";
        }
        return "redirect:/personlInfo";
    }
}
