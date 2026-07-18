package com.goat.portfolio;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ContactController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @PostMapping("/submit-form")
    public String submitForm(@ModelAttribute Contact contact) {
        log.info("Form submitted from method");
        log.info(contact.toString());

        // 1. Save to Database using plain SQL
        String sql = "INSERT INTO contact_messages (name, email, message) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, contact.getName(), contact.getEmail(), contact.getMessage());

        // 2. Send the Email
        sendEmailNotification(contact);

        // 3. Redirect back to home
        return "redirect:/";
    }

    private void sendEmailNotification(Contact contact) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("sebhatahsin22@gmail.com");
        mailMessage.setSubject("New Portfolio Message from " + contact.getName());
        mailMessage.setText(
                "You received a new message!\n\n" +
                        "Name: " + contact.getName() + "\n" +
                        "Email: " + contact.getEmail() + "\n\n" +
                        "Message:\n" + contact.getMessage()
        );
        mailSender.send(mailMessage);
    }
}
