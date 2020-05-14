package coronhackathon.backend.service;

import coronhackathon.backend.DTO.UserDTO;
import coronhackathon.backend.entity.Challenge;
import coronhackathon.backend.entity.HasCompleted;
import coronhackathon.backend.entity.User;
import coronhackathon.backend.repository.CompletedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Date;

@Service
public class CompletedService {
    @Autowired
    private CompletedRepository completedRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChallengeService challengeService;

    public long getNumberOfCompletedChallenges(long userId){
        return getCompletedChallenges(userId).size();
    }
    
    public List<Challenge> getCompletedChallenges(long userId) {
        List<Challenge> l = new ArrayList<>();
        User u = userService.getUser(userId);
        List<HasCompleted> lhc = completedRepository.findByUser(u);
        for (HasCompleted hc : lhc) {
            l.add(hc.getChallenge());
        }
        return l;
    }

    public List<Challenge> getCompletedChallenges(String username) {
        List<Challenge> l = new ArrayList<>();
        User u = userService.getUserByUsername(username);
        List<HasCompleted> lhc = completedRepository.findByUser(u);
        for (HasCompleted hc : lhc) {
            l.add(hc.getChallenge());
        }
        return l;
    }

    public List<UserDTO> getCompletersOfChallenge(long challengeId) {
        List<UserDTO> l = new ArrayList<>();
        Challenge c = challengeService.getChallenge(challengeId);
        for (HasCompleted hc : completedRepository.findByChallenge(c)) {
            l.add(new UserDTO(hc.getUser()));
        }
        return l;
    }

    /** Add a completed Challenge
     *
     * @param imgBase64 the image encoded in a base64 String
     * @param imgFormat jpg, png or jpeg
     * @return success message
     * @throws IOException
     */
    public String addCompletedChallenge(String username, long challengeId, String commentary, String imgBase64, String imgFormat)
    throws IOException {
        User user = userService.getUserByUsername(username);
        Challenge challenge = challengeService.getChallenge(challengeId);
        HasCompleted hc;
        if(completedRepository.findByUserAndChallenge(user, challenge).isPresent())
            hc = completedRepository.findByUserAndChallenge(user, challenge).get();
        else {
            hc = new HasCompleted();
        }
        hc.setChallenge(challenge);
        hc.setUser(user);
        hc.setCommentary(commentary.trim());

        if(imgBase64.length() > 0) {

            Long id = userService.getUserByUsername(username).getId();

            String destinationPath = "resources/myCompletedImage/hasCompleted_"
                    + Long.toString(id)
                    + "_"
                    + Long.toString(challengeId) + "."+ZonedDateTime
                    .now(ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern("uuuu.MM.dd.HH.mm.ss")) + "."+imgFormat;

            byte[] byteImg = Base64.getDecoder().decode(imgBase64);
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(byteImg));
            ImageIO.write(img, "jpg", new File("src/main/" + destinationPath));

            //Remove previous image
            File previousImage = new File("./src/main/"+hc.getPicture());
            if(previousImage.isFile()){
                previousImage.delete();
            }
            hc.setPicture(destinationPath);
        }


        completedRepository.save(hc);
        return "User " + user.getUsername() + " has completed " + challenge.getName();

    }

    public List<Challenge> getCompletedChallengesByCategory(long userId, long categoryId) {
        List<Challenge> l = new ArrayList<>();
        User u = userService.getUser(userId);
        for (HasCompleted hc : completedRepository.findByUser(u)) {
            if (hc.getChallenge().getCategoryId() == categoryId)
                l.add(hc.getChallenge());
        }
        return l;
    }

    public List<Challenge> getCompletedChallengesByCategory(String username, long categoryId) {
        List<Challenge> l = new ArrayList<>();
        User u = userService.getUserByUsername(username);
        for (HasCompleted hc : completedRepository.findByUser(u)) {
            if (hc.getChallenge().getCategoryId() == categoryId)
                l.add(hc.getChallenge());
        }
        return l;
    }
    
    public List<Challenge> getCompletedChallengesByCategory(long userId, String name) {
        return getCompletedChallengesByCategory(userId, categoryService.getIdFromName(name));
    }

    public List<Challenge> getCompletedChallengesByCategory(String username, String name) {
        return getCompletedChallengesByCategory(username, categoryService.getIdFromName(name));
    }

    /**
     * return a list of (user, comment) pairs
     */
    public List<List<String>> getCommentsOfChallenge(long challengeId) {
        Challenge challenge = challengeService.getChallenge(challengeId);

        List<List<String>> userAndComments = new ArrayList<>();
        for (HasCompleted hc : completedRepository.findByChallenge(challenge)) {

            if(hc.getCommentary() != null && hc.getCommentary().length() > 0) {
                List<String> UserAndComment = new ArrayList<String>();
                UserAndComment.add(hc.getUser().getUsername());
                UserAndComment.add(hc.getCommentary());
                userAndComments.add(UserAndComment);
            }
        }

        return userAndComments;
    }

    public List<String> getDataCompleted(String name, long challengeId){
        List<String> l = new ArrayList<>();


        User u = userService.getUserByUsername(name);
        Challenge challenge = challengeService.getChallenge(challengeId);
        Optional<HasCompleted> oh  = completedRepository.findByUserAndChallenge(u,challenge);

        if (!oh.isPresent()) //If the user didn't complete the challenge yet return an empty array
            return l;
        HasCompleted hc = oh.get();
        l.add(hc.getCommentary());
        l.add(hc.getPicture());
        return l;
    }
}
