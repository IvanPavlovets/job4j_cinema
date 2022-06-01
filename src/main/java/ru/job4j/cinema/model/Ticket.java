package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Модель данных.
 * Класс описывает купленный билет на сеанс.
 */
public class Ticket {
    private int id;
    private Session filmSession; // сеанс
    private int row; // ряд
    private int cell; // место
    private User user;

    public Ticket() {
    }

    public Ticket(int id, Session filmSession, int row, int cell, User user) {
        this.id = id;
        this.filmSession = filmSession;
        this.row = row;
        this.cell = cell;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Session getFilmSession() {
        return filmSession;
    }

    public void setFilmSession(Session filmSession) {
        this.filmSession = filmSession;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id &&
                row == ticket.row &&
                cell == ticket.cell &&
                Objects.equals(filmSession, ticket.filmSession) &&
                Objects.equals(user, ticket.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filmSession, row, cell, user);
    }
}
