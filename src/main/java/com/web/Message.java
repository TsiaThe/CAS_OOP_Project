package com.web;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * User entity. It represents one of the four game users.
 * Contains only id and the message itself.
 * @author Theofanis Tsiantas
 * @version  2021.12.10 - version 1
 */
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Message is mandatory")
    private String messageText;

    public Message() {}

    public Message(String messageText) {
        this.messageText = messageText;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", messageText='" + messageText + '\'' +
                '}';
    }
}

