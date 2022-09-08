package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@ThreadSafe
public class UserController {

    /**
     * Работа с БД через слой Service.
     */
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * Обрабатывает переход на страницу personalInfo.html
     * Spring создаст сам этот обьект и загрузит его данные
     * в предсавлении (html странице).
     * @param model
     * @param session
     * @return String
     */
    @GetMapping("/personlInfo")
    public String personlInfo(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUserName("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("tickets", userService.findUserTickets(user));
        return "personlInfo";
    }

    /**
     * загружает страницу addUser.html.
     * - Параметр fail создаеться для отработки
     * предупрежения alert в предсавлении
     * на тот случай когда вернеться
     * пустой Optional в условии.
     * @param model
     * @return String
     */
    @GetMapping("/formAddUser")
    public String addUser(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        model.addAttribute("user", new User());
        return "addUser";
    }

    /**
     * Метод перехода на обновления карточки
     * пользователя. Передаються параметры
     * выбранные ранее userName, userEmail.
     * @param model
     * @param name
     * @param email
     * @param fail
     * @return String
     */
    @GetMapping("/formUpdateUser/{userName}/{userEmail}")
    public String addUser(Model model,
                          @PathVariable("userName") String name,
                          @PathVariable("userEmail") String email,
                          @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        Optional<User> userDb = userService.findUserByUserNameAndEmail(
                name, email
        );
        model.addAttribute("user", userDb.get());
        return "updateUser";
    }

    /**
     * Метод обработки изменений в карточки пользователя
     * @param user
     * @param req
     * @return String
     */
    @PostMapping("/updateUser")
    public String updatePost(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> updateUser = userService.update(user);
        if (updateUser.isEmpty()) {
            return "redirect:/formUpdateUser?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", updateUser.get());
        return "redirect:/personlInfo";
    }

    /**
     * Метод регистрации впервые.
     * Обрабатывает добавление данных
     * из полеей ввода в обьект user и
     * и последующеее сохранение в UserDB.
     * В условии проверка на пустой Optional
     * - При возврате пустого Optional у параметра fail
     * значение поменяеться на true и переход на formAddUser.
     * @param model
     * @param user
     * @return String
     */
    @PostMapping("/registration")
    public String registration(@ModelAttribute User user) {
        Optional<User> regUser = userService.add(user);
        if (regUser.isEmpty()) {
            return "redirect:/formAddUser?fail=true";
        }
        return "redirect:/loginPage";
    }

    /**
     * Загружает страницу login.html
     * - Параметр fail создаеться для отработки
     * предупрежения alert в предсавлении
     * на тот случай когда вернеться
     * пустой Optional в условии.
     * @param model
     * @param fail
     * @return String
     */
    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    /**
     * Метод авторизации.
     * Делает обработку действий на странице login.html
     * В условии проверка на пустой Optional.
     * Из HttpServletRequest получаем обьект
     * HttpSession - текущая ссесия в данном браузере,
     * внутри используется ConcurrentHashMap, в котором
     * можно хранить текущего user, добовляем его в map
     * с помощью setAttribute().
     * При возврате пустого Optional у параметра fail
     * значение поменяеться на true и переход на loginPage.
     * @param user
     * @param req
     * @return String
     */
    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = userService.findUserByUserNameAndEmail(
                user.getUserName(), user.getEmail()
        );
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }

    /**
     * Обработки нажатия ссылки "Выход"
     * Удаляет все данные связанные с текущем пользователем
     * и завершает текущую сессию.
     * @param session
     * @return String
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }

}

