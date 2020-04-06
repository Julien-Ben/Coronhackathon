package coronhackathon.backend.controller;

import coronhackathon.backend.entity.Category;
import coronhackathon.backend.entity.Challenge;

import coronhackathon.backend.entity.Tag;
import coronhackathon.backend.entity.User;
import coronhackathon.backend.service.*;
import coronhackathon.backend.service.ChallengeService;
import coronhackathon.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
public class mainController {
    @Autowired
    private UserService userService;
    @Autowired
    private ChallengeService challengeService;
    @Autowired
    private CompletedService completedService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagOfChallengeService tagOfChallengeService;

    //TODO delete this test method when not needed anymore
    @GetMapping("/ping")
    public String ping() {
        return "pong!";
    }

    @RequestMapping(value = "/api/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Principal principal) {
        return principal.getName();
    }

    @RequestMapping(value = "/api/userProfile", method = RequestMethod.GET)
    @ResponseBody
    public Optional<User> currentUserProfile(Principal principal) {
        return userService.getUserByUsername(principal.getName());
    }

    @PostMapping("/register")
    public Optional<User> showRegistrationForm(@RequestParam String username,
                                               @RequestParam String hashPwd,
                                               @RequestParam String hashPwd2) {
        return userService.register(username, hashPwd, hashPwd2);
    }

    //TODO delete this test method when not needed anymore
    @GetMapping("/api/ping")
    public String fancyPing() {
        return "authenticated pong!";
    }

    /* --------------------------------------------*/
    /*------------------USER--------------------- */
    /* --------------------------------------------*/

    /* ---Users ----*/
    @GetMapping("/api/allUsers")

    public List<User> allUsers() {
        return userService.getAllUsers();
    }


    @RequestMapping(path = "/api/getUser/{userId}", method = RequestMethod.GET)
    public Optional<User> getUser(@PathVariable long userId) {
        return userService.getUser(userId);
    }

    @RequestMapping(path = "/api/getUserByName/{username}", method = RequestMethod.GET)
    public Optional<User> getUserByName(@PathVariable String username) {

        return userService.getUserByUsername(username);
    }

    @PostMapping("/api/addUser")
    /**
     * security config modifications were needed to allow post requests. See dedicated file.
     */
    public void addUser(@RequestBody User user) {
        userService.insert(user);
    }

    /* --------------------------------------------*/
    /*----------------COMPLETED------------------- */
    /* --------------------------------------------*/

    /**
     * Marks user and challenge as completed.
     *
     * @param userId      the completer's Id
     * @param challengeId the completed challenge's Id
     * @param commentary  a commentary on the realisation of the challenge
     * @param picture     a picture on the realisation of the challenge
     * @return a verification message
     */
    @PostMapping("/api/completeChallenge")
    public String completeChallenge(@RequestParam long userId, @RequestParam long challengeId,
                                    @RequestParam String commentary, @RequestParam String picture) {
        return completedService.addCompletedChallenge(userId, challengeId, commentary, picture);
    }

    @PostMapping("/api/completeMyChallenge")
    public String completeChallenge(Principal principal, @RequestParam long challengeId,
                                    @RequestParam String commentary, @RequestParam String picture) {
        return completedService.addCompletedChallenge(principal.getName(), challengeId, commentary, picture);
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


    /**
     * Retrieve all users that complete challenge Challenge
     *
     * @param challengeId the id of Challenge
     * @return completers as a list
     */
    @RequestMapping(path = "/api/getCompleters/{challengeId}", method = RequestMethod.GET)

    public List<User> getCompletersOfChallenge(@PathVariable long challengeId) {
        return completedService.getCompletersOfChallenge(challengeId);
    }

    /**
     * Return a list with all the comment on a specified challenge
     *
     * @param challengeId the id of a challenge
     * @return all the comment of a given challenge
     */
    @RequestMapping(path = "/api/getCommentsOfChallenge/{challengeId}", method = RequestMethod.GET)
    public List<String> getCommentsOfChallenge(@PathVariable long challengeId) {
        return completedService.getCommentsOfChallenge(challengeId);
    }

    /* --------------------------------------------*/
    /*----------------CHALLENGE------------------- */
    /* --------------------------------------------*/

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
     * Returns the number of challenges
     *
     * @return the number of challenges
     */
    @GetMapping("/api/numberOfChallenges")
    public long numberOfChallenges() {
        return challengeService.numberOfChallenges();
    }

    /**
     * Add a challenge given as argument to the database
     *
     * @param challenge a challenge we want to add
     */
    @PostMapping(path = "/api/addChallenge")
    public void addChallenge(@RequestBody Challenge challenge) {
        challengeService.addChallenge(challenge);
    }


    /* --------------------------------------------*/
    /*-----------------Category------------------- */
    /* --------------------------------------------*/

    /**
     * @param id of the category
     * @return the category corresponding to the id
     */
    @RequestMapping(path = "/api/getCategory/{id}", method = RequestMethod.GET)
    public Optional<Category> getCategory(@PathVariable long id) {
        return categoryService.getCategory(id);
    }

    /**
     * @param name of the category
     * @return the category corresponding to the name
     */
    @RequestMapping(path = "/api/getCategoryByName/{name}", method = RequestMethod.GET)
    public Optional<Category> getCategoryByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name);
    }

    /**
     * Adds a category to the database
     *
     * @param category
     * @return
     */
    @PostMapping("/api/addCategory")
    public void addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
    }

    /**
     * Returns a list with all the categories
     *
     * @param
     * @return a list with all the categories
     */
    @GetMapping("/api/allCategories")
    public List<Category> allCategories() {
        return categoryService.allCategories();
    }

    /**
     * Returns the number of categories
     *
     * @param
     * @return a long, the number of categories
     */
    @GetMapping("/api/numberOfCategories")
    public long numberOfCategories() {
        return categoryService.numberOfCategories();
    }


    /**
     * Returns all challenges completed by User in a certain category
     *
     * @param userId     Id of User
     * @param categoryId category of the challenges completed by the user
     * @return completed challenges of a certain category as a list
     */
    @RequestMapping(path = "/api/getCompletedByCat/{userId}/{categoryId}", method = RequestMethod.GET)
    public List<Challenge> getCompletedChallengesByCategory(@PathVariable long userId, @PathVariable long categoryId) {
        return completedService.getCompletedChallengesByCategory(userId, categoryId);
    }

    @RequestMapping(path = "/api/getMyCompletedByCat/{categoryId}", method = RequestMethod.GET)
    public List<Challenge> getCompletedChallengesByCategory(Principal principal, @PathVariable long categoryId) {
        return completedService.getCompletedChallengesByCategory(principal.getName(), categoryId);
    }

    /**
     * Returns all challenges completed by User in a certain category
     *
     * @param userId Id of User
     * @param name   category of the challenges completed by the user
     * @return completed challenges of a certain category as a list
     */
    @RequestMapping(path = "/api/getCompletedByCatName/{userId}/{name}", method = RequestMethod.GET)
    public List<Challenge> getCompletedChallengesByCategory(@PathVariable long userId, @PathVariable String name) {
        return completedService.getCompletedChallengesByCategory(userId, name);
    }

    @RequestMapping(path = "/api/getMyCompletedByCatName/{name}", method = RequestMethod.GET)
    public List<Challenge> getCompletedChallengesByCategory(Principal principal, @PathVariable String name) {
        return completedService.getCompletedChallengesByCategory(principal.getName(), name);
    }



    /* --------------------------------------------*/
    /*-------------------TAG---------------------- */
    /* --------------------------------------------*/

    /**
     * Add a tag given as argument to the database
     *
     * @param tag a tag we want to add
     */
    @PostMapping("/api/addTag")
    public void addTag(@RequestBody Tag tag) {
        tagService.addTag(tag);
    }

    /**
     * Return a tag with a given Id
     *
     * @param tagId the id of a tag
     * @return the tag with this associated Id
     */
    @RequestMapping(path = "/api/getTag/{tagId}", method = RequestMethod.GET)
    public Optional<Tag> getTag(@PathVariable long tagId) {
        return tagService.getTag(tagId);
    }

    /**
     * Return a tag with a given name
     *
     * @param tagName the name of the tag
     * @return the tag that has a given name
     */
    @RequestMapping(path = "/api/getTagByName/{tagName}", method = RequestMethod.GET)
    public Optional<Tag> getTagByName(@PathVariable String tagName) {
        return tagService.getTagByName(tagName);
    }


    /**
     * Returns a list with all the tags
     *
     * @param
     * @return a list with all the tags
     */
    @GetMapping("/api/allTags")
    public List<Tag> allTags() {
        return tagService.allTags();
    }


    /* --------------------------------------------*/
    /*------------Tag of Challenge---------------- */
    /* --------------------------------------------*/

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

    /**
     * Retrieve all the tag that has a Challenge
     *
     * @param challengeId the id of the challenge of interest
     * @return a list of all the tags that are linked to this challenge
     */
    @RequestMapping(path = "/api/getTagsOfChallenge/{challengeId}", method = RequestMethod.GET)
    public List<Tag> getTagsOfChallenge(@PathVariable long challengeId) {
        return tagOfChallengeService.getTagsOfChallenge(challengeId);
    }

    /**
     * Add a tag to a particular challenge
     *
     * @param tagId       the tag we want to link
     * @param challengeId the challenge we want to link to
     * @return a string if the link was done
     */
    @PostMapping("/api/addTagToChallenge")
    public String addTagToChallenge(@RequestParam long tagId, @RequestParam long challengeId) {
        return tagOfChallengeService.addTagToChallenge(tagId, challengeId);
    }

    /* --------------------------------------------*/
    /*-------------IMAGE REQUESTS----------------- */
    /* --------------------------------------------*/

    // TODO remove this test method when not needed anymore
    @GetMapping(
            value = "/banana",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody
    byte[] getImageWithMediaType() throws IOException {
        String path = "src/main/resources/banana.jpg";
        return Files.readAllBytes(Paths.get(path));
    }

    /**
     * Get an image from path
     *
     * @param path : should start with 'resources/'
     * @return the image data as byte array
     * @throws IOException
     * @use ip:8080/api/image/jpg?<path> where <path> was received from a previous query
     */
    @GetMapping(
            value = "/static/image/jpg",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody
    byte[] getJPG(@RequestParam String path) throws IOException {
        return Files.readAllBytes(Paths.get("src/main/" + path));
    }

    /**
     * Get an image from path
     *
     * @param path : should start with 'resources/'
     * @return the image data as byte array
     * @throws IOException
     * @use ip:8080/api/image/png?<path> where <path> was received from a previous query
     */
    @GetMapping(
            value = "/static/image/png",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody
    byte[] getPNG(@RequestParam String path) throws IOException {
        return Files.readAllBytes(Paths.get("src/main/" + path));
    }


    /*
    TODO remove when unecessary
    use it as test method
     */
    @PostMapping(
            value = "/uploadImage",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody
    String uploadImageTest(@RequestBody byte[] imgData) throws IOException {
        String destinationPath = "src/main/resources/upload.jpg";

        BufferedImage img = ImageIO.read(new ByteArrayInputStream(imgData));
        ImageIO.write(img, "jpg", new File(destinationPath));

        return destinationPath;
    }

    // TODO check if path argument like this can contain "/"

    /**
     * Receive a JPG image
     *
     * @param imgData: the uploaded image as byte array. see https://www.tutorialspoint.com/How-to-convert-Image-to-Byte-Array-in-java
     * @param path     the path and name without extension
     * @return the path to find the image
     * @throws IOException
     */
    @RequestMapping(
            value = "/api/uploadImage/jpg/{path}",
            method = RequestMethod.POST,
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody
    String uploadJPGImage(
            @RequestBody byte[] imgData,
            @PathVariable("path") String path) throws IOException {
        String destinationPath = "resources/misc/" + path + ".jpg";

        BufferedImage img = ImageIO.read(new ByteArrayInputStream(imgData));
        ImageIO.write(img, "jpg", new File("src/main/" + destinationPath));
        return destinationPath;
    }

    /**
     * Receive a PNG image
     *
     * @param imgData: the uploaded image as byte array. see https://www.tutorialspoint.com/How-to-convert-Image-to-Byte-Array-in-java
     * @param path     the path and name of the image without extension
     * @return the path to find the image
     * @throws IOException
     */
    @RequestMapping(
            value = "/api/uploadImage/png/{path}",
            method = RequestMethod.POST,
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody
    String uploadPNGImage(
            @RequestBody byte[] imgData,
            @PathVariable("path") String path) throws IOException {
        String destinationPath = "resources/misc/" + path + ".jpg";

        BufferedImage img = ImageIO.read(new ByteArrayInputStream(imgData));
        ImageIO.write(img, "jpg", new File("src/main/" + destinationPath));
        return destinationPath;
    }

    /**
     * Receive a JPG image illustrating a completion, save it and set path in hasCompleted entity
     *
     * @param imgData:     the uploaded image as byte array. see https://www.tutorialspoint.com/How-to-convert-Image-to-Byte-Array-in-java
     * @param userId:      the id of hasCompleted entity received from a previous query
     * @param challengeId: the id of hasCompleted entity received from a previous query
     * @return the path to find the image
     * @throws IOException
     */
    @RequestMapping(
            value = "/api/uploadCompletedImage/jpg/{userId}-{challengeId}",
            method = RequestMethod.POST,
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody
    String uploadCompletedJPGImage(
            @RequestBody byte[] imgData,
            @PathVariable("userId") long userId,
            @PathVariable("challengeId") long challengeId) throws IOException {
        String destinationPath = "resources/completedImage/hasCompleted_"
                + Long.toString(userId)
                + "_"
                + Long.toString(challengeId) + ".jpg";

        BufferedImage img = ImageIO.read(new ByteArrayInputStream(imgData));
        ImageIO.write(img, "jpg", new File("src/main/" + destinationPath));
        completedService.setPath(userId, challengeId, destinationPath);
        return destinationPath;
    }

    /**
     * Receive a PNG image illustrating a completion, save it and set path in hasCompleted entity
     *
     * @param imgData:     the uploaded image as byte array. see https://www.tutorialspoint.com/How-to-convert-Image-to-Byte-Array-in-java
     * @param userId:      the id of hasCompleted entity received from a previous query
     * @param challengeId: the id of hasCompleted entity received from a previous query
     * @return the path to find the image
     * @throws IOException
     */
    @RequestMapping(
            value = "/api/uploadCompletedImage/png/{userId}-{challengeId}",
            method = RequestMethod.POST,
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody
    String uploadCompletedPNGImage(
            @RequestBody byte[] imgData,
            @PathVariable("userId") long userId,
            @PathVariable("challengeId") long challengeId) throws IOException {
        String destinationPath = "resources/completedImage/hasCompleted_user"
                + Long.toString(userId)
                + "_challenge"
                + Long.toString(challengeId) + ".png";

        BufferedImage img = ImageIO.read(new ByteArrayInputStream(imgData));
        ImageIO.write(img, "png", new File("src/main/" + destinationPath));
        completedService.setPath(userId, challengeId, destinationPath);
        return destinationPath;
    }
}