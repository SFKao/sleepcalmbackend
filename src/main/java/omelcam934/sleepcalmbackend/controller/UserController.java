package omelcam934.sleepcalmbackend.controller;

import omelcam934.sleepcalmbackend.dto.UserDto;
import omelcam934.sleepcalmbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getUser(Authentication authentication){
        System.out.println(authentication.getPrincipal());
        System.out.println(authentication.getName());
        return (ResponseEntity<?>) ResponseEntity.ok();
    }

}
