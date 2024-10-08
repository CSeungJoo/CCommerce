package kr.cseungjoo.ccommerce.global.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${default.protocol}")
    public String serverProtocol;
    @Value("${default.host}")
    public String serverHost;
    @Value("${default.port}")
    public String serverPort;


    @Async
    public void sendEmail(String recipientAddress, String subject, String text) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(fromEmail);
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(text);

        mailSender.send(email);
    }
}
