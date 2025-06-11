package dev.danvega.social.controller;

import dev.danvega.social.service.LoginService;
import dev.danvega.social.service.SmsLoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {


    private final SmsLoginService smsLoginService;

    public HomeController(SmsLoginService smsLoginService) {
        this.smsLoginService = smsLoginService;
    }

    @PostMapping("/send-code")
    public ResponseEntity<String> sendCode(@RequestParam String phoneNumber) {
        smsLoginService.sendCode(phoneNumber);
        return ResponseEntity.ok("Code sent");
    }

    @PostMapping("/validate-code")
    public ResponseEntity<String> validateCode(@RequestParam String phoneNumber, @RequestParam String code) {
        boolean valid = smsLoginService.validateCode(phoneNumber, code);
        return valid ? ResponseEntity.ok("Logged in successfully")
                : ResponseEntity.badRequest().body("Invalid code");
    }

    @GetMapping("/")
    public String home() {
        return "Estas dentro!";
    }

    @GetMapping("/secured")
    public String secured() {
        return "Estas dentro y seguro!";
    }

}
