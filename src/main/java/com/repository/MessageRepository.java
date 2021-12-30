package com.repository;

import com.web.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repo with all communicated messages.
 * @author Theofanis Tsiantas
 * @version  2021.12.17 - version 1
 */
@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findByMessageText(String messageText);
}

