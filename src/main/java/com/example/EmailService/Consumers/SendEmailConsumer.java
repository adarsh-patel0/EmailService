package com.example.EmailService.Consumers;


@Service
public class SendEmailConsumer {

    @Autowired
    private ObjectMapper objectMapper;


    @KafkaListener(topics="sendEmail", groupId = "emailService")
    public void handleSendEmail(String message) {
        try {
            SendEmailMessageDto sendEmailMessageDto = objectMapper.readValue(message, SendEmailMessageDto.class);

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            //create Authenticator object to pass in Session.getInstance argument
            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("anuragbatch@gmail.com", "");
                }
            };
            Session session = Session.getInstance(props, auth);

            EmailUtil.sendEmail(session, sendEmailMessageDto.getTo(), sendEmailMessageDto.getSubject(), sendEmailMessageDto.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
