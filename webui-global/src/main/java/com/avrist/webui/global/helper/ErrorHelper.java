package com.avrist.webui.global.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

@Component
public class ErrorHelper {

    private static final Logger log = LoggerFactory.getLogger(ErrorHelper.class);

    private final JavaMailSender mailSender;
    private final String to;
    private final String cc;

    @Autowired
    public ErrorHelper(
            @Value("${err.mail.to}") String to,
            @Value("${err.mail.cc}") String cc,
            @Value("${err.mail.smtp.host}") String smtpHost,
            @Value("${err.mail.smtp.port}") int smtpPort,
            @Value("${err.mail.smtp.username}") String smtpUsername,
            @Value("${err.mail.smtp.password}") String smtpPassword) {
        this.mailSender = createMailSender(smtpHost, smtpPort, smtpUsername, smtpPassword);
        this.to = to;
        this.cc = cc;
    }

    private JavaMailSender createMailSender(
            String host,
            int port,
            String username,
            String password) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    public File getLogFile(String logPath) {
        File logFile = new File(logPath);
        if (logFile.exists() && logFile.isFile()) {
            return logFile;
        } else {
            log.warn("Log file not found or is not a file: " + logPath);
            return null;
        }
    }

    public void sendErrorNotif(
            String subject,
            String message,
            Throwable t) {
        // Convert stack trace to string
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        for (StackTraceElement element : t.getStackTrace()) {
            pw.println(element.toString());
        }

        String stackTrace = sw.toString();

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(to);

            // Set CC addresses
            if (cc != null && !cc.isEmpty()) {
                String[] ccAddresses = cc.split(",");
                helper.setCc(ccAddresses);
            }

            helper.setSubject(subject);
            helper.setText("Message: " + message + "\n\nStackTrace:\n" + stackTrace);

            mailSender.send(mimeMessage);
            log.info("Sent message to developer successfully....");
        } catch (MessagingException mex) {
            log.error("Sent message to developer failed....", mex);
        }
    }
}
