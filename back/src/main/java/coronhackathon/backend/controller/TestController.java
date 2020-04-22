package coronhackathon.backend.controller;

import coronhackathon.backend.entity.Category;
import coronhackathon.backend.entity.Challenge;

import coronhackathon.backend.service.*;
import coronhackathon.backend.service.ChallengeService;
import coronhackathon.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class TestController {
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

    //TODO delete this test method when not needed anymore
    @GetMapping("/api/ping")
    public String fancyPing() {
        return "authenticated pong!";
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

}