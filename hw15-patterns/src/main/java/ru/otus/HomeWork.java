package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.Listener;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.processor.Processor;
import ru.otus.processor.ProcessorChangeFields;
import ru.otus.processor.ProcessorThrowException;

import java.time.LocalDateTime;
import java.util.List;

public class HomeWork {

    /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage) - done!
       2. Сделать процессор, который поменяет местами значения field11 и field12 - done!
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду
       (сделайте тест с гарантированным результатом)
            Секунда должна определяьться во время выполнения. - done!
       4. Сделать Listener для ведения истории: старое сообщение - новое
       (подумайте, как сделать, чтобы сообщения не портились) - done!
     */

    public static void main(String[] args) {
        /*
           по аналогии с Demo.class
           из элеменов "to do" создать new ComplexProcessor и обработать сообщение
         */
        // Done!

        List<Processor> processors = List.of(new ProcessorThrowException(LocalDateTime::now),
                new ProcessorChangeFields());

        ComplexProcessor complexProcessor = new ComplexProcessor(processors, ex -> {});
        Listener historyListener = new HistoryListener();
        complexProcessor.addListener(historyListener);

        Message message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .build();

        Message result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        complexProcessor.removeListener(historyListener);
    }
}
