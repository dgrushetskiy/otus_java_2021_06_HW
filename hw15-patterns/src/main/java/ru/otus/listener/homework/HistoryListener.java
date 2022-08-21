package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {
    // хранение всей истории, по id получаем последнее добавленное сообщение
    private final HashMap<Long, LinkedList<Memento>> history = new HashMap<>();
    private final DateTimeProvider provider = LocalDateTime::now;

    @Override
    public void onUpdated(Message msg) {
        ObjectForMessage field13 = new ObjectForMessage();
        if (msg.getField13() != null) {
            field13.setData(new ArrayList<>(msg.getField13().getData()));
        }
        Message copiedMessage = new Message.Builder(msg.getId())
                .field1(msg.getField1())
                .field2(msg.getField2())
                .field3(msg.getField3())
                .field4(msg.getField4())
                .field5(msg.getField5())
                .field6(msg.getField6())
                .field7(msg.getField7())
                .field8(msg.getField8())
                .field9(msg.getField9())
                .field10(msg.getField10())
                .field11(msg.getField11())
                .field12(msg.getField12())
                .field13(field13)
                .build();
        LinkedList<Memento> historyList = history.getOrDefault(msg.getId(), new LinkedList<>());
        historyList.add(new Memento(copiedMessage, provider.getDateTime()));
        history.put(msg.getId(), historyList);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return history.containsKey(id)
                ? Optional.ofNullable(history.get(id).getLast().getMessage())
                : Optional.empty();
    }
}
