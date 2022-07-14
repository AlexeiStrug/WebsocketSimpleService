package com.example.websocket.repository;

import com.example.websocket.domain.UnprocessedMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnprocessedMessageRepository extends CrudRepository<UnprocessedMessage, Long> {

}
