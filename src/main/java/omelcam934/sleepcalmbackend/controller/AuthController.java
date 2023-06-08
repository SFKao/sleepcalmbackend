package omelcam934.sleepcalmbackend.controller;


import jakarta.mail.MessagingException;
import omelcam934.sleepcalmbackend.dto.LoginDto;
import omelcam934.sleepcalmbackend.dto.NewPasswordDTO;
import omelcam934.sleepcalmbackend.dto.RegistroDto;
import omelcam934.sleepcalmbackend.email.EmailSender;
import omelcam934.sleepcalmbackend.models.User;
import omelcam934.sleepcalmbackend.service.TokenService;
import omelcam934.sleepcalmbackend.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder, EmailSender emailSender) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.emailSender = emailSender;
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

        return new ResponseEntity<>(true, HttpStatus.OK);

    }

    @PostMapping("/login")
    public String token(@RequestBody LoginDto userLogin) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLogin.usernameOrEmail(), userLogin.password()));
        return tokenService.generateToken(authentication);
    }


    @PostMapping("/forgotPassword")
    public ResponseEntity<?> resetPassword(@RequestBody String email) throws MessagingException, UnsupportedEncodingException {
        Optional<User> optinalUser = userService.findByEmailLikeIgnoreCase(email);
        if(optinalUser.isPresent()){

            User user = optinalUser.get();
            String nuevaContrasenya = RandomStringUtils.randomAlphanumeric(12);
            user.setPassword(passwordEncoder.encode(nuevaContrasenya));
            userService.save(user);
            emailSender.enviarEmailConNuevaContrasenya(user, nuevaContrasenya);
        }
        return ResponseEntity.ok().build();
    }



    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody NewPasswordDTO newPasswordDTO) throws MessagingException, UnsupportedEncodingException {
        Optional<User> optinalUser = userService.findByEmailLikeIgnoreCase(newPasswordDTO.email());
        if(optinalUser.isPresent()){
            User user = optinalUser.get();
            if(passwordEncoder.matches(newPasswordDTO.lastPassword(), user.getPassword())){
                user.setPassword(passwordEncoder.encode(newPasswordDTO.newPassword()));
                userService.save(user);
                emailSender.enviarEmailCambioDeContrasenya(user);
            }
            else
                return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}