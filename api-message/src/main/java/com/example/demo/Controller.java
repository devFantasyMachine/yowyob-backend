package com.example.demo;

import com.example.demo.entities.MessageRequest;
import com.example.demo.entities.SenderMethod;
import com.example.demo.services.MessageSender;
import com.example.demo.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    private MessageService messageService;

    private boolean done;

    @PostMapping(value = "/send-message")
    public ResponseEntity<Boolean> sendMessage(@RequestBody MessageRequest messageRequest,
                                               @RequestParam SenderMethod method){

        MessageSender messageSender = messageService.getMessageSender(method);
        done = messageSender.sendMessage(messageRequest);
        if (!done) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);

    }



}