package ru.otus.listener.homework;

import ru.otus.model.Message;

import java.time.LocalDateTime;

public class Memento {

    private final Message message;
    private final LocalDateTime dateTime;

    public Memento(Message message, LocalDateTime dateTime) {
        this.dateTime = dateTime;
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

}
