package ru.otus.processor;

import ru.otus.exception.ApocalypseException;
import ru.otus.listener.homework.DateTimeProvider;
import ru.otus.model.Message;

import java.time.LocalDateTime;

public class ProcessorThrowException implements Processor {

    private final DateTimeProvider provider;

    public ProcessorThrowException(DateTimeProvider provider) {
        this.provider = provider;
    }

    @Override
    public Message process(Message message) {
        LocalDateTime dateTime = provider.getDateTime();
        if (dateTime.getSecond() % 2 == 0) {
            throw new ApocalypseException();
        }
        return message;
    }
}
