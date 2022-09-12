package ru.job4j.cinema.controller;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class TicketControllerTest {

    /**
     * Прверяем метод hall из TicketController.
     * Метод должен вернуть список фильмов,
     * на странице hall.html
     * verify - проверяет данные в Model,
     * Метод verify анализирует вызванные
     * методы у объекта заглушки model.
     * Если коллекция sessions1 переданная
     * в модель и вызванная у метода verify
     * будут отличаться, то тест упадет с ошибкой.
     */
    @Test
    public void whenHall() {
        List<Session> sessions1 = Arrays.asList(
                new Session(1, "Фильм 1"),
                new Session(2, "Фильм 2")
        );
        Model model = mock(Model.class);
        HttpSession httpSession = mock(HttpSession.class);
        TicketService ticketService = mock(TicketService.class);
        SessionService sessionService = mock(SessionService.class);
        HallService hallService = mock(HallService.class);

        when(sessionService.findAllSession()).thenReturn(sessions1);
        TicketController ticketController = new TicketController(
                ticketService,
                sessionService,
                hallService);
        String page = ticketController.hall(model, httpSession);
        verify(model).addAttribute("films", sessions1);
        assertThat(page, is("hall"));
    }

    /**
     * В методе проверяем создание Ticket.
     * Создание билета происходит после
     * прохождения html страниц: hall,
     * selectSeat, payment. Где происходит
     * выбор данных, пользователем,
     * формирующих в последующем поля ticket.
     */
    @Test
    public void whenCreateTicket() {
        Ticket testTicket = new Ticket(5, 3, 2, 2, 1);
        TicketService ticketService = mock(TicketService.class);
        SessionService sessionService = mock(SessionService.class);
        HallService hallService = mock(HallService.class);
        TicketController ticketController = new TicketController(
                ticketService,
                sessionService,
                hallService);
        String page = ticketController.createTicket(testTicket);
        verify(ticketService).create(testTicket);
        assertThat(page, is("redirect:/hall?fail=true"));
    }

}
