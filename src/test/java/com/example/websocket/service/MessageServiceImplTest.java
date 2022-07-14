package com.example.websocket.service;

import com.example.websocket.configuration.builders.MessageDtoBuilder;
import com.example.websocket.configuration.builders.MessageModelBuilder;
import com.example.websocket.controller.dto.MessageDto;
import com.example.websocket.domain.Message;
import com.example.websocket.mapper.MessageMapper;
import com.example.websocket.repository.MessageRepository;
import com.example.websocket.service.impl.MessageServiceImpl;
import com.example.websocket.service.kafka.KafkaMessageWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {

    @Mock
    MessageRepository messageRepository;

    @Mock
    KafkaMessageWriter kafkaEventWriter;

    @Mock
    MessageMapper messageMapper;

    @InjectMocks
    MessageServiceImpl testedObj;

    @Test
    void saveMessage_shouldSaveAndSendMessage() {
        //given
        final var dto = new MessageDtoBuilder().build();
        final var modelWithoutId = new MessageModelBuilder().withId(null).build();
        final var modelWithId = new MessageModelBuilder().withId(1L).build();

        //when
        when(messageMapper.dtoToModel(isA(MessageDto.class))).thenReturn(modelWithoutId);
        when(messageRepository.save(isA(Message.class))).thenReturn(modelWithId);
        doNothing().when(kafkaEventWriter).write(isA(Message.class));

        testedObj.saveMessage(dto);

        //then
        verify(messageMapper,times(1)).dtoToModel(isA(MessageDto.class));
        verify(messageRepository, times(1)).save(isA(Message.class));
        verify(kafkaEventWriter, times(1)).write(isA(Message.class));
    }

    @Test
    void saveMessage_shouldNotSaveAndSendMessage_exceptionDuringSave() {
        //given
        final var dto = new MessageDtoBuilder().build();
        final var model = new MessageModelBuilder().build();

        //when
        when(messageMapper.dtoToModel(isA(MessageDto.class))).thenReturn(model);
        when(messageRepository.save(isA(Message.class))).thenThrow(RuntimeException.class);

        //then
        assertThrows(RuntimeException.class, () ->  testedObj.saveMessage(dto));

        verify(messageMapper,times(1)).dtoToModel(isA(MessageDto.class));
        verify(messageRepository, times(1)).save(isA(Message.class));
        verify(kafkaEventWriter, never()).write(isA(Message.class));
    }
}
