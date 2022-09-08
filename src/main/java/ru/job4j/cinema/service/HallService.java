package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.MovieHall;
import ru.job4j.cinema.model.Seat;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Класс для работы с залом.
 * Конфигурирование размеров зала.
 */
@Service
public class HallService {

    /**
     * Карта с различными залами разных размеров.
     */
    private final Map<Integer, MovieHall> halls = new ConcurrentHashMap<>();

    public HallService() {
        halls.putIfAbsent(1, new MovieHall(1, "Средний зал", 5, 6));
        halls.putIfAbsent(2, new MovieHall(2, "Малый зал", 3, 4));
        halls.putIfAbsent(3, new MovieHall(3, "Большой Зал",6, 7));
    }


    /**
     * Найти зал по id
     * @param id - индекс зала
     * @return обект зала
     */
    public List<Seat> findById(int id) {
        return halls.get(id).getSeats();
    }

    /**
     * Посмотреть размер зала
     * @return зал
     */
    public MovieHall getHall(int i) {
        return halls.get(i);
    }
}
