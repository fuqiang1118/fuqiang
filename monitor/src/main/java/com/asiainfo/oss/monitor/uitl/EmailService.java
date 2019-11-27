package com.asiainfo.oss.monitor.uitl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

/**
 * @program: monitor
 * @description:
 * @author: fuqiang
 * @date: 2019-11-14 17:26
 **/
@Component
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public synchronized void sendSimpleEmail(String[] emails, String title, String text) {

        for (String email : emails){
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(email);
            mailMessage.setSubject(title);
            mailMessage.setText(text);
            javaMailSender.send(mailMessage);
            log.info("SendEmail to " + email + " Success!");
        }
    }

}

