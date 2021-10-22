package com.breakbooking.eventbookingapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.file.FileSystemNotFoundException;

@Service
public class EmailServiceImpl implements EmailService{


    private JavaMailSender mailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendBookingNotificationEmail(String toEmail, String body, String subject) {

        //   SimpleMailMessage message=new SimpleMailMessage();

        try{

            MimeMessage mimeMessage=mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true,"utf-8");
            mimeMessageHelper.setFrom("shwetalishine@gmail.com");
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setText(body,true);
            mimeMessageHelper.setSubject(subject);

            mailSender.send(mimeMessage);

        }catch (MessagingException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("Mail Sent...");
    }



    @Override
    public void sendBookingNotificationEmailWithAttachment(String toEmail, String body, String subject, String attachment) {

        try{
            MimeMessage mimeMessage=mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setFrom("shwetalishine@gmail.com");
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setText(body);
            mimeMessageHelper.setSubject(subject);

            FileSystemResource fileSystemResource=new FileSystemResource(new File(attachment));
            mimeMessageHelper.addAttachment(fileSystemResource.getFilename(),fileSystemResource);

            mailSender.send(mimeMessage);

        }catch (MessagingException e){
            e.printStackTrace();
        }catch (FileSystemNotFoundException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("Mail sent..");
    }

}
