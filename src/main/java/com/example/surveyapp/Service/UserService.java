package com.example.surveyapp.Service;


import com.example.surveyapp.Models.User;
import com.example.surveyapp.Repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;



    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public void saveUser(User user) throws NoSuchAlgorithmException {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> getCurrentUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userEmail")) {
                    Long userId = this.findUserByEmail(cookie.getValue()).getId();
                    return findUserById(userId);
                }
            }
        }
        return Optional.empty();
    }

    public boolean validateUserCredentials(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return false;
        } else {
            return password.equals(user.getPassword());
        }
    }

}
