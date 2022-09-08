package ru.job4j.cinema.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

@Repository
public class UserDBStore {

    private final BasicDataSource pool;

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * Добавляет User в бд.
     * На поле email, phone ноложено ограничение
     * UNIQUE, при добавлении дубликата email,
     * phone в бд, возникнет исключение и метод
     * вернет пустой Optional.
     * @param user
     * @return Optional<User>
     */
    public Optional<User> add(User user) {
        String insertStmnt = "INSERT INTO users(username, email, phone) VALUES (?,?,?)";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(insertStmnt, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.ofNullable(user);
    }

    /**
     * Находит запись в БД по условию.
     * Возвращает найденую запись или пустой Optional.
     * @param userName
     * @param email
     * @return Optional<User>
     */
    public Optional<User> findUserByUserNameAndEmail(String userName, String email) {
        User user;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM users WHERE username = ?"
                     + " AND email = ?")) {
            ps.setString(1, userName);
            ps.setString(2, email);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    user = new User(it.getInt("id"),
                            it.getString("username"),
                            it.getString("email"),
                            it.getString("phone")
                    );
                    return Optional.of(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Обновляет запись в БД.
     * Поля старой записи по id меняеться на
     * поля из переданого user.
     * @param user
     */
    public Optional<User> update(User user) {
        String updateQuery = "UPDATE users SET username = ?, email = ?, phone = ? WHERE id = ?";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(updateQuery)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setInt(4, user.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.ofNullable(user);
    }

}
