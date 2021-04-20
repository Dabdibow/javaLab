package ru.itis.javalab.util;

import org.springframework.stereotype.Component;

@Component
public interface MailGenerator {
    String getMailForConfirm(String serverUrl, String code);
}
