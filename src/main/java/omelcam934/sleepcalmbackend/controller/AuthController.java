package omelcam934.sleepcalmbackend.controller;


import omelcam934.sleepcalmbackend.dto.LoginDto;
import omelcam934.sleepcalmbackend.dto.RegistroDto;
import omelcam934.sleepcalmbackend.models.User;
import omelcam934.sleepcalmbackend.service.TokenService;
import omelcam934.sleepcalmbackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registerUser(@RequestBody RegistroDto registroDto){

        // add check for username exists in a DB
        if(userService.existsByUsernameLikeIgnoreCase(registroDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(userService.existsByEmailLikeIgnoreCase(registroDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        User user = new User();
        user.setUsername(registroDto.getUsername());
        user.setUsername(registroDto.getUsername());
        user.setEmail(registroDto.getEmail());
        user.setPassword(passwordEncoder.encode(registroDto.getPassword()));

        userService.save(user);

        return new ResponseEntity<>("Usuario registrado correctamente.", HttpStatus.OK);

    }

    @PostMapping("/login")
    public String token(@RequestBody LoginDto userLogin) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLogin.usernameOrEmail(), userLogin.password()));
        return tokenService.generateToken(authentication);
    }
}