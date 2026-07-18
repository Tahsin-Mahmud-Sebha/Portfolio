package com.goat.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ContactController {

    private final JavaMailSender mailSender;

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @PostMapping("/submit-form")
    public String submitForm(@RequestParam String name, @RequestParam String email, @RequestParam String message) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo("sebhatahsin22@gmail.com");
            mailMessage.setReplyTo(email);
            mailMessage.setSubject("New Portfolio Message from: " + name);
            mailMessage.setText("Name: " + name + "\nEmail: " + email + "\n\nMessage:\n" + message);

            mailSender.send(mailMessage);

        } catch (Exception e) {
            // We removed the log, so this just prints the error to the server console if it fails
            e.printStackTrace();
        }

        // Redirect back to the main page
        return "redirect:/";
    }
}