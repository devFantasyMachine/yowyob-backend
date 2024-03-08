package com.example.demo.services;

import com.example.demo.entities.MessageRequest;

public interface MessageSender {
    boolean sendMessage(MessageRequest messageRequest);
}
