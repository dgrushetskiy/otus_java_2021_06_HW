package ru.otus.testing.exercise.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.testing.exercise.MessageBuilder;
import ru.otus.testing.exercise.MessageTemplateProvider;
import ru.otus.testing.exercise.TemplateNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static ru.otus.testing.exercise.impl.DefaultMessageTemplateProvider.DEFAULT_TEMPLATE;

// with annotations  @ExtendWith(MockitoExtension.class)
public class MessageBuilderImplTest {
    public static final String DEFAULT_TEMPLATE_NAME = "defaultTemplate";
    public static final String DEFAULT_MESSAGE_TEXT = "defaultText";
    public static final String DEFAULT_SIGNATURE = "defaultSignature";

    //with annotations    @Mock
    private MessageTemplateProvider provider;

    //with annotations    @InjectMocks
    //private MessageBuilderImpl messageBuilder;
    private MessageBuilder messageBuilder;

    /**
     * with annotations not needed
     */
    @BeforeEach
    void setUp() {
        provider = mock(MessageTemplateProvider.class);
        messageBuilder = new MessageBuilderImpl(provider);
    }

    @Test
    void buildMessageTestOne() {
        when(provider.getMessageTemplate(Mockito.any())).thenReturn(DEFAULT_TEMPLATE);

        String expectedMessage = String.format(DEFAULT_TEMPLATE, DEFAULT_MESSAGE_TEXT, DEFAULT_SIGNATURE);
        String actualMessage = messageBuilder.buildMessage(null, DEFAULT_MESSAGE_TEXT, DEFAULT_SIGNATURE);

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void buildMessageTestTwo() {
        when(provider.getMessageTemplate(DEFAULT_TEMPLATE_NAME)).thenReturn(" ");

        messageBuilder.buildMessage(DEFAULT_TEMPLATE_NAME, null, null);

        verify(provider, times(1)).getMessageTemplate(DEFAULT_TEMPLATE_NAME);
    }

    @Test
    void buildMessageTestThree() {
        assertThrows(TemplateNotFoundException.class, () -> messageBuilder.buildMessage(null, null, null));
    }
}
