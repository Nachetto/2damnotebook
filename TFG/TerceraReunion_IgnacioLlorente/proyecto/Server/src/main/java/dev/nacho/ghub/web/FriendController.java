package dev.nacho.ghub.web;


import com.google.api.Authentication;
import dev.nacho.ghub.domain.model.dto.FriendRequestDTO;
import dev.nacho.ghub.domain.model.dto.FriendshipDTO;
import dev.nacho.ghub.service.AmistadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/friends")
public class FriendController {

    private final AmistadService friendService;

    public FriendController(AmistadService friendService) {
        this.friendService = friendService;
    }

    @PostMapping("/request")
    public ResponseEntity<FriendshipDTO> requestFriend(
            @RequestBody FriendRequestDTO requestDto,
            Authentication authentication) {

        // Suponemos que authentication.getName() es el UUID de Usuario
        String requesterId = authentication.getDescriptorForType().getName();

        FriendshipDTO dto = friendService.requestFriend(requesterId, requestDto);
        return ResponseEntity.ok(dto);
    }
}