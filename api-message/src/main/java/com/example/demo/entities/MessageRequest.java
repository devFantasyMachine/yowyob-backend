package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    private String subject;// for gmail purpose
    private String to;
    private String content;
    private String type;//for whatsapp purpose

}
