package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Модель данных.
 * Класс описывает купленный билет на сеанс.
 */
public class Ticket {
    private int id;
    private int filmSessionId;
    private int row;
    private int cell;
    private int userId;
    private Session session;

    public Ticket() {
        this.id = 0;
        this.filmSessionId = 0;
        this.row = 0;
        this.cell = 0;
        this.userId = 0;
    }

    public Ticket(int id, int filmSession_id, int row, int cell, int userId) {
        this.id = id;
        this.filmSessionId = filmSession_id;
        this.row = row;
        this.cell = cell;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFilmSessionId() {
        return filmSessionId;
    }

    public void setFilmSessionId(int filmSessionId) {
        this.filmSessionId = filmSessionId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return id == ticket.id
                && filmSessionId == ticket.filmSessionId
                && row == ticket.row
                && cell == ticket.cell
                && userId == ticket.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filmSessionId, row, cell, userId);
    }
}
