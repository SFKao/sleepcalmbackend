package omelcam934.sleepcalmbackend.controller;

import io.jsonwebtoken.Jwt;
import omelcam934.sleepcalmbackend.dto.UserDto;
import omelcam934.sleepcalmbackend.models.User;
import omelcam934.sleepcalmbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getUser(Authentication authentication){
        User user = userService.findByUsernameLikeIgnoreCaseOrEmailLikeIgnoreCase(authentication.getName(), authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("El token contenia un usuario que no existe, hiuston tenemos un problema"));
        return ResponseEntity.ok(
                new UserDto(user.getUsername(),user.getEmail())
        );
    }

    @PutMapping
    public ResponseEntity<?> putUser(Authentication authentication, @RequestBody UserDto userDto){
        User user = userService.findByUsernameLikeIgnoreCaseOrEmailLikeIgnoreCase(authentication.getName(), authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("El token contenia un usuario que no existe, hiuston tenemos un problema"));
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        userService.save(user);
        return ResponseEntity.ok(
                new UserDto(user.getUsername(),user.getEmail())
        );
    }
}
