package ru.otus.processor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.homework.DateTimeProvider;
import ru.otus.model.Message;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProcessorThrowExceptionTest {

    @Test
    @DisplayName("Тестируем нечетные секунды")
    void test_odd() {
        Message message = new Message.Builder(1L).field7("field7").build();

        DateTimeProvider provider = mock(DateTimeProvider.class);
        when(provider.getDateTime()).thenReturn(LocalDateTime.of(LocalDate.now(), LocalTime.of(2, 2, 1)));

        Processor throwProcessor = new ProcessorThrowException(provider);

        ComplexProcessor complexProcessor = new ComplexProcessor(Collections.singletonList(throwProcessor),
                (ex) -> {});

        Message message1 = complexProcessor.handle(message);

        assertThat(message).isEqualTo(message1);
    }

    @Test
    @DisplayName("Тестируем четные секунды")
    void test_even() {
        Message message = new Message.Builder(1L).field7("field7").build();

        DateTimeProvider provider = mock(DateTimeProvider.class);
        when(provider.getDateTime()).thenReturn(LocalDateTime.of(LocalDate.now(),
                LocalTime.of(2, 2, 2)));

        Processor throwProcessor = new ProcessorThrowException(provider);

        ComplexProcessor complexProcessor = new ComplexProcessor(Collections.singletonList(throwProcessor), (ex) -> {
            throw new TestException(ex.getMessage());
        });


        Exception exception = assertThrows(TestException.class, () -> complexProcessor.handle(message));
        assertEquals("Oh no! Even second, my only weakness!", exception.getMessage());
    }

    private static class TestException extends RuntimeException {
        public TestException(String message) {
            super(message);
        }
    }
}