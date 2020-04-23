package coronhackathon.backend.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class MiscController {

    /**
     * Get an image from path
     *
     * @param path : should start with 'resources/'
     * @return the image data as byte array
     * @throws IOException
     * @use ip:8080/static/image/jpg?<path> where <path> was received from a previous query
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
     * @use ip:8080/static/image/png?<path> where <path> was received from a previous query
     */
    @GetMapping(
            value = "/static/image/png",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody
    byte[] getPNG(@RequestParam String path) throws IOException {
        return Files.readAllBytes(Paths.get("src/main/" + path));
    }


}