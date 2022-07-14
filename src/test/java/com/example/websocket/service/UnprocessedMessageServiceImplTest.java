package com.example.websocket.service;

import com.example.websocket.configuration.builders.MessageModelBuilder;
import com.example.websocket.configuration.builders.UnprocessedMessageModelBuilder;
import com.example.websocket.domain.UnprocessedMessage;
import com.example.websocket.repository.UnprocessedMessageRepository;
import com.example.websocket.service.impl.UnprocessedMessageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnprocessedMessageServiceImplTest {

    @Mock
    UnprocessedMessageRepository unprocessedMessageRepository;

    @InjectMocks
    UnprocessedMessageServiceImpl testedObj;


    @Test
    void saveUnprocessedMessage_shouldSaveToDb() {
        //given
        final var input = new MessageModelBuilder().build();
        final var model = new UnprocessedMessageModelBuilder().build();

        //when
        when(unprocessedMessageRepository.save(isA(UnprocessedMessage.class))).thenReturn(model);

        //then
        testedObj.saveUnprocessedMessage(input);

        verify(unprocessedMessageRepository, times(1)).save(isA(UnprocessedMessage.class));
    }

    @Test
    void saveUnprocessedMessage_shouldNotSaveToDb_throwException() {
        //given
        final var input = new MessageModelBuilder().build();

        //when
        when(unprocessedMessageRepository.save(isA(UnprocessedMessage.class))).thenThrow(RuntimeException.class);

        //then
        assertThrows(RuntimeException.class, () -> testedObj.saveUnprocessedMessage(input));

        verify(unprocessedMessageRepository, times(1)).save(isA(UnprocessedMessage.class));
    }
}
