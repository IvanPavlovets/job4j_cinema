package ru.job4j.cinema.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс кинозала с различным количеством мест.
 */
public class MovieHall {
    private int id;
    private String name;
    private final int rows;
    private final int cells;

    private final int numOfSeats;
    private final List<Seat> seats = new ArrayList<>();

    /**
     * В конструкторе инициализируем зал местами.
     * @param id
     * @param rows
     * @param cells
     */
    public MovieHall(int id, String name, int rows, int cells) {
        this.id = id;
        this.name = name;
        this.rows = rows;
        this.cells = cells;
        numOfSeats = rows * cells;
        for (int row = 1; row <= rows; row++) {
            for (int cell = 1; cell <= cells; cell++) {
                seats.add(new Seat(row, cell));
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRows() {
        return rows;
    }

    public int getCells() {
        return cells;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public int getNumOfSeats() {
        return numOfSeats;
    }
}
