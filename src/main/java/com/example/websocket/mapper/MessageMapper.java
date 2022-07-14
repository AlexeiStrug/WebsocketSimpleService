package com.example.websocket.mapper;

import com.example.websocket.controller.dto.MessageDto;
import com.example.websocket.domain.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    Message dtoToModel(MessageDto dto);

    MessageDto modelToDto(Message model);
}
