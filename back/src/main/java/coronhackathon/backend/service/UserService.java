package coronhackathon.backend.service;

import coronhackathon.backend.entity.User;
import coronhackathon.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long id){
        return userRepository.findById(id);
    }


    public void insert(User user) {
        userRepository.save(user);
    }


    public Optional<User> login(String username, long hash) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            if(user.get().getPwdHash() == hash) return user;
            else return Optional.empty();
        }
        else return Optional.empty();
        // should be unique anyway because username is unique
        // TODO add internal behavior, at least register that this user is logged in
    }
}
