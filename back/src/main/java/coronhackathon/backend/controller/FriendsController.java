package coronhackathon.backend.controller;

import coronhackathon.backend.entity.Friends;
import coronhackathon.backend.entity.User;
import coronhackathon.backend.repository.FriendsRepository;
import coronhackathon.backend.service.FriendsService;
import coronhackathon.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class FriendsController {
    @Autowired
    private FriendsService friendsService;
    @Autowired
    private FriendsRepository friendsRepository;
    @Autowired
    private UserService userService;
    /**
     * Returns the list of all the friends of the current user ordered by the number of completed Challenges
     *
     * @param principal needed to know who is the current user
     * @return A list with the friends of the current user ordered by the number of completed Challenges
     */
    @RequestMapping(path = "/api/getFriends", method = RequestMethod.GET)
    public List<User> getFriendsOrderByCompletedChallenges(Principal principal) {
        User user = userService.getUserByUsername(principal.getName()).get();
        return friendsService.getFriendsOrderByCompletedChallenges(user);
    }

    /**
     * Returns the list of all the user that asks the current user to be friend
     *
     * @param principal needed to know who is the current user
     * @return the list of all the user that asks the current user to be friend
     */
    @RequestMapping(path = "/api/getFriendRequests", method = RequestMethod.GET)
    public List<User> getFriendsRequests(Principal principal) {
        return friendsService.getFriendsRequests(getCurrentUser(principal));
    }

    //To be removed when necessary
    @RequestMapping(path = "/api/getAllFriendships", method = RequestMethod.GET)
    public List<Friends> getAllFriendships() {
        return friendsRepository.findAll();
    }

    /**
     * Tell if user1 is friend with user2
     * user1 is friend with user 2 iff user2 is friend with user1
     *
     * @return true if it is the case false otherwise
     */
    @RequestMapping(path = "/api/isFriend/{user1Id}/{user2Id}", method = RequestMethod.GET)
    public boolean isFriend(@PathVariable long user1Id, @PathVariable long user2Id) {
        return friendsService.isFriend(user1Id,user2Id);
    }


    @PostMapping("/api/friendRequest")
    public String friendRequest(Principal principal, @RequestParam long userId) {
        return friendsService.friendRequest(getCurrentUser(principal) ,userId);
    }

    @PostMapping("/api/acceptFriendRequest")
    public String acceptFriendRequest(Principal principal, @RequestParam long userId) {
        return friendsService.acceptFriendRequest(getCurrentUser(principal) ,userId);
    }

    private User getCurrentUser(Principal principal){
        return userService.getUserByUsername(principal.getName()).get();
    }
}
