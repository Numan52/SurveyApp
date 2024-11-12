package com.example.surveyapp.Controllers;

import com.example.surveyapp.Security.JpaUserDetailsService;
import com.example.surveyapp.Jwt.AuthenticationRequest;
import com.example.surveyapp.Jwt.AuthenticationResponse;
import com.example.surveyapp.Jwt.JwtUtil;
import com.example.surveyapp.Models.User;
import com.example.surveyapp.Service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JpaUserDetailsService jpaUserDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    private UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@ModelAttribute("user") User user) {
        User existingUser = userService.findUserByEmail(user.getEmail());
        System.out.println(existingUser);
        if (existingUser != null) {
            System.out.println("user exists");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("A user with this email address already exists.");
        } else {
            try {
                userService.saveUser(user);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return ResponseEntity.status(HttpStatus.OK).body("User registered successfully. Redirecting to login page.");
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                                       HttpServletResponse response) throws Exception {
        System.out.println(authenticationRequest.getUsername());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            System.out.println("Incorrect username or passowrd" + e);
            return null;
        }
        System.out.println("yololo");
        UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String jwt = jwtTokenUtil.generateToken(userDetails);

        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        System.out.println("Successful login " + jwt);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> loginUser(@RequestParam("email") String email,
//                                       @RequestParam("password") String password,
//                                       HttpServletResponse response) {
//        System.out.println(email);
//        System.out.println(password);
//        boolean correctCredentials = userService.validateUserCredentials(email, password);
//
//        if (correctCredentials) {
//            User user = userService.findUserByEmail(email);
//            Cookie userCookie = new Cookie("userEmail", email);
//            userCookie.setHttpOnly(true);
//            userCookie.setSecure(true);
//            response.addCookie(userCookie);
//            return ResponseEntity.ok(Map.of("username", user.getUsername()));
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong credentials");
//        }
//
//    }

//    @GetMapping("/logout")
//    public ResponseEntity<?> logoutUser(HttpServletRequest request, HttpServletResponse response) {
//        Cookie userCookie = new Cookie("userEmail", "");
//        userCookie.setMaxAge(0);
//        response.addCookie(userCookie);
//
//        request.getSession().invalidate();
//
//        return ResponseEntity.ok("Successfully logged out");
//    }
}
