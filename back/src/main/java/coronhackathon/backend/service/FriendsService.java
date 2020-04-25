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
import java.util.*;

@Service
public class FriendsService {
    @Autowired
    private FriendsRepository friendsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompletedService completedService;



    public List<User> getFriendsRequests(User user) {
        List<User> friends = new ArrayList<>();
        for(Friends f : friendsRepository.findByUser2(user)) {
            if (!f.getCompleted()) {
                friends.add(f.getUser1());
            }
        }
        return friends;
    }

    public boolean isFriend(long user1Id, long user2Id) {
        Optional<User> ou1 = userRepository.findById(user1Id);
        if (!ou1.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user with id : " + user1Id + " not found");
        Optional<User> ou2 = userRepository.findById(user2Id);
        if (!ou2.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user with id : " + user2Id + " not found");

        User user1 = ou1.get(), user2 = ou2.get();
        Optional<Friends> of = friendsRepository.findByUser1AndUser2(user1, user2);
        Optional<Friends> of2 = friendsRepository.findByUser1AndUser2(user2, user1);
        return (of.isPresent() && of.get().getCompleted())  ||
                (of2.isPresent() && of2.get().getCompleted());
    }

    public String friendRequest(User currentUser, String username) {
        Optional<User> ou = userRepository.findByUsername(username);
        if (!ou.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user with username : " + username + " not found");
        User user = ou.get();
        if(user.equals(currentUser))
            return "You cannot be friends with yourself";

        Optional<Friends> of1 = friendsRepository.findByUser1AndUser2(currentUser, user);
        Optional<Friends> of2 = friendsRepository.findByUser1AndUser2(user, currentUser);
        if (of1.isPresent()) {  //if currentUser already asked user to be his/her friend
                                //and user has or has not already answered
            Friends f1 = of1.get();
            if (f1.getCompleted())
                return "" + currentUser.getUsername() + " and " + ou.get().getUsername() + " are already friends";
            else
                return "user : " + currentUser.getUsername() + " has already asked " +
                        "" + user.getUsername() + " to be his/her friend";
        }else if (of2.isPresent()) {//if user already asked currentUser to be his/her friend
                                    //and currentUser has or has not already answered
            Friends f2 = of2.get();
            if (f2.getCompleted())
                return "" + currentUser.getUsername() + " and " + ou.get().getUsername() + " are already friends";
            else
                return ""+currentUser.getUsername()+" and "+ou.get().getUsername()+" are now friends";
        }else { //if they never asked each other
            Friends friends = new Friends();
            friends.setUser1(currentUser);
            friends.setUser2(ou.get());
            friends.setCompleted(false);
            friendsRepository.save(friends);
            return "" + currentUser.getUsername() + " asked " + ou.get().getUsername() + " to be his/her friend";
        }
    }


    public String acceptFriendRequest(User currentUser, long userId) {
        Optional<User> ou = userRepository.findById(userId);
        if (!ou.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user with id : " + userId + " not found");
        User user = ou.get();
        Optional<Friends> of = friendsRepository.findByUser1AndUser2(user, currentUser);
        if (!of.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "!!! Should not happen !!! Report this error !!! but it is because user : " + user.getUsername() + " has not asked " +
                    "" + currentUser.getUsername() + " to be his/her friend.");
        }
        Friends f = of.get();
        if(f.getCompleted())
            return ""+currentUser.getUsername()+" is already friend with "+user.getUsername();
        else {
            f.setCompleted(true);
            friendsRepository.save(f);
            return "" + currentUser.getUsername() + " and " + ou.get().getUsername() + " are now friends";
        }
    }

    public String refuseFriendRequest(User currentUser, long userId) {
        Optional<User> ou = userRepository.findById(userId);
        if (!ou.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user with id : " + userId + " not found");
        User user = ou.get();
        Optional<Friends> of = friendsRepository.findByUser1AndUser2(user, currentUser);
        if (!of.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "!!! Should not happen !!! Report this error !!! but it is because user : " + user.getUsername() + " has not asked " +
                    "" + currentUser.getUsername() + " to be his/her friend.");
        }else{
            friendsRepository.delete(of.get());
            return "Request from " + user.getUsername() + " to " + currentUser.getUsername() + " deleted";
        }
    }


    public List<UserAndNbChallenge> getFriendsOrderByCompletedChallenges(User user) {
        List<UserAndNbChallenge> friends = new ArrayList<>();
        for(Friends f : friendsRepository.findByUser1(user)) {
            if (f.getCompleted()) {
                User u = f.getUser2();
                friends.add(new UserAndNbChallenge(u.getId(), u.getUsername(),
                        completedService.getNumberOfCompletedChallenges(u.getId())));
            }
        }
        for(Friends f : friendsRepository.findByUser2(user)) {
            if (f.getCompleted()) {
                User u = f.getUser1();
                friends.add(new UserAndNbChallenge(u.getId(), u.getUsername(),
                        completedService.getNumberOfCompletedChallenges(u.getId())));
            }
        }
        friends.sort((x,y) -> x.getNbChall() == y.getNbChall()? 0 :
                (x.getNbChall() > y.getNbChall() ? 1 : 0 ));
        return friends;
    }

    public class UserAndNbChallenge{
        private long userId;
        private String username;
        private long nbChall;
        private UserAndNbChallenge(long userId, String username, long nbChall){
            this.userId = userId;
            this.username = username;
            this.nbChall = nbChall;
        }

        public long getNbChall() {
            return nbChall;
        }

        public long getUserId() {
            return userId;
        }

        public String getUsername() {
            return username;
        }
    }
}
