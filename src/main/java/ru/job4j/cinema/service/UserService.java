package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.persistence.UserDBStore;

import java.util.Optional;


@Service
@ThreadSafe
public class UserService {
    private final UserDBStore userDBStore;

    public UserService(UserDBStore userDBStore) {
        this.userDBStore = userDBStore;
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
}
