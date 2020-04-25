package coronhackathon.backend.controller;

import coronhackathon.backend.DTO.UserDTO;
import coronhackathon.backend.entity.Challenge;

import coronhackathon.backend.entity.User;
import coronhackathon.backend.service.*;
import coronhackathon.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CompletedService completedService;

    @PostMapping("/register")
    public Optional<User> showRegistrationForm(@RequestParam String username,
                                               @RequestParam String hashPwd,
                                               @RequestParam String hashPwd2) {
        return userService.register(username, hashPwd, hashPwd2);
    }

    @RequestMapping(value = "/api/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Principal principal) {
        return principal.getName();
    }

    @RequestMapping(value = "/api/userProfile", method = RequestMethod.GET)
    @ResponseBody
    public UserDTO currentUserProfile(Principal principal) {
        return userService.getUserByUsername(principal.getName());
    }

    @GetMapping("/api/allUsers")
    public List<User> allUsers() {
        return userService.getAllUsers();
    }


    @RequestMapping(path = "/api/getUser/{userId}", method = RequestMethod.GET)
    public UserDTO getUser(@PathVariable long userId) {
        return userService.getUser(userId);
    }

    @RequestMapping(path = "/api/getUserByName/{username}", method = RequestMethod.GET)
    public UserDTO getUserByName(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    /**
     * Returns all challenges completed by User
     *
     * @param userId Id of User
     * @return completed challenges as a list
     */

    @RequestMapping(path = "/api/getCompleted/{userId}", method = RequestMethod.GET)
    public List<Challenge> getCompletedChallenges(@PathVariable long userId) {
        return completedService.getCompletedChallenges(userId);
    }

    @RequestMapping(path = "/api/getMyCompleted", method = RequestMethod.GET)
    public List<Challenge> getCompletedChallenges(Principal principal) {
        return completedService.getCompletedChallenges(principal.getName());
    }

}