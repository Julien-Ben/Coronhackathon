package coronhackathon.backend.controller;

import coronhackathon.backend.DTO.UserDTO;
import coronhackathon.backend.entity.Challenge;

import coronhackathon.backend.entity.User;
import coronhackathon.backend.service.*;
import coronhackathon.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CompletedService completedService;

    @PostMapping("/register")
    public ResponseEntity<String> showRegistrationForm(@RequestParam String username,
                                                       @RequestParam String hashPwd,
                                                       @RequestParam String hashPwd2) {
        return new ResponseEntity<>(userService.register(username, hashPwd, hashPwd2), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/api/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Principal principal) {
        return principal.getName();
    }

    @RequestMapping(value = "/api/userProfile", method = RequestMethod.GET)
    @ResponseBody
    public UserDTO currentUserProfile(Principal principal) {
        return new UserDTO(userService.getUserByUsername(principal.getName()));
    }

    @GetMapping("/api/allUsers")
    public List<UserDTO> allUsers() {
        List<UserDTO> uDto = new ArrayList<>();
        for(User u  : userService.getAllUsers())
            uDto.add(new UserDTO(u));
        return uDto;
    }


    @RequestMapping(path = "/api/getUser/{userId}", method = RequestMethod.GET)
    public UserDTO getUser(@PathVariable long userId) {
        return new UserDTO(userService.getUser(userId));
    }

    @RequestMapping(path = "/api/getUserByName/{username}", method = RequestMethod.GET)
    public UserDTO getUserByName(@PathVariable String username) {
        return new UserDTO(userService.getUserByUsername(username));
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