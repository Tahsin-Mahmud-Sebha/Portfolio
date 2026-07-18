package com.goat.portfolio;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ContactController {

    private static final Logger log = LoggerFactory.getLogger(ContactController.class);

    // We only need the MailSender now!
    private final JavaMailSender mailSender;

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @PostMapping("/submit-form")
    public String submitForm(@RequestParam String name, @RequestParam String email, @RequestParam String message) {
        log.info("Processing form submission from: {}", name);

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo("sebhatahsin22@gmail.com"); // Sending it to yourself
            mailMessage.setReplyTo(email); // So you can hit "Reply" and email them back
            mailMessage.setSubject("New Portfolio Message from: " + name);
            mailMessage.setText("Name: " + name + "\nEmail: " + email + "\n\nMessage:\n" + message);

            mailSender.send(mailMessage);
            log.info("Email successfully sent!");

        } catch (Exception e) {
            log.error("Failed to send email: ", e);
        }

        // Redirect back to the page so they don't get stuck on a blank screen
        return "redirect:/";
    }
}