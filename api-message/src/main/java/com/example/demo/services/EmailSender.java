package com.example.demo.services;

import com.example.demo.entities.MessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class EmailSender implements MessageSender{
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String username;
    @Override
    public boolean sendMessage(MessageRequest messageRequest) {

        try{
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(messageRequest.getTo());
            mail.setFrom(username);
            mail.setSubject(messageRequest.getSubject());
            mail.setText(messageRequest.getContent());
            javaMailSender.send(mail);
            return true;
        }
        catch (Exception e){
            System.out.println(e);
            return false;
        }
//            return true;
    }
}
