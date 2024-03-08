package com.example.demo.services;

import com.example.demo.entities.SenderMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class MessageService {

    private final Map<SenderMethod,MessageSender> methods = new HashMap<>();


    public MessageService(TwilioSender twilioSender, EmailSender gmailSender, WhatsappSender whatsappSender){
        methods.put(SenderMethod.EMAIL, Objects.requireNonNull(gmailSender));
        methods.put(SenderMethod.WHATSAPP, Objects.requireNonNull(whatsappSender));
        methods.put(SenderMethod.SMS,Objects.requireNonNull(twilioSender));

    }


    public MessageSender getMessageSender(SenderMethod type) {
        MessageSender messageSender = methods.get(type);

        if (messageSender == null)
            throw new IllegalArgumentException();

        return messageSender;
    }

}



