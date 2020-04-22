package coronhackathon.backend.controller;

import coronhackathon.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

@RestController
public class CompleteController {
    @Autowired
    private CompletedService completedService;

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
