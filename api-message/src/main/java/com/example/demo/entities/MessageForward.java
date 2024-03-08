package com.example.demo.entities;

import com.example.demo.config.WhatsappConfig;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class MessageForward {
    @Autowired
    private WhatsappConfig whatsappConfig;

    private MessageRequest messageRequest;

    public MessageForward (MessageRequest messageRequest){
        this.messageRequest = messageRequest;
    }


}
