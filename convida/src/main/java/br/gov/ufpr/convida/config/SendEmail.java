package br.gov.ufpr.convida.config;

import java.io.IOException;

import javax.management.MBeanException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class SendEmail {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String email, String password) {

        System.out.println("------------ CHEGUEI AQUI PRA MANDAR O EMAIL --------------");
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);

        System.out.println(" ---------- email: " + email);
        System.out.println("-------------- senha: " + password);
        msg.setSubject("Recuperação de senha - Convida");
        msg.setText("Sua nova senha é: " + password
                + ". Utilize-a para se logar no nosso aplicativo e troque por uma senha de sua preferencia!");
        try {
            javaMailSender.send(msg);
            System.out.println("------------ ENVIEI O EMAIL NA TEORIA --------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}