package com.example.demo.services;

import com.example.demo.config.WhatsappConfig;
import com.example.demo.entities.MessageForward;
import com.example.demo.entities.MessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WhatsappSender implements MessageSender{
    @Autowired
    private WhatsappConfig whatsappConfig;

    private RestTemplate restTemplate;


    @Override
    public boolean sendMessage(MessageRequest messageRequest) {
        HttpHeaders headers = new HttpHeaders();
        System.out.println("from whatsapp");

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(whatsappConfig.getToken());
        String url = "https://graph.facebook.com/v17.0/"+whatsappConfig.getAccountId()+"/messages";


        HttpEntity<MessageRequest> requestEntity = new HttpEntity<>(messageRequest,headers);

        restTemplate = new RestTemplate();
        ResponseEntity<MessageForward> responseEntity = restTemplate.postForEntity(url,requestEntity, MessageForward.class);


        return false;
    }
}
