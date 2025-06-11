package dev.nacho.ghub.service.impl;

import org.springframework.stereotype.Service;

@Service
public class SmsLoginServiceImpl {
    public void sendCode(String phoneNumber) {
        // Generate and send code via SMS gateway
    }

    public boolean validateCode(String phoneNumber, String code) {
        // Validate the code
        return true;
    }
}
