package com.example.demo.services;

import com.example.demo.config.TwilioConfig;
import com.example.demo.entities.MessageRequest;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class TwilioSender implements MessageSender {

    @Autowired
    private TwilioConfig twilioConfig;


    @Override
    public boolean sendMessage(MessageRequest messageRequest) {
        try {
            System.out.println("from twilio");
            PhoneNumber to = new PhoneNumber(messageRequest.getTo());
            Message message = Message
                    .creator(to,
                            twilioConfig.getServiceSid(),
                            messageRequest.getContent())
                    .create();
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
