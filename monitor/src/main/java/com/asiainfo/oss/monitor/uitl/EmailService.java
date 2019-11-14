package com.asiainfo.oss.monitor.uitl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @program: monitor
 * @description:
 * @author: fuqiang
 * @date: 2019-11-14 17:26
 **/
@Component
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public synchronized void sendSimpleEmail(String email, String title, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(email);
        mailMessage.setSubject(title);
        mailMessage.setText(text);
        javaMailSender.send(mailMessage);
    }

}

