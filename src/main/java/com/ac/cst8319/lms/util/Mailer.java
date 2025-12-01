package com.ac.cst8319.lms.util;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.exceptions.MailerSendException;

import lombok.extern.java.Log;

@Log
public class Mailer {
    private static final String PROPERTIES_FILE = "mail.properties";
    private static Properties properties = new Properties();
    private static String domainName;
    private static String apiToken;

    static private String deQuote(String str) {
        if (str.startsWith("\"")) {
            str = str.substring(1, str.length());
        }
        if (str.endsWith("\"")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }
    static {
        // Load database properties when class is initialized
        try (InputStream input = DatabaseConnection.class.getClassLoader()
                .getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new RuntimeException("Unable to find " + PROPERTIES_FILE);
            }
            properties.load(input);

            domainName = deQuote(properties.getProperty("mailersend.domainname").trim()).trim();
            apiToken = deQuote(properties.getProperty("mailersend.token").trim()).trim();

        } catch (IOException e) {
            throw new RuntimeException("Error loading email properties", e);
        }
    }

    public static void sendEmail(String subject,
                                 String fromName, String fromEmail,
                                 String toName, String toEmail,
                                 String message) {
        MailerSend ms = new MailerSend();
        ms.setToken(apiToken);

        Email email = new Email();

        email.setFrom(fromName, fromEmail+domainName);
        email.addRecipient(toName, toEmail);
        email.setSubject(subject);
        email.setPlain(message);

        try {
            MailerSendResponse msResponse = ms.emails().send(email);

            if (msResponse.responseStatusCode == 202) {
                log.info("Email sent successfully!");
            } else {
                log.warning("Failed to send email. Please try again later.");
            }
        } catch (MailerSendException e) {
            log.severe("MailSend: Exception Caught! " + e);
        }
    }
}