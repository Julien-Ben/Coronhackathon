package coronhackathon.backend.controller;

import coronhackathon.backend.DTO.UserDTO;
import coronhackathon.backend.entity.Challenge;

import coronhackathon.backend.entity.User;
import coronhackathon.backend.service.*;
import coronhackathon.backend.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class ChallengeController {
    @Autowired
    private ChallengeService challengeService;
    @Autowired
    private CompletedService completedService;
    @Autowired
    private TagOfChallengeService tagOfChallengeService;

    /**
     * Retrieve all users that complete challenge Challenge
     *
     * @param challengeId the id of Challenge
     * @return completers as a list
     */
    @RequestMapping(path = "/api/getCompleters/{challengeId}", method = RequestMethod.GET)
    public List<UserDTO> getCompletersOfChallenge(@PathVariable long challengeId) {
        return completedService.getCompletersOfChallenge(challengeId);
    }

    /**
     * Return a list with all the comment on a specified challenge
     *
     * @param challengeId the id of a challenge
     * @return a list of (user, comment) pairs
     */
    @RequestMapping(path = "/api/getCommentsOfChallenge/{challengeId}", method = RequestMethod.GET)
    public List<List<String>> getCommentsOfChallenge(@PathVariable long challengeId) {
        return completedService.getCommentsOfChallenge(challengeId);
    }

    /**
     * Returns all the challenges stored in the database
     * curl -X GET localhost:8080/api/allChallenges
     *
     * @return a list that contains all the challenges stored in the database
     */
    @GetMapping("/api/allChallenges")
    public List<Challenge> allChallenges() {
        return challengeService.getAllChallenges();
    }

    /**
     * Returns all the challenges stored in the database
     *
     * @return a list that contains all the challenges stored in the database
     */
    @GetMapping("/api/nineChallenges")
    public List<Challenge> nineChallenges() {
        return challengeService.getNineChallenges();
    }

    /**
     * Returns nine of the challenges stored in the database and if they are completed or not
     *
     * @return a list of nine challenges and a list of booleans (completed by the logged user or not)
     */
    @RequestMapping(path = "/api/nineChallengesBool", method = RequestMethod.GET)
    public Map<String, Object> nineChallengesBool(Principal principal) {
        HashMap<String, Object> map = new HashMap<>();
        List<Challenge> lc = challengeService.getNineChallenges();
        List<Boolean> lb =challengeService.getNineBoolean(principal.getName(), lc);
        map.put("Challenge", lc);
        map.put("Completed", lb);
        return map;
    }

    /**
     * Returns an Optional that contains a challenge with a specified id if it exists
     * otherwise returns an empty Optional
     *
     * @param challengeId the id of a challenge
     * @return the challenge with the specified id
     */
    @RequestMapping(path = "/api/getChallenge/{challengeId}", method = RequestMethod.GET)
    public Optional<Challenge> getChallenge(@PathVariable long challengeId) {
        return challengeService.getChallenge(challengeId);
    }

    /**
     * Returns the number of challenges of a given category
     *
     * @param categoryId the name of the category
     * @return the number of challenges in the category
     */
    @RequestMapping(path = "/api/numberOfChallengesByCategory/{categoryId}", method = RequestMethod.GET)
    public Long numberOfChallengesOfCategory(@PathVariable long categoryId) {
        return challengeService.numberOfChallengesByCategoryId(categoryId);
    }

    /**
     * Returns the number of challenges of a given category
     *
     * @param category the name of the category
     * @return the number of challenges in the category
     */
    @RequestMapping(path = "/api/numberOfChallengesByCategoryName/{category}", method = RequestMethod.GET)
    public Long numberOfChallengesByCategoryName(@PathVariable String category) {
        return challengeService.numberOfChallengesByCategoryName(category);
    }

    /**
     * Returns an Optional that contains a challenge with a specified name if it exists
     * otherwise returns an empty Optional
     *
     * @param name the name of a challenge
     * @return the challenge with the specified name
     */
    @RequestMapping(path = "/api/getChallengeByName/{name}", method = RequestMethod.GET)
    public Optional<Challenge> getChallengeByName(@PathVariable String name) {
        return challengeService.getChallengeByName(name);
    }

    /**
     * Returns a list with all the challenges that are in a category
     *
     * @param categoryId a category of challenges
     * @return a list with all the challenge of a category
     */
    @RequestMapping(path = "/api/getChallengeByCategory/{categoryId}", method = RequestMethod.GET)
    public List<Challenge> getChallengeByCategory(@PathVariable long categoryId) {
        return challengeService.getChallengeByCategory(categoryId);
    }

    /**
     * Returns a list with all the challenges that are in a category
     *
     * @param name a category of challenges
     * @return a list with all the challenge of a category
     */
    @RequestMapping(path = "/api/getChallengeByCategoryName/{name}", method = RequestMethod.GET)
    public List<Challenge> getChallengeByCategory(@PathVariable String name) {
        return challengeService.getChallengeByCategory(name);
    }

    /**
     * Returns a list with all challenges of a category, with a boolean that indicates if it is validated or not
     * @param categoryId the id of the category
     * @return a map with all the challenges and their corresponding boolean
     */
    @RequestMapping(path = "/api/getChallengeByCategoryBool/{categoryId}", method = RequestMethod.GET)
    public Map<String, Object> getChallengeByCategoryBool(Principal principal, @PathVariable long categoryId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Challenge", challengeService.getChallengeByCategory(categoryId));
        map.put("Completed", challengeService.getChallengeBool(principal.getName(), categoryId));
        return map;
    }

    /**
     * Returns the number of challenges
     *
     * @return the number of challenges
     */
    @GetMapping("/api/numberOfChallenges")
    public long numberOfChallenges() {
        return challengeService.numberOfChallenges();
    }

    /**
     * Retrieve all Challenges that have the tag Tag
     *
     * @param tagId the id of the tag
     * @return challenges as a list
     */
    @RequestMapping(path = "/api/getChallengesByTag/{tagId}", method = RequestMethod.GET)
    public List<Challenge> getChallengesByTag(@PathVariable long tagId) {
        return tagOfChallengeService.getChallengesOfTag(tagId);
    }
}