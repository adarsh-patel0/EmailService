package com.example.EmailService.Dtos;


@Getter
@Setter
public class SendEmailMessageDto {

    private String to;
    private String from;
    private String subject;
    private String body;
}
