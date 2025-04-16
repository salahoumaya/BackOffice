package com.phegondev.usersmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMail(String to, String subject, String body) {
        System.out.println("📤 Envoi réel de l'email à : " + to);
        System.out.println("📌 Sujet : " + subject);
        System.out.println("📌 Message : " + body);

        // Si tu utilises JavaMailSender
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);

            System.out.println("✅ Email envoyé avec succès !");
        } catch (Exception e) {
            System.out.println("❌ Erreur lors de l'envoi : " + e.getMessage());
        }
    }

}