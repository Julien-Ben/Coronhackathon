package coronhackathon.backend.controller;

import coronhackathon.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.io.IOException;
import java.util.List;

@RestController
public class CompleteController {
    @Autowired
    private CompletedService completedService;

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

}
