package coronhackathon.backend.service;

import coronhackathon.backend.entity.Challenge;
import coronhackathon.backend.entity.HasCompleted;
import coronhackathon.backend.entity.User;
import coronhackathon.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public void insert(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "user with username : " + user.getUsername() + " already exists");
        userRepository.save(user);
    }

    /**
     * Username must be unique and that the two hashes match
     * we must store encoded passwords as spring security expects
     */
    public Optional<User> register(String username, String pwd, String pwdBis) {
        //if passwords match
        if (pwdBis.equals(pwd)) {
            // if the username is new
            if (!userRepository.findByUsername(username).isPresent()) {

                User user = new User();
                user.setUsername(username);
                // Store encoded passwords
                user.setPwdHash(passwordEncoder.encode(pwd));

                insert(user);
                return Optional.of(user);
            } else {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "user with username : " + username + " already exists");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "passwords dont match");
        }
    }
}
