package com.aakiproject.journalApp.services;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    @Disabled
    public void testSendEmail() {
        emailService.sendEmail("aakashbhatt2008@gmail.com",
                "Testing Java Mail Sender","Hi, Aap kaise hain?");
    }
}
