package coronhackathon.backend.controller;

import coronhackathon.backend.entity.User;
import coronhackathon.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class CompleteController {
    @Autowired
    private CompletedService completedService;

    @Autowired
    private UserService userService;

    /**
     * Marks user and challenge as completed.
     *
     * @param challengeId the completed challenge's Id
     * @param commentary  a commentary on the realisation of the challenge
     * @param imgBase64   image encoded in a base64 String
     * @param imgFormat jpg, png, jpeg...
     * @return a verification message
     */
    @PostMapping("/api/completeMyChallenge")
    public String completeChallenge(Principal principal, @RequestParam long challengeId,
                                    @RequestParam String commentary, @RequestParam String imgBase64, @RequestParam String imgFormat)
                                    throws IOException{
        return completedService.addCompletedChallenge(principal.getName(), challengeId, commentary, imgBase64, imgFormat);
    }


    /**
     * Returns the comment and the picture of a completed challenge if completed, otherwise empty strings
     * @param challengeId the id of the category
     * @return List of string containing the if the challenge is validated, the comment and the image
     */
    @RequestMapping(path = "/api/getDataCompleted/{challengeId}", method = RequestMethod.GET)
    public List<String> getDataCompleted(Principal principal, @PathVariable long challengeId) {
        return completedService.getDataCompleted(principal.getName(),challengeId);
    }

    /**
     * Returns the comment and the picture of a completed challenge if completed, otherwise empty strings
     * @param userId the id of the user
     * @param challengeId the id of the category
     * @return List of string containing the if the challenge is validated, the comment and the image
     */
    @RequestMapping(path = "/api/getDataCompleted/{userId}/{challengeId}", method = RequestMethod.GET)
    public List<String> getDataCompleted(@PathVariable long userId, @PathVariable long challengeId) {
        Optional<User> ou = userService.getUser(userId);
        if(!ou.isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with user id "+userId+" unknown");

        return completedService.getDataCompleted(ou.get().getUsername(),challengeId);
    }

}
