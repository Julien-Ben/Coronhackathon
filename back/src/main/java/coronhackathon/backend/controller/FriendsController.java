package coronhackathon.backend.controller;

import coronhackathon.backend.DTO.UserAndNbChallengeDTO;
import coronhackathon.backend.DTO.UserDTO;
import coronhackathon.backend.entity.User;
import coronhackathon.backend.repository.FriendsRepository;
import coronhackathon.backend.service.FriendsService;
import coronhackathon.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
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
    public List<UserAndNbChallengeDTO> getFriendsOrderByCompletedChallenges(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return friendsService.getFriendsOrderByCompletedChallenges(user);
    }

    /**
     * Returns the list of all the user that asks the current user to be friend
     *
     * @param principal needed to know who is the current user
     * @return the list of all the user that asks the current user to be friend
     */
    @RequestMapping(path = "/api/getFriendRequests", method = RequestMethod.GET)
    public List<UserDTO> getFriendsRequests(Principal principal) {
        List<UserDTO> uDto = new ArrayList<>();
        for(User u : friendsService.getFriendsRequests(getCurrentUser(principal)))
            uDto.add(new UserDTO(u));
        return uDto;
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

    /**
     * The current user asks the user given in parameter to be his friend
     *
     * @param principal needed to now who is the current user
     * @param username the username of the user to which we want to ask to be her/his friend
     * @return a comment on the post request
     */
    @PostMapping("/api/friendRequest")
    public String friendRequest(Principal principal, @RequestParam String username) {
        return friendsService.friendRequest(getCurrentUser(principal) ,username);
    }

    /**
     * Accept the pending request previously done by the user given in argument
     *
     * @param principal needed to kwon who is the current user
     * @param userId the userId of the user who previously made a friend request
     * @return a comment on the post request
     */
    @PostMapping("/api/acceptFriendRequest")
    public String acceptFriendRequest(Principal principal, @RequestParam long userId) {
        return friendsService.acceptFriendRequest(getCurrentUser(principal) ,userId);
    }

    /**
     * Refuse the pending request previously done by the user given in argument
     *
     * @param principal needed to know who is the current user
     * @param userId the userId of the user who previously made a friend request
     * @return a comment on the post request
     */
    @PostMapping("/api/refuseFriendRequest")
    public String refuseFriendRequest(Principal principal, @RequestParam long userId) {
        return friendsService.refuseFriendRequest(getCurrentUser(principal) ,userId);
    }

    /**
     * Return the current user
     */
    private User getCurrentUser(Principal principal){
        return userService.getUserByUsername(principal.getName());
    }
}
