package com.example.demo.Services;

import com.example.demo.DTOs.AuthenticationResponse;
import com.example.demo.DTOs.LoginRequest;
import com.example.demo.DTOs.RegisterRequest;
import com.example.demo.Entities.NotificationEmail;
import com.example.demo.Entities.User;
import com.example.demo.Entities.VerificationToken;
import com.example.demo.Exceptions.CoinTraderException;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Repositories.VerificationTokenRepository;
import com.example.demo.Security.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;

import static java.time.Instant.now;
import static com.example.demo.util.Constants.ACTIVATION_EMAIL;
import static java.util.Optional.ofNullable;

import java.util.Optional;
import java.util.UUID;


@Service
@ComponentScan(basePackages = {"com.example.demo"})
@EnableJpaRepositories(basePackages = {"com.example.demo.Repositories"})
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private MailContentBuilder mailContentBuilder;

    @Autowired
    private MailService mailService;

    public AuthService() {
    }


    @Transactional
    public void register(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setCreated(now());
        user.setEnabled(false);

//        userRepository.save(user);
        save(user);

        String token = generateVerificationToken(user);
        String message = mailContentBuilder.build("Thank you for signing up to CoinTrader, please click on the below url to activate your account : "
                + ACTIVATION_EMAIL + "/" + token);

        mailService.sendMail(new NotificationEmail("Please Activate your account", user.getEmail(), message));
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void verifyAccount(String token)  {
        Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);
        verificationTokenOptional.orElseThrow(() -> new CoinTraderException("Invalid Token"));
        fetchUserAndEnable(verificationTokenOptional.get());
    }

    @Transactional
    public void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new CoinTraderException("User Not Found with id - " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public User save(User user) {
        Optional<User> optionalUser;
        Optional<User> optionalUser1;

        optionalUser = userRepository.findByEmail(user.getEmail());
        optionalUser1 = userRepository.findByUsername(user.getUsername());

        User user1;
        if(optionalUser.isEmpty() && optionalUser1.isEmpty())
            user1 = userRepository.save(user);
        else
            user1= null;

        return ofNullable(user1)
                .orElseThrow(() -> new EntityExistsException("Email or username already exist: " ));
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String authenticationToken = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
    }

    @Transactional(readOnly = true)
    User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

}
