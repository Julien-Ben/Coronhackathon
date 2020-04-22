package coronhackathon.backend.service;

import coronhackathon.backend.entity.Friends;
import coronhackathon.backend.entity.User;
import coronhackathon.backend.repository.FriendsRepository;
import coronhackathon.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FriendsService {
    @Autowired
    private FriendsRepository friendsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompletedService completedService;

    public List<User> getFriendsOrderByCompletedChallenges(User user) {
        List<User> friends = new ArrayList<>();
        for(Friends f : friendsRepository.findByUser1(user)) {
            if (f.getCompleted()) {
                friends.add(f.getUser2());
            }
        }
        for(Friends f : friendsRepository.findByUser2(user)) {
            if (f.getCompleted()) {
                friends.add(f.getUser2());
            }
        }
        friends.sort((x,y) -> {
            long user1 = completedService.getNumberOfCompletedChallenges(x.getId());
            long user2 = completedService.getNumberOfCompletedChallenges(y.getId());
            return user1 == user2 ? 0 : (user1 > user2 ? 1 : -1);
        });
        return friends;
    }

    public List<User> getFriendsRequests(User user) {
        List<User> friends = new ArrayList<>();
        for(Friends f : friendsRepository.findByUser2(user)) {
            if (!f.getCompleted()) {
                friends.add(f.getUser2());
            }
        }
        return friends;
    }

    public boolean isFriend(long user1Id, long user2Id) {
        Optional<User> ou1 = userRepository.findById(user1Id);
        if (!ou1.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user with id : " + user1Id + " not found");
        Optional<User> ou2 = userRepository.findById(user1Id);
        if (!ou2.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user with id : " + user2Id + " not found");

        User user1 = ou1.get(), user2 = ou2.get();
        return friendsRepository.findByUser1AndUser2(user1, user2).size() >0 ||
                friendsRepository.findByUser1AndUser2(user2, user1).size() >0;
    }
}
