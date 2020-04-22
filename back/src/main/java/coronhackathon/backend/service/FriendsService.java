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
import java.util.OptionalInt;

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
        Optional<Friends> of = friendsRepository.findByUser1AndUser2(user1, user2);
        Optional<Friends> of2 = friendsRepository.findByUser1AndUser2(user2, user1);
        return of.isPresent() && of.get().getCompleted()  ||
                of2.isPresent() && of2.get().getCompleted();
    }

    public String friendRequest(User currentUser, String username) {
        Optional<User> ou = userRepository.findByUsername(username);
        if (!ou.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user with username : " + username + " not found");
        User user = ou.get();
        Optional<Friends> of1 = friendsRepository.findByUser1AndUser2(currentUser, user);
        Optional<Friends> of2 = friendsRepository.findByUser1AndUser2(user, currentUser);
        if (of1.isPresent()) {  //if currentUser already asked user to be his/her friend
                                //and user has or has not already answered
            Friends f1 = of1.get();
            if (f1.getCompleted())
                return "" + currentUser.getUsername() + " and " + ou.get().getUsername() + " are already friends";
            else
                return "user : " + user.getUsername() + " has already asked " +
                        "" + currentUser.getUsername() + " to be his/her friend";
        }else if (of2.isPresent()) {//if user already asked currentUser to be his/her friend
                                    //and currentUser has or has not already answered
            Friends f2 = of2.get();
            if (f2.getCompleted())
                return "" + currentUser.getUsername() + " and " + ou.get().getUsername() + " are already friends";
            else
                return ""+currentUser.getUsername()+" and "+ou.get().getUsername()+" are now friends";
        }else { //if they never asked each other
            friendsRepository.save(new Friends(currentUser, ou.get(), false));
            return "" + currentUser.getUsername() + " ask " + ou.get().getUsername() + " to be his/her friend";
        }
    }


    public String acceptFriendRequest(User currentUser, long userId) {
        Optional<User> ou = userRepository.findById(userId);
        if (!ou.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user with id : " + userId + " not found");
        User user = ou.get();
        Optional<Friends> of = friendsRepository.findByUser1AndUser2(currentUser, user);
        if (!of.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "!!! Should not happen !!! Report this error !!! but it is because user : " + user.getUsername() + " has not asked " +
                    "" + currentUser.getUsername() + " to be his/her friend.");
        }
        of.get().setCompleted(true);
        return ""+currentUser.getUsername()+" and "+ou.get().getUsername()+" are now friends";
    }
}
