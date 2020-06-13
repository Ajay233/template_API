package template.API.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import template.API.Authentication.model.AuthRequest;
import template.API.Authentication.model.AuthResponse;
import template.API.User.model.User;
import template.API.User.repository.UserRepository;

@RestController
public class AuthenticationController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @RequestMapping(value = "/auth/createAccount", method = RequestMethod.POST)
    private ResponseEntity<String> createAccount(@RequestBody User user){
        if(userRepository.findByEmail(user.getEmail()) == null){
            User userToSave = new User(user.getForename(), user.getSurname(), user.getEmail(), new BCryptPasswordEncoder().encode(user.getPassword()));
            userRepository.save(userToSave);
            return new ResponseEntity<String>("ACCOUNT CREATED", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("ACCOUNT ALREADY EXISTS", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    private ResponseEntity<?> login(@RequestBody AuthRequest request){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword())
            );
            AuthResponse response = new AuthResponse(userRepository.findByEmail(request.getEmail()), "jwt");
            return new ResponseEntity<AuthResponse>(response, HttpStatus.OK);
        } catch (BadCredentialsException e){
            return new ResponseEntity<String>("INVALID CREDENTIALS", HttpStatus.BAD_REQUEST);
        }
    }

}
