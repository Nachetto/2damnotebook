package dev.nacho.ghub.web;

import dev.nacho.ghub.service.impl.SmsLoginServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class HomeController {

    @GetMapping("/me")
    public Map<String, Object> me(@AuthenticationPrincipal OAuth2User oauthUser) {
        // user.getName() suele ser el "sub" de Google
        String googleId = oauthUser.getName();

        String email    = oauthUser.getAttribute("email");
        String nombre   = oauthUser.getAttribute("name");

        return Map.of(
                "googleId", googleId,
                "email",    email,
                "name",     nombre
        );
    }

    private final SmsLoginServiceImpl smsLoginServiceImpl;

    public HomeController(SmsLoginServiceImpl smsLoginServiceImpl) {
        this.smsLoginServiceImpl = smsLoginServiceImpl;
    }

    @PostMapping("/send-code")
    public ResponseEntity<String> sendCode(@RequestParam String phoneNumber) {
        smsLoginServiceImpl.sendCode(phoneNumber);
        return ResponseEntity.ok("Code sent");
    }

    @PostMapping("/validate-code")
    public ResponseEntity<String> validateCode(@RequestParam String phoneNumber, @RequestParam String code) {
        boolean valid = smsLoginServiceImpl.validateCode(phoneNumber, code);
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
