package coronhackathon.backend.service;

import coronhackathon.backend.entity.Challenge;
import coronhackathon.backend.entity.HasCompleted;
import coronhackathon.backend.entity.User;
import coronhackathon.backend.repository.ChallengeRepository;
import coronhackathon.backend.repository.CompletedRepository;
import coronhackathon.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompletedService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChallengeRepository challengeRepository;
    @Autowired
    private CompletedRepository completedRepository;
    @Autowired
    private CategoryService categoryService;



    public List<Challenge> getCompletedChallenges(long userId) {
        List<Challenge> l = new ArrayList<Challenge>();
        Optional<User> ou = userRepository.findById(userId);
        if(!ou.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user with id : "+ userId+" not found");
        List<HasCompleted> lhc = completedRepository.findByUser(ou.get());
        for(HasCompleted hc : lhc){
            l.add(hc.getChallenge());
        }
        return l;
    }

    public List<User> getCompletersOfChallenge(long challengeId) {
        List<User> l = new ArrayList<User>();
        Optional<Challenge> oc = challengeRepository.findById(challengeId);
        if(!oc.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "challenge with id : "+challengeId+" not found");
        for(HasCompleted hc : completedRepository.findByChallenge(oc.get())){
            l.add(hc.getUser());
        }
        return l;
    }

    // TODO return type? (python c'est cool pour le packing)
    public String addCompletedChallenge(long userId, long challengeId, String commentary, String picture){
        Optional<User> ou = userRepository.findById(userId);
        Optional<Challenge> oc = challengeRepository.findById(challengeId);
        if(!ou.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user with id : "+ userId+" not found");
        if( !oc.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "challenge with id : "+challengeId+" not found");

        User user = ou.get();
        Challenge challenge = oc.get();
        HasCompleted hc = new HasCompleted();
        hc.setChallenge(challenge);
        hc.setUser(user);
        hc.setCommentary(commentary);
        hc.setPicture(picture);
        completedRepository.save(hc);
        return "User " + user.getUsername() + " has completed " + challenge.getName();
    }

    public List<Challenge> getCompletedChallengesByCategory(long userId, long categoryId) {
        List<Challenge> l = new ArrayList<>();
        Optional<User> ou = userRepository.findById(userId);
        if(!ou.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user with id : "+ userId+" not found");

        for(HasCompleted hc : completedRepository.findByUser(ou.get())){
            if(hc.getChallenge().getCategoryId() == categoryId)
            l.add(hc.getChallenge());
        }
        return l;
    }

    public List<Challenge> getCompletedChallengesByCategory(long userId, String name) {
        return getCompletedChallengesByCategory(userId, categoryService.getIdFromName(name));
    }

    public List<String> getCommentsOfChallenge(long challengeId) {
        List<String> l = new ArrayList<>();
        Optional<Challenge> oc = challengeRepository.findById(challengeId);
        if(!oc.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "challenge with id : "+challengeId+" not found");
        for(HasCompleted hc : completedRepository.findByChallenge(oc.get())){
            l.add(hc.getCommentary());
        }
        return l;
    }
}
