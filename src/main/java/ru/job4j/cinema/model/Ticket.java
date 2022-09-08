package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Модель данных.
 * Класс описывает купленный билет на сеанс.
 */
public class Ticket {
    private int id;
    private int filmSession_id; // сеанс
    private int row; // ряд
    private int cell; // место
    private int user_id;
    private Session session;

    public Ticket() {
        this.id = 0;
        this.filmSession_id = 0;
        this.row = 0;
        this.cell = 0;
        this.user_id = 0;
    }

    public Ticket(int id, int filmSession_id, int row, int cell, int user_id) {
        this.id = id;
        this.filmSession_id = filmSession_id;
        this.row = row;
        this.cell = cell;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFilmSession_id() {
        return filmSession_id;
    }

    public void setFilmSession_id(int filmSession_id) {
        this.filmSession_id = filmSession_id;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id &&
                filmSession_id == ticket.filmSession_id &&
                row == ticket.row &&
                cell == ticket.cell &&
                user_id == ticket.user_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filmSession_id, row, cell, user_id);
    }
}
